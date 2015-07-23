package com.wiztelsys.ihnastudenthub;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_pin_page);
        initializeviews();
        count=0;
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
    }

    @Override
    public void onClick(View view) {
        Button b = (Button)view;
        buttonText = b.getText().toString();
        Log.d("bbbbbbbbbbbbbbbbbbb", "" + buttonText);

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
}
