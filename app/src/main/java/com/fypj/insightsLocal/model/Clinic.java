package com.fypj.insightsLocal.model;

/**
 * Created by L33524 on 22/9/2014.
 */
public class Clinic {
    public String getClinicName() {
        return ClinicName;
    }

    public void setClinicName(String clinicName) {
        ClinicName = clinicName;
    }

    private String ClinicName;

    public String getClinicAddress() {
        return ClinicAddress;
    }

    public void setClinicAddress(String clinicAddress) {
        ClinicAddress = clinicAddress;
    }

    private String ClinicAddress;

    public String getClinicPostalCode() {
        return ClinicPostalCode;
    }

    public void setClinicPostalCode(String clinicPostalCode) {
        ClinicPostalCode = clinicPostalCode;
    }

    private String ClinicPostalCode;

    public int getClinicID() {
        return ClinicID;
    }

    public void setClinicID(int clinicID) {
        ClinicID = clinicID;
    }

    private int ClinicID;

    public Clinic (int ClinicID , String ClinicName , String ClinicAddress , String ClinicPostalCode)
    {
        this.ClinicID = ClinicID;
        this.ClinicName = ClinicName;
        this.ClinicAddress = ClinicAddress;
        this.ClinicPostalCode = ClinicPostalCode;
    }
}
