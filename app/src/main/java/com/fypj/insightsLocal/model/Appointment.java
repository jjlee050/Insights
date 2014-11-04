package com.fypj.insightsLocal.model;

import java.sql.Date;
import java.sql.Time;
import com.fypj.insightsLocal.model.Clinic;

/**
 * Created by L33525 on 8/10/2014.
 */
public class Appointment {
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
