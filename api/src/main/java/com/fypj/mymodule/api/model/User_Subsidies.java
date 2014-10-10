package com.fypj.mymodule.api.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by L33525 on 8/10/2014.
 */
@Entity
public class User_Subsidies {
    @Id
    private User user;
    @Id
    private Subsidies subsidies;
    private float balance;

    public User_Subsidies() {
    }

    public User_Subsidies(User user, Subsidies subsidies, float balance) {
        this.user = user;
        this.subsidies = subsidies;
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Subsidies getSubsidies() {
        return subsidies;
    }

    public void setSubsidies(Subsidies subsidies) {
        this.subsidies = subsidies;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
