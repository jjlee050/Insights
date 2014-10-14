package com.fypj.mymodule.api.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by L33525 on 8/10/2014.
 */
@Entity
public class User_Packages {
    @Id
    private Long userID;
    @Id
    private Long packagesID;

    public User_Packages() {
    }

    public User_Packages(Long userID, Long packages) {
        this.userID = userID;
        this.packagesID = packagesID;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long user) {
        this.userID = userID;
    }

    public Long getPackagesID() {
        return packagesID;
    }

    public void setPackagesID(Long packagesID) {
        this.packagesID = packagesID;
    }
}
