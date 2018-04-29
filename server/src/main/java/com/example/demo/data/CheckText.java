package com.example.demo.data;

import com.example.demo.testResult.TestResult;
import com.example.demo.utils.driverUtils;
import org.openqa.selenium.WebDriver;

/**
 * Created by sondo on 24/04/2018.
 */
public class CheckText extends  TestItem {
    @Override
    public void execute(WebDriver driver, Action action, TestResult result) {
        boolean testb = driverUtils.verify(driver, action.getValue());
//        if (testb) {
//            result.log(LogStatus.PASS, "Found this word " + ((Text) testItem).getWord());
//        } else {
//            test.log(LogStatus.FAIL, "can not find this word " + ((Text) testItem).getWord());
//        }
    }
}
