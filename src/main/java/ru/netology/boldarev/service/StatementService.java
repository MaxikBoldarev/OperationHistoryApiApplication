package ru.netology.boldarev.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ru.netology.boldarev.model.Operation;
import ru.netology.boldarev.repository.StatementRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StatementService {
    private final StatementRepository statementRepository;

    public StatementService(StatementRepository statementRepository) {
        this.statementRepository = statementRepository;
    }

    public void addId(long customerId, Operation operation) {
        Map<Long, List<Operation>> statements = statementRepository.getStatements();
        if (statements.containsKey(customerId)) {
            List<Operation> list = statements.get(customerId);
            list.add(operation);
            statements.put(customerId, list);
            statementRepository.setStatements(statements);
        } else {
            List<Operation> list = new ArrayList<>();
            list.add(operation);
            statementRepository.add(customerId, list);
        }
    }

    public List<Operation> operationByCustomer(long customerId) {
        Map<Long, List<Operation>> statements = statementRepository.getStatements();
        return statements.get(customerId);
    }

    public Map<Long, List<Operation>> getAllStatement() {
        return statementRepository.getStatements();
    }

    public void setStatementRepository(Map<Long, List<Operation>> listMap) {
        statementRepository.setStatements(listMap);
    }
}

