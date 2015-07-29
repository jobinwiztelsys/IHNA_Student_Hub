package com.wiztelsys.ihnastudenthub;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Raju on 25-07-2015.
 */
public class Linked_devices_page extends Home_page {
    ListView listView;
    ArrayList<String> al = new ArrayList<>();
    String authen;
    SharedPreferences sharedPreferences;
    JSONObject jobj;
    JSONArray result=null;
    JSONObject jsonObject;
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

        public Base_view(Context con, ArrayList<String> al) {
            // TODO Auto-generated constructor stub
            this.context = con;
            this.al = al;


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
                holder.id = (TextView) convertView.findViewById(R.id.tv1);
                holder.stas = (TextView) convertView.findViewById(R.id.tv2);
                convertView.setTag(holder);

            }

            holder = (Viewholder) convertView.getTag();

            holder.id.setText(al.get(position));
            holder.cmphd.setText(al.get(position));

            holder.id.setText("devices");

            return convertView;
        }

    }

    public void calling_webservice() {
        sharedPreferences = getSharedPreferences("IHNA_STUDENTHUB", Context.MODE_PRIVATE);
        authen = sharedPreferences.getString("firstlogin_auth", null);
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
                Log.d("response from server", "is" + s);
                try{

                    jsonObject=new JSONObject(s);
                    result = jsonObject.getJSONArray("installations");
                    for(Integer i=0;i<result.length();i++){
                        jobj=result.getJSONObject(i);

                        al.add(jobj.getString("device_name"));


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
                listView.setAdapter(new Base_view(getApplicationContext(), al));

            }
        }.execute(authen);
    }
}
