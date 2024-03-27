package ru.netology.boldarev.controller;

import org.springframework.web.bind.annotation.*;
import ru.netology.boldarev.model.Customer;
import ru.netology.boldarev.model.Operation;
import ru.netology.boldarev.service.AsyncInputOperationService;
import ru.netology.boldarev.service.CustomerService;
import ru.netology.boldarev.service.OperationService;
import ru.netology.boldarev.service.StatementService;

import java.util.List;

@RestController
@RequestMapping(path = "api/operations")
public class OperationController {

    private final OperationService operationService;
    private final CustomerService customerService;
    private final AsyncInputOperationService asyncInputOperationService;
    private final StatementService statementService;

    public OperationController(OperationService operationService, CustomerService customerService, AsyncInputOperationService asyncInputOperationService, StatementService statementService) {
        this.operationService = operationService;
        this.customerService = customerService;
        this.asyncInputOperationService = asyncInputOperationService;
        this.statementService = statementService;
    }

    @GetMapping
    public List<Operation> getAllOperation() {
        return operationService.getAll();
    }

    @GetMapping("/{id}")
    public List<Operation> getOperationByCustomerId(@PathVariable long id) {
        return operationService.getOperationsCustomer(id);
    }

    @PostMapping("/post/{id}/{amount}/{date}")
    public String addOperation(@PathVariable long id, @PathVariable double amount, @PathVariable int date) {
        Customer customer = customerService.getCustomer(id);
        if (customer.getId() == id) {
            long operationId = operationService.countId();
            Operation operation = new Operation(operationId, amount, date);
            statementService.addId(id, operation);
            asyncInputOperationService.offerOperation(operation);
            return operation + " added";
        }
        return "Customer id not found";
    }

    @DeleteMapping("/delete/{customerId}/{operationId}")
    public String deleteOperation(@PathVariable long customerId, @PathVariable long operationId) {
        Operation operation = operationService.deleteOperation(customerId, operationId);
        return operation + " is delete";
    }
}
