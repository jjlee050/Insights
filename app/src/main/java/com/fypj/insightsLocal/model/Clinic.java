package com.fypj.insightsLocal.model;

public class Clinic{
    private Long clinicID;
    private String name;
    private String address;
    private String operatingHours;
    private String contactNo;
    private String type;

    public Clinic(Long clinicID, String clinicName, String clinicOH, String clinicAddress, String clinicContactNo) {
    }

    public Clinic(Long clinicID, String name, String address, String operatingHours, String contactNo,String type) {
        this.clinicID = clinicID;
        this.name = name;
        this.address = address;
        this.operatingHours = operatingHours;
        this.contactNo = contactNo;
        this.type = type;
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
    public String getType() {return type;}

    public void setType(String type) {this.type = type;}
}
