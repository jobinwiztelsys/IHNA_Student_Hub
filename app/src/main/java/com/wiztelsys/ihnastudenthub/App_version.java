package com.wiztelsys.ihnastudenthub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by dep2 on 8/4/2015.
 */
public class App_version extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_version);
    }

    @Override
    public void onBackPressed() {
        Intent home=new Intent(getApplicationContext(),Home_page.class);
        startActivity(home);
        finish();
    }
}
