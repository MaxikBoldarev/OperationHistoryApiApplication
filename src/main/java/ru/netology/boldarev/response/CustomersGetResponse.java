package ru.netology.boldarev.response;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import ru.netology.boldarev.DTO.CustomerDTO;
import ru.netology.boldarev.model.Customer;

import java.util.List;
import java.util.Objects;

@Component
public class CustomersGetResponse {

    private final List<CustomerDTO> customers;


    public CustomersGetResponse(List<CustomerDTO> customers) {
        this.customers = customers;
    }


    public List<CustomerDTO> getCustomers() {
        return customers;
    }

    @Override
    public String toString() {
        return "CustomersGetResponse{" +
                "customers=" + customers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomersGetResponse that = (CustomersGetResponse) o;
        return Objects.equals(customers, that.customers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customers);
    }

}
