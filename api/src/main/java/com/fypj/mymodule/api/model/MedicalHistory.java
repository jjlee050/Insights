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
    @Parent
    private Clinic clinic;
    private Date date;
    private Time time;
    private String service;
    private float amt;

    public MedicalHistory() {
    }

    public MedicalHistory(Long medicalHistoryID, Clinic clinic, Date date, Time time, String service, float amt) {
        this.medicalHistoryID = medicalHistoryID;
        this.clinic = clinic;
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

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public float getAmt() {
        return amt;
    }

    public void setAmt(float amt) {
        this.amt = amt;
    }
}
