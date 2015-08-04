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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by Raju on 22-07-2015.
 */
public class Reset_pin_page extends Activity implements View.OnClickListener {

    Button button_1,button_2,button_3,button_4,button_5,button_6,button_7,button_8,button_9,button_0,button_backspace,button_ok;
    ImageButton imageButton_1a,imageButton_1b,imageButton_1c,imageButton_1d;
    ImageButton imageButton_2a,imageButton_2b,imageButton_2c,imageButton_2d;
    ImageButton imageButton_3a,imageButton_3b,imageButton_3c,imageButton_3d;

    static int count;  // to set '*' symbol in the imageview..
    String buttonText; // variable to store the button click text

    StringBuilder password_new_enter=new StringBuilder(); // to save the new user entered password
    StringBuilder password_confirm_1=new StringBuilder(); // for password confirmation
    StringBuilder password_confirm_2=new StringBuilder();

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String password;

    String authorization;

    Integer install_id;
    Server_utilities server_utilities=new Server_utilities();
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_pin_page);
        initializeviews();
        count=0;
        sharedPreferences = getSharedPreferences("IHNA_STUDENTHUB", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        password = sharedPreferences.getString("password", null);
        authorization= sharedPreferences.getString("firstlogin_auth", null);
        install_id=sharedPreferences.getInt("installation_id", 0);
    }

    public void initializeviews()
    {
        button_backspace=(Button)findViewById(R.id.reset_pin_img_btn_erase);
        button_ok=(Button)findViewById(R.id.reset_pin_img_btn_ok);
        button_0=(Button)findViewById(R.id.reset_pin_img_btn_0);
        button_1=(Button)findViewById(R.id.reset_pin_img_btn_1);
        button_2=(Button)findViewById(R.id.reset_pin_img_btn_2);
        button_3=(Button)findViewById(R.id.reset_pin_img_btn_3);
        button_4=(Button)findViewById(R.id.reset_pin_img_btn_4);
        button_5=(Button)findViewById(R.id.reset_pin_img_btn_5);
        button_6=(Button)findViewById(R.id.reset_pin_img_btn_6);
        button_7=(Button)findViewById(R.id.reset_pin_img_btn_7);
        button_8=(Button)findViewById(R.id.reset_pin_img_btn_8);
        button_9=(Button)findViewById(R.id.reset_pin_img_btn_9);

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

        imageButton_1a=(ImageButton)findViewById(R.id.Resetpin_1a);
        imageButton_1b=(ImageButton)findViewById(R.id.Resetpin_1b);
        imageButton_1c=(ImageButton)findViewById(R.id.Resetpin_1c);
        imageButton_1d=(ImageButton)findViewById(R.id.Resetpin_1d);
        imageButton_2a=(ImageButton)findViewById(R.id.Resetpin_2a);
        imageButton_2b=(ImageButton)findViewById(R.id.Resetpin_2b);
        imageButton_2c=(ImageButton)findViewById(R.id.Resetpin_2c);
        imageButton_2d=(ImageButton)findViewById(R.id.Resetpin_2d);
        imageButton_3a=(ImageButton)findViewById(R.id.Resetpin_3a);
        imageButton_3b=(ImageButton)findViewById(R.id.Resetpin_3b);
        imageButton_3c=(ImageButton)findViewById(R.id.Resetpin_3c);
        imageButton_3d=(ImageButton)findViewById(R.id.Resetpin_3d);
        progressBar=(ProgressBar)findViewById(R.id.pbHeaderProgress);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        Button b = (Button)view;
        buttonText = b.getText().toString();
        Log.d("bbbbbbbbbbbbbbbbbbb", "" + buttonText);

        if(buttonText.contains("OK")){
            if(password_new_enter.length()==4&&password_new_enter.toString().contains(password)&&password_confirm_1.length()==4&&password_confirm_1.toString().contains(password_confirm_2.toString())){

                callwebservice();
     /*   Intent home=new Intent(Register_Pin_Class.this,Home_page.class);
        startActivity(home);
        finish(); */
            }
            else{
                Toast.makeText(getApplicationContext(), "Password Missmatch", Toast.LENGTH_LONG).show();
                return;
            }
        }

        if(buttonText.contains("12")) {
            count = count - 1;
            if (count == -1) {
                count = 0;
                return;
            }
            switch (count) {
                case 0:
                    imageButton_1a.setImageResource(R.drawable.pinbox_xml);
                    password_new_enter.deleteCharAt(count);
                    Log.d("password",""+password_new_enter.toString());
                    return;
                case 1:
                    imageButton_1b.setImageResource(R.drawable.pinbox_xml);
                    password_new_enter.deleteCharAt(count);
                    Log.d("password",""+password_new_enter.toString());
                    return;
                case 2:
                    imageButton_1c.setImageResource(R.drawable.pinbox_xml);
                    password_new_enter.deleteCharAt(count);
                    Log.d("password",""+password_new_enter.toString());
                    return;
                case 3:
                    imageButton_1d.setImageResource(R.drawable.pinbox_xml);
                    password_new_enter.deleteCharAt(count);
                    Log.d("password",""+password_new_enter.toString());

                    return;

                case 4:
                    imageButton_2a.setImageResource(R.drawable.pinbox_xml);
                    password_confirm_1.deleteCharAt(count-4);
                    Log.d("password",""+password_confirm_1.toString());
                    return;

                case 5:
                    imageButton_2b.setImageResource(R.drawable.pinbox_xml);
                    password_confirm_1.deleteCharAt(count-4);
                    Log.d("password",""+password_confirm_1.toString());
                    return;

                case 6:
                    imageButton_2c.setImageResource(R.drawable.pinbox_xml);
                    password_confirm_1.deleteCharAt(count-4);
                    Log.d("password",""+password_confirm_1.toString());
                    return;

                case 7:
                    imageButton_2d.setImageResource(R.drawable.pinbox_xml);
                    password_confirm_1.deleteCharAt(count-4);
                    Log.d("password",""+password_confirm_1.toString());
                    return;


                case 8:
                    imageButton_3a.setImageResource(R.drawable.pinbox_xml);
                    password_confirm_2.deleteCharAt(count-8);
                    Log.d("password",""+password_confirm_2.toString());
                    return;

                case 9:
                    imageButton_3b.setImageResource(R.drawable.pinbox_xml);
                    password_confirm_2.deleteCharAt(count-8);
                    Log.d("password",""+password_confirm_2.toString());
                    return;

                case 10:
                    imageButton_3c.setImageResource(R.drawable.pinbox_xml);
                    password_confirm_2.deleteCharAt(count-8);
                    Log.d("password",""+password_confirm_2.toString());
                    return;

                case 11:
                    imageButton_3d.setImageResource(R.drawable.pinbox_xml);
                    password_confirm_2.deleteCharAt(count-8);
                    Log.d("password",""+password_confirm_2.toString());
                    return;

            }
        }

        if(count==0){
            imageButton_1a.setImageResource(R.drawable.security_star);
            count=count+1;
            password_new_enter.append(buttonText);
            Log.d("password",""+password_new_enter.toString());
            return;
        }
        if(count==1){
            imageButton_1b.setImageResource(R.drawable.security_star);
            count=count+1;
            password_new_enter.append(buttonText);
            Log.d("password",""+password_new_enter.toString());
            return;
        }
        if(count==2){
            imageButton_1c.setImageResource(R.drawable.security_star);
            count=count+1;
            password_new_enter.append(buttonText);
            Log.d("password",""+password_new_enter.toString());
            return;
        }
        if(count==3){
            imageButton_1d.setImageResource(R.drawable.security_star);
            count=count+1;
            password_new_enter.append(buttonText);
            Log.d("password",""+password_new_enter.toString());

            return;
        }

        if(count==4){
            imageButton_2a.setImageResource(R.drawable.security_star);
            count=count+1;
            password_confirm_1.append(buttonText);
            Log.d("password",""+password_confirm_1.toString());

            return;
        }
        if(count==5){
            imageButton_2b.setImageResource(R.drawable.security_star);
            count=count+1;
            password_confirm_1.append(buttonText);
            Log.d("password",""+password_confirm_1.toString());

            return;
        }
        if(count==6){
            imageButton_2c.setImageResource(R.drawable.security_star);
            count=count+1;
            password_confirm_1.append(buttonText);
            Log.d("password",""+password_confirm_1.toString());

            return;
        }

        if(count==7){
            imageButton_2d.setImageResource(R.drawable.security_star);
            count=count+1;
            password_confirm_1.append(buttonText);
            Log.d("password",""+password_confirm_1.toString());

            return;
        }
        if(count==8){
            imageButton_3a.setImageResource(R.drawable.security_star);
            count=count+1;
            password_confirm_2.append(buttonText);
            Log.d("password",""+password_confirm_2.toString());

            return;
        }
        if(count==9){
            imageButton_3b.setImageResource(R.drawable.security_star);
            count=count+1;
            password_confirm_2.append(buttonText);
            Log.d("password",""+password_confirm_2.toString());

            return;
        }
        if(count==10){
            imageButton_3c.setImageResource(R.drawable.security_star);
            count=count+1;
            password_confirm_2.append(buttonText);
            Log.d("password",""+password_confirm_2.toString());

            return;
        }

        if(count==11){
            imageButton_3d.setImageResource(R.drawable.security_star);
            count=count+1;
            password_confirm_2.append(buttonText);
            Log.d("password",""+password_confirm_2.toString());

            return;
        }

    }

    @Override
    public void onBackPressed() {
        Intent home=new Intent(getApplicationContext(),Home_page.class);
        startActivity(home);
        finish();
    }

    public void callwebservice(){


        new AsyncTask<String,Void,String>(){
            @Override
            protected void onPreExecute() {
progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected String doInBackground(String...strings) {
                return server_utilities.webservicefor_reeset_pin(strings[0],install_id,password_confirm_2.toString());

            }

            @Override
            protected void onPostExecute(String s) {
Log.d("ressssssssssssss",""+s);
                progressBar.setVisibility(View.INVISIBLE);
                if(s==null){
                    Toast.makeText(getApplication(),"Connection TimeOut",Toast.LENGTH_LONG).show();
                }
                try {
                    JSONObject j=new JSONObject(s);

                   if(j.getString("message").contains("Saved")){

                       Toast.makeText(getApplication(),"Password Changed",Toast.LENGTH_LONG).show();
                       sharedPreferences = getSharedPreferences("IHNA_STUDENTHUB", Context.MODE_PRIVATE);
                       editor = sharedPreferences.edit();

                       editor.putString("password", password_confirm_2.toString());
                       editor.commit();
                       Intent home=new Intent(getApplicationContext(),Home_page.class);
                       startActivity(home);
                       finish();
                   }

                }catch(JSONException e){
                    e.printStackTrace();
                }
                catch (NullPointerException e){

                    e.printStackTrace();
                }

            }

        }.execute(authorization);

    }
}
