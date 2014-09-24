package com.fypj.insightsLocal.ui_logic;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.util.SwipeGestureFilter;

/**
 * Created by jess on 25-Sep-14.
 */

public class ViewPioneerPackageActivity extends Activity implements SwipeGestureFilter.SimpleGestureListener {
    private SwipeGestureFilter detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pioneer_package);
        final TextView tvHeader = (TextView) findViewById(R.id.tv_header);
        final TextView tvContent = (TextView) findViewById(R.id.tv_content);
        RelativeLayout rlPackage = (RelativeLayout) findViewById(R.id.rl_package);
        tvHeader.setText("Overview");
        tvContent.setText("The Government has introduced the Pioneer Generation Package to honour and thank our pioneers for their hard work and dedication. They have made Singapore what it is today. About 450,000 Singaporeans will benefit from the Pioneer Generation Package.");
        // Detect touched area
        detector = new SwipeGestureFilter(this,this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_pioneer_package, menu);
        return true;
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
    @Override
    public boolean dispatchTouchEvent(MotionEvent me){
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }
    @Override
    public void onSwipe(int direction) {
        String str = "";

        switch (direction) {

            case SwipeGestureFilter.SWIPE_RIGHT : str = "Swipe Right";
                break;
            case SwipeGestureFilter.SWIPE_LEFT :  str = "Swipe Left";
                break;
            case SwipeGestureFilter.SWIPE_DOWN :  str = "Swipe Down";
                break;
            case SwipeGestureFilter.SWIPE_UP :    str = "Swipe Up";
                break;

        }
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDoubleTap() {
        Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
    }
}