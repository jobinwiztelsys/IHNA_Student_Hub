package com.wiztelsys.ihnastudenthub;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
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
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


/**
 * Created by Raju on 15-07-2015.
 */
public class Home_page extends FragmentActivity implements View.OnClickListener{
    SharedPreferences sharedPreferences;  // for setting the first time login to false
    SharedPreferences.Editor editor;
    Toolbar toolbar;
    ListView listView;
    Button notification_button;
    DrawerLayout drawerLayout;
    private ArrayAdapter<String> mAdapter;
    ImageButton imageButton_toolbar;
    Button call_IhnaBtn;
    CirclePageIndicator pageIndicator;
    ViewPager pager;
    Intent from_login=new Intent();
    Integer user_id;
    Integer user_id1;
    String password;
Server_utilities server_utilities=new Server_utilities();
    BroadcastReceiver mRegistrationBroadcastReceiver;
    boolean sentToken;
    boolean sender_token_by_me;
ProgressBar progressBar;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    Intent from_pin_login;
    String password_pin;
    JSONObject jobj;
    JSONArray result=null;
    JSONObject jsonObject;
    Bundle bundle;
    Integer fragment_valie;
TextView notification_count;
    static final String DISPLAY_MESSAGE_ACTION =
            "com.wiztelsys.ihnastudenthub.DISPLAY_MESSAGE";
    /*RegistrationIntentService**QuickstartPreferences**MyGcmListenerService**MyInstanceIDListenerService
    are the classes for sending and receiving notification..it is called in the oncreate method of home class */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_activity);
        pageIndicator = (CirclePageIndicator) findViewById(R.id.titles);
        from_pin_login=getIntent();
        fragment_valie=from_pin_login.getIntExtra("fragment_value",0);

        Log.d("fragmentvale",""+fragment_valie);


        sharedPreferences = getSharedPreferences("IHNA_STUDENTHUB", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putBoolean("firstlogin", false);
        editor.commit();

        sharedPreferences = getSharedPreferences("IHNA_STUDENTHUB", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        password = sharedPreferences.getString("password", null);
        user_id1=sharedPreferences.getInt("installation_id",0);
        Log.d("useridinhome","111111111111:"+password+""+user_id1);


        from_login=getIntent();
        user_id=from_login.getIntExtra("user_id",0);



        sharedPreferences = getSharedPreferences("notification", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        sender_token_by_me = sharedPreferences.getBoolean("server_reg", true);
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
         sentToken = sharedPreferences
                .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, true);

        Log.d("111111111111111111",""+sentToken);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {


                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                 sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);

            }
        };
        if (checkPlayServices()&&sender_token_by_me) {
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }

        registerReceiver(mHandleMessageReceiver, new IntentFilter(
                DISPLAY_MESSAGE_ACTION));
    }






    public void initializeviews(){




        toolbar= (Toolbar) findViewById(R.id.toolbar);
        listView=(ListView)findViewById(R.id.home_Listview);
        drawerLayout=(DrawerLayout)findViewById(R.id.home_drawer_layout);
        imageButton_toolbar=(ImageButton)findViewById(R.id.toolbar_imagebutton);
        notification_button=(Button)findViewById(R.id.notification_button);
        notification_button.setOnClickListener(this);
        toolbar.setOnClickListener(this);
        imageButton_toolbar.setOnClickListener(this);
        call_IhnaBtn=(Button)findViewById(R.id.call_IhnaBtn);
        progressBar=(ProgressBar)findViewById(R.id.pbHeaderProgress);
        progressBar.setVisibility(View.INVISIBLE);
        call_IhnaBtn.setOnClickListener(this);
        notification_count=(TextView)findViewById(R.id.notification_count);
        try{
            if(Notification_variables.count==0){
                notification_count.setText("");
            }
            else {
                notification_count.setVisibility(View.VISIBLE);
                notification_count.setText("" + Notification_variables.count);
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        }
       // setSupportActionBar(toolbarBottom);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
  switch (item.getItemId()){
      case R.id.exit_app:

          Intent intent = new Intent(Intent.ACTION_MAIN);
          intent.addCategory(Intent.CATEGORY_HOME);
          intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

          startActivity(intent);
          finish();
          System.exit(0);
          break;
          case R.id.about_us:

          startActivity(new Intent(Home_page.this,About_us.class));
              finish();

          break;
          case R.id.version:

          startActivity(new Intent(Home_page.this,App_version.class));
              finish();

          break;




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
     case R.id.notification_button:
         Intent noftify=new Intent(getApplicationContext(),Notification_page.class);
         startActivity(noftify);
        finish();
         break;

     case R.id.call_IhnaBtn:
         Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "1800225283"));
         startActivity(intent);
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

//            if(fragment_valie.toString().contains("1")){
//                pos=fragment_valie;
//            }

            switch (fragment_valie) {

                case 0: fragment_valie+=1;
                    return Profile_home_fragment.newInstance("FirstFragment, Instance 1");

                case 1:fragment_valie-=1;
                    return Privacy_home_fragment.newInstance("secondFragment, Instance 1");
            }
             return Profile_home_fragment.newInstance("FirstFragment, Instance 1");
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    private class Viewholder{
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
    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
        registerReceiver(mHandleMessageReceiver, new IntentFilter(
                DISPLAY_MESSAGE_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
unregisterReceiver(mHandleMessageReceiver);

    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {

                finish();
            }
            return false;
        }
        return true;
    }

    /**
     * Receiving push messages
     * */
    public final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Integer newMessage = intent.getExtras().getInt("message");
            // Waking up mobile if it is sleeping


            /**
             * Take appropriate action on this message
             * depending upon your app requirement
             * For now i am just displaying it on the screen
             * */

            // Showing received message
            initializeviews();
         //   notification_count.setText(""+newMessage);

          //  Toast.makeText(getApplicationContext(), "New Message: " + newMessage, Toast.LENGTH_LONG).show();


        }
    };


    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
        finish();
        System.exit(0);
    }
}
