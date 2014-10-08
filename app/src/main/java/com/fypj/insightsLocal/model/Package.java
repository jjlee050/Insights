package com.fypj.insightsLocal.model;

/**
 * Created by L33525 on 8/10/2014.
 */
public class Package {
    private Long packageID;
    private String name;
    private String image;
    private String overview;
    private String benefits;
    private String subsidies;
    private String eligible;

    public Package() {
    }

    public Package(Long packageID, String name, String image, String overview, String benefits, String subsidies, String eligible) {
        this.packageID = packageID;
        this.name = name;
        this.image = image;
        this.overview = overview;
        this.benefits = benefits;
        this.subsidies = subsidies;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getSubsidies() {
        return subsidies;
    }

    public void setSubsidies(String subsidies) {
        this.subsidies = subsidies;
    }

    public String getEligible() {
        return eligible;
    }

    public void setEligible(String eligible) {
        this.eligible = eligible;
    }
}
