package com.fypj.insightsLocal.model;

/**
 * Created by L33525 on 22/9/2014.
 */
public class HomeSection {
    private String title;
    private String name;
    private String location;

    public HomeSection(String title, String name, String location) {
        this.title = title;
        this.name = name;
        this.location = location;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
