package com.fypj.insightsLocal.sqlite_controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fypj.mymodule.api.insightsEvent.model.Event;
import com.fypj.mymodule.api.insightsUser.model.User;

/**
 * Created by L33525 on 21/10/2014.
 */
public class UserSQLController {
    Context context;
    SQLiteController conn;

    public UserSQLController(Context context){
        this.context = context;
        conn = new SQLiteController(context);
    }

    public void insertUser(User user){
        conn.open();
        ContentValues cv = new ContentValues();
        cv.put("nric", user.getNric());
        cv.put("name", user.getName());
        cv.put("password", user.getPassword());
        cv.put("age", user.getAge());
        cv.put("contactNo", user.getContactNo());
        cv.put("address", user.getAddress());
        cv.put("preferredLanguage","English");
        cv.put("firstTimeSignIn",0);
        conn.getDB().insert(conn.getUserTable(), null, cv);
        conn.close();
    }

    public User getUser(String nric){
        User user = new User();
        conn.open();
        Cursor cursor = conn.getDB().query(conn.getUserTable(), null, "nric = ?", new String[]{ nric }, null, null, null, null);
        if(cursor.moveToFirst()){
            user.setNric(cursor.getString(cursor.getColumnIndex("nric")));
            user.setName(cursor.getString(cursor.getColumnIndex("name")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            user.setAge(cursor.getInt(cursor.getColumnIndex("age")));
            user.setContactNo(cursor.getString(cursor.getColumnIndex("contactNo")));
            user.setAddress(cursor.getString(cursor.getColumnIndex("address")));
        }
        else{
            user.setNric("");
        }
        conn.close();
        return user;
    }

    public String getUserPreferredLanguage(String nric){
        String preferredLanguage = "";
        conn.open();
        Cursor cursor = conn.getDB().query(conn.getUserTable(), new String[]{"preferredLanguage"}, "nric = ?", new String[]{ nric }, null, null, null, null);
        if(cursor.moveToFirst()){
            preferredLanguage = cursor.getString(cursor.getColumnIndex("preferredLanguage"));
        }
        return preferredLanguage;
    }

    public int getUserSignInStatus(String nric){
        int status = -1;
        conn.open();
        Cursor cursor = conn.getDB().query(conn.getUserTable(), new String[]{"firstTimeSignIn"}, "nric = ?", new String[]{ nric }, null, null, null, null);
        if(cursor.moveToFirst()){
            status = cursor.getInt(cursor.getColumnIndex("firstTimeSignIn"));
        }
        return status;
    }


    public long updateSignInStatus(User user, int signInStatus){
        ContentValues cv = new ContentValues();
        /**
         * Sign-in Status:
         * 0 = First-Time
         * 1 = Already sign in before
         */
        cv.put("firstTimeSignIn",signInStatus);
        return conn.getDB().update(conn.getUserTable(), cv, "nric = ?", new String[]{user.getNric()});
    }

    public long updatePreferredLanguage(User user, String preferredLanguage){
        ContentValues cv = new ContentValues();
        cv.put("preferredLanguage",preferredLanguage);
        return conn.getDB().update(conn.getUserTable(), cv, "nric = ?", new String[]{user.getNric()});
    }

    public void deleteAllUsers(){
        conn.open();
        conn.getDB().delete(conn.getUserTable(), null, null);
        conn.close();
    }
}
