package com.fypj.insightsLocal.model;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by L33525 on 7/10/2014.
 */
public class MedicalHistory {
    private Long medicalHistoryID;
    private Clinic clinic;
    private Date date;
    private Time time;
    private String service;
    private int amt;

    public MedicalHistory() {
    }

    public MedicalHistory(Long medicalHistoryID, Clinic clinic, Date date, Time time, String service, int amt) {
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

    public int getAmt() {
        return amt;
    }

    public void setAmt(int amt) {
        this.amt = amt;
    }
}
