package ru.netology.boldarev.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ru.netology.boldarev.exception.OperationRuntimeException;
import ru.netology.boldarev.model.Operation;
import ru.netology.boldarev.repository.StorageOperationRepository;

import java.util.List;
import java.util.Map;

@Service
public class OperationService {

    private final StorageOperationRepository operationStorageRepository;
    private final StatementService statementService;
    private final IOService ioService;

    public OperationService(StorageOperationRepository operationStorageRepository, StatementService statementService, IOService ioService) {
        this.operationStorageRepository = operationStorageRepository;
        this.statementService = statementService;
        this.ioService = ioService;
    }

    private int operationId = 1;

    public int countId() {
        if (operationId == 1) {
            List<Operation> listOperations = operationStorageRepository.getAll();
            for (Operation oper : listOperations) {
                if (oper != null) {
                    operationId++;
                }
            }
            return operationId;
        }
        return 0;
    }

    public void addOperation(Operation operation) throws OperationRuntimeException {
        try {
            operationStorageRepository.add(operation);
            ioService.serializableFiles();
        } catch (OperationRuntimeException e) {
            System.out.println("Ошибка добавления получателя");
        }
    }

    public List<Operation> getOperationsCustomer(long clientId) {
        return statementService.operationByCustomer(clientId);
    }

    public List<Operation> getAll() {
        return operationStorageRepository.getAll();
    }

    public Operation deleteOperation(long customerId, long operationId) {
        Map<Long, List<Operation>> longListMap = statementService.getAllStatement();
        List<Operation> listOperations = longListMap.get(customerId);
        if (longListMap.get(customerId) != null) {
            for (Operation operation : listOperations) {
                if (operationId == operation.getId()) {
                    listOperations.remove(operation);
                    operationStorageRepository.setList(listOperations);
                    longListMap.put(customerId, listOperations);
                    statementService.setStatementRepository(longListMap);
                    ioService.serializableFiles();
                    return operation;
                }
            }
        }
        return null;
    }
}