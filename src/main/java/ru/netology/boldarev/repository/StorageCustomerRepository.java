package ru.netology.boldarev.repository;

import org.springframework.stereotype.Repository;
import ru.netology.boldarev.model.Customer;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StorageCustomerRepository {
    private List<Customer> list = new ArrayList<>();

    public void add(Customer customer) {
        list.add(customer);
    }

    public List<Customer> getAll() {
        return list;
    }

    public void setList(List<Customer> list) {
        this.list = list;
    }
}
