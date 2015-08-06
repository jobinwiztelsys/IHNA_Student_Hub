package com.wiztelsys.ihnastudenthub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by Raju on 25-07-2015.
 */
public class Mobile_number_page extends Home_page {
TextView mobile_current_numberTV;
    Button mobilenumber_submit;
    EditText enter_number;
    String mob_number;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String pin;
    Integer install_id;
    String authorization;
    String output;
    JSONObject jsonObject;
    JSONArray result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobile_number_verification);
        initializeviews();
        addDrawerItems();
        mobile_current_numberTV=(TextView)findViewById(R.id.mobile_current_numberTV);


        try {
            mobile_current_numberTV.setText(Notification_variables.mobile_number);
        }catch (NullPointerException e){
            e.printStackTrace();
        }


        enter_number=(EditText)findViewById(R.id.textView);
        int settings = EditorInfo.TYPE_CLASS_TEXT;
        enter_number.setInputType(settings);
        enter_number.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mobilenumber_submit=(Button)findViewById(R.id.button);

        sharedPreferences =getSharedPreferences("IHNA_STUDENTHUB", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        pin = sharedPreferences.getString("password", null);
        install_id=sharedPreferences.getInt("installation_id",0);
        mobilenumber_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mob_number=enter_number.getText().toString();

                calltowebservice();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent home=new Intent(getApplicationContext(),Home_page.class);
        home.putExtra("fragment_value",1);
        startActivity(home);
        finish();
    }

    public void calltowebservice(){

        byte[] data = null;
        authorization = install_id + ":" + pin;
        try {
            data = authorization.getBytes("UTF-8");
            output= Base64.encodeToString(data, Base64.DEFAULT);
        }
        catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        new AsyncTask<String,Void,String>(){

            @Override
            protected void onPreExecute() {

                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected String doInBackground(String...S) {

                return server_utilities.webserviceformobile_number(S[0],mob_number);

            }

            @Override
            protected void onPostExecute(String s) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.d("inside notificationss", "is" + s);
                //  progressBar.setVisibility(View.INVISIBLE);
                try {

                    jsonObject = new JSONObject(s);
                    if (jsonObject.getString("message").contains("Saved")) {

                        mobile_current_numberTV.setText(mob_number);
                        Toast.makeText(getApplicationContext(),"Saved Successfully",Toast.LENGTH_LONG).show();
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }catch (NullPointerException e){
                    e.printStackTrace();
                }



            }

        }.execute(output);
    }
}
