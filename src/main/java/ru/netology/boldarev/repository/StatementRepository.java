package ru.netology.boldarev.repository;

import org.springframework.stereotype.Repository;
import ru.netology.boldarev.model.Operation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StatementRepository {

    Map<Long, List<Operation>> statements = new HashMap<>();

    public void add(Long l, List<Operation> list) {
        statements.put(l, list);
    }

    public Map<Long, List<Operation>> getStatements() {
        return statements;
    }

    public void setStatements(Map<Long, List<Operation>> statements) {
        this.statements = statements;
    }
}
