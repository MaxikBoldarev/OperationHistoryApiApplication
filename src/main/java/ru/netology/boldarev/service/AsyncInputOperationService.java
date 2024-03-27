package ru.netology.boldarev.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ru.netology.boldarev.configuration.OperationProperties;
import ru.netology.boldarev.model.Operation;

import java.util.LinkedList;
import java.util.Queue;

@Service
public class AsyncInputOperationService {
    private final OperationService operationService;
    private final OperationProperties operationProperties;

    public AsyncInputOperationService(OperationService operationService, OperationProperties operationProperties) {
        this.operationService = operationService;
        this.operationProperties = operationProperties;
    }

    private final Queue<Operation> queue = new LinkedList<>();

    public void offerOperation(Operation operation) {
        queue.offer(operation);
    }

    private void processQueue() {
        while (true) {
            Operation operation = queue.poll();
            if (operation == null) {
                try {
                    System.out.println("Processing wait...");
                    Thread.sleep(operationProperties.getSleepMilliSeconds());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("Processing operation:" + operation);
                operationService.addOperation(operation);
            }
        }
    }

    @PostConstruct
    public void startAsyncOperationProcessing() {
        Thread t = new Thread(this::processQueue);
        t.start();
    }
}