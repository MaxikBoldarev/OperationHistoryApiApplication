package ru.netology.boldarev.DTO;

import java.util.Objects;

public class CustomerDTO {
    private final long id;
    private final String name;

    public CustomerDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDTO that = (CustomerDTO) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}