package com.fypj.insightsLocal.ui_logic;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.sqlite_controller.PackagesSQLController;
import com.fypj.insightsLocal.sqlite_controller.SubsidiesSQLController;
import com.fypj.insightsLocal.util.ViewAllPioneerPackagesListAdapter;
import com.fypj.mymodule.api.insightsPackages.model.Packages;
import com.fypj.mymodule.api.insightsSubsidies.model.Subsidies;

import java.util.ArrayList;

/**
 * Created by jess on 20-Sep-14.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ViewAllPioneerPackagesActivity extends ActionBarActivity {
    public ViewAllPioneerPackagesActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_pioneer_packages);

        getSupportActionBar().setTitle("View Pioneer Packages");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ListView lvPackagesList = (ListView) findViewById(R.id.lv_packages_list);

        final ArrayList<String> packagesArrList = new ArrayList<String>();
        packagesArrList.add("CHAS for Pioneer Generation");
        packagesArrList.add("CHAS Orange");
        packagesArrList.add("CHAS Blue");

        ViewAllPioneerPackagesListAdapter adapter = new ViewAllPioneerPackagesListAdapter(ViewAllPioneerPackagesActivity.this,android.R.id.text1,packagesArrList);
        lvPackagesList.setAdapter(adapter);
        lvPackagesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(ViewAllPioneerPackagesActivity.this, ViewPioneerPackageActivity.class);
                System.out.println("Selected Position: " + position);

                PackagesSQLController controller = new PackagesSQLController(ViewAllPioneerPackagesActivity.this);

                String id = String.valueOf(position + 1);
                System.out.println("Package ID: " + id);
                Packages packages = controller.getPackage(Long.parseLong(id));
                if(packages != null) {
                    intent.putExtra("packagesID",packages.getPackageID());
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
}
