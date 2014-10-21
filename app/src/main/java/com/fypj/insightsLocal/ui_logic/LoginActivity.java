package com.fypj.insightsLocal.ui_logic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.controller.GetUser;

/**
 * Created by jess on 20-Oct-14.
 */
public class LoginActivity extends ActionBarActivity {
    private EditText etNric, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getActionBar().hide();
        getData();
        etNric = (EditText) findViewById(R.id.et_nric);
        etPassword = (EditText) findViewById(R.id.et_password);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if((etNric.getText().toString().equals("")) || (etPassword.getText().toString().equals(""))) {
                    if(etNric.getText().toString().equals("")) {
                        etNric.setError("Please enter your nric");
                    }
                    if(etPassword.getText().toString().equals("")) {
                        etPassword.setError("Please enter your password");
                    }
                }
                else{
                    new GetUser(this,etNric.getText().toString(),etPassword.getText().toString()).execute();
                }
                break;
        }
    }

    public void getData(){
        SharedPreferences sharedPref= getSharedPreferences("insightsPreferences", 0);
        String nric = sharedPref.getString("nric", "");
        String password = sharedPref.getString("password", "");
        if((!nric.equals("")) && (!password.equals(""))){
            Intent i = new Intent(this, MainPageActivity.class);
            startActivity(i);
            finish();
        }
    }

}
