package com.example.demo.data;

import com.example.demo.testResult.TestResult;
import org.openqa.selenium.WebDriver;

public abstract class TestItem {
    public abstract void execute(WebDriver driver, Action action, TestResult result);
}
