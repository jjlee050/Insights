package com.fypj.insightsLocal.sqlite_controller;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jess on 18-Oct-14.
 */
public class SQLiteController {
    private static final String database_name = "insights";
    private static final String database_event = "event";
    private static final String database_user = "user";
    private static final String database_clinic = "clinics";
    private static final String database_user_medical_histories = "user_medical_histories";
    private static final String database_packages = "packages";
    private static final String database_subsidies = "subsidies";
    private static final String database_user_packages = "user_packages";
    private static final String database_user_subsidies = "user_subsidies";
    private static final String database_appointment = "Appointment";
    private static final int database_version = 1;
    private DBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    private static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, database_name, null, database_version);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            /*db.execSQL("CREATE TABLE "
                    + database_user
                    + "(nric TEXT PRIMARY KEY, name TEXT, type TEXT, password TEXT, contactNo TEXT, address TEXT, email TEXT, active INTEGER, points INTEGER)");
            db.execSQL("CREATE TABLE "
                    + database_article
                    + "(articleID INTEGER PRIMARY KEY, title TEXT, content TEXT, dateTime DATETIME, category TEXT, location TEXT, userNRIC TEXT, active INTEGER, approved INTEGER)");
            db.execSQL("CREATE TABLE "
                    + database_comments
                    + "(postID INTEGER PRIMARY KEY, userNRIC TEXT, content TEXT, dateTime DATETIME, location TEXT)");*/
            db.execSQL("CREATE TABLE "
                    + database_event
                    + "(eventID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, dateAndTime TEXT, guestOfHonour TEXT, desc TEXT, organizer TEXT, contactNo TEXT, location TEXT)");
            db.execSQL("CREATE TABLE "
                    + database_user
                    + "(userID INTEGER PRIMARY KEY AUTOINCREMENT, nric TEXT, name TEXT, password TEXT, age TEXT, contactNo TEXT, address TEXT, firstTimeSignIn INTEGER, houseHoldMonthlyIncome REAL, annualValueOfResidence REAL)");
            db.execSQL("CREATE TABLE "
                    + database_user_medical_histories
                    + "(medicalHistoryID LONG PRIMARY KEY, clinicID LONG, nric TEXT, date TEXT, time TEXT, service TEXT, amt REAL)");
            db.execSQL("CREATE TABLE "
                    + database_clinic
                    + "(clinicID INTEGER PRIMARY KEY, name TEXT, address TEXT, operatingHours TEXT, contactNo TEXT, category TEXT)");
            db.execSQL("CREATE TABLE "
                    + database_packages
                    + "(packagesID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, overview TEXT, benefits TEXT, eligible TEXT)");
            db.execSQL("CREATE TABLE "
                    + database_subsidies
                    + "(subsidiesID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, amt TEXT, packagesID INTEGER)");
            db.execSQL("CREATE TABLE "
                    + database_user_packages
                    + "(userPackageID LONG PRIMARY KEY, nric TEXT, packagesID INTEGER)");
            db.execSQL("CREATE TABLE "
                    + database_user_subsidies
                    + "(userSubsidiesID LONG PRIMARY KEY, nric TEXT, subsidiesID INTEGER, balance REAL)");
            db.execSQL("CREATE TABLE "
                    + database_appointment
                    + "(appointmentID INTEGER PRIMARY KEY, nric TEXT, name TEXT , contactNo Text, Date Text , Time Text)");
           /* db.execSQL("CREATE TABLE "
                    + database_fb_comments
                    + "(fbPostID TEXT PRIMARY KEY, name TEXT, comment TEXT, time DATETIME)");
            db.execSQL("CREATE TABLE "
                    + database_groupmember
                    + "(groupID INTEGER PRIMARY KEY, userNRIC TEXT, dateTImeJoined DATETIME , active INTEGER)");
            db.execSQL("CREATE TABLE "
                    + database_post
                    + "(postID INTEGER PRIMARY KEY, datetime DATETIME, content TEXT , location TEXT, groupID INTEGER)");
            db.execSQL("CREATE TABLE "
                    + database_hobby
                    + "(groupID INTEGER PRIMARY KEY AUTOINCREMENT, groupName TEXT, category TEXT, location TEXT, description TEXT, groupImg BLOB, active INTEGER)");
            db.execSQL("CREATE TABLE "
                    + database_riddle
                    + "(riddleID INTEGER PRIMARY KEY AUTOINCREMENT, riddleTitle TEXT, riddleContent TEXT, riddleAnsID INTEGER, riddlePoints INTEGER)");
            db.execSQL("CREATE TABLE "
                    + database_riddle_answered
                    + "(riddleAnsID INTEGER PRIMARY KEY AUTOINCREMENT, riddleID INTEGER, nric TEXT, riddleAns TEXT, riddleStatus TEXT)");*/
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS " + database_name);
            onCreate(db);
        }
    }

    public SQLiteController(Context context) {
        ourContext = context;
    }

    public SQLiteController open() throws SQLException {
        ourHelper = new DBHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        ourHelper.close();
    }

    public SQLiteDatabase getDB(){
        return ourDatabase;
    }

    public String getEventTable(){
        return database_event;
    }

    public String getUserTable(){
        return database_user;
    }

    public String getUserMedicalHistoriesTable(){
        return database_user_medical_histories;
    }

    public String getClinicTable(){ return database_clinic; }

    public String getPackagesTable(){ return database_packages; }

    public String getSubsidiesTable(){ return database_subsidies; }

    public String getUserPackagesTable(){ return database_user_packages; }

    public String getUserSubsidiesTable(){ return database_user_subsidies; }

    public String getAppointmentTable(){ return database_appointment; }
}