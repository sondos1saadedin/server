package com.example.demo.data;

import com.example.demo.testResult.TestResult;
import org.openqa.selenium.WebDriver;

/**
 * Created by sondo on 01/05/2018.
 */
public class NavigatePage extends TestItem {
    @Override
    public void execute(Scenario scenario, WebDriver driver, Action action, TestResult result) {
        String url = scenario.getUrl() + action.getValue();
        try {
            driver.get(url);
            result.addTestMessage(TestResult.PASS_MESSAGE, "Correct Url " + url);
            result.addPassedCase();

        } catch (Exception e) {
            result.addTestMessage(TestResult.FAIL_MESSAGE,  "can not find this url " + url);
            result.addFailedCase();
        }
    }
}
