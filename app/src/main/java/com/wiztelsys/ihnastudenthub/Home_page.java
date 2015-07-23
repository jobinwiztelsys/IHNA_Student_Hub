package com.wiztelsys.ihnastudenthub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;


/**
 * Created by Raju on 15-07-2015.
 */
public class Home_page extends FragmentActivity implements View.OnClickListener{
    SharedPreferences sharedPreferences;  // for setting the first time login to false
    SharedPreferences.Editor editor;
    Toolbar toolbar;
    ListView listView;
    DrawerLayout drawerLayout;
    private ArrayAdapter<String> mAdapter;
    ImageButton imageButton_toolbar;
    CirclePageIndicator pageIndicator;
    ViewPager pager;

    /*RegistrationIntentService**QuickstartPreferences**MyGcmListenerService**MyInstanceIDListenerService
    are the classes for sending and receiving notification..it is called in the oncreate method of home class */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_activity);
        pageIndicator = (CirclePageIndicator) findViewById(R.id.titles);


        sharedPreferences = getSharedPreferences("IHNA_STUDENTHUB", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putBoolean("firstlogin", false);
        editor.commit();


        initializeviews();

        addDrawerItems();


        //final RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radiogroup);
        pager= (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
      //  pageIndicator.setViewPager(pager);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position){
                    case 0:
                     //   radioGroup.check(R.id.radioButton);
                        pageIndicator.setViewPager(pager);
                        break;
                    case 1:
                      //  radioGroup.check(R.id.radioButton2);
                        pageIndicator.setViewPager(pager);
                        break;
                    case 2:
                       // radioGroup.check(R.id.radioButton3);
                        pageIndicator.setViewPager(pager);
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // for the notification SharedPreferences sharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean sentToken = sharedPreferences
                .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
        if (sentToken) {
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        } else {
           return;
        }

    }




    public void initializeviews(){

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        listView=(ListView)findViewById(R.id.home_Listview);
        drawerLayout=(DrawerLayout)findViewById(R.id.home_drawer_layout);
        imageButton_toolbar=(ImageButton)findViewById(R.id.toolbar_imagebutton);
        toolbar.setOnClickListener(this);
        imageButton_toolbar.setOnClickListener(this);


       // setSupportActionBar(toolbarBottom);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
  switch (item.getItemId()){
      case R.id.reset:
          Intent reset=new Intent(getApplicationContext(),Reset_pin_page.class);
          startActivity(reset);
          finish();
  }
                return true;
            }
        });
        // Inflate a menu to be displayed in the toolbar
        toolbar.inflateMenu(R.menu.menu_login_student_hub);
    }

// to populate the listview inside the drawer

    public void addDrawerItems() {
       ArrayList<String>list_home=new ArrayList<>();
        list_home.add("Profile");
        list_home.add("Library");
     //   mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        listView.setAdapter(new Base(getApplicationContext(),list_home));
        listView.setOnItemClickListener(new DrawerItemClickListener());


    }





    @Override
    public void onClick(View view) {
 switch (view.getId()){
     case R.id.toolbar_imagebutton:
         drawerLayout.openDrawer(Gravity.LEFT);


         break;
 }
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0:
                    Intent home=new Intent(getApplicationContext(),Home_page.class);
                    startActivity(home);
                    finish();

                    break;
                case 1:

                    Intent library=new Intent(Home_page.this,Library_page.class);
                    startActivity(library);
                    finish();
            }
        }

    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    return Profile_home_fragment.newInstance("FirstFragment, Instance 1");

                case 1: return Privacy_home_fragment.newInstance("secondFragment, Instance 1");
            }
             return Profile_home_fragment.newInstance("FirstFragment, Instance 1");
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    public class Viewholder{
        TextView text;

    }


    // for listview

    public class Base extends BaseAdapter {
        ArrayList<String> list_home_drawer=new ArrayList<String>();

        Context context;

        public Base(Context con, ArrayList<String> drawer) {
            // TODO Auto-generated constructor stub
            this.context=con;

            this.list_home_drawer=drawer;


        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list_home_drawer.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return list_home_drawer.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @SuppressWarnings("unused")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            Viewholder holder=new Viewholder();

            //holder.tname.setText(act.get(position));

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.home_listview_custom_xml, null);

                holder.text=(TextView)convertView.findViewById(R.id.home_textView6);

                convertView.setTag(holder);

            }

            holder=(Viewholder)convertView.getTag();



            holder.text.setText(list_home_drawer.get(position));



            return convertView;
        }

    }

}
