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
import com.fypj.insightsLocal.util.ViewAllPioneerPackagesListAdapter;

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

        ViewAllPioneerPackagesListAdapter adapter = new ViewAllPioneerPackagesListAdapter(ViewAllPioneerPackagesActivity.this,android.R.id.text1,packagesArrList);
        lvPackagesList.setAdapter(adapter);
        lvPackagesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(ViewAllPioneerPackagesActivity.this, ViewPioneerPackageActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pioneer_packages,menu);
        return true;
    }
}
