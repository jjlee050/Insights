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
    private User user;
    private Date date;
    private Time time;
    private Clinic clinic;

    public Appointment() {
    }

    public Appointment(Long appointmentID, User user, Date date, Time time, Clinic clinic) {
        this.appointmentID = appointmentID;
        this.user = user;
        this.date = date;
        this.time = time;
        this.clinic = clinic;
    }

    public Long getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(Long appointmentID) {
        this.appointmentID = appointmentID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }
}
