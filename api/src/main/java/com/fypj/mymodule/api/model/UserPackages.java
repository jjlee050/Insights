package com.fypj.mymodule.api.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by L33525 on 8/10/2014.
 */
@Entity
public class UserPackages {
    @Id
    private Long userPackagesID;
    private String nric;
    private Long packagesID;

    public UserPackages() {
    }

    public UserPackages(Long userPackagesID, String nric, Long packagesID) {
        this.userPackagesID = userPackagesID;
        this.nric = nric;
        this.packagesID = packagesID;
    }

    public Long getUserPackagesID() {
        return userPackagesID;
    }

    public void setUserPackagesID(Long userPackagesID) {
        this.userPackagesID = userPackagesID;
    }

    public String getNric() {
        return nric;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }

    public Long getPackagesID() {
        return packagesID;
    }

    public void setPackagesID(Long packagesID) {
        this.packagesID = packagesID;
    }
}
