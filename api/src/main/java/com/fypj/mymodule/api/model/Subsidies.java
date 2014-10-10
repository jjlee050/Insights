package com.fypj.mymodule.api.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by L33525 on 8/10/2014.
 */
@Entity
public class Subsidies {
    @Id
    private Long subsidiesID;
    private String name;
    private float amt;
    private Package packages;

    public Subsidies() {
    }

    public Subsidies(Long subsidiesID, String name, float amt, Package packages) {
        this.subsidiesID = subsidiesID;
        this.name = name;
        this.amt = amt;
        this.packages = packages;
    }

    public Long getSubsidiesID() {
        return subsidiesID;
    }

    public void setSubsidiesID(Long subsidiesID) {
        this.subsidiesID = subsidiesID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAmt() {
        return amt;
    }

    public void setAmt(float amt) {
        this.amt = amt;
    }

    public Package getPackages() {
        return packages;
    }

    public void setPackages(Package packages) {
        this.packages = packages;
    }
}
