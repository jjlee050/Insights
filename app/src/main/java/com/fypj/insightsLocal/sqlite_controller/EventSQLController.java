package com.fypj.insightsLocal.sqlite_controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fypj.mymodule.api.insightsEvent.model.Event;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by jess on 18-Oct-14.
 */
public class EventSQLController {
    Context context;
    SQLiteController conn;

    public EventSQLController(Context context){
        this.context = context;
        conn = new SQLiteController(context);
    }

    public void insertEvent(Event event){
        conn.open();
        ContentValues cv = new ContentValues();
        cv.put("name", event.getName());
        cv.put("dateAndTime", event.getDateAndTime());
        cv.put("guestOfHonour", event.getGuestOfHonour());
        cv.put("desc", event.getDesc());
        cv.put("organizer", event.getOrganizer());
        cv.put("contactNo", event.getContactNo());
        cv.put("location", event.getLocation());

        conn.getDB().insert(conn.getEventTable(), null, cv);
        conn.close();
    }

    public ArrayList<Event> getAllEvent(){
        ArrayList<Event> eventList = new ArrayList<Event>();
        conn.open();
        Cursor cursor = conn.getDB().query(conn.getEventTable(), null, null, null, null, null, "dateAndTime", null);
        if(cursor.moveToFirst()){
            do{
                Event event = new Event();
                event.setEventID(cursor.getLong(cursor.getColumnIndex("eventID")));
                event.setName(cursor.getString(cursor.getColumnIndex("name")));
                event.setDateAndTime(cursor.getString(cursor.getColumnIndex("dateAndTime")));
                event.setGuestOfHonour(cursor.getString(cursor.getColumnIndex("guestOfHonour")));
                event.setDesc(cursor.getString(cursor.getColumnIndex("desc")));
                event.setOrganizer(cursor.getString(cursor.getColumnIndex("organizer")));
                event.setContactNo(cursor.getString(cursor.getColumnIndex("contactNo")));
                event.setLocation(cursor.getString(cursor.getColumnIndex("location")));
                eventList.add(event);
            }while(cursor.moveToNext());
        }
        conn.close();
        return eventList;
    }

    public Event getEvent(Long eventID){
        Event event = new Event();
        conn.open();
        Cursor cursor = conn.getDB().query(conn.getEventTable(), null, "eventID = ?", new String[]{ String.valueOf(eventID) }, null, null, null, null);
        if(cursor.moveToFirst()){
                event.setEventID(cursor.getLong(cursor.getColumnIndex("eventID")));
                event.setName(cursor.getString(cursor.getColumnIndex("name")));
                event.setDateAndTime(cursor.getString(cursor.getColumnIndex("dateAndTime")));
                event.setGuestOfHonour(cursor.getString(cursor.getColumnIndex("guestOfHonour")));
                event.setDesc(cursor.getString(cursor.getColumnIndex("desc")));
                event.setOrganizer(cursor.getString(cursor.getColumnIndex("organizer")));
                event.setContactNo(cursor.getString(cursor.getColumnIndex("contactNo")));
                event.setLocation(cursor.getString(cursor.getColumnIndex("location")));
        }
        else{
            event.setEventID(Long.parseLong("0"));
        }
        conn.close();
        return event;
    }

    public ArrayList<Event> searchEvent(String query){
        ArrayList<Event> eventList = new ArrayList<Event>();
        conn.open();
        Cursor cursor = conn.getDB().query(conn.getEventTable(), null, "name LIKE '%" + query + "%'", null, null, null, "dateAndTime", null);
        if(cursor.moveToFirst()){
            do{
                Event event = new Event();
                event.setEventID(cursor.getLong(cursor.getColumnIndex("eventID")));
                event.setName(cursor.getString(cursor.getColumnIndex("name")));
                event.setDateAndTime(cursor.getString(cursor.getColumnIndex("dateAndTime")));
                event.setGuestOfHonour(cursor.getString(cursor.getColumnIndex("guestOfHonour")));
                event.setDesc(cursor.getString(cursor.getColumnIndex("desc")));
                event.setOrganizer(cursor.getString(cursor.getColumnIndex("organizer")));
                event.setContactNo(cursor.getString(cursor.getColumnIndex("contactNo")));
                event.setLocation(cursor.getString(cursor.getColumnIndex("location")));
                eventList.add(event);
            }while(cursor.moveToNext());
        }
        conn.close();
        return eventList;
    }

    public void deleteAllEvents(){
        conn.open();
        conn.getDB().delete(conn.getEventTable(), null, null);
        conn.close();
    }
}
