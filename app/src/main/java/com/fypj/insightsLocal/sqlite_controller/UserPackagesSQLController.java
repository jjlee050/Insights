package com.fypj.insightsLocal.sqlite_controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fypj.mymodule.api.insightsUserPackages.model.UserPackages;

import java.util.ArrayList;

/**
 * Created by L33525 on 27/10/2014.
 */
public class UserPackagesSQLController {
    Context context;
    SQLiteController conn;

    public UserPackagesSQLController(Context context){
        this.context = context;
        conn = new SQLiteController(context);
    }

    public void insertUserPackage(UserPackages userPackages){
        conn.open();
        ContentValues cv = new ContentValues();
        cv.put("userPackageID", userPackages.getUserPackagesID());
        cv.put("nric", userPackages.getNric());
        cv.put("packagesID", userPackages.getPackagesID());

        conn.getDB().insert(conn.getUserPackagesTable(), null, cv);
        conn.close();
    }

    public ArrayList<UserPackages> getUserPackagesByNRIC(String nric){
        ArrayList<UserPackages> userPackagesArrList = new ArrayList<UserPackages>();
        conn.open();
        Cursor cursor = conn.getDB().query(conn.getUserPackagesTable(), null, "nric = '" + nric + "'", null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                UserPackages userPackages = new UserPackages();
                userPackages.setUserPackagesID(cursor.getLong(cursor.getColumnIndex("userPackageID")));
                userPackages.setNric(cursor.getString(cursor.getColumnIndex("nric")));
                userPackages.setPackagesID(cursor.getLong(cursor.getColumnIndex("packagesID")));
                userPackagesArrList.add(userPackages);
            }while(cursor.moveToNext());
        }
        conn.close();
        return userPackagesArrList;
    }

    public UserPackages getUserPackages(String nric, Long packagesID){
        UserPackages userPackages = new UserPackages();
        conn.open();
        Cursor cursor = conn.getDB().query(conn.getUserPackagesTable(), null, "nric = '" + nric + "' AND packagesID = '" + packagesID + "'", null, null, null, null, null);

        if(cursor.moveToFirst()){
            userPackages.setUserPackagesID(cursor.getLong(cursor.getColumnIndex("userPackageID")));
            userPackages.setNric(cursor.getString(cursor.getColumnIndex("nric")));
            userPackages.setPackagesID(cursor.getLong(cursor.getColumnIndex("packagesID")));
        }
        else{
            userPackages.setPackagesID(Long.parseLong("0"));
        }
        conn.close();
        return userPackages;
    }

    public void deleteAllUserPackages(){
        conn.open();
        conn.getDB().delete(conn.getUserPackagesTable(), null, null);
        conn.close();
    }
}
