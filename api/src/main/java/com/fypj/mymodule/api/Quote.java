package com.fypj.mymodule.api;

/**
 * Created by irani_r on 8/25/2014.
 */
public class Quote {
    Long id;
    String who;
    String whom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getWhom() {
        return whom;
    }

    public void setWhom(String whom) {
        this.whom = whom;
    }
}