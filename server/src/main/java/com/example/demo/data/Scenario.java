package com.example.demo.data;

import com.example.demo.testResult.TestResult;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;

@XmlRootElement
public class Scenario {

    private String email;
    private String url;
    private String description;
    private String title;
    private Date date;

    @XmlElement
    private
    ArrayList<Action> data;
    private TestResult testResult;



    public Scenario() {

        data = new ArrayList<>();
    }

    public Scenario(ArrayList<Action> actions, String url) {
        this.data = actions;
        this.url = url;
    }

    public ArrayList<Action> getData() {
        return data;
    }

    public void setData(ArrayList<Action> data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TestResult getTestResult() {
        return testResult;
    }

    public void setTestResult(TestResult testResult) {
        this.testResult = testResult;
    }
}
