package com.wiztelsys.ihnastudenthub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Raju on 05-08-2015.
 */
public class Notification_details extends Activity {

    TextView notification_detail_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_details);
        notification_detail_tv=(TextView)findViewById(R.id.notification_detail_tv);
        try{
            notification_detail_tv.setText(Notification_variables.notification_message_string);
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        Intent home=new Intent(getApplicationContext(),Notification_page.class);
        startActivity(home);
        finish();
    }
}
