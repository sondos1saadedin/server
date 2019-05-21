package com.example.demo.selenium;

import com.example.demo.data.*;
import com.example.demo.database.BaseDatabase;
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

        try {
            driver.navigate().to(scenario.getUrl());
            testResult.addTestMessage(TestResult.PASS_MESSAGE, "Correct Url " + scenario.getUrl());
            testResult.addPassedCase();
        } catch (Exception e) {
            testResult.addTestMessage(TestResult.FAIL_MESSAGE, "Can not find this url " + scenario.getUrl());
            testResult.addFailedCase();
        }
        for (Action action : scenario.getData()) {
            if (!action.getVisible().equals("YES")) {
                continue;
            }
            if (action.getAction().equals(Constants.ACTION_CLICK_LINK)) {
                ClickLink clickLink = new ClickLink();
                clickLink.execute(scenario, driver, action, testResult);
            }
            if (action.getAction().equals(Constants.ACTION_SUBMIT_FORM)) {
                SubmitForm submitForm = new SubmitForm();
                submitForm.execute(scenario, driver, action, testResult);
            }
            if (action.getAction().equals(Constants.ACTION_FILL_PARAM)) {
                FillParam fillParam = new FillParam();
                fillParam.execute(scenario, driver, action, testResult);
            }
            if (action.getAction().equals(Constants.ACTION_CHECK_TEXT)) {
                CheckText checkText = new CheckText();
                checkText.execute(scenario, driver, action, testResult);
            }
            if (action.getAction().equals(Constants.ACTION_NAVIGATE_PAGE)) {
                NavigatePage navigatePage = new NavigatePage();
                navigatePage.execute(scenario, driver, action, testResult);
            }
        }

        scenario.setTestResult(testResult);
        System.out.println(testResult.getPasses());
        BaseDatabase.getmDatabase().getReference("ScenarioUsers").child(userId)
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
