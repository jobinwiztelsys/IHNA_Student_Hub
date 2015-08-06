package com.wiztelsys.ihnastudenthub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Raju on 05-08-2015.
 */
public class TermsAndConditions  extends Home_page{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_and_conditions);
        initializeviews();
        addDrawerItems();
    }

    @Override
    public void onBackPressed() {
        Intent home=new Intent(getApplicationContext(),Home_page.class);
        startActivity(home);
        finish();
    }
}
