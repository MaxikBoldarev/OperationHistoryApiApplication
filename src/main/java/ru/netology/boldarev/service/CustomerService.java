package ru.netology.boldarev.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ru.netology.boldarev.exception.OperationRuntimeException;
import ru.netology.boldarev.model.Customer;
import ru.netology.boldarev.repository.StorageCustomerRepository;

import java.util.List;

@Service
public class CustomerService {

    private final StorageCustomerRepository storageCustomerRepository;
    private final IOService ioService;

    public CustomerService(StorageCustomerRepository storageCustomerRepository, IOService ioService) {
        this.storageCustomerRepository = storageCustomerRepository;
        this.ioService = ioService;
    }

    private int customerId = 1;

    public int countId() {
        if (customerId == 1) {
            List<Customer> listCustomer = storageCustomerRepository.getAll();
            for (Customer cust : listCustomer) {
                if (cust != null) {
                    customerId++;
                }
            }
            return customerId;
        }
        return 0;
    }

    public void add(Customer customer) throws OperationRuntimeException {
        try {
            storageCustomerRepository.add(customer);
            ioService.serializableFiles();
        } catch (OperationRuntimeException e) {
            System.out.println("Error add customer");
        }
    }

    public List<Customer> getAll() {
        return storageCustomerRepository.getAll();
    }

    public Customer getCustomer(long id) {
        List<Customer> listCustomer = storageCustomerRepository.getAll();
        for (Customer customer : listCustomer) {
            if (id == customer.getId()) {
                return customer;
            }
        }
        System.out.println("Customer not found");
        return null;
    }

    public Customer editCustomer(Customer customer, String name) {
        customer.setName(name);
        ioService.serializableFiles();
        return customer;
    }

    public Customer deleteCustomer(long id) {
        List<Customer> listCustomer = storageCustomerRepository.getAll();
        for (Customer customer : listCustomer) {
            if (id == customer.getId()) {
                listCustomer.remove(customer);
                storageCustomerRepository.setList(listCustomer);
                ioService.serializableFiles();
                return customer;
            }
        }
        System.out.println("Customer not found");
        return null;
    }

    @PostConstruct
    public void initStorageCustomer() {
        storageCustomerRepository.add(new Customer(1, "Spring", 25));
        storageCustomerRepository.add(new Customer(2, "Boot", 30));
    }
}