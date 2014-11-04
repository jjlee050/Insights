package com.fypj.insightsLocal.service;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.controller.GetEvent;
import com.fypj.insightsLocal.controller.GetMedicalHistory;
import com.fypj.insightsLocal.controller.GetUserPackages;
import com.fypj.insightsLocal.controller.GetUserSubsidies;
import com.fypj.insightsLocal.options.CheckNetworkConnection;
import com.fypj.insightsLocal.sqlite_controller.EventSQLController;
import com.fypj.insightsLocal.sqlite_controller.PackagesSQLController;
import com.fypj.insightsLocal.sqlite_controller.SubsidiesSQLController;
import com.fypj.insightsLocal.ui_logic.ViewAllLatestEventsActivity;
import com.fypj.insightsLocal.util.HandleXML;
import com.fypj.mymodule.api.insightsEvent.model.Event;
import com.fypj.mymodule.api.insightsPackages.model.Packages;
import com.fypj.mymodule.api.insightsSubsidies.model.Subsidies;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BackgroundService extends Service {
    public BackgroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("Connection Checker", "Connection Checker started");
        insertPackages();

        SharedPreferences sharedPref = getSharedPreferences("insightsPreferences", Context.MODE_PRIVATE);
        String nric = sharedPref.getString("nric", "");

        if(CheckNetworkConnection.isNetworkConnectionAvailable(this)) {

            if (!nric.equals("")){
                new GetUserPackages(this, nric).execute();
                new GetUserSubsidies(this, nric).execute();
            }

            GetMedicalHistory getMedicalHistory = new GetMedicalHistory(this);
            getMedicalHistory.execute();
            GetEvent getEvent = new GetEvent(this);
            getEvent.execute();

            /*HandleXML obj = new HandleXML("http://www.pa.gov.sg/index.php?option=com_events&view=events&rss=1&Itemid=170", this);
            obj.fetchXML();*/
        }
        notifyUser();
        return super.onStartCommand(intent, flags, startId);
    }

    public void notifyUser(){
        EventSQLController controller = new EventSQLController(this);
        ArrayList<Event> eventArrList = controller.getAllEvent();
        if((eventArrList != null) && (eventArrList.size() > 0)){

// Instantiate a Builder object.
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setContentTitle("Today's Events")
                    .setContentText("Scroll down to view today's events.")
                    .setTicker("Today's events")
                    .setSmallIcon(R.drawable.hearts_logo);
            NotificationCompat.InboxStyle inboxStyle =
                    new NotificationCompat.InboxStyle();
            String[] events = new String[6];
// Sets a title for the Inbox in expanded layout
            inboxStyle.setBigContentTitle("Today's Event:");
            boolean hasEvent = false;
            for(int i=0;i<eventArrList.size();i++){
                DateFormat df = new SimpleDateFormat("dd MMMM yyyy h:mma");
                try {
                    Date dt = df.parse(eventArrList.get(0).getDateAndTime().substring(0, eventArrList.get(0).getDateAndTime().lastIndexOf("to") - 1));
                    Date dnow = new Date();
                    Calendar ca = Calendar.getInstance();
                    Calendar cnow = Calendar.getInstance();
                    ca.setTime(dt);
                    cnow.setTime(dnow);
                    int differenceInDays = (int) Math.floor((ca.getTimeInMillis()-cnow.getTimeInMillis())/-86400000);
                    int days = -86400000 * differenceInDays;
                    if (cnow.getTimeInMillis() - ca.getTimeInMillis() < 0) {
                        // Moves events into the expanded layout
                        inboxStyle.addLine(eventArrList.get(i).getName());
                        hasEvent = true;
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }

// Moves the expanded layout object into the notification object.
            builder.setStyle(inboxStyle);
// Creates an Intent for the Activity
            Intent notifyIntent =
                    new Intent(this, ViewAllLatestEventsActivity.class);
// Sets the Activity to start in a new, empty task
            notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
// Creates the PendingIntent
            PendingIntent notifyingIntent =
                    PendingIntent.getActivity(
                            this,
                            0,
                            notifyIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );

// Puts the PendingIntent into the notification builder
            builder.setContentIntent(notifyingIntent);
// Notifications are issued by sending them to the
// NotificationManager system service.
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// Builds an anonymous Notification object from the builder, and
// passes it to the NotificationManager
            if(hasEvent) {
                mNotificationManager.notify(0, builder.build());
            }
        }
    }

    public void insertPackages(){
        PackagesSQLController packagesController = new PackagesSQLController(this);
        SubsidiesSQLController subsidiesController = new SubsidiesSQLController(this);

        Packages package1 = new Packages();
        package1.setPackageID(Long.parseLong("1"));
        package1.setName("CHAS for Pioneer Generation");
        package1.setOverview("<p>The Government has introduced the Pioneer Generation Package to honour and thank our pioneers for their hard work and dedication. They have made Singapore what it is today.</p><p>About 450,000 Singaporeans will benefit from the Pioneer Generation Package.</p>");
        package1.setBenefits("<u> Outpatient Care </u><p>The package will help Pioneers with their healthcare costs for life. The benefits are as below: </p><p><b> Additional 50% off subsidised services </b> at polyclinics and Specialist Outpatient Clinics. </p><p><b> Additional 50% off subsidised medications </b> at polyclinics and Specialist Outpatient Clinics. </p><br/><br/><u>Medisave Top-ups</u><p>$200 to $800 annually for life. (July 2014). More for older cohorts:</p><p>Born 1934 or earlier: $800</p><p>Born 1935 - 1939 $600</p><p>Born 1940 - 1944: $400</p><p>Born 1945 - 1949: $200</p><br/><br/><u>MediShield Life</u><p>Support for all Pioneers’ MediShield Life Premiums with special premium subsidies and Medisave top-ups.</p><p>Age 80 and above* : <b> Premiums fully covered </b></p><p>Age 65 to 79* : <b> Pay half of current premiums </b></p>");
        package1.setEligible("<p>The package will help Pioneers with their healthcare costs for life. The benefits are as below: </p><p>In order to apply CHAS for Pioneer Generation, Living Singapore Citizens <b>who meet 2 criteria:</b> <br /><br />1. Aged 16 and above in 1965 - this means:<br />&nbsp; &nbsp;&nbsp;&nbsp;• Born on or before 31 December 1949<br />&nbsp; &nbsp;&nbsp;&nbsp;• Aged 65 and above in 2014<br /><br />2. Obtained citizenship on or before 31 December 1986.</p><p>Those eligible for the Pioneer Generation Package would have received a notification letter in June 2014. Please keep your NRIC address updated.<br /><br /><b>For more information:</b> Call the Pioneer Generation hotline at 1800-2222-888 or visit <a href=http://www.cpf.gov.sg/pioneers/pgp_Faq.asp> the website</a>.</p>");

        Packages package2 = new Packages();
        package2.setPackageID(Long.parseLong("2"));
        package2.setName("CHAS Orange");
        package2.setOverview("<p>CHAS is a scheme that enables Singapore Citizens from lower- and middle-income households to receive subsidies for medical and dental care at participating General Practitioners (GPs) and dental clinics near their homes.</p><p>Singapore Citizens who qualify for CHAS will receive either an individual blue or orange Health Assist card.</p>");
        package2.setBenefits("<p>CHAS enables Singapore Citizens from lower- and middle-income households to receive subsidies for medical and dental care at participating General Practitioners (GPs) and dental clinics near their homes.</p><br/><p>Singapore Citizens who qualify for CHAS will receive an individual blue or orange Health Assist card. Health Assist cardholders will also enjoy subsidised referrals to Specialist Outpatient Clinics (SOCs) located at Public Hospitals or National Dental Centre when required.</p><br/><p>For patients with chronic conditions, CHAS complements the Chronic Disease Management Programme (CDMP), which allows for Medisave to be used for outpatient treatment (for the same set of chronic conditions) covered under CHAS. Besides enjoying CHAS subsidies for treatment of their chronic conditions, patients can also tap on their Medisave to defray part of the cost of these treatments. The Medisave withdrawal limit is $400 per Medisave account, per year.</p>");
        package2.setEligible("<p>To qualify for CHAS, applicants must be Singapore Citizens and meet the following criteria:</p>\n<p>• For households with income, the household monthly income per person must be $1,800 and below^.\n</p><p>• For households with no income, the Annual Value (AV) of residence as reflected on the NRIC must be $21,000 and below^^.</p><br/><p>^Household monthly income per person is the total gross household monthly income divided by total number of family members living together. Gross monthly income refers to your basic income, overtime pay, allowances, cash awards, commissions and bonuses.</p><br/><p>^^ AV is the estimated annual rent of your residence if it is rented out. An AV of up to $21,000 will cover all HDB flats and some lower-value private residences.</p></br><p>Members of the public who are on the Public Assistance (PA) scheme do not need to apply as they are already eligible for CHAS.</p><br/><br/><p>For instance, a family of four living at the same address with a total gross household monthly income of $7,200 (household monthly income per person is $7,200 ÷ 4 = $1,800) will qualify for CHAS, provided that they are all Singapore Citizens.</p>");

        Packages package3 = new Packages();
        package3.setPackageID(Long.parseLong("3"));
        package3.setName("CHAS Blue");
        package3.setOverview("<p>CHAS is a scheme that enables Singapore Citizens from lower- and middle-income households to receive subsidies for medical and dental care at participating General Practitioners (GPs) and dental clinics near their homes.</p><p>Singapore Citizens who qualify for CHAS will receive either an individual blue or orange Health Assist card.</p>");
        package3.setBenefits("<p>CHAS enables Singapore Citizens from lower- and middle-income households to receive subsidies for medical and dental care at participating General Practitioners (GPs) and dental clinics near their homes.</p><br/><p>Singapore Citizens who qualify for CHAS will receive an individual blue or orange Health Assist card. Health Assist cardholders will also enjoy subsidised referrals to Specialist Outpatient Clinics (SOCs) located at Public Hospitals or National Dental Centre when required.</p><br/><p>For patients with chronic conditions, CHAS complements the Chronic Disease Management Programme (CDMP), which allows for Medisave to be used for outpatient treatment (for the same set of chronic conditions) covered under CHAS. Besides enjoying CHAS subsidies for treatment of their chronic conditions, patients can also tap on their Medisave to defray part of the cost of these treatments. The Medisave withdrawal limit is $400 per Medisave account, per year.</p>");
        package3.setEligible("<p>To qualify for CHAS, applicants must be Singapore Citizens and meet the following criteria:</p>\n<p>• For households with income, the household monthly income per person must be $1,000 and below^.\n</p><p>• For households with no income, the Annual Value (AV) of residence as reflected on the NRIC must be $13,000 and below^^.</p><br/><p>^Household monthly income per person is the total gross household monthly income divided by total number of family members living together. Gross monthly income refers to your basic income, overtime pay, allowances, cash awards, commissions and bonuses.</p><br/><p>^^ AV is the estimated annual rent of your residence if it is rented out. An AV of up to $21,000 will cover all HDB flats and some lower-value private residences.</p></br><p>Members of the public who are on the Public Assistance (PA) scheme do not need to apply as they are already eligible for CHAS.</p><br/><br/><p>For instance, a family of four living at the same address with a total gross household monthly income of $4,000 (household monthly income per person is $4,000 ÷ 4 = $1,000) will qualify for CHAS, provided that they are all Singapore Citizens.</p>");

        if(packagesController.getPackage(Long.parseLong("1")).getPackageID().equals(Long.parseLong("0"))) {
            packagesController.insertPackages(package1);
        }
        if(packagesController.getPackage(Long.parseLong("2")).getPackageID().equals(Long.parseLong("0"))) {
            packagesController.insertPackages(package2);
        }
        if(packagesController.getPackage(Long.parseLong("3")).getPackageID().equals(Long.parseLong("0"))) {
            packagesController.insertPackages(package3);
        }



        ArrayList<String> package1SubsidiesNameArrList = new ArrayList<String>();
        package1SubsidiesNameArrList.add("Common illnesses:\n(e.g. cough and cold)");
        package1SubsidiesNameArrList.add("Simple Chronic conditions:\nunder CDMP");
        package1SubsidiesNameArrList.add("Complex Chronic conditions:\nunder CDMP");
        package1SubsidiesNameArrList.add("Selected dental services:");
        package1SubsidiesNameArrList.add("Health screening under\nHPB’s ISP4:");

        ArrayList<String> package1SubsidiesAmtArrList = new ArrayList<String>();
        package1SubsidiesAmtArrList.add("$28.50 per visit");
        package1SubsidiesAmtArrList.add("$90 per visit,\ncapped at $360 \nper year");
        package1SubsidiesAmtArrList.add("$135 per visit,\ncapped at $540 \nper year");
        package1SubsidiesAmtArrList.add("$21 to $266.50\nper procedure\n(dependent on \nprocedure)");
        package1SubsidiesAmtArrList.add("Screening tests: Free \nwith HPB’s invitation \nletter; and \nDoctor’s consultation");

        ArrayList<String> package2SubsidiesNameArrList = new ArrayList<String>();
        package2SubsidiesNameArrList.add("Common illnesses:\n(e.g. cough and cold)");
        package2SubsidiesNameArrList.add("Simple Chronic conditions:\nunder CDMP");
        package2SubsidiesNameArrList.add("Complex Chronic conditions:\nunder CDMP");
        package2SubsidiesNameArrList.add("Selected dental services:");
        package2SubsidiesNameArrList.add("Health screening under\nHPB’s ISP4:");

        ArrayList<String> package2SubsidiesAmtArrList = new ArrayList<String>();
        package2SubsidiesAmtArrList.add("Not applicable");
        package2SubsidiesAmtArrList.add("$50 per visit,\ncapped at $200 \nper year");
        package2SubsidiesAmtArrList.add("$75 per visit,\ncapped at $300 \nper year");
        package2SubsidiesAmtArrList.add("$65.50 to $170.50 per procedure\n(for crowning, denture & root canal treatments only)");
        package2SubsidiesAmtArrList.add("Screening test: Free with HPB’s invitation letter; and\nDoctor’s consultation: $18.50 per visit\n(up to 2 times per year)");


        ArrayList<String> package3SubsidiesNameArrList = new ArrayList<String>();
        package3SubsidiesNameArrList.add("Common illnesses:\n(e.g. cough and cold)");
        package3SubsidiesNameArrList.add("Simple Chronic conditions:\nunder CDMP");
        package3SubsidiesNameArrList.add("Complex Chronic conditions:\nunder CDMP");
        package3SubsidiesNameArrList.add("Selected dental services:");
        package3SubsidiesNameArrList.add("Health screening under\nHPB’s ISP4:");

        ArrayList<String> package3SubsidiesAmtArrList = new ArrayList<String>();
        package3SubsidiesAmtArrList.add("$18.50 per visit");
        package3SubsidiesAmtArrList.add("$80 per visit,\ncapped at $320 \nper year");
        package3SubsidiesAmtArrList.add("$120 per visit,\ncapped at $480 \nper year");
        package3SubsidiesAmtArrList.add("$11 to $256.50\nper procedure\n(dependent on \nprocedure)");
        package3SubsidiesAmtArrList.add("Screening test: Free with HPB’s invitation letter; and\nDoctor’s consultation: $18.50 per visit\n(up to 2 times per year)");


        for(int i=0;i<package1SubsidiesNameArrList.size();i++){
            Subsidies subsidies = new Subsidies();
            subsidies.setSubsidiesID(Long.parseLong(String.valueOf(i + 1)));
            subsidies.setName(package1SubsidiesNameArrList.get(i));
            subsidies.setAmt(package1SubsidiesAmtArrList.get(i));
            subsidies.setPackagesID(Long.parseLong("1"));
            if(subsidiesController.getSubsidies(Long.parseLong(String.valueOf(i + 1))).getSubsidiesID().equals(Long.parseLong("0"))){
                subsidiesController.insertSubsidy(subsidies);
            }
        }

        for(int i=0;i<package2SubsidiesNameArrList.size();i++){
            Subsidies subsidies = new Subsidies();
            subsidies.setSubsidiesID(Long.parseLong(String.valueOf(i + 6)));
            subsidies.setName(package2SubsidiesNameArrList.get(i));
            subsidies.setAmt(package2SubsidiesAmtArrList.get(i));
            subsidies.setPackagesID(Long.parseLong("2"));
            if(subsidiesController.getSubsidies(Long.parseLong(String.valueOf(i + 6))).getSubsidiesID().equals(Long.parseLong("0"))){
                subsidiesController.insertSubsidy(subsidies);
            }
        }

        for(int i=0;i<package3SubsidiesNameArrList.size();i++){
            Subsidies subsidies = new Subsidies();
            subsidies.setSubsidiesID(Long.parseLong(String.valueOf(i + 11)));
            subsidies.setName(package3SubsidiesNameArrList.get(i));
            subsidies.setAmt(package3SubsidiesAmtArrList.get(i));
            subsidies.setPackagesID(Long.parseLong("3"));
            if(subsidiesController.getSubsidies(Long.parseLong(String.valueOf(i + 11))).getSubsidiesID().equals(Long.parseLong("0"))){
                subsidiesController.insertSubsidy(subsidies);
            }
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
