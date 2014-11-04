package com.fypj.insightsLocal.sqlite_controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fypj.mymodule.api.insightsUserPackages.model.UserPackages;
import com.fypj.mymodule.api.insightsUserSubsidies.model.UserSubsidies;

import java.util.ArrayList;

/**
 * Created by L33525 on 27/10/2014.
 */
public class UserSubsidiesSQLController {
    Context context;
    SQLiteController conn;

    public UserSubsidiesSQLController(Context context){
        this.context = context;
        conn = new SQLiteController(context);
    }

    public void insertUserSubsidies(UserSubsidies userSubsidies){
        conn.open();
        ContentValues cv = new ContentValues();
        cv.put("userSubsidiesID", userSubsidies.getUserSubsidiesID());
        cv.put("nric", userSubsidies.getNric());
        cv.put("subsidiesID", userSubsidies.getSubsidiesID());
        cv.put("balance", userSubsidies.getBalance());
        conn.getDB().insert(conn.getUserSubsidiesTable(), null, cv);
        conn.close();
    }

    public ArrayList<UserSubsidies> getUserSubsidiesByNRIC(String nric){
        ArrayList<UserSubsidies> userSubsidiesArrList = new ArrayList<UserSubsidies>();
        conn.open();
        Cursor cursor = conn.getDB().query(conn.getUserSubsidiesTable(), null, "nric = '" + nric + "'", null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                UserSubsidies userSubsidies = new UserSubsidies();
                userSubsidies.setUserSubsidiesID(cursor.getLong(cursor.getColumnIndex("userSubsidiesID")));
                userSubsidies.setNric(cursor.getString(cursor.getColumnIndex("nric")));
                userSubsidies.setSubsidiesID(cursor.getLong(cursor.getColumnIndex("subsidiesID")));
                userSubsidies.setBalance(cursor.getFloat(cursor.getColumnIndex("balance")));
                userSubsidiesArrList.add(userSubsidies);
            }while(cursor.moveToNext());
        }
        conn.close();
        return userSubsidiesArrList;
    }

    public ArrayList<UserSubsidies> getUserSubsidiesBySubsidiesID(Long subsidiesID){
        ArrayList<UserSubsidies> userSubsidiesArrList = new ArrayList<UserSubsidies>();
        conn.open();
        Cursor cursor = conn.getDB().query(conn.getUserSubsidiesTable(), null, "subsidiesID = " + subsidiesID, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                UserSubsidies userSubsidies = new UserSubsidies();
                userSubsidies.setUserSubsidiesID(cursor.getLong(cursor.getColumnIndex("userSubsidiesID")));
                userSubsidies.setNric(cursor.getString(cursor.getColumnIndex("nric")));
                userSubsidies.setSubsidiesID(cursor.getLong(cursor.getColumnIndex("subsidiesID")));
                userSubsidies.setBalance(cursor.getFloat(cursor.getColumnIndex("balance")));
                userSubsidiesArrList.add(userSubsidies);
            }while(cursor.moveToNext());
        }
        conn.close();
        return userSubsidiesArrList;
    }

    public UserSubsidies getUserSubsidies(String nric, Long subsidiesID){
        UserSubsidies userSubsidies = new UserSubsidies();
        conn.open();
        Cursor cursor = conn.getDB().query(conn.getUserSubsidiesTable(), null, "nric = '" + nric + "' AND subsidiesID = " + subsidiesID, null, null, null, null, null);

        if(cursor.moveToFirst()){
            userSubsidies.setUserSubsidiesID(cursor.getLong(cursor.getColumnIndex("userSubsidiesID")));
            userSubsidies.setNric(cursor.getString(cursor.getColumnIndex("nric")));
            userSubsidies.setSubsidiesID(cursor.getLong(cursor.getColumnIndex("subsidiesID")));
            userSubsidies.setBalance(cursor.getFloat(cursor.getColumnIndex("balance")));
        }
        else{
            userSubsidies.setSubsidiesID(Long.parseLong("0"));
        }
        conn.close();
        return userSubsidies;
    }

    public void deleteAllUserSubsidies(){
        conn.open();
        conn.getDB().delete(conn.getUserSubsidiesTable(), null, null);
        conn.close();
    }
}
