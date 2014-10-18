package com.fypj.insightsLocal.model;

/**
 * Created by L33524 on 10/10/2014.
 */
public class Dental {
    public String getClinicName() {
        return ClinicName;
    }

    public void setClinicName(String clinicName) {
        ClinicName = clinicName;
    }

    private String ClinicName;



    public int getClinicID() {
        return ClinicID;
    }

    public void setClinicID(int clinicID) {
        ClinicID = clinicID;
    }

    private int ClinicID;

    public String getClinicOH() {
        return ClinicOH;
    }

    public void setClinicOH(String clinicOH) {
        ClinicOH = clinicOH;
    }

    private String ClinicOH;

    public Dental (int ClinicID , String ClinicName ,String ClinicOH)
    {
        this.ClinicID = ClinicID;
        this.ClinicName = ClinicName;
        this.ClinicOH = ClinicOH;
    }
}

