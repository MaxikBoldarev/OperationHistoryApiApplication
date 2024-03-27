package ru.netology.boldarev.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ru.netology.boldarev.model.Customer;
import ru.netology.boldarev.model.Operation;
import ru.netology.boldarev.repository.StatementRepository;
import ru.netology.boldarev.repository.StorageCustomerRepository;
import ru.netology.boldarev.repository.StorageOperationRepository;
import ru.netology.boldarev.serializable.OperationData;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

@Service
public class IOService {
    private static final String PATH = "D:\\save.ser";
    private final Path serializable = Paths.get(PATH);

    private final StatementRepository statementRepository;

    private final StorageOperationRepository operationRepository;

    private final StorageCustomerRepository customerRepository;

    public IOService(StatementRepository statementRepository, StorageOperationRepository operationRepository, StorageCustomerRepository customerRepository) {
        this.statementRepository = statementRepository;
        this.operationRepository = operationRepository;
        this.customerRepository = customerRepository;
    }


    public void serializableFiles() {
        try (FileOutputStream outputStream = new FileOutputStream(PATH)) {
            OperationData operationData = new OperationData(operationRepository.getAll(), customerRepository.getAll(), statementRepository.getStatements());
            System.out.println(operationData);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(operationData);
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    public void existAndSerializable() {
        if (Files.exists(serializable)) {
            try (FileInputStream fileInputStream = new FileInputStream(String.valueOf(serializable));
                 ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                OperationData operationData = (OperationData) objectInputStream.readObject();

                customerRepository.setList(operationData.getCustomers());

                operationRepository.setList(operationData.getOperations());

                statementRepository.setStatements(operationData.getStatement());

                System.out.println(operationData);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Ошибка восстановления данных");
            }
        } else
            System.out.printf("'%s' не сущетсвует%n", serializable);
    }
}

