package com.wiztelsys.ihnastudenthub;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


/**
 * Created by Raju on 20-07-2015.
 */
public class Profile_home_fragment extends Fragment {
    TextView t;
    String password;
    Integer user_id1;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
Server_utilities server_utilities;
    String authorization;
    String output;
    JSONObject jobj;
    JSONArray result=null;
    JSONObject jsonObject;
    String Profile_name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.home_profile_fragmentxml, container, false);

        sharedPreferences = getActivity().getSharedPreferences("IHNA_STUDENTHUB", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        password = sharedPreferences.getString("password", null);
        user_id1=sharedPreferences.getInt("user_id",0);
        Log.d("useridinhome", "111111111111:" + password + "" + user_id1);


        t=(TextView)v.findViewById(R.id.Profile_page_first_nametv);

        return v;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        calltowebservice();
    }

    public static Profile_home_fragment newInstance(String text) {

        Profile_home_fragment f = new Profile_home_fragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
    public void calltowebservice(){

        byte[] data = null;
        authorization = user_id1 + ":" + password;
        try {
            data = authorization.getBytes("UTF-8");
            output= Base64.encodeToString(data, Base64.DEFAULT);
        }
        catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        new AsyncTask<String,Void,String>(){

            @Override
            protected String doInBackground(String...S) {

                return server_utilities.webservice_home_profile(S[0]);

            }

            @Override
            protected void onPostExecute(String s) {
                Log.d("inside homeeeeeeeeeeee","is"+s);
                try{

                    jsonObject=new JSONObject(s);
                    result = jsonObject.getJSONArray("profiles");
                    for(Integer i=0;i<result.length();i++){
                        jobj=result.getJSONObject(i);

                       Profile_name=jobj.getString("first_name");
                        Log.d("999999999999",""+jobj.getString("first_name"));

                        t.setText(Profile_name);
                    }
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
