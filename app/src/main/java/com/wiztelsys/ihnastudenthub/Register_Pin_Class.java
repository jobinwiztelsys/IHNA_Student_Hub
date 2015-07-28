package com.wiztelsys.ihnastudenthub;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by Raju on 15-07-2015.
 */
public class Register_Pin_Class extends Activity implements View.OnClickListener {
    Button button_1, button_2, button_3, button_4, button_5, button_6, button_7, button_8, button_9, button_0,button_ok;
    Button button_backspace;
    ImageButton imageView_1a,imageView_1b,imageView_1c,imageView_1d;
    ImageButton imageView_2a,imageView_2b,imageView_2c,imageView_2d;
    static int count;  // to set '*' symbol in the imageview..
    Intent from_login;
    Integer user_id;
    String username;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

Server_utilities server_utilities=new Server_utilities();
    StringBuilder password_enter=new StringBuilder(); // to save the user entered password
    StringBuilder password_confirm=new StringBuilder(); // to save the confirm password
    String buttonText; // variable to store the button click text
    String auth="";
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_pin_page);
        from_login=getIntent();
        user_id=from_login.getIntExtra("user_id",0);
        Log.d("userid_reg",""+user_id);
       username=from_login.getStringExtra("username");
        Log.d("unameeeeeeeeeee",""+username);
        password=from_login.getStringExtra("password");
        count=0;

        initializeviews();
    }

    public void initializeviews(){
        button_0=(Button)findViewById(R.id.register_pin_img_btn_0);
        button_1=(Button)findViewById(R.id.register_pin_img_btn_1);
        button_2=(Button)findViewById(R.id.register_pin_img_btn_2);
        button_3=(Button)findViewById(R.id.register_pin_img_btn_3);
        button_4=(Button)findViewById(R.id.register_pin_img_btn_4);
        button_5=(Button)findViewById(R.id.register_pin_img_btn_5);
        button_6=(Button)findViewById(R.id.register_pin_img_btn_6);
        button_7=(Button)findViewById(R.id.register_pin_img_btn_7);
        button_8=(Button)findViewById(R.id.register_pin_img_btn_8);
        button_9=(Button)findViewById(R.id.register_pin_img_btn_9);
        button_ok=(Button)findViewById(R.id.register_pin_img_btn_ok);
        button_backspace=(Button)findViewById(R.id.register_pin_img_btn_erase);

        imageView_1a=(ImageButton)findViewById(R.id.register_pin_1a);
        imageView_1b=(ImageButton)findViewById(R.id.register_pin_1b);
        imageView_1c=(ImageButton)findViewById(R.id.register_pin_1c);
        imageView_1d=(ImageButton)findViewById(R.id.register_pin_1d);
        imageView_2a=(ImageButton)findViewById(R.id.register_pin_2a);
        imageView_2b=(ImageButton)findViewById(R.id.register_pin_2b);
        imageView_2c=(ImageButton)findViewById(R.id.register_pin_2c);
        imageView_2d=(ImageButton)findViewById(R.id.register_pin_2d);

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
    }

    public String Mac_address(){
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        String macAddress = wInfo.getMacAddress();
        Log.d("mac_address",macAddress);
        return macAddress;

    }

    public String getPhoneName() {
        BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
        String deviceName = myDevice.getName();
        return deviceName;
    }
    @Override
    public void onClick(View view) {


        Button b = (Button)view;
         buttonText = b.getText().toString();
        Log.d("bbbbbbbbbbbbbbbbbbb",""+buttonText);

if(buttonText.contains("OK")){
    if(password_confirm.length()==4&&password_enter.length()==4&&password_enter.toString().contains(password_confirm.toString())){

        callwebservice();
     /*   Intent home=new Intent(Register_Pin_Class.this,Home_page.class);
        startActivity(home);
        finish(); */
    }
    else{
        Toast.makeText(getApplicationContext(),"Password Missmatch",Toast.LENGTH_LONG).show();
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
                    imageView_1a.setImageResource(R.drawable.pinbox_xml);
                    password_enter.deleteCharAt(count);
                    Log.d("password",""+password_enter.toString());
                    return;
                case 1:
                    imageView_1b.setImageResource(R.drawable.pinbox_xml);
                    password_enter.deleteCharAt(count);
                    Log.d("password",""+password_enter.toString());
                    return;
                case 2:
                    imageView_1c.setImageResource(R.drawable.pinbox_xml);
                    password_enter.deleteCharAt(count);
                    Log.d("password",""+password_enter.toString());
                    return;
                case 3:
                    imageView_1d.setImageResource(R.drawable.pinbox_xml);
                    password_enter.deleteCharAt(count);
                    Log.d("password",""+password_enter.toString());

                    return;

                case 4:
                    imageView_2a.setImageResource(R.drawable.pinbox_xml);
                    password_confirm.deleteCharAt(count-4);
                    Log.d("password",""+password_confirm.toString());
                    return;

                case 5:
                    imageView_2b.setImageResource(R.drawable.pinbox_xml);
                    password_confirm.deleteCharAt(count-4);
                    Log.d("password",""+password_confirm.toString());
                    return;

                case 6:
                    imageView_2c.setImageResource(R.drawable.pinbox_xml);
                    password_confirm.deleteCharAt(count-4);
                    Log.d("password",""+password_confirm.toString());
                    return;

                case 7:
                    imageView_2d.setImageResource(R.drawable.pinbox_xml);
                    password_confirm.deleteCharAt(count-4);
                    Log.d("password",""+password_confirm.toString());
                    return;

            }
        }

        if(count==0){
           imageView_1a.setImageResource(R.drawable.security_star);
            count=count+1;
            password_enter.append(buttonText);
            Log.d("password",""+password_enter.toString());
            return;
        }
        if(count==1){
            imageView_1b.setImageResource(R.drawable.security_star);
            count=count+1;
            password_enter.append(buttonText);
            Log.d("password",""+password_enter.toString());
            return;
        }
        if(count==2){
            imageView_1c.setImageResource(R.drawable.security_star);
            count=count+1;
            password_enter.append(buttonText);
            Log.d("password",""+password_enter.toString());
            return;
        }
        if(count==3){
            imageView_1d.setImageResource(R.drawable.security_star);
            count=count+1;
            password_enter.append(buttonText);
            Log.d("password",""+password_enter.toString());

            return;
        }

        if(count==4){
            imageView_2a.setImageResource(R.drawable.security_star);
            count=count+1;
           password_confirm.append(buttonText);
            Log.d("password",""+password_confirm.toString());

            return;
        }
        if(count==5){
            imageView_2b.setImageResource(R.drawable.security_star);
            count=count+1;
            password_confirm.append(buttonText);
            Log.d("password",""+password_confirm.toString());

            return;
        }
        if(count==6){
            imageView_2c.setImageResource(R.drawable.security_star);
            count=count+1;
            password_confirm.append(buttonText);
            Log.d("password",""+password_confirm.toString());

            return;
        }

        if(count==7){
            imageView_2d.setImageResource(R.drawable.security_star);
            count=count+1;
            password_confirm.append(buttonText);
            Log.d("password",""+password_confirm.toString());

            return;
        }



    }

