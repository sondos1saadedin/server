package com.example.demo.testResult;

import com.example.demo.data.TestItemMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class TestResult {
    private int passes;
    private int fails;

    private ArrayList<TestItemMessage> testMessages;
    public static final String FAIL_MESSAGE = "Fail";
    public static final String PASS_MESSAGE = "Pass";

    public TestResult() {
        passes = 0;
        fails = 0;
    }

    public void addPassedCase() {
        passes++;
    }


    public void addFailedCase() {
        fails++;
    }


    public int getNumCases() {
        return passes + fails;
    }


    public int getPasses() {
        return passes;
    }

    public void setPasses(int passes) {
        this.passes = passes;
    }

    public int getFails() {
        return fails;
    }

    public void setFails(int fails) {
        this.fails = fails;
    }


    public void addTestMessage(String status, String msg) {
        if (testMessages == null) {
            testMessages = new ArrayList<>();
        }

        testMessages.add(new TestItemMessage(status, msg));
    }

    public ArrayList<TestItemMessage> getTestMessages() {
        return testMessages;
    }

    public void setTestMessages(ArrayList<TestItemMessage> testMessages) {
        this.testMessages = testMessages;
    }

}
