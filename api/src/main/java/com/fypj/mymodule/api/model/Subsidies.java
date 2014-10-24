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
    private String amt;
    private Long packagesID;

    public Subsidies() {
    }

    public Subsidies(Long subsidiesID, String name, String amt, Long packagesID) {
        this.subsidiesID = subsidiesID;
        this.name = name;
        this.amt = amt;
        this.packagesID = packagesID;
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

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public Long getPackages() {
        return packagesID;
    }

    public void setPackages(Long packagesID) {
        this.packagesID = packagesID;
    }
}
