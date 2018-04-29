package com.example.demo.data;

import com.example.demo.testResult.TestResult;
import com.example.demo.utils.driverUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by sondo on 23/04/2018.
 */
public class FillParam extends TestItem {


    @Override
    public void execute(WebDriver driver, Action action, TestResult result) {
        String value = action.getValue();

        WebElement element;
        if(action.getSelectBy().equals("class")){
            element = driverUtils.findElementByClass(driver, action.getName());
        }else {
            element = driverUtils.findElementByAll(driver, action.getName() );
        }

        if (element == null) {
            result.addTestMessage(TestResult.FAIL_MESSAGE, "can not find element with this id " + action.getName());
            result.addFailedCase();
        } else {
            element.sendKeys(value);
            result.addTestMessage(TestResult.PASS_MESSAGE, "element found with this id " + action.getName());
            result.addPassedCase();
        }

        System.out.println("fill param action");

    }
}
