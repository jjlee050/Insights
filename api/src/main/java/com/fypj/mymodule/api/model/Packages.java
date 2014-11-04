package com.fypj.mymodule.api.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by L33525 on 8/10/2014.
 */
@Entity
public class Packages {
    @Id
    private Long packageID;
    private String name;
    private String overview;
    private String benefits;
    private String eligible;

    public Packages() {
    }

    public Packages(Long packageID, String name, String overview, String benefits, String eligible) {
        this.packageID = packageID;
        this.name = name;
        this.overview = overview;
        this.benefits = benefits;
        this.eligible = eligible;
    }

    public Long getPackageID() {
        return packageID;
    }

    public void setPackageID(Long packageID) {
        this.packageID = packageID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getEligible() {
        return eligible;
    }

    public void setEligible(String eligible) {
        this.eligible = eligible;
    }
}
