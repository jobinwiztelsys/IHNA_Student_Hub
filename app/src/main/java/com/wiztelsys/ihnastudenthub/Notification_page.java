package com.wiztelsys.ihnastudenthub;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Raju on 21-07-2015.
 */
public class Notification_page extends Home_page {

    static ArrayList<String>notification_message=new ArrayList<>();
    ArrayList<String>notification_date=new ArrayList<>();
   static ArrayList<String>notification_time=new ArrayList<>();

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
    }

    public static void displaymessage(String message){

notification_message.add(message);
        Log.d("222222222222222222222", "Message: " + message);
    }

    private class Viewholder{
        TextView notification_desc;
        TextView notifctn_date;

    }

    public class Base extends BaseAdapter {
        ArrayList<String>notification=new ArrayList<String>();
        ArrayList<String>notification_dte=new ArrayList<String>();
        Context context;

        public Base(Context con, ArrayList<String> ntfy) {
            // TODO Auto-generated constructor stub
            this.context=con;

            this.notification=ntfy;
          //  this.notification_dte=ntfy_dte;


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



            holder.notification_desc.setText("" + notification.get(position));
         //   holder.notifctn_date.setText(""+notification_dte.get(position));


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
                        Log.d("11111111111111111",""+jobj1.getString("message"));
                        notification_message.add(jobj1.getString("message"));
                        for (String retval: jobj1.getString("created").split("T")){
                            notification_date.add(retval);
                        }


                    }
                    notification.setAdapter(new Base(getApplicationContext(),notification_message));
                }

                catch (JSONException e){
                    e.printStackTrace();
                }catch (NullPointerException e){
                    e.printStackTrace();
                }



            }

        }.execute(output);
    }



}
