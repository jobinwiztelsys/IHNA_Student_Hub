package com.wiztelsys.ihnastudenthub;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Created by Raju on 15-07-2015.
 */
public class Home_page extends Activity {
    SharedPreferences sharedPreferences;  // for setting the first time login to false
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_activity);
        sharedPreferences = getSharedPreferences("IHNA_STUDENTHUB", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putBoolean("firstlogin", false);
        editor.commit();
    }
}
