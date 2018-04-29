package com.example.demo.testScenarios;

import com.example.demo.database.TestDatabase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class TestScenarioCommandLineRunner implements CommandLineRunner {

    private final TestScenarioRepository repository;

    public TestScenarioCommandLineRunner(TestScenarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) throws Exception {



        Stream.of("A", "B", "C", "D",
                "E", "F", "G").forEach(name ->
                repository.save(new TestScenario(name))
        );
        repository.findAll().forEach(System.out::println);
    }
}