package ru.netology.boldarev.serializable;

import ru.netology.boldarev.model.Customer;
import ru.netology.boldarev.model.Operation;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class OperationData implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final List<Operation> operationList;
    private final List<Customer> customerList;
    private final Map<Long, List<Operation>> statementsMap;

    public OperationData(List<Operation> operationList, List<Customer> customerList, Map<Long, List<Operation>> statementsMap) {
        this.customerList = customerList;
        this.operationList = operationList;
        this.statementsMap = statementsMap;
    }


    public List<Operation> getOperations() {
        return operationList;
    }

    public List<Customer> getCustomers() {
        return customerList;
    }

    public Map<Long, List<Operation>> getStatement() {
        return statementsMap;
    }

    @Override
    public String toString() {
        return "Customer: " + customerList + '\n' +
                "Operations: " + operationList + '\n' +
                "Statement: " + statementsMap;
    }
}