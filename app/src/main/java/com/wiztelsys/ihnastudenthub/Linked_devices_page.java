package com.wiztelsys.ihnastudenthub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Raju on 25-07-2015.
 */
public class Linked_devices_page extends Home_page {
    ListView listView;
    ArrayList<String> al = new ArrayList<>();
    ArrayList<String> time_device = new ArrayList<>();
    ArrayList<String> last_log = new ArrayList<>();
    String authen;
    SharedPreferences sharedPreferences;
    JSONObject jobj;
    JSONArray result=null;
    JSONObject jsonObject;
    String pin;
    Integer installation_id;
    String authorization;
    String output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linked_devices_xml_page);
        initializeviews();
        addDrawerItems();
        listView = (ListView) findViewById(R.id.linked_listview);

        calling_webservice();
     //   listView.setAdapter(new Base_view(getApplicationContext(), al));
    }

    private class Viewholder {
        ImageView imageView;
        TextView id;
        TextView cmphd;
        TextView stas;
    }


    public class Base_view extends BaseAdapter {

        Context context;
        ArrayList<String> al = new ArrayList<>();
        ArrayList<String> reg_on = new ArrayList<>();
        ArrayList<String> last_on = new ArrayList<>();

        public Base_view(Context con, ArrayList<String> al,ArrayList<String> reg_on,ArrayList<String> last_on) {
            // TODO Auto-generated constructor stub
            this.context = con;
            this.al = al;
            this.reg_on=reg_on;
            this.last_on=last_on;

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return al.size();

        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return al.get(position);

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
            Viewholder holder = new Viewholder();

            //holder.tname.setText(act.get(position));

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.linked_devices_listview_root_element, null);

                holder.cmphd = (TextView) convertView.findViewById(R.id.linked_device_nameTV);
                holder.imageView = (ImageView) findViewById(R.id.imageView);
                holder.id = (TextView) convertView.findViewById(R.id.linked_device_last_usedTV);
                holder.stas = (TextView) convertView.findViewById(R.id.linked_device_registered_onTV);
                convertView.setTag(holder);

            }

            holder = (Viewholder) convertView.getTag();


            holder.cmphd.setText(al.get(position));
            holder.id.setText(last_log.get(position));
            holder.stas.setText(reg_on.get(position));
            return convertView;
        }

    }

    public void calling_webservice() {
        sharedPreferences = getSharedPreferences("IHNA_STUDENTHUB", Context.MODE_PRIVATE);
        authen = sharedPreferences.getString("firstlogin_auth", null);
        pin= sharedPreferences.getString("password", null);
        installation_id=sharedPreferences.getInt("installation_id",0);
        byte[] data = null;
        authorization = installation_id + ":" + pin;
        try {
            data = authorization.getBytes("UTF-8");
            output= Base64.encodeToString(data, Base64.DEFAULT);

        }
        catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        Log.d("Authentication",""+authen);
        new AsyncTask<String, Void, String>() {

            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected String doInBackground(String... strings) {
                return server_utilities.webservice_for_device_details(strings[0]);

            }

            @Override
            protected void onPostExecute(String s)
            {
                progressBar.setVisibility(View.INVISIBLE);
                if(s==null){
                    Toast.makeText(getApplicationContext(), "Timeout...", Toast.LENGTH_LONG).show();
                    return;
                }
                Log.d("response from server", "is" + s);
                try{

                    jsonObject=new JSONObject(s);
                    result = jsonObject.getJSONArray("installations");
                    for(Integer i=0;i<result.length();i++){
                        jobj=result.getJSONObject(i);

                        al.add(jobj.getString("device_name"));
                        String inst_time=jobj.getString("installation_time");
                        String last_login=jobj.getString("last_login");
                        String[] time_last=last_login.split("T");
                        last_log.add(time_last[0]);
                        String[]time=inst_time.split("T");
                        time_device.add(time[0]);
//                        time_device.add(jobj.getString("installation_time"));
//                        last_log.add(jobj.getString("last_login"));

                      //  f_name.setText(Profile_name);
                      //  t_name.setText(jobj.getString("title"));
                      //  l_name.setText(jobj.getString("last_name"));
                     //   p_name.setText(jobj.getString("prefer_name"));
                      //  u_name.setText(jobj.getString("institute_email"));
                    }
                }
                catch (JSONException e){

                    e.printStackTrace();
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
                listView.setAdapter(new Base_view(getApplicationContext(), al,time_device,last_log));

            }
        }.execute(output);
    }


    @Override
    public void onBackPressed() {
        Intent home=new Intent(getApplicationContext(),Home_page.class);
        startActivity(home);
        finish();
    }
}
