package com.fypj.insightsLocal.ui_logic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.controller.GetUser;
import com.fypj.insightsLocal.options.CheckNetworkConnection;
import com.fypj.insightsLocal.sqlite_controller.UserSQLController;
import com.fypj.mymodule.api.insightsUser.model.User;

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
                    if(CheckNetworkConnection.isNetworkConnectionAvailable(this)) {
                        new GetUser(this, etNric.getText().toString(), etPassword.getText().toString()).execute();
                    }
                    else{
                        UserSQLController controller = new UserSQLController(this);
                        String nric = etNric.getText().toString();
                        User user = controller.getUser(nric);
                        if(user != null){
                            if(etPassword.getText().toString().equals(user.getPassword())){
                                int status = controller.getUserSignInStatus(nric);
                                if(status == 0) {
                                    final AlertDialog alertDialog = createAlertDialogForPreferredLanguage(controller,user);
                                    // show it
                                    alertDialog.show();
                                    alertDialog.setCanceledOnTouchOutside(false);
                                }
                                else if(status == 1){
                                    goToMainPage();
                                }
                            }
                            else{
                                errorOnExecuting();
                            }
                        }
                        else{
                            errorOnExecuting();
                        }
                    }
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


    public AlertDialog createAlertDialogForPreferredLanguage(final UserSQLController controller, User foundUser){

        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dialog_set_preferred_language, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);

        // set dialog message
        alertDialogBuilder.setTitle("First-Time Login");
        // create alert dialog

        final Spinner mSpinner= (Spinner) promptsView.findViewById(R.id.sp_language);
        ArrayAdapter<String> spLanguageArrAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.language));
        mSpinner.setAdapter(spLanguageArrAdapter);

        final User finalFoundUser = foundUser;
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                controller.updateSignInStatus(finalFoundUser,1);
                controller.updatePreferredLanguage(finalFoundUser,mSpinner.getSelectedItem().toString());
                goToMainPage();
            }
        });
        return alertDialogBuilder.create();
    }

    public void goToMainPage(){
        writeData(etNric.getText().toString(),etPassword.getText().toString());
        Intent i = new Intent(this, MainPageActivity.class);
        startActivity(i);
        finish();
    }


    public void writeData(String nric, String password){
        SharedPreferences sharedPref = getSharedPreferences("insightsPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("nric", nric);
        editor.putString("password", password);
        editor.commit();
    }

    public void errorOnExecuting(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error in verifying user ");
        builder.setMessage("Please try again.");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

}
