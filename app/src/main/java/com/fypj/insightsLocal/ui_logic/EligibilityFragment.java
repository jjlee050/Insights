package com.fypj.insightsLocal.ui_logic;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fypj.insightsLocal.R;

import java.util.Locale;

public class EligibilityFragment extends Fragment {
    private final String ARG_SECTION_NUMBER = "section_number";
    ViewPioneerPackageActivity activity;

    /**
     * The {@link android.support.v4.view.ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    public EligibilityFragment newInstance(ViewPioneerPackageActivity activity,int sectionNumber) {
        EligibilityFragment fragment = new EligibilityFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, 3);
        this.activity = activity;
        fragment.setArguments(args);
        setHasOptionsMenu(true);
        return fragment;
    }
    public EligibilityFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_view_pg_eligibility, container, false);
        TextView tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        TextView tvHeader = (TextView) rootView.findViewById(R.id.tv_header);
        View horizontalLine = rootView.findViewById(R.id.horizontal_line);
        TextView tvContent = (TextView) rootView.findViewById(R.id.tv_content);
        TextView tvContent1 = (TextView) rootView.findViewById(R.id.tv_content1);
        ImageView ivImg = (ImageView) rootView.findViewById(R.id.iv_image);


        tvContent1.setClickable(true);
        tvContent1.setMovementMethod(LinkMovementMethod.getInstance());



        tvTitle.setText("Eligibility for Pioneer Generation Package");
        tvHeader.setText("The package will help Pioneers with their healthcare costs for life. The benefits are as below: ");


            tvContent.setText(Html.fromHtml("In order to apply CHAS for Pioneer Generation, Living Singapore Citizens <b>who meet 2 criteria:</b> <br /><br />" +

                    "1. Aged 16 and above in 1965 - this means:<br />" +
                    " &nbsp; &nbsp;&nbsp;&nbsp;• Born on or before 31 December 1949<br />" +
                    " &nbsp; &nbsp;&nbsp;&nbsp;• Aged 65 and above in 2014<br /><br />"+
                    "2. Obtained citizenship on or before 31 December 1986."));
           tvContent1.setText(Html.fromHtml("Those eligible for the Pioneer Generation Package would have received a notification letter in June 2014. Please keep your NRIC address updated.<br /><br />" +
                    "<b>For more information:</b> Call the Pioneer Generation hotline at 1800-2222-888 or visit <a href=http://www.cpf.gov.sg/pioneers/pgp_Faq.asp> the website</a>." ));


        horizontalLine.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        tvHeader.setVisibility(View.GONE);
        tvContent.setVisibility(View.VISIBLE);
        tvContent1.setVisibility(View.VISIBLE);
        ivImg.setVisibility(View.VISIBLE);

        return rootView;
    }
}
