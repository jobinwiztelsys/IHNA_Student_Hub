package com.wiztelsys.ihnastudenthub;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Created by Raju on 23-07-2015.
 */
public class Server_utilities {
    HttpURLConnection myURLConnection;
    private static final int CONN_TIMEOUT = 60 * 1000;

    // socket timeout, in milliseconds (waiting for data)
    private static final int SOCKET_TIMEOUT = 60 * 1000;
    String authorization1=null;
    HttpURLConnection urlConnection;

    Server_utilities() {

    }

    HttpParams getHttpParams() {

        HttpParams htpp = new BasicHttpParams();

        HttpConnectionParams.setConnectionTimeout(htpp, CONN_TIMEOUT);
        HttpConnectionParams.setSoTimeout(htpp, SOCKET_TIMEOUT);

        return htpp;
    }

    // *******8function to call webservice for login in user information ****** //

    public String login_webservice(String authorization) {



            // String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));
/*
            myURLConnection.setRequestMethod("POST");

            myURLConnection.addRequestProperty("Authorization",authorization);
         //   myURLConnection.setRequestProperty("Content-Type", "application/json");
            myURLConnection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        //    myURLConnection.setRequestProperty("Accept", "application/json");
            myURLConnection.setUseCaches(false);
            myURLConnection.setDoInput(true);
            myURLConnection.setDoOutput(true);
           Log.d("rajeev","111111111111"+myURLConnection.getResponseMessage());  */



       String authorization1= authorization.trim();




        HttpClient httpclient = new DefaultHttpClient(getHttpParams());

        HttpResponse response = null;

        HttpPost httppost;
        String result = null;
        String basic="Basic ";

        httppost = new HttpPost("http://220.227.57.26/ihna_webapp/users/login");
       httppost.setHeader("Accept", "application/json");
        httppost.setHeader("Content-type", "application/json");
      //  httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httppost.setHeader("Authorization",basic.concat(authorization1));
        try {
            response = httpclient.execute(httppost);
            result=inputStreamToString(response.getEntity()
                    .getContent());
            Log.d("response111111111111111","response:"+authorization1.toString());
            Log.d("response","response:"+result);

        }
        catch (SocketTimeoutException e) {

            System.out.println("After Execute TIME_OUT_EXECPTION \n");

            // TODO: handle exception
            // response=StaticValues.TIME_OUT_EXECPTION;
        } catch (ConnectTimeoutException e) {
            // TODO: handle exception

        }

        catch (Exception e) {
            System.out.println("OOOOOOOOOOOPSSSSS \n");



        }
return result;
    }

    //********************************************* for parsing**************************
    public String inputStreamToString(InputStream is) {

        String line = "";
        StringBuilder total = new StringBuilder();

        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        try {
            // Read response until the end
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return full string
        return total.toString();
    }
//************************************************************************************//

    public String webservice_home_profile(String authen){
        HttpClient httpclient = new DefaultHttpClient(getHttpParams());

        HttpResponse response = null;

        HttpGet httpGet;
        String result = null;
        String basic="Basic ";
        StringEntity se = null;
        JSONObject jobj=new JSONObject();


        httpGet = new HttpGet("http://220.227.57.26/ihna_webapp/profiles");
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-type", "application/json");
     //   httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpGet.setHeader("Authorization",basic.concat(authen.trim()));
        try {
            response = httpclient.execute(httpGet);
            result=inputStreamToString(response.getEntity()
                    .getContent());
            Log.d("response111111111111111","response:"+authen.toString());
            Log.d("response","response:"+result);

        }
        catch (SocketTimeoutException e) {

            System.out.println("After Execute TIME_OUT_EXECPTION \n");

            // TODO: handle exception
            // response=StaticValues.TIME_OUT_EXECPTION;
        } catch (ConnectTimeoutException e) {
            // TODO: handle exception

        }

        catch (Exception e) {
            System.out.println("OOOOOOOOOOOPSSSSS \n");



        }
        return result;
    }

//*******************************************************************************************
    public String webservicefor_register_pin(String auth,Integer user_id,String passwd){
        StringEntity se = null;
        JSONObject jobj=new JSONObject();
        try {
            jobj.put("user_id", user_id);
            jobj.put("four_digit_pin", passwd);
            Log.d("jsonString", "" + jobj.toString());
            se = new StringEntity(jobj.toString());
        }
            catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        catch(JSONException e){
            e.printStackTrace();

        }
        HttpClient httpclient = new DefaultHttpClient(getHttpParams());

        HttpResponse response = null;

        HttpPost httppost;
        String result = null;
        String basic="Basic ";

        httppost = new HttpPost("http://220.227.57.26/ihna_webapp/installations/add");
        httppost.setHeader("Accept", "application/json");
          httppost.setHeader("Content-type", "application/json");
       // httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httppost.setHeader("Authorization",basic.concat(auth.trim()));
        httppost.setEntity(se);
        try {
            response = httpclient.execute(httppost);
            result=inputStreamToString(response.getEntity()
                    .getContent());
            Log.d("response111111111111111","response:"+auth.toString());
            Log.d("response","response:"+result);

        }
        catch (SocketTimeoutException e) {

            System.out.println("After Execute TIME_OUT_EXECPTION \n");

            // TODO: handle exception
            // response=StaticValues.TIME_OUT_EXECPTION;
        } catch (ConnectTimeoutException e) {
            // TODO: handle exception

        }

        catch (Exception e) {
            System.out.println("OOOOOOOOOOOPSSSSS \n");



        }
        return result;
    }

//**************************************************************************************
    public String webservice_for_notification_db(String authn){

     /*   String basicc="Basic ";
        try {
            URL url = new URL("http://220.227.57.26/ihna_webapp/notifications");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
            urlConnection.addRequestProperty("Accept", "application/json");
            urlConnection.addRequestProperty("Content-type", "application/json");
            //   httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.addRequestProperty("Authorization",basicc.concat(authn.trim()));

            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            int HttpResult =urlConnection.getResponseCode();

            if(HttpResult ==HttpURLConnection.HTTP_OK){

               BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));

               String line=inputStreamT(br);

                Log.d("rajeev","111111111111"+line);
                JSONObject jsonobj=new JSONObject(line);



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

            if(urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null; */

        HttpClient httpclient = new DefaultHttpClient(getHttpParams());

        HttpResponse response = null;

        HttpGet httpGet;
        String result = null;
        String basicc="Basic ";
        StringEntity se = null;
        JSONObject jobj=new JSONObject();


        httpGet = new HttpGet("http://220.227.57.26/ihna_webapp/notifications");
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-type", "application/json");
        //   httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpGet.setHeader("Authorization",basicc.concat(authn.trim()));
        try {
            response = httpclient.execute(httpGet);
            result=inputStreamToString(response.getEntity()
                    .getContent());

            Log.d("response","response:"+result);

        }
        catch (SocketTimeoutException e) {

            System.out.println("After Execute TIME_OUT_EXECPTION \n");

            // TODO: handle exception
            // response=StaticValues.TIME_OUT_EXECPTION;
        } catch (ConnectTimeoutException e) {
            // TODO: handle exception

        }

        catch (Exception e) {
            System.out.println("OOOOOOOOOOOPSSSSS \n");



        }
        return result;


    }

    public String inputStreamT(BufferedReader rd) {

        String line = "";
        StringBuilder total = new StringBuilder();

        // Wrap a BufferedReader around the InputStream


        try {
            // Read response until the end
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return full string
        return total.toString();
    }


    }



