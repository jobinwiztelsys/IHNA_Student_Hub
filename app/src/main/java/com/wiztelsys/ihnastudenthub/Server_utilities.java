package com.wiztelsys.ihnastudenthub;

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


    /*   try {

            URL myURL = new URL("http://10.0.0.37/ihna_webapp/users/login");
            HttpURLConnection myURLConnection = (HttpURLConnection) myURL.openConnection();
            String userCredentials = "username:password";
            // String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));

            myURLConnection.setRequestMethod("POST");

            myURLConnection.addRequestProperty("Authorization",authorization);
         //   myURLConnection.setRequestProperty("Content-Type", "application/json");
            myURLConnection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        //    myURLConnection.setRequestProperty("Accept", "application/json");
            myURLConnection.setUseCaches(false);
            myURLConnection.setDoInput(true);
            myURLConnection.setDoOutput(true);
           Log.d("rajeev","111111111111"+myURLConnection.getResponseMessage());
            BufferedReader reader = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
          //  BufferedReader br = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream(),"utf-8"));

            String line = reader.readLine();

            Log.d("rajeev","111111111111"+line);
            return null;
        } catch (Exception e) {

            e.printStackTrace();

        } finally {


        }
       return null; */
   /*     HttpClient httpClient=new DefaultHttpClient();
        HttpPost httpPost=new HttpPost("http://220.227.57.26/ihna_webapp/users/login");
        StringEntity se = null;

        try {
            //Log.v("jsonString new","Username"  +Username);
            se=new StringEntity("http://10.0.0.30/ihna_webapp/users/login".toString());
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type", "application/json");
        httpPost.addHeader("Authorization", "Basic c3JlZWt1bWFyOjEyMzQ1Ng==");





        try {

            HttpResponse httpResponse=httpClient.execute(httpPost);
            Log.d("post data","httppost"  +httpResponse);
            HttpEntity entity=httpResponse.getEntity();
            if(entity!=null){

                InputStream in=entity.getContent();
                String line = null;
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                line= reader.readLine();
                Log.d("response","response:"+line);

            }

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return  null; */
     /*   try {
            HttpUriRequest request = new HttpPost("http://10.0.0.37/ihna_webapp/users/login");
            HttpClient httpclient = new DefaultHttpClient();
         //   request.setHeader("Accept", "application/json");
         //   request.setHeader("Content-type", "application/json");
            request.addHeader("Content-Type", "application/x-www-form-urlencoded");
            request.addHeader("Authorization", authorization);

            HttpResponse httpResponse= httpclient.execute(request);
            HttpEntity entity=httpResponse.getEntity();
            InputStream in=entity.getContent();
            String line = null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            line= reader.readLine();
            Log.d("response","response:"+line);
            JSONObject jsonObject=new JSONObject(line);
            Log.d("response","response:"+jsonObject.getString("message"));
        }
        catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return null; */
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


    public String webservice_home_profile(String authen){
        HttpClient httpclient = new DefaultHttpClient(getHttpParams());

        HttpResponse response = null;

        HttpPost httppost;
        String result = null;
        String basic="Basic ";

        httppost = new HttpPost("http://10.0.0.100/ihna_webapp/profiles/view/1700");
        httppost.setHeader("Accept", "application/json");
        httppost.setHeader("Content-type", "application/json");
        //  httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httppost.setHeader("Authorization",basic.concat(authen.trim()));
        try {
            response = httpclient.execute(httppost);
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

    }



