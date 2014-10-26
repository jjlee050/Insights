package com.fypj.insightsLocal.ui_logic;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.sqlite_controller.SubsidiesSQLController;
import com.fypj.mymodule.api.insightsSubsidies.model.Subsidies;

import java.util.ArrayList;
import java.util.Locale;

public class SubsidesFragment extends Fragment {
    private final String ARG_SECTION_NUMBER = "section_number";
    ViewPioneerPackageActivity activity;

    /**
     * The {@link android.support.v4.view.ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    public SubsidesFragment newInstance(ViewPioneerPackageActivity activity,int sectionNumber, String name, Long packagesID) {
        SubsidesFragment fragment = new SubsidesFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putLong("packagesID", packagesID);
        this.activity = activity;
        fragment.setArguments(args);
        setHasOptionsMenu(true);
        return fragment;
    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.nearest_clinc, menu);
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
        return super.onOptionsItemSelected(item);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_pg_subsidies, container, false);
        TextView tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        View horizontalLine = rootView.findViewById(R.id.horizontal_line);
        ImageView ivImg = (ImageView) rootView.findViewById(R.id.iv_image);
        TableLayout tableLayoutSubsidies = (TableLayout) rootView.findViewById(R.id.tableLayout_subsidies);

        Bundle bundle = getArguments();

        String name = bundle.getString("name");
        if(name.equals("CHAS for Pioneer Generation")){
            ivImg.setImageResource(R.drawable.pioneercard);
        }
        else if(name.equals("CHAS Orange")){
            ivImg.setImageResource(R.drawable.orangecard);
        }
        else if(name.equals("CHAS Blue")){
            ivImg.setImageResource(R.drawable.bluecard);
        }        tvTitle.setText("Subsidies available: " + bundle.getString("name"));

        SubsidiesSQLController subsidiesController = new SubsidiesSQLController(this.getActivity());
        ArrayList<Subsidies> subsidiesArrList = subsidiesController.getSubsidiesByPackageID(bundle.getLong("packagesID"));

        for (int x = 0; x < subsidiesArrList.size(); x++) {
            TextView tvLeft = new TextView(this.getActivity());
            TextView tvRight = new TextView(this.getActivity());
            tvLeft.setText(subsidiesArrList.get(x).getName());
            tvRight.setText(subsidiesArrList.get(x).getAmt());
            TableRow tr = new TableRow(this.getActivity());
            tr.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            tr.addView(tvLeft);
            tr.addView(tvRight);
            tvLeft.setPadding(16,16,16,16);
            tvRight.setPadding(16,16,16,16);
            tableLayoutSubsidies.addView(tr, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        return rootView;
    }
}
