package com.example.demo.data;

/**
 * Created by sondo on 26/04/2018.
 */
public class TestItemMessage {
    private String status;
    private String message;

    public TestItemMessage(){};


    public TestItemMessage(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
