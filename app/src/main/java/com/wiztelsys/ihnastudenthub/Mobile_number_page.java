package com.wiztelsys.ihnastudenthub;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Raju on 25-07-2015.
 */
public class Mobile_number_page extends Home_page {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobile_number_verification);
        initializeviews();
        addDrawerItems();
    }
}
