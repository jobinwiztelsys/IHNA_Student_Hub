package com.wiztelsys.ihnastudenthub;

import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Raju on 23-07-2015.
 */
public class Server_utilities {
    HttpURLConnection myURLConnection;
    Server_utilities(){

    }

    // *******8function to call webservice for login in user information ****** //

    public String login_webservice(String authorization){

        try {
            String url1="http://10.0.0.30/ihna_webapp/users/login";


            Log.d("rajeev", "inside service handler webservice"+authorization.toString());
            URL url = new URL("http://10.0.0.30/ihna_webapp/users/login");
             myURLConnection = (HttpURLConnection)url.openConnection();
            
            myURLConnection.setRequestProperty ("Authorization","Basic c3JlZWt1bWFyOjEyMzQ1Ng==");
            myURLConnection.setRequestMethod("POST");
            myURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
         //   myURLConnection.setRequestProperty("Content-Length", "" + Integer.toString(postData.getBytes().length));
            myURLConnection.setRequestProperty("Content-Language", "en-US");
            myURLConnection.setUseCaches(false);
            myURLConnection.setDoInput(true);
            myURLConnection.setDoOutput(true);
        /*    URL url = new URL(url1);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);

         //   urlConnection.setRequestProperty("Content-Type", "application/json");

          //  urlConnection.setRequestProperty("Accept", "application/json");

            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Authorization","Basic c3JlZWt1bWFyOjEyMzQ1Ng==");
            urlConnection.connect(); */

          /*  OutputStreamWriter wr= new OutputStreamWriter(urlConnection.getOutputStream());
            Log.d("rajeev","sending json data"+authorization.toString());
            String eg=authorization.toString();
            wr.write(eg);

            wr.flush();
            wr.close();  */BufferedReader br = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream(),"utf-8"));

            String line = br.readLine();
            Log.d("rajeev111111111111","111111111111"+line);

            StringBuilder sb = new StringBuilder();

            int HttpResult =myURLConnection.getResponseCode();

            if(HttpResult ==HttpURLConnection.HTTP_OK){

               // BufferedReader br = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream(),"utf-8"));

             //   String line = br.readLine();
                Log.d("rajeev111111111111","111111111111"+line);
                JSONObject jsonobj=new JSONObject(line);
                Log.d("rajeev111111111111","111111111111"+jsonobj.getString("message"));




                br.close();
                return  jsonobj.toString();




            }


        }

        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (Exception e) {

            e.printStackTrace();

        } finally {

            if(myURLConnection != null) {
                myURLConnection.disconnect();
            }
        }
        return null;
    }
}
