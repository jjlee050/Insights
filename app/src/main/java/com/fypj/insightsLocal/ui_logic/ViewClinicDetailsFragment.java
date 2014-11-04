package com.fypj.insightsLocal.ui_logic;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fypj.insightsLocal.R;
import com.fypj.mymodule.api.insightsClinics.model.Clinic;

/**
 * Created by L33524 on 22/9/2014.
 */
public class ViewClinicDetailsFragment extends Fragment{

    private final String ARG_SECTION_NUMBER = "section_number";
    ViewClinicActivity activity;
    private Clinic clinic = new Clinic();
    private TextView tvClinicName;
    private TextView tvClinicAddress;
    private TextView tvClinicOH;
    private TextView tvClinicContactNo;
    private Long ClinicID;
    private ImageButton imgBtn_;
    private String category;


    public ViewClinicDetailsFragment newInstance(ViewClinicActivity activity,int sectionNumber) {
        ViewClinicDetailsFragment fragment = new ViewClinicDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, 1);
        this.activity = activity;
        fragment.setArguments(args);
        return fragment;
    }

    public ViewClinicDetailsFragment() {
    }



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.view__clinic,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        else if( id == R.id.Appointment){
            Intent intent = new Intent(this.getActivity(), BookingAppt.class);

            intent.putExtra("clinicID",ClinicID);
            intent.putExtra("name", tvClinicName.getText().toString());
            intent.putExtra("address",tvClinicAddress.getText().toString());
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_clinic_details, container, false);
        Bundle bundle = getArguments();

        tvClinicName = (TextView) rootView.findViewById(R.id.tv_Clinic_name);
        tvClinicAddress = (TextView) rootView.findViewById(R.id.tv_Clinic_Address);
        tvClinicOH = (TextView) rootView.findViewById(R.id.tv_ClinicOH);
        tvClinicContactNo = (TextView) rootView.findViewById(R.id.tv_Clinic_Contact);
        imgBtn_ = (ImageButton) rootView.findViewById(R.id.imgBtn_latest_events);


        tvClinicName.setText(bundle.getString("name"));
        tvClinicAddress.setText(bundle.getString("address"));
        tvClinicOH.setText(bundle.getString("operatingHours"));
        tvClinicContactNo.setText(bundle.getString("contactNo"));

        ClinicID = bundle.getLong("clinicID");
        category = bundle.getString("category");
        if (category.equals("Medical"))
        {
         imgBtn_.setImageResource(R.drawable.pic_medical);
        }
        else if (category.equals("Dental"))
        {
            imgBtn_.setImageResource(R.drawable.teeth);
        }

        return rootView;
    }





}
