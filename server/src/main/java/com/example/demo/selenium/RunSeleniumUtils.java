package com.example.demo.selenium;

import com.example.demo.data.*;
import com.example.demo.database.TestDatabase;
import com.example.demo.testResult.TestResult;
import com.example.demo.utils.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by sondo on 04/04/2018.
 */
public class RunSeleniumUtils {

    private static WebDriver driver;


    public void init() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

    }

    public void runTest(Scenario scenario, String userId) {
        TestResult testResult = new TestResult();
        driver.navigate().to(scenario.getUrl());
        for (Action action : scenario.getData()) {
            if (action.getAction().equals(Constants.ACTION_CLICK_LINK)) {
                ClickLink clickLink = new ClickLink();
                clickLink.execute(driver, action, testResult);
            }
            if (action.getAction().equals(Constants.ACTION_SUBMIT_FORM)) {
                SubmitForm submitForm = new SubmitForm();
                submitForm.execute(driver, action, testResult);
            }
            if (action.getAction().equals(Constants.ACTION_FILL_PARAM)) {
                FillParam fillParam = new FillParam();
                fillParam.execute(driver, action, testResult);
            }
        }

        scenario.setTestResult(testResult);
        System.out.println(testResult.getPasses());
        TestDatabase.getmDatabase().getReference("ScenarioUsers").child(userId)
                .child(scenario.getTitle()).child("testResult").setValue(testResult);

    }


    public void execute(Scenario scenario, String userId) {
        this.init();
        this.runTest(scenario, userId);
    }


    public static void main(String[] args) {
        Scenario scenario = new Scenario();

        scenario.setUrl("https://www.google.com");
        RunSeleniumUtils runSeleniumUtils = new RunSeleniumUtils();
        runSeleniumUtils.execute(scenario, "");
    }
}
