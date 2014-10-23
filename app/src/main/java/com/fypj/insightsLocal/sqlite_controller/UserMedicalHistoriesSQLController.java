package com.fypj.insightsLocal.sqlite_controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fypj.mymodule.api.insightsEvent.model.Event;
import com.fypj.mymodule.api.insightsMedicalHistory.model.MedicalHistory;

/**
 * Created by L33525 on 23/10/2014.
 */
public class UserMedicalHistoriesSQLController {
    Context context;
    SQLiteController conn;

    public UserMedicalHistoriesSQLController(Context context){
        this.context = context;
        conn = new SQLiteController(context);
    }

    public void insertUserMedicalHistory(MedicalHistory medicalHistory){
        conn.open();
        ContentValues cv = new ContentValues();
        cv.put("medicalHistoryID", medicalHistory.getMedicalHistoryID());
        cv.put("clinicID", medicalHistory.getClinicID());
        cv.put("nric", medicalHistory.getNric());
        cv.put("date", medicalHistory.getDate());
        cv.put("time", medicalHistory.getTime());
        cv.put("service", medicalHistory.getService());
        cv.put("amt", medicalHistory.getAmt());

        conn.getDB().insert(conn.getUserMedicalHistoriesTable(), null, cv);
        conn.close();
    }

    public MedicalHistory getMedicalHistory(Long medicalHistoryID){
        MedicalHistory medicalHistory = new MedicalHistory();
        conn.open();
        Cursor cursor = conn.getDB().query(conn.getUserMedicalHistoriesTable(), null, "medicalHistoryID = ?", new String[]{ String.valueOf(medicalHistoryID) }, null, null, null, null);
        if(cursor.moveToFirst()){
            medicalHistory.setMedicalHistoryID(cursor.getLong(cursor.getColumnIndex("medicalHistoryID")));
            medicalHistory.setClinicID(cursor.getLong(cursor.getColumnIndex("clinicID")));
            medicalHistory.setNric(cursor.getString(cursor.getColumnIndex("nric")));
            medicalHistory.setDate(cursor.getString(cursor.getColumnIndex("date")));
            medicalHistory.setTime(cursor.getString(cursor.getColumnIndex("time")));
            medicalHistory.setService(cursor.getString(cursor.getColumnIndex("service")));
            medicalHistory.setAmt(cursor.getFloat(cursor.getColumnIndex("amt")));
        }
        else{
            medicalHistory.setMedicalHistoryID(Long.parseLong("0"));
        }
        conn.close();
        return medicalHistory;
    }

    public void deleteAllMedicalHistories(){
        conn.open();
        conn.getDB().delete(conn.getUserMedicalHistoriesTable(), null, null);
        conn.close();
    }
}
