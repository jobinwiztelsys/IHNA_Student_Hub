package com.wiztelsys.ihnastudenthub;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Raju on 21-07-2015.
 */
public class Notification_page extends Home_page implements AdapterView.OnItemClickListener {

    ArrayList<String>notification_message=new ArrayList<>();
    ArrayList<String>read_flag=new ArrayList<>();
    ArrayList<String>notification_date=new ArrayList<>();
    ArrayList<String>notification_time=new ArrayList<>();
    ArrayList<String>notification_id=new ArrayList<>();

    ListView notification;
    Integer installation_id;
    String password;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String authorization;
    String output;
    JSONObject jobj1;
    JSONArray result1=null;
    JSONObject jsonObject1;
    static final String DISPLAY_MESSAGE_ACTION =
            "com.wiztelsys.ihnastudenthub.DISPLAY_MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_page_xml);

        initializeviews();
        addDrawerItems();

        sharedPreferences=getSharedPreferences("IHNA_STUDENTHUB", Context.MODE_PRIVATE);
        password= sharedPreferences.getString("password", null);
        installation_id=sharedPreferences.getInt("installation_id",0);

        Log.d("inside notification","111111111111:"+installation_id+""+password);

        notification=(ListView)findViewById(R.id.notification_listView);
        callingwebservice();
       // notification.setAdapter(new Base(getApplicationContext(),notification_date));
        notification.setOnItemClickListener(this);

    }

//    public static void displaymessage(String message){
//
//        notification_message.add(message);
//        Log.d("222222222222222222222", "Message: " + message);
//
//    }

    static void displayMessage(Context context, Integer message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra("message", message);
        context.sendBroadcast(intent);
    }



    private class Viewholder{
        TextView notification_desc;
        TextView notifctn_date;

    }

    public class Base extends BaseAdapter {
        ArrayList<String>notification=new ArrayList<String>();
        ArrayList<String>notification_dte=new ArrayList<String>();
        ArrayList<String>readflag=new ArrayList<>();

        Context context;

        public Base(Context con, ArrayList<String> ntfy,ArrayList<String>read_flag,ArrayList<String>notification_dte) {
            // TODO Auto-generated constructor stub
            this.context=con;

            this.notification=ntfy;
            this.readflag=read_flag;
            this.notification_dte=notification_dte;



        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return notification.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return notification.get(position);
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
                convertView = inflater.inflate(R.layout.notification_listview_root_element, null);

                holder.notification_desc=(TextView)convertView.findViewById(R.id.notificationdescriptionTV);
                holder.notifctn_date=(TextView)convertView.findViewById(R.id.ntification_dateTV);
                convertView.setTag(holder);

            }

            holder=(Viewholder)convertView.getTag();

            if(readflag.get(position).toString().contains("0")){
                holder.notification_desc.setText("" + notification.get(position));
                holder.notification_desc.setTextColor(getApplicationContext().getResources().getColor(R.color.greenTextcolour));
            }
            else if(readflag.get(position).toString().contains("1")){
                holder.notification_desc.setText("" + notification.get(position));
                holder.notification_desc.setTextColor(getApplicationContext().getResources().getColor(R.color.black));

            }






            holder.notifctn_date.setText("" + notification_date.get(position));
            holder.notifctn_date.setTextColor(getApplicationContext().getResources().getColor(R.color.black));




       return convertView;

        }

    }

    public void callingwebservice(){
        byte[] data = null;
        authorization = installation_id + ":" + password;
        try {
            data = authorization.getBytes("UTF-8");
            output= Base64.encodeToString(data, Base64.DEFAULT);
        }
        catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        new AsyncTask<String,Void,String>(){

            @Override
            protected void onPreExecute() {

                progressBar.setVisibility(View.VISIBLE);

            }

            @Override
            protected String doInBackground(String...S) {

                return server_utilities.webservice_for_notification_db(S[0]);

            }

            @Override
            protected void onPostExecute(String s) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.d("inside notificationss","is"+s);
                //  progressBar.setVisibility(View.INVISIBLE);
                try{

                    jsonObject1=new JSONObject(s);
                    result1 = jsonObject1.getJSONArray("notifications");
                    for(Integer i=0;i<result1.length();i++){
                        jobj1=result1.getJSONObject(i);
                        Log.d("reaaddddddddddddddddd",""+jobj1.getString("read_flag"));
                        notification_message.add(jobj1.getString("message"));
                        read_flag.add(jobj1.getString("read_flag"));
                        notification_id.add(jobj1.getString("id"));
                       String date=jobj1.getString("created");
                        String[] date1=date.split("T");
                        for (int j =0; j < date1.length ; j++){
                            notification_date.add(date1[0]);
                        }




                    }
                    notification.setAdapter(new Base(getApplicationContext(),notification_message,read_flag,notification_date));
                }

                catch (JSONException e){
                    e.printStackTrace();
                }catch (NullPointerException e){
                    e.printStackTrace();
                }



            }

        }.execute(output);
    }

    @Override
    public void onBackPressed() {
        Intent home=new Intent(getApplicationContext(),Home_page.class);
        startActivity(home);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        if(read_flag.get(i).contains("0")){

            String id=notification_id.get(i);
            callingwebservicefor_read(id);
            return;
        }


    }

    public void callingwebservicefor_read(final String id){
        byte[] data = null;
        authorization = installation_id + ":" + password;
        try {
            data = authorization.getBytes("UTF-8");
            output= Base64.encodeToString(data, Base64.DEFAULT);
        }
        catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        new AsyncTask<String,Void,String>() {
            @Override
            protected String doInBackground(String... strings) {
                return server_utilities.webservice_for_read_flag(strings[0],id);
            }

            @Override
            protected void onPostExecute(String s) {
                Log.d("11111111111",""+s);
            }
        }.execute(output);
        }
}
