package com.example.demo.testScenarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
interface TestScenarioRepository extends JpaRepository<TestScenario, Long> {
}