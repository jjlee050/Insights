package com.fypj.insightsLocal.ui_logic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.sqlite_controller.PackagesSQLController;
import com.fypj.insightsLocal.sqlite_controller.UserPackagesSQLController;
import com.fypj.insightsLocal.util.ProfileListAdapter;
import com.fypj.mymodule.api.insightsUser.model.User;
import com.fypj.mymodule.api.insightsUserPackages.model.UserPackages;

import java.util.ArrayList;

/**
 * Created by L33525 on 26/9/2014.
 */
public class ViewProfileFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ViewProfileFragment newInstance(int sectionNumber) {
        ViewProfileFragment fragment = new ViewProfileFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ViewProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_profile, container, false);
        ListView lvProfileList = (ListView) rootView.findViewById(R.id.lv_profile_list);
        ArrayList<String> titleArrList = new ArrayList<String>();
        titleArrList.add("Basic Information");

        Bundle bundle = getArguments();
        User user = new User();
        user.setNric(bundle.getString("nric"));
        user.setName(bundle.getString("name"));
        user.setPassword(bundle.getString("password"));
        user.setAge(bundle.getInt("age"));
        user.setContactNo(bundle.getString("contactNo"));
        user.setAddress(bundle.getString("address"));

        UserPackagesSQLController controller = new UserPackagesSQLController(this.getActivity());
        PackagesSQLController packagesController = new PackagesSQLController(this.getActivity());
        ArrayList<UserPackages> userPackagesArrayList = controller.getUserPackagesByNRIC(user.getNric());
        for(int i=0;i<userPackagesArrayList.size();i++){
            System.out.println("Title: " + packagesController.getPackage(userPackagesArrayList.get(i).getPackagesID()).getName());
            titleArrList.add(packagesController.getPackage(userPackagesArrayList.get(i).getPackagesID()).getName());
        }

        ProfileListAdapter adapter = new ProfileListAdapter(ViewProfileFragment.this.getActivity(),android.R.id.text1,titleArrList,user);
        lvProfileList.setAdapter(adapter);
        return rootView;
    }
}
