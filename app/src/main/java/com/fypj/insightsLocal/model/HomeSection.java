package com.fypj.insightsLocal.model;

/**
 * Created by L33525 on 22/9/2014.
 */
public class HomeSection {
    private String title;
    private String name;
    private String contactNo;

    public HomeSection(String title, String name, String contactNo) {
        this.title = title;
        this.name = name;
        this.contactNo = contactNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
}
