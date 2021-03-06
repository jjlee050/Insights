package com.fypj.mymodule.api.model;

import com.google.appengine.api.images.Image;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
/**
 * Created by L33525 on 7/10/2014.
 */
@Entity
public class Clinic{
    @Id
    private Long clinicID;
    private String name;
    private String category;
    private String address;
    private String operatingHours;
    private String contactNo;

    public Clinic(Long clinicID, String name, String category, String address, String operatingHours, String contactNo) {
        this.clinicID = clinicID;
        this.name = name;
        this.category = category;
        this.address = address;
        this.operatingHours = operatingHours;
        this.contactNo = contactNo;

    }

    public Long getClinicID() {
        return clinicID;
    }

    public void setClinicID(Long clinicID) {
        this.clinicID = clinicID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOperatingHours() {
        return operatingHours;
    }

    public void setOperatingHours(String operatingHours) {
        this.operatingHours = operatingHours;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }


}
