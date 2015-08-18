package com.wiztelsys.ihnastudenthub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Raju on 17-07-2015.
 */
public class Library_page extends Home_page {

GridView gridView;
    SharedPreferences sharedPreferences;
    String pin;
    Integer installation_id;
    Server_utilities server_utilities=new Server_utilities();
    String authorization;
    String output;
    JSONObject jsonObject1;
    JSONArray result1;
    JSONObject jobj1;
    ArrayList<String>library_icons=new ArrayList<>();
    ArrayList<String>library_name=new ArrayList<>();
    ArrayList<Bitmap>icon_bitmap=new ArrayList<>();
    ArrayList<String>url_student=new ArrayList<>();
    Integer user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_view_xml);
        gridView=(GridView)findViewById(R.id.gridView);
        sharedPreferences=getSharedPreferences("IHNA_STUDENTHUB", Context.MODE_PRIVATE);
        pin= sharedPreferences.getString("password", null);
        installation_id=sharedPreferences.getInt("installation_id",0);
        user_id=sharedPreferences.getInt("user_id",0);
      //  CustomGrid adapter = new CustomGrid(Library_page.this,);



        initializeviews();
        addDrawerItems();

        callingwebservice();




    }

    public class CustomGrid extends BaseAdapter {
        private Context mContext;
        ArrayList<Bitmap>bitmap_icon;
        ArrayList<String>librarynames;
        ArrayList<String>url_std;

        public CustomGrid(Context c,ArrayList<Bitmap>bitmap_icon,ArrayList<String>librarynames,ArrayList<String>url_std ) {
            mContext = c;
       this.bitmap_icon=bitmap_icon;
            this.librarynames=librarynames;
            this.url_std=url_std;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return bitmap_icon.size();

        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
           return bitmap_icon.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View grid;
            Holder holder=new Holder();

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {

                grid = new View(mContext);
                grid = inflater.inflate(R.layout.library_listview_root_element, null);
                holder.tv = (TextView) grid.findViewById(R.id.library_listview_icon_nameTV);
                holder.img = (ImageView) grid.findViewById(R.id.library_listview_icon_imageview);
                try {
                    if (bitmap_icon.size() != 0) {
                        Log.d("sizeeeeeeeee", "" + bitmap_icon.size());
                        holder.img.setImageBitmap(bitmap_icon.get(position));
                        holder.tv.setText(librarynames.get(position));
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                grid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    //   Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_LONG).show();

                        try {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url_std.get(position)));
                            startActivity(i);
                        }catch (NullPointerException e){
                            e.printStackTrace();
                        }
                        catch (ArrayIndexOutOfBoundsException e){
                            e.printStackTrace();
                        }
                    }
                });
                return grid;
            }
           else {
               grid = (View) convertView;

           }

            return grid;
        }
    }

    @Override
    public void onBackPressed() {
        Intent home=new Intent(getApplicationContext(),Home_page.class);
        startActivity(home);
        finish();
    }

    public void callingwebservice(){

        byte[] data = null;
        authorization = installation_id + ":" + pin;
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

                return server_utilities.webservice_library(S[0]);

            }

            @Override
            protected void onPostExecute(String s) {
                progressBar.setVisibility(View.INVISIBLE);

                if(s==null){
                    Toast.makeText(getApplicationContext(),"Timeout...",Toast.LENGTH_LONG).show();
                    return;
                }
                Log.d("inside notificationss", "is" + s);


                try {

                    jsonObject1 = new JSONObject(s);
                    result1 = jsonObject1.getJSONArray("libraries");
                    for (Integer i = 0; i < result1.length(); i++) {
                        jobj1 = result1.getJSONObject(i);
                        Log.d("reaaddddddddddddddddd","http://220.227.57.26/ihna_webapp".concat(jobj1.getString("icon")).trim());
                       library_icons.add(jobj1.getString("icon"));
                        library_name.add(jobj1.getString("name"));
                        String url=jobj1.getString("sh_url");
                        if(url.contains("{--user_id--}")){
                            Log.d("urllllllllllllllll",""+url.replace("{--user_id--}",user_id.toString()));
                         url_student.add(url.replace("{--user_id--}",user_id.toString()));
                        }
else {                   Log.d("urllllll222222222222",""+url);
                            url_student.add(url);
                        }



                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
                   displayimage();
            }

        }.execute(output);
    }

    public void displayimage(){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... voids) {
               for(Integer i=0;i<library_icons.size();i++){
    try {
    icon_bitmap.add(BitmapFactory.decodeStream((InputStream) new URL(library_icons.get(i)).getContent()));
   }
    catch (MalformedURLException m){
    m.printStackTrace();
   }
  catch (NullPointerException e){
    e.printStackTrace();
  }
    catch (IOException r){
       r.printStackTrace();
               }

               }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                progressBar.setVisibility(View.INVISIBLE);
                CustomGrid adapter = new CustomGrid(Library_page.this,icon_bitmap,library_name,url_student);
                gridView.setAdapter(adapter);


            }
        }.execute();
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
}
