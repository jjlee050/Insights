package com.fypj.insightsLocal.sqlite_controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.fypj.mymodule.api.insightsEvent.model.Event;
import com.fypj.mymodule.api.insightsMedicalHistory.model.MedicalHistory;
import com.fypj.mymodule.api.insightsSubsidies.model.Subsidies;
import com.fypj.mymodule.api.insightsUser.model.User;

import java.util.ArrayList;

/**
 * Created by jess on 24-Oct-14.
 */
public class SubsidiesSQLController {
    Context context;
    SQLiteController conn;

    public SubsidiesSQLController(Context context){
        this.context = context;
        conn = new SQLiteController(context);
    }

    public void insertSubsidy(Subsidies subsidies){
        conn.open();
        ContentValues cv = new ContentValues();
        cv.put("name", subsidies.getName());
        cv.put("amt", subsidies.getAmt());
        cv.put("packagesID", subsidies.getPackagesID());

        conn.getDB().insert(conn.getSubsidiesTable(), null, cv);
        conn.close();
    }

    public ArrayList<Subsidies> getSubsidiesByPackageID(Long packagesID){
        ArrayList<Subsidies> subsidiesArrList = new ArrayList<Subsidies>();
        conn.open();
        Cursor cursor = conn.getDB().query(conn.getSubsidiesTable(), null, "packagesID = " + packagesID, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                Subsidies subsidies = new Subsidies();
                subsidies.setSubsidiesID(cursor.getLong(cursor.getColumnIndex("subsidiesID")));
                subsidies.setName(cursor.getString(cursor.getColumnIndex("name")));
                subsidies.setAmt(cursor.getString(cursor.getColumnIndex("amt")));
                subsidies.setPackagesID(cursor.getLong(cursor.getColumnIndex("packagesID")));
                subsidiesArrList.add(subsidies);
            }while(cursor.moveToNext());
        }
        conn.close();
        return subsidiesArrList;
    }

    public Subsidies getSubsidies(Long subsidiesID){
        Subsidies subsidies = new Subsidies();
        conn.open();
        Cursor cursor = conn.getDB().query(conn.getSubsidiesTable(), null, "subsidiesID = ?", new String[]{ String.valueOf(subsidiesID) }, null, null, null, null);
        if(cursor.moveToFirst()){
            subsidies.setSubsidiesID(cursor.getLong(cursor.getColumnIndex("subsidiesID")));
            subsidies.setName(cursor.getString(cursor.getColumnIndex("name")));
            subsidies.setAmt(cursor.getString(cursor.getColumnIndex("amt")));
            subsidies.setPackagesID(cursor.getLong(cursor.getColumnIndex("packagesID")));
        }
        else{
            subsidies.setSubsidiesID(Long.parseLong("0"));
        }
        conn.close();
        return subsidies;
    }
}
