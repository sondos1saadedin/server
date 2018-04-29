package com.example.demo.data;

import com.example.demo.testResult.TestResult;
import com.example.demo.utils.driverUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class ClickLink extends TestItem {
    @Override
    public void execute(WebDriver driver, Action action, TestResult result) {
        String textUrl = action.getName();

        WebElement element;
        if(action.getSelectBy().equals("class")){
            element = driverUtils.findElementByClass(driver, textUrl);
        }else {
            element = driverUtils.findElementByAll(driver, textUrl);
        }
        if (element == null) {
            result.addTestMessage(TestResult.FAIL_MESSAGE, "can not find click word  with this id " + textUrl);
            result.addFailedCase();
        } else {

            result.addTestMessage(TestResult.PASS_MESSAGE, "click word was found with this id " + textUrl);
            result.addPassedCase();
            element.click();

        }
        System.out.println("click link action");
    }
}
