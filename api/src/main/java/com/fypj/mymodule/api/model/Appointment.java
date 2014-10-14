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
    private Long userID;
    private String date;
    private String time;
    private Long clinicID;

    public Appointment() {
    }

    public Appointment(Long appointmentID, Long userID, String date, String time, Long clinicID) {
        this.appointmentID = appointmentID;
        this.userID = userID;
        this.date = date;
        this.time = time;
        this.clinicID = clinicID;
    }

    public Long getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(Long appointmentID) {
        this.appointmentID = appointmentID;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
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

    public Long getClinicID() {
        return clinicID;
    }

    public void setClinicID(Long clinicID) {
        this.clinicID = clinicID;
    }
}
