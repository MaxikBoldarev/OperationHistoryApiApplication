package ru.netology.boldarev;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import ru.netology.boldarev.DTO.CustomerDTO;
import ru.netology.boldarev.configuration.OperationProperties;
import ru.netology.boldarev.controller.CustomerController;
import ru.netology.boldarev.controller.OperationController;
import ru.netology.boldarev.model.Customer;
import ru.netology.boldarev.model.Operation;
import ru.netology.boldarev.repository.StatementRepository;
import ru.netology.boldarev.repository.StorageCustomerRepository;
import ru.netology.boldarev.repository.StorageOperationRepository;
import ru.netology.boldarev.response.CustomersGetResponse;
import ru.netology.boldarev.service.*;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class OperationHistoryApiApplicationTests {

    private OperationService operationService;
    private CustomerService customerService;
    private AsyncInputOperationService asyncInputOperationService;
    private StatementService statementService;
    private StorageCustomerRepository storageCustomerRepository;
    private IOService ioService;
    private StatementRepository statementRepository;

    private StorageOperationRepository operationRepository;

    private CustomerController customerController;
    private OperationController operationController;
    private OperationProperties operationProperties;

    @BeforeEach
    public void initCustomerController() {
        storageCustomerRepository = new StorageCustomerRepository();
        storageCustomerRepository.add(new Customer(1, "Spring", 25));
        storageCustomerRepository.add(new Customer(2, "Boot", 30));

        operationRepository = new StorageOperationRepository();
        operationRepository.add(new Operation(1, 5000, 25));
        operationRepository.add(new Operation(2, 4000, 30));

        statementRepository = new StatementRepository();
        statementRepository.add(1L, operationRepository.getAll());

        ioService = new IOService(statementRepository, operationRepository, storageCustomerRepository);
        statementService = new StatementService(statementRepository);
        operationProperties = new OperationProperties();
        asyncInputOperationService = new AsyncInputOperationService(operationService, operationProperties);

        customerService = new CustomerService(storageCustomerRepository, ioService);
        operationService = new OperationService(operationRepository, statementService, ioService);

        customerController = new CustomerController(customerService);
        operationController = new OperationController(operationService, customerService, asyncInputOperationService, statementService);
    }

    @AfterEach
    public void finalizeTest() {
        statementRepository = null;
        operationRepository = null;
        storageCustomerRepository = null;
        ioService = null;
        statementService = null;
        operationProperties = null;
        asyncInputOperationService = null;


        customerService = null;
        operationService = null;

        customerController = null;
        operationController = null;
    }

    static Stream<Arguments> argumentsForAddCustomer() {
        return Stream.of(Arguments.of("Ivan", 25),
                Arguments.of("Oleg", 30)
        );
    }

    static Stream<Arguments> argumentsForAddOperation() {
        return Stream.of(Arguments.of(6000, 20),
                Arguments.of(7000, 21)
        );
    }


    @Test
    public void getCustomersTest() {
        CustomersGetResponse customers = customerController.getCustomers();
        List<CustomerDTO> customersAll = customers.getCustomers();
        CustomerDTO customer1 = customersAll.get(0);
        CustomerDTO customer2 = customersAll.get(1);

        assertEquals(1, customer1.getId());
        assertEquals("Spring", customer1.getName());
        assertEquals(2, customer2.getId());
        assertEquals("Boot", customer2.getName());
    }

    @Test
    public void getCustomerTest() {
        Customer customer = customerService.getCustomer(1);
        assertEquals(1, customer.getId());
        assertEquals("Spring", customer.getName());
    }

    @ParameterizedTest
    @MethodSource("argumentsForAddCustomer")
    public void addCustomerTest(String name, int age) {
        long customerId = customerService.countId();
        Customer customer = new Customer(customerId, name, age);

        customerService.add(customer);

        Customer customerTest = customerService.getCustomer(customerId);
        assertEquals(customerId, customerTest.getId());
        assertEquals(name, customerTest.getName());
        assertEquals(age, customerTest.getAge());
    }

    @Test
    public void editCustomerTest() {
        String name = "Ira";
        Customer customer = customerService.getCustomer(1);

        Customer customerEdit = customerService.editCustomer(customer, name);

        assertEquals(1, customerEdit.getId());
        assertEquals(name, customerEdit.getName());
    }

    @Test
    public void deleteCustomerTest() {
        customerService.deleteCustomer(1);
        customerService.getCustomer(1);

        assertNull(customerService.getCustomer(1));
    }

    @Test
    public void getAllOperationTest() {
        List<Operation> operationList = operationController.getAllOperation();
        Operation operation1 = operationList.get(0);
        Operation operation2 = operationList.get(1);

        assertEquals(1, operation1.getId());
        assertEquals(5000, operation1.getAmount());
        assertEquals(25, operation1.getDate());

        assertEquals(2, operation2.getId());
        assertEquals(4000, operation2.getAmount());
        assertEquals(30, operation2.getDate());
    }

    @Test
    public void getOperationByCustomerIdTest() {
        List<Operation> operationList = operationService.getOperationsCustomer(1);
        Operation operation = operationList.get(0);

        assertEquals(1, operation.getId());
        assertEquals(5000, operation.getAmount());
    }

    @ParameterizedTest
    @MethodSource("argumentsForAddOperation")
    public void addOperationTest(int amount, int date) {
        long customerId = 1L;
        operationController.addOperation(customerId, amount, date);

        List<Operation> operationList = operationService.getOperationsCustomer(customerId);
        Operation operationTest1 = operationList.get(2);

        assertEquals(3, operationTest1.getId());
        assertEquals(amount, operationTest1.getAmount());
        assertEquals(date, operationTest1.getDate());
    }

    @Test
    public void deleteOperationTest() {
        long customerId = 1L;
        long operationId = 1L;
        operationService.deleteOperation(customerId, operationId);
        List<Operation> operationList = operationService.getOperationsCustomer(customerId);
        Operation operation = operationList.get(0);
        assertNotEquals(operationId, operation.getId());
    }
}
