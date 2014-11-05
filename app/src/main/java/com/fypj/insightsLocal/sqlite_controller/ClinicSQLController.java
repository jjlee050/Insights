package com.fypj.insightsLocal.sqlite_controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


import com.fypj.mymodule.api.insightsClinics.model.Clinic;

import java.text.ParseException;
import java.util.ArrayList;



/**
 * Created by jess on 18-Oct-14.
 */
public class ClinicSQLController {
    Context context;
    SQLiteController conn;

    public ClinicSQLController(Context context){
        this.context = context;
        conn = new SQLiteController(context);
    }

    public void insertClinic(Clinic clinic){
        conn.open();
        ContentValues cv = new ContentValues();
        cv.put("clinicID", clinic.getClinicID());
        cv.put("name", clinic.getName());
        cv.put("address", clinic.getAddress());
        cv.put("operatingHours", clinic.getOperatingHours());
        cv.put("contactNo", clinic.getContactNo());
        cv.put("category", clinic.getCategory());


        conn.getDB().insert(conn.getClinicTable(), null, cv);
        conn.close();
    }

    public ArrayList<Clinic> getAllClinic(){
        ArrayList<Clinic> ClinicArrList = new ArrayList<Clinic>();
        conn.open();
        Cursor cursor = conn.getDB().query(conn.getClinicTable(), null, null, null, null, null,null);
        if(cursor.moveToFirst()){
            do{
                Clinic clinic = new Clinic();
                clinic.setClinicID(cursor.getLong(cursor.getColumnIndex("clinicID")));
                clinic.setName(cursor.getString(cursor.getColumnIndex("name")));
                clinic.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                clinic.setOperatingHours(cursor.getString(cursor.getColumnIndex("operatingHours")));
                clinic.setContactNo(cursor.getString(cursor.getColumnIndex("contactNo")));
                clinic.setCategory(cursor.getString(cursor.getColumnIndex("category")));
                ClinicArrList.add(clinic);
            }while(cursor.moveToNext());
        }
        conn.close();
        return ClinicArrList;
    }

    public Clinic getClinic (Long clinicID){
        Clinic clinic = new Clinic();
        conn.open();
        Cursor cursor = conn.getDB().query(conn.getClinicTable(),null,"clinicID = ?", new String[]{ String.valueOf(clinicID) }, null, null, null, null);
        if(cursor.moveToFirst()){
            clinic.setClinicID(cursor.getLong(cursor.getColumnIndex("clinicID")));
            clinic.setName(cursor.getString(cursor.getColumnIndex("name")));
            clinic.setAddress(cursor.getString(cursor.getColumnIndex("address")));
            clinic.setOperatingHours(cursor.getString(cursor.getColumnIndex("operatingHours")));
            clinic.setContactNo(cursor.getString(cursor.getColumnIndex("contactNo")));
            clinic.setCategory(cursor.getString(cursor.getColumnIndex("category")));

        }
        else{
            clinic.setClinicID(Long.parseLong("0"));
        }
        conn.close();
        return clinic;
    }

    public ArrayList<Clinic> searchClinic(String query, String category){
        ArrayList<Clinic> ClinicArrList = new ArrayList<Clinic>();
        conn.open();
        Cursor cursor = conn.getDB().query(conn.getClinicTable(), null, "name LIKE '%" + query + "%' AND category = '"+category+"'", null, null, null,null, null);
        if(cursor.moveToFirst()){
            do{
                Clinic clinic = new Clinic();
                clinic.setClinicID(cursor.getLong(cursor.getColumnIndex("clinicID")));
                clinic.setName(cursor.getString(cursor.getColumnIndex("name")));
                clinic.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                clinic.setOperatingHours(cursor.getString(cursor.getColumnIndex("operatingHours")));
                clinic.setContactNo(cursor.getString(cursor.getColumnIndex("contactNo")));
                clinic.setCategory(cursor.getString(cursor.getColumnIndex("category")));

                ClinicArrList.add(clinic);
            }while(cursor.moveToNext());
        }
        conn.close();
        return ClinicArrList;
    }

    public ArrayList<Clinic> getAllCategoryClinic(String category){
        ArrayList<Clinic> ClinicArrList = new ArrayList<Clinic>();
        conn.open();
        Cursor cursor = conn.getDB().query(conn.getClinicTable(), null, "category = '"+category+"'", null, null, null,null, null);
        if(cursor.moveToFirst()){
            do{
                Clinic clinic = new Clinic();
                clinic.setClinicID(cursor.getLong(cursor.getColumnIndex("clinicID")));
                clinic.setName(cursor.getString(cursor.getColumnIndex("name")));
                clinic.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                clinic.setOperatingHours(cursor.getString(cursor.getColumnIndex("operatingHours")));
                clinic.setContactNo(cursor.getString(cursor.getColumnIndex("contactNo")));
                clinic.setCategory(cursor.getString(cursor.getColumnIndex("category")));

                ClinicArrList.add(clinic);
            }while(cursor.moveToNext());
        }
        conn.close();
        return ClinicArrList;
    }

    public void deleteAllClinic(String category){
        conn.open();
        conn.getDB().delete(conn.getClinicTable(), "category = ? ", new String[]{category});
        conn.close();
    }
}
