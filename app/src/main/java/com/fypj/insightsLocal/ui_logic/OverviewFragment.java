package com.fypj.insightsLocal.ui_logic;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fypj.insightsLocal.R;

/**
 * Created by L33525 on 28/10/2014.
 */
public class OverviewFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    ViewPioneerPackageActivity activity;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public OverviewFragment newInstance(ViewPioneerPackageActivity activity, int sectionNumber, String name, String overview) {
        OverviewFragment fragment = new OverviewFragment();
        Bundle args = new Bundle();
        OverviewFragment.this.activity = activity;
        args.putString("name",name);
        args.putString("overview",overview);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_pioneer_package, container, false);
        TextView tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        TextView tvHeader = (TextView) rootView.findViewById(R.id.tv_header);
        View horizontalLine = rootView.findViewById(R.id.horizontal_line);
        TextView tvContent = (TextView) rootView.findViewById(R.id.tv_content);
        ImageView ivImg = (ImageView) rootView.findViewById(R.id.iv_image);
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
        }
        tvTitle.setText(bundle.getString("name"));
        tvHeader.setText("The package will help Pioneers with their healthcare costs for life. The benefits are as below: ");

        tvContent.setText(Html.fromHtml(bundle.getString("overview")));

        horizontalLine.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        tvHeader.setVisibility(View.GONE);
        tvContent.setVisibility(View.VISIBLE);
        ivImg.setVisibility(View.VISIBLE);

        return rootView;
    }
}
