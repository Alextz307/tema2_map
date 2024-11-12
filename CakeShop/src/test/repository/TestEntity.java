package test.repository;

import main.domain.Identifiable;

import java.io.Serializable;

public class TestEntity implements Identifiable<Long>, Serializable {
    private Long id;
    private String name;

    public TestEntity(String name) {
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
}