package com.fypj.mymodule.api.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by L33525 on 8/10/2014.
 */
@Entity
public class User_Subsidies {
    @Id
    private Long userID;
    @Id
    private Long subsidiesID;
    private Float balance;

    public User_Subsidies() {
    }

    public User_Subsidies(Long userID, Long subsidiesID, Float balance) {
        this.userID = userID;
        this.subsidiesID = subsidiesID;
        this.balance = balance;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getSubsidiesID() {
        return subsidiesID;
    }

    public void setSubsidiesID(Long subsidies) {
        this.subsidiesID = subsidiesID;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }
}
