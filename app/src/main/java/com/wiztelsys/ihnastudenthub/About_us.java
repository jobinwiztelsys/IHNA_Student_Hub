package com.wiztelsys.ihnastudenthub;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by dep2 on 8/4/2015.
 */
public class About_us extends Home_page {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);
        initializeviews();
        addDrawerItems();

   /*  Window window=About_us.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags((WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS));
        window.setStatusBarColor(About_us.this.getResources().getColor((R.color.darkblue)));
        */
    }

    @Override
    public void onBackPressed() {
        Intent home=new Intent(getApplicationContext(),Home_page.class);
        startActivity(home);
        finish();
    }
}
