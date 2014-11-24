package com.fypj.mymodule.api.model;

import com.google.api.server.spi.types.DateAndTime;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by L33525 on 7/10/2014.
 */
@Entity
public class Event {
    @Id
    private Long eventID;
    private String name;
    private String dateAndTime;
    private String guestOfHonour;
    private String desc;
    private String organizer;
    private String contactNo;
    private String location;

    public Event(Long eventID, String name, String dateAndTime, String guestOfHonour, String desc, String organizer, String contactNo, String location) {
        this.eventID = eventID;
        this.name = name;
        this.dateAndTime = dateAndTime;
        this.guestOfHonour = guestOfHonour;
        this.desc = desc;
        this.organizer = organizer;
        this.contactNo = contactNo;
        this.location = location;
    }

    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getGuestOfHonour() {
        return guestOfHonour;
    }

    public void setGuestOfHonour(String guestOfHonour) {
        this.guestOfHonour = guestOfHonour;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
