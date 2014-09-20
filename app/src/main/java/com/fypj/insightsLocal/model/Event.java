package com.fypj.insightsLocal.model;

/**
 * Created by jess on 20-Sep-14.
 */
public class Event {
    private int eventID;
    private String eventName;
    private String eventDateTime;
    private String eventDescription;

    public Event(int eventID, String eventName, String eventDateTime, String eventDescription) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventDateTime = eventDateTime;
        this.eventDescription = eventDescription;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(String eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
}
