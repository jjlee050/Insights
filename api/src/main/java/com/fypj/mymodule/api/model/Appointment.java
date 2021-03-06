package com.fypj.mymodule.api.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by L33525 on 8/10/2014.
 */
@Entity
public class Appointment {
    @Id
    private Long appointmentID;
    private Long clinicID;
    private String date;
    private String time;
    private String nric;

    public Appointment() {
    }

    public Appointment(Long appointmentID, Long clinicID, String date, String time, String nric) {
        this.appointmentID = appointmentID;
        this.clinicID = clinicID;
        this.date = date;
        this.time = time;
        this.nric = nric;
    }

    public Long getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(Long appointmentID) {
        this.appointmentID = appointmentID;
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

    public String getNric() {
        return nric;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }
}
