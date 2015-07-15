package com.wiztelsys.ihnastudenthub;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by Raju on 15-07-2015.
 */
public class Pin_Login extends Activity implements View.OnClickListener {

    ImageButton imageButton_1a, imageButton_1b, imageButton_1c, imageButton_1d;
    Button button_1, button_2, button_3, button_4, button_5, button_6, button_7, button_8, button_9, button_0, button_ok;
    Button button_backspace;
    String buttonText; // for saving the button text
    static int count;
    StringBuilder password_enter = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pin_login_page);
        initializeviews();
        count = 0;

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

    }

    @Override
    public void onClick(View view) {
        Button b = (Button) view;
        buttonText = b.getText().toString();
        Log.d("bbbbbbbbbbbbbbbbbbb", "" + buttonText);

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

            return;
        }

    }
}