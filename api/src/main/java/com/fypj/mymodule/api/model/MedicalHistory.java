package com.fypj.mymodule.api.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by L33525 on 7/10/2014.
 */
@Entity
public class MedicalHistory {
    @Id
    private Long medicalHistoryID;
    private Long clinicID;
    private String date;
    private String time;
    private String service;
    private Float amt;

    public MedicalHistory() {
    }

    public MedicalHistory(Long medicalHistoryID, Long clinicID, String date, String time, String service, Float amt) {
        this.medicalHistoryID = medicalHistoryID;
        this.clinicID = clinicID;
        this.date = date;
        this.time = time;
        this.service = service;
        this.amt = amt;
    }

    public Long getMedicalHistoryID() {
        return medicalHistoryID;
    }

    public void setMedicalHistoryID(Long medicalHistoryID) {
        this.medicalHistoryID = medicalHistoryID;
    }

    public Long getClinicID() {
        return clinicID;
    }

    public void setClinicID(Long clinicID) {
        this.clinicID = clinicID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Float getAmt() {
        return amt;
    }

    public void setAmt(Float amt) {
        this.amt = amt;
    }
}
