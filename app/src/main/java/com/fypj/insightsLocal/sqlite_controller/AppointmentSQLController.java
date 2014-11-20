package com.fypj.insightsLocal.sqlite_controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fypj.mymodule.api.insightsAppointment.model.Appointment;
import com.fypj.mymodule.api.insightsClinics.model.Clinic;

import java.util.ArrayList;


/**
 * Created by jess on 18-Oct-14.
 */
public class AppointmentSQLController {
    Context context;
    SQLiteController conn;

    public AppointmentSQLController(Context context){
        this.context = context;
        conn = new SQLiteController(context);
    }

    public void insertAppointment(Appointment appointment){
        conn.open();
        System.out.println(appointment.getTime());
        ContentValues cv = new ContentValues();
        cv.put("appointmentID", appointment.getAppointmentID());
        cv.put("clinicID", appointment.getClinicID());
        cv.put("Date", appointment.getDate());
        cv.put("Time", appointment.getTime());
        cv.put("nric", appointment.getNric());


        conn.getDB().insert(conn.getAppointmentTable(), null, cv);
        conn.close();
    }

    public ArrayList<Appointment> getAllAppointment(){
        ArrayList<Appointment> AppointmentArrList = new ArrayList<Appointment>();
        conn.open();
        Cursor cursor = conn.getDB().query(conn.getAppointmentTable(), null, null, null, null, null,null);
        if(cursor.moveToFirst()){
            do{
                Appointment appointment = new Appointment();
                appointment.setAppointmentID(cursor.getLong(cursor.getColumnIndex("appointmentID")));
                appointment.setClinicID(cursor.getLong(cursor.getColumnIndex("clinicID")));
                appointment.setDate(cursor.getString(cursor.getColumnIndex("Date")));
                appointment.setTime(cursor.getString(cursor.getColumnIndex("Time")));
                appointment.setNric(cursor.getString(cursor.getColumnIndex("nric")));
                AppointmentArrList.add(appointment);
            }while(cursor.moveToNext());
        }
        conn.close();
        return AppointmentArrList;
    }

    public Appointment getAppointment (Long appointmentID){
        Appointment appointment = new Appointment();
        conn.open();
        Cursor cursor = conn.getDB().query(conn.getAppointmentTable(),null,"appointmentID = ?", new String[]{ String.valueOf(appointmentID) }, null, null, null, null);
        if(cursor.moveToFirst()){
            appointment.setAppointmentID(cursor.getLong(cursor.getColumnIndex("appointmentID")));
            appointment.setClinicID(cursor.getLong(cursor.getColumnIndex("clinicID")));
            appointment.setDate(cursor.getString(cursor.getColumnIndex("Date")));
            appointment.setTime(cursor.getString(cursor.getColumnIndex("Time")));
            appointment.setNric(cursor.getString(cursor.getColumnIndex("nric")));

        }
        else{
            appointment.setAppointmentID(Long.parseLong("0"));
        }
        conn.close();
        return appointment;
    }

    public ArrayList<Appointment> getAppointmentByClinic(Long clinicID){
        ArrayList<Appointment> AppointmentArrList = new ArrayList<Appointment>();
        conn.open();
        Cursor cursor = conn.getDB().query(conn.getAppointmentTable(),null,"clinicID = ?", new String[]{ String.valueOf(clinicID) }, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                Appointment appointment = new Appointment();
                appointment.setAppointmentID(cursor.getLong(cursor.getColumnIndex("appointmentID")));
                appointment.setClinicID(cursor.getLong(cursor.getColumnIndex("clinicID")));
                appointment.setDate(cursor.getString(cursor.getColumnIndex("Date")));
                appointment.setTime(cursor.getString(cursor.getColumnIndex("Time")));
                appointment.setNric(cursor.getString(cursor.getColumnIndex("nric")));
                AppointmentArrList.add(appointment);
            }while(cursor.moveToNext());
        }
        conn.close();
        return AppointmentArrList;
    }



    public void deleteAllAppointment(){
        conn.open();
        conn.getDB().delete(conn.getAppointmentTable(),null,null);
        conn.close();
    }
}
