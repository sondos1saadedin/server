package com.example.demo.utils;

/**
 * Created by ssaadedin on 7/5/17.
 */
public class FourInputs {
    String action, getBy, id, value;


    public FourInputs(String action, String getBy, String id, String value) {
        this.action = action;
        this.getBy = getBy;
        this.id = id;
        this.value = value;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getGetBy() {
        return getBy;
    }

    public void setGetBy(String getBy) {
        this.getBy = getBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
