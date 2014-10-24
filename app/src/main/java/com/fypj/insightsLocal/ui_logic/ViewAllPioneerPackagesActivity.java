package com.fypj.insightsLocal.ui_logic;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.sqlite_controller.PackagesSQLController;
import com.fypj.insightsLocal.util.ViewAllPioneerPackagesListAdapter;
import com.fypj.mymodule.api.insightsPackages.model.Packages;

import java.util.ArrayList;

/**
 * Created by jess on 20-Sep-14.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ViewAllPioneerPackagesActivity extends Activity {

    public ViewAllPioneerPackagesActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_pioneer_packages);
        getActionBar().setTitle("View Pioneer Packages");

        final ListView lvPackagesList = (ListView) findViewById(R.id.lv_packages_list);

        final ArrayList<String> packagesArrList = new ArrayList<String>();
        packagesArrList.add("CHAS for Pioneer Generation");
        packagesArrList.add("CHAS Orange");
        packagesArrList.add("CHAS Blue");

        insertPackages();

        ViewAllPioneerPackagesListAdapter adapter = new ViewAllPioneerPackagesListAdapter(ViewAllPioneerPackagesActivity.this,android.R.id.text1,packagesArrList);
        lvPackagesList.setAdapter(adapter);
        lvPackagesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(ViewAllPioneerPackagesActivity.this, ViewPioneerPackageActivity.class);
                System.out.println("Selected Position: " + position);

                PackagesSQLController controller = new PackagesSQLController(ViewAllPioneerPackagesActivity.this);
                String id = String.valueOf(position + 1);
                Packages packages = controller.getPackage(Long.parseLong(id));
                if(packages != null) {
                    intent.putExtra("name",packages.getName());
                    intent.putExtra("overview",packages.getOverview());
                    intent.putExtra("benefits",packages.getBenefits());
                    intent.putExtra("eligible",packages.getEligible());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pioneer_packages,menu);
        return true;
    }

    public void insertPackages(){
        PackagesSQLController controller = new PackagesSQLController(this);
        ArrayList<Packages> packagesArrayList = controller.getAllPackages();
        Packages package1 = new Packages();
        package1.setName("CHAS for Pioneer Generation");
        package1.setOverview("<p>The Government has introduced the Pioneer Generation Package to honour and thank our pioneers for their hard work and dedication. They have made Singapore what it is today.</p><p>About 450,000 Singaporeans will benefit from the Pioneer Generation Package.</p>");
        package1.setBenefits("<u> OutPatient Care </u><p>The package will help Pioneers with their healthcare costs for life. The benefits are as below: </p><p><b> Additional 50% off subsidised services </b> at polyclinics and Specialist Outpatient Clinics. </p><p><b> Additional 50% off subsidised medications </b> at polyclinics and Specialist Outpatient Clinics. </p><u>MediShield Life</u><p>Support for all Pioneers’ MediShield Life Premiums with special premium subsidies and Medisave top-ups.</p><p>Age 80 and above* : <b> Premiums fully covered </b></p><p>Age 65 to 79* : <b> Pay half of current premiums </b></p>");
        package1.setEligible("<p>The package will help Pioneers with their healthcare costs for life. The benefits are as below: </p><p>In order to apply CHAS for Pioneer Generation, Living Singapore Citizens <b>who meet 2 criteria:</b> <br /><br />1. Aged 16 and above in 1965 - this means:<br />&nbsp; &nbsp;&nbsp;&nbsp;• Born on or before 31 December 1949<br />&nbsp; &nbsp;&nbsp;&nbsp;• Aged 65 and above in 2014<br /><br />2. Obtained citizenship on or before 31 December 1986.</p><p>Those eligible for the Pioneer Generation Package would have received a notification letter in June 2014. Please keep your NRIC address updated.<br /><br /><b>For more information:</b> Call the Pioneer Generation hotline at 1800-2222-888 or visit <a href=http://www.cpf.gov.sg/pioneers/pgp_Faq.asp> the website</a>.</p>");



        Packages package2 = new Packages();
        Packages package3 = new Packages();
        if(packagesArrayList.size() < 1) {
            controller.insertPackages(package1);
        }
    }
}
