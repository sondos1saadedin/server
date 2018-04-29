package com.example.demo.testScenarios;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TestScenario {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public TestScenario(String aseel, int i) {
    }

    public TestScenario(String name) {
        this.name = name;
    }

    public TestScenario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TestScenario{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}