package com.wiztelsys.ihnastudenthub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Raju on 15-07-2015.
 */
public class Pin_Login extends Activity implements View.OnClickListener {

    ImageButton imageButton_1a, imageButton_1b, imageButton_1c, imageButton_1d;
    Button button_1, button_2, button_3, button_4, button_5, button_6, button_7, button_8, button_9, button_0, button_ok;
    Button forgot_pin;
    Button button_backspace;
    String buttonText; // for saving the button text
    static int count;
    StringBuilder password_enter = new StringBuilder();
    Integer user_id;
    String pswd;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
String authorization;
    String output;
    Server_utilities server_utilities=new Server_utilities();
    String response;
    ConnectivityManager connect_pinlogin = null;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pin_login_page);
        initializeviews();
        count = 0;
        sharedPreferences = getSharedPreferences("IHNA_STUDENTHUB", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        user_id = sharedPreferences.getInt("installation_id", 0);
        pswd=sharedPreferences.getString("password",null);

        connect_pinlogin=(ConnectivityManager) this.getSystemService(this.CONNECTIVITY_SERVICE);
Log.d("1111111111",""+user_id+""+pswd);
    }

    public void initializeviews() {
        button_0 = (Button) findViewById(R.id.register_pin_img_btn_0);
        button_1 = (Button) findViewById(R.id.register_pin_img_btn_1);
        button_2 = (Button) findViewById(R.id.register_pin_img_btn_2);
        button_3 = (Button) findViewById(R.id.register_pin_img_btn_3);
        button_4 = (Button) findViewById(R.id.register_pin_img_btn_4);
        button_5 = (Button) findViewById(R.id.register_pin_img_btn_5);
        button_6 = (Button) findViewById(R.id.register_pin_img_btn_6);
        button_7 = (Button) findViewById(R.id.register_pin_img_btn_7);
        button_8 = (Button) findViewById(R.id.register_pin_img_btn_8);
        button_9 = (Button) findViewById(R.id.register_pin_img_btn_9);
        button_ok = (Button) findViewById(R.id.register_pin_img_btn_ok);
        forgot_pin=(Button)findViewById(R.id.pin_login_forgot_pinBtn);
        forgot_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = getSharedPreferences("IHNA_STUDENTHUB", Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();

                editor.putBoolean("firstlogin", true);
                editor.commit();

                Intent login_home=new Intent(Pin_Login.this,LoginStudentHub.class);
                startActivity(login_home);
                finish();
            }
        });
        button_backspace = (Button) findViewById(R.id.register_pin_img_btn_erase);

        button_0.setOnClickListener(this);
        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);
        button_6.setOnClickListener(this);
        button_7.setOnClickListener(this);
        button_8.setOnClickListener(this);
        button_9.setOnClickListener(this);
        button_ok.setOnClickListener(this);
        button_backspace.setOnClickListener(this);

        imageButton_1a = (ImageButton) findViewById(R.id.login_pin_1a);
        imageButton_1b = (ImageButton) findViewById(R.id.login_pin_1b);
        imageButton_1c = (ImageButton) findViewById(R.id.login_pin_1c);
        imageButton_1d = (ImageButton) findViewById(R.id.login_pin_1d);
        progressBar=(ProgressBar)findViewById(R.id.pbHeaderProgress);
        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onClick(View view) {
        Button b = (Button) view;
        buttonText = b.getText().toString();
        Log.d("bbbbbbbbbbbbbbbbbbb", "" + buttonText);
        if(buttonText.contains("10")){
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "1800225283"));
            startActivity(intent);
            return;
        }

        if (buttonText.contains("12")) {
            count = count - 1;
            if (count == -1) {
                count = 0;
                return;
            }
            switch (count) {
                case 0:
                    imageButton_1a.setImageResource(R.drawable.pinbox_xml);
                    password_enter.deleteCharAt(count);
                    Log.d("password", "" + password_enter.toString());
                    return;
                case 1:
                    imageButton_1b.setImageResource(R.drawable.pinbox_xml);
                    password_enter.deleteCharAt(count);
                    Log.d("password", "" + password_enter.toString());
                    return;
                case 2:
                    imageButton_1c.setImageResource(R.drawable.pinbox_xml);
                    password_enter.deleteCharAt(count);
                    Log.d("password", "" + password_enter.toString());
                    return;
                case 3:
                    imageButton_1d.setImageResource(R.drawable.pinbox_xml);
                    password_enter.deleteCharAt(count);
                    Log.d("password", "" + password_enter.toString());

                    return;

            }
        }
        if(count==0){
            imageButton_1a.setImageResource(R.drawable.security_star);
            count=count+1;
            password_enter.append(buttonText);
            Log.d("password",""+password_enter.toString());
            return;
        }
        if(count==1){
            imageButton_1b.setImageResource(R.drawable.security_star);
            count=count+1;
            password_enter.append(buttonText);
            Log.d("password",""+password_enter.toString());
            return;
        }
        if(count==2){
            imageButton_1c.setImageResource(R.drawable.security_star);
            count=count+1;
            password_enter.append(buttonText);
            Log.d("password",""+password_enter.toString());
            return;
        }
        if(count==3){
            imageButton_1d.setImageResource(R.drawable.security_star);
            count=count+1;
            password_enter.append(buttonText);
            Log.d("password",""+password_enter.toString());

            if (connect_pinlogin != null) {
                NetworkInfo result = (connect_pinlogin.getNetworkInfo(ConnectivityManager.TYPE_MOBILE));
                NetworkInfo result1 = connect_pinlogin.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if ((result != null && result.isConnectedOrConnecting()) || (result1 != null && result1.isConnectedOrConnecting())) {
                    calltowebservice();
                } else {
                    Toast.makeText(getApplicationContext(), "network not available", Toast.LENGTH_LONG).show();
                    return;
                }
            }


          /*  Intent home=new Intent(Pin_Login.this,Home_page.class);
            startActivity(home);
            finish();  */

        }

    }

    public void calltowebservice(){
        byte[] data = null;
        authorization = user_id + ":" + password_enter.toString().trim();
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

                return server_utilities.webservice_home_profile(S[0]);

            }



            @Override
            protected void onPostExecute(String s) {
                progressBar.setVisibility(View.INVISIBLE);

                if(s==null){
                    Toast.makeText(getApplicationContext(), "Timed Out Check The Password Entered", Toast.LENGTH_LONG).show();
                    return;
                }

                Log.d("response from server","is"+s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    response=jsonObject.getString("message");
                    if(response.contains("Unauthorized")){
                        Toast.makeText(getApplicationContext(), "Password Incorrect", Toast.LENGTH_LONG).show();
                        return;
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }catch (NullPointerException e){
                    e.printStackTrace();
                }

                if(s!=null) {
                    Intent home = new Intent(Pin_Login.this, Home_page.class);
                    home.putExtra("password", password_enter.toString().trim());
                    home.putExtra("user_id", user_id);
                    startActivity(home);
                    finish();
                }


            }

        }.execute(output);
    }
}