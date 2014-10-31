package com.fypj.insightsLocal.model;

import java.sql.Date;
import java.sql.Time;
import com.fypj.insightsLocal.model.Clinic;

/**
 * Created by L33525 on 8/10/2014.
 */
public class Appointment {

    private Long appointmentID;
    private String nric;
    private String name;
    private String date;
    private String time;
    private String clinicname;

    public Appointment() {
    }

    public Appointment(Long appointmentID, String nric, String name , String date, String time, String clinicname) {
        this.appointmentID = appointmentID;
        this.nric = nric;
        this.name = name;
        this.date = date;
        this.time = time;
        this.clinicname = clinicname;
    }

    public Long getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(Long appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getNric() { return nric;}

    public void setNric(String nric) {this.nric = nric;}

    public String name(){return name ;}

    public void setName(String name){this.name = name;}

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

    public String getClinic() {
        return clinicname;
    }

    public void setClinic(String clinicname) {
        this.clinicname = clinicname;
    }
}
