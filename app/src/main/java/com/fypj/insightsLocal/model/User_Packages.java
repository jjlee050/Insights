package com.fypj.insightsLocal.model;

import com.fypj.insightsLocal.model.User;
import com.fypj.insightsLocal.model.Package;
/**
 * Created by L33525 on 8/10/2014.
 */
public class User_Packages {
    private User user;
    private Package packages;

    public User_Packages() {
    }

    public User_Packages(User user, Package packages) {
        this.user = user;
        this.packages = packages;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Package getPackages() {
        return packages;
    }

    public void setPackages(Package packages) {
        this.packages = packages;
    }
}
