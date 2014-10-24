package com.fypj.insightsLocal.ui_logic;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.fypj.insightsLocal.R;

import java.util.ArrayList;

public class CheckEligibilityActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_eligibility);
        TableLayout tableLayoutSubsidies = (TableLayout) findViewById(R.id.tableLayout_subsidies);

        ArrayList<String> leftArrList = new ArrayList<String>();
        leftArrList.add("Common illnesses:\n(e.g. cough and cold)");
        leftArrList.add("Simple Chronic conditions:\nunder CDMP");
        leftArrList.add("Complex Chronic conditions:\nunder CDMP");
        leftArrList.add("Selected dental services:");
        leftArrList.add("Health screening under\nHPB’s ISP4:");

        ArrayList<String> rightArrList = new ArrayList<String>();
        rightArrList.add("$28.50 per visit");
        rightArrList.add("$90 per visit,\ncapped at $360 per year");
        rightArrList.add("$135 per visit,\ncapped at $540 per year");
        rightArrList.add("$21 to $266.50\nper procedure\n(dependent on procedure)");
        rightArrList.add("Screening tests: Free \nwith HPB’s invitation letter;\nand Doctor’s consultation");


        for (int x = 0; x < leftArrList.size(); x++) {
            TextView tvLeft = new TextView(this);
            TextView tvRight = new TextView(this);
            tvLeft.setText(leftArrList.get(x));
            tvRight.setText(rightArrList.get(x));
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            tr.addView(tvLeft);
            tr.addView(tvRight);
            tvLeft.setPadding(10,10,10,10);
            tvRight.setPadding(10,10,10,10);
            tableLayoutSubsidies.addView(tr, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nearest_clinc, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == android.R.id.home) {
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}



