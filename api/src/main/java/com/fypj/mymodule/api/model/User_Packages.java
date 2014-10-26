package com.fypj.mymodule.api.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by L33525 on 8/10/2014.
 */
@Entity
public class User_Packages {
    @Id
    private String nric;
    @Id
    private Long packagesID;

    public User_Packages() {
    }

    public User_Packages(String nric, Long packagesID) {
        this.nric = nric;
        this.packagesID = packagesID;
    }

    public String getNric() {
        return nric;
    }

    public void setNric(String user) {
        this.nric = nric;
    }

    public Long getPackagesID() {
        return packagesID;
    }

    public void setPackagesID(Long packagesID) {
        this.packagesID = packagesID;
    }
}
