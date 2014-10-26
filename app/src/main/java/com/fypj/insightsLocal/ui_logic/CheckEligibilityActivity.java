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
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.sqlite_controller.PackagesSQLController;
import com.fypj.insightsLocal.util.CheckEligibilityListAdapter;
import com.fypj.insightsLocal.util.HomeListAdapter;
import com.fypj.mymodule.api.insightsPackages.model.Packages;

import java.util.ArrayList;

public class CheckEligibilityActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_eligibility);
        ListView packagesEligibileList = (ListView) findViewById(R.id.lv_eligibility_list);

        PackagesSQLController controller = new PackagesSQLController(this);
        ArrayList<Packages> packagesArrList = controller.getAllPackages();

        CheckEligibilityListAdapter adapter = new CheckEligibilityListAdapter(this,android.R.id.text1,packagesArrList);
        packagesEligibileList.setAdapter(adapter);
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



