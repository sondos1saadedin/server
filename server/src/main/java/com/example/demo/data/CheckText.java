package com.example.demo.data;

import com.example.demo.testResult.TestResult;
import com.example.demo.utils.driverUtils;
import org.openqa.selenium.WebDriver;

/**
 * Created by sondo on 24/04/2018.
 */
public class CheckText extends  TestItem {
    @Override
    public void execute(Scenario scenario, WebDriver driver, Action action, TestResult result) {
        boolean testb = driverUtils.verify(driver, action.getValue());
        if (testb) {
            result.addTestMessage(TestResult.PASS_MESSAGE,
                    "Found this word " + action.getValue());
            result.addPassedCase();
        } else {
            result.addTestMessage(TestResult.FAIL_MESSAGE, "can not find this word " + action.getValue());
            result.addFailedCase();
        }
    }
}
