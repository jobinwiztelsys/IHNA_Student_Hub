package com.wiztelsys.ihnastudenthub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Raju on 15-07-2015.
 */
public class Register_Pin_Class extends Activity implements View.OnClickListener {
    Button button_1, button_2, button_3, button_4, button_5, button_6, button_7, button_8, button_9, button_0,button_ok;
    Button button_backspace;
    ImageButton imageView_1a,imageView_1b,imageView_1c,imageView_1d;
    ImageButton imageView_2a,imageView_2b,imageView_2c,imageView_2d;
    static int count;  // to set '*' symbol in the imageview..


    StringBuilder password_enter=new StringBuilder(); // to save the user entered password
    StringBuilder password_confirm=new StringBuilder(); // to save the confirm password
    String buttonText; // variable to store the button click text

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_pin_page);
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

    @Override
    public void onClick(View view) {


        Button b = (Button)view;
         buttonText = b.getText().toString();
        Log.d("bbbbbbbbbbbbbbbbbbb",""+buttonText);

if(buttonText.contains("OK")){
    if(password_confirm.length()==4&&password_enter.length()==4&&password_enter.toString().contains(password_confirm.toString())){

        Intent home=new Intent(Register_Pin_Class.this,Home_page.class);
        startActivity(home);
        finish();
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


}

