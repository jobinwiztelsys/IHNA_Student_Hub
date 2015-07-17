package com.wiztelsys.ihnastudenthub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

/**
 * Created by Raju on 15-07-2015.
 */
public class Home_page extends Activity implements View.OnClickListener,ListView.OnItemClickListener {
    SharedPreferences sharedPreferences;  // for setting the first time login to false
    SharedPreferences.Editor editor;
    Toolbar toolbar;
    ListView listView;
    DrawerLayout drawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_activity);
        sharedPreferences = getSharedPreferences("IHNA_STUDENTHUB", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putBoolean("firstlogin", false);
        editor.commit();

        initializeviews();
        addDrawerItems();
        setupDrawer();
        listView.setOnItemClickListener(this);
    }

    public void initializeviews(){

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        listView=(ListView)findViewById(R.id.home_Listview);
        drawerLayout=(DrawerLayout)findViewById(R.id.home_drawer_layout);
        toolbar.setOnClickListener(this);
       // setSupportActionBar(toolbarBottom);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                return true;
            }
        });
        // Inflate a menu to be displayed in the toolbar
        toolbar.inflateMenu(R.menu.menu_login_student_hub);
    }

// to populate the listview inside the drawer

    private void addDrawerItems() {
        String[] osArray = { "Profile","Library" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        listView.setAdapter(mAdapter);


    }

    //  setting up the drawer

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this,                  /* host Activity */
                drawerLayout,null,         /* DrawerLayout object */
                R.string.drawer_open,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(mDrawerToggle);
    }




    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_LONG).show();
    }
}
