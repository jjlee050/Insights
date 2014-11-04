package com.fypj.insightsLocal.sqlite_controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fypj.mymodule.api.insightsEvent.model.Event;
import com.fypj.mymodule.api.insightsPackages.model.Packages;

import java.util.ArrayList;

/**
 * Created by L33525 on 24/10/2014.
 */
public class PackagesSQLController {
    Context context;
    SQLiteController conn;

    public PackagesSQLController(Context context){
        this.context = context;
        conn = new SQLiteController(context);
    }

    public void insertPackages(Packages packages){
        conn.open();
        ContentValues cv = new ContentValues();
        System.out.println("Putting event.");
        cv.put("name", packages.getName());
        cv.put("overview", packages.getOverview());
        cv.put("benefits", packages.getBenefits());
        cv.put("eligible", packages.getEligible());

        conn.getDB().insert(conn.getPackagesTable(), null, cv);
        conn.close();
    }

    public ArrayList<Packages> getAllPackages(){
        ArrayList<Packages> packagesList = new ArrayList<Packages>();
        conn.open();
        Cursor cursor = conn.getDB().query(conn.getPackagesTable(), null, null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                Packages packages = new Packages();
                packages.setPackageID(cursor.getLong(cursor.getColumnIndex("packagesID")));
                packages.setName(cursor.getString(cursor.getColumnIndex("name")));
                packages.setOverview(cursor.getString(cursor.getColumnIndex("overview")));
                packages.setBenefits(cursor.getString(cursor.getColumnIndex("benefits")));
                packages.setEligible(cursor.getString(cursor.getColumnIndex("eligible")));
                packagesList.add(packages);
            }while(cursor.moveToNext());
        }
        conn.close();
        return packagesList;
    }

    public Packages getPackage(Long packageID){
        Packages packages = new Packages();
        conn.open();
        Cursor cursor = conn.getDB().query(conn.getPackagesTable(), null, "packagesID = ?", new String[]{ String.valueOf(packageID) }, null, null, null, null);
        if(cursor.moveToFirst()){

            packages.setPackageID(cursor.getLong(cursor.getColumnIndex("packagesID")));
            packages.setName(cursor.getString(cursor.getColumnIndex("name")));
            packages.setOverview(cursor.getString(cursor.getColumnIndex("overview")));
            packages.setBenefits(cursor.getString(cursor.getColumnIndex("benefits")));
            packages.setEligible(cursor.getString(cursor.getColumnIndex("eligible")));
        }
        else{
            packages.setPackageID(Long.parseLong("0"));
        }
        conn.close();
        return packages;
    }
}
