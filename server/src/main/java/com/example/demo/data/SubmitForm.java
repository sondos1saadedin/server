package com.example.demo.data;

import com.example.demo.testResult.TestResult;
import com.example.demo.utils.driverUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by sondo on 23/04/2018.
 */
public class SubmitForm extends TestItem {
    @Override
    public void execute(Scenario scenario, WebDriver driver, Action action, TestResult result) {

        String formId = action.getName();
        WebElement element;
        if (action.getSelectBy().equals("class")) {
            element = driverUtils.findElementByClass(driver, formId);
        } else {
            element = driverUtils.findElementByAll(driver, formId);
        }
        if (element == null) {
            result.addTestMessage(TestResult.FAIL_MESSAGE, "can not find a form with this id " + formId);
            result.addFailedCase();
        } else {
            result.addTestMessage(TestResult.PASS_MESSAGE, "form found with this id " + formId);
            result.addPassedCase();
            element.submit();


        }

        System.out.println("submit form action");
    }
}