public void callwebservice(){
    byte[] data = null;
    auth = username.trim() + ":" + password.trim();
    try {
        data = auth.getBytes("UTF-8");
    }
    catch (UnsupportedEncodingException e1) {
        e1.printStackTrace();
    }


    String output= Base64.encodeToString(data, Base64.DEFAULT);
Log.d("outputttttttttt",""+output);

    new AsyncTask<String,Void,String>(){

        @Override
        protected String doInBackground(String...strings) {
            return server_utilities.webservicefor_register_pin(strings[0],user_id,password_confirm.toString(),Mac_address(),getPhoneName());

        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("response from server","is"+s);
            try {

                JSONObject jsonObject = new JSONObject(s);
                String msg=jsonObject.getString("message");
                Integer instal_id=jsonObject.getInt("installation_id");
                Log.d("response from server","is"+user_id);
                sharedPreferences = getSharedPreferences("IHNA_STUDENTHUB", Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putInt("installation_id", instal_id);
                editor.putString("password", password_confirm.toString());

                editor.commit();
                if(msg.contains("Saved")){


                        Intent register_pin=new Intent(Register_Pin_Class.this,Home_page.class);
                        register_pin.putExtra("user_id",user_id);

                        startActivity(register_pin);
                        finish();

                  /*  Intent home=new Intent(LoginStudentHub.this,Home_page.class);
                    home.putExtra("user_id",user_id);
                    startActivity(home);
                    finish();  */
                }

            }catch(JSONException e){
                e.printStackTrace();
            }

        }

    }.execute(output);
}
}

