package com.example.demo.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by sondo on 04/04/2018.
 */

@XmlRootElement
public class Action implements Serializable {

    @XmlElement
    private String id;
    @XmlElement
    private String action;

    @XmlElement
    private String selectBy;

    @XmlElement
    private String name;

    @XmlElement
    private String value;

    @XmlElement
    private String description;

    @XmlElement
    private String visible;

    public Action() {

    }
    public Action(String action, String selectBy, String name, String value, String description, String visible) {
        this.action = action;
        this.selectBy = selectBy;
        this.name = name;
        this.value = value;
        this.description = description;
        this.visible = visible;
    }


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSelectBy() {
        return selectBy;
    }

    public void setSelectBy(String selectBy) {
        this.selectBy = selectBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
