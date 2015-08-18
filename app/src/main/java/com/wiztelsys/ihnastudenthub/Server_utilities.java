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
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
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
import java.net.URLEncoder;

/**
 * Created by Raju on 23-07-2015.
 */
public class Server_utilities {
    HttpURLConnection myURLConnection;
    private static final int CONN_TIMEOUT = 10 * 1000;

    // socket timeout, in milliseconds (waiting for data)
    private static final int SOCKET_TIMEOUT = 60 * 1000;
    String authorization1 = null;
    HttpURLConnection myurlcon;
    JSONObject jobj;

    Server_utilities() {

    }

    // Establish connection and socket (data retrieval) timeouts
    HttpParams getHttpParams() {

        HttpParams htpp = new BasicHttpParams();

        HttpConnectionParams.setConnectionTimeout(htpp, CONN_TIMEOUT);
        HttpConnectionParams.setSoTimeout(htpp, SOCKET_TIMEOUT);

        return htpp;
    }


    // *******8function to call webservice for login in user information ****** //

    public String login_webservice(String authorization) {

        String authorization1 = authorization.trim();
        String basic = "Basic ";


        String line = null;
        try {
            URL myurl = new URL("http://10.0.0.37/ihna_webapp/users/login");
            myurlcon = (HttpURLConnection) myurl.openConnection();

            myurlcon.setRequestProperty("Accept", "application/json");
            myurlcon.setRequestProperty("Content-type", "application/json");
            myurlcon.setRequestProperty("Authorization", basic.concat(authorization1.toString().trim()));
            myurlcon.setRequestMethod("POST");
            myurlcon.setUseCaches(false);
            myurlcon.setDoInput(true);
            myurlcon.setDoOutput(true);
            myurlcon.connect();
            //  BufferedReader br = new BufferedReader(new InputStreamReader(myurlcon.getInputStream(),"utf-8"));

            line = inputStreamToString(myurlcon.getInputStream());
            Log.d("response from server", "is" + line);
            return line;


        } catch (SocketTimeoutException s) {

            s.printStackTrace();
            return null;
        } catch (ConnectTimeoutException e) {

            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // response=null;
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // response=null;
            e.printStackTrace();
        } catch (IOException e) {
            // response=null;
            e.printStackTrace();
        } catch (Exception e) {
            // response=null;
            Log.e("Buffer Error", "Error: " + e.toString());
        } finally {
            myurlcon.disconnect();
        }
        return line;
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

    public String webservice_home_profile(String authen) {
//
        String basic = "Basic ";
        try {
            URL myurl = new URL("http://10.0.0.37/ihna_webapp/profiles");
            myurlcon = (HttpURLConnection) myurl.openConnection();
            myurlcon.setRequestMethod("GET");
            myurlcon.setRequestProperty("Accept", "application/json");
            myurlcon.setRequestProperty("Content-type", "application/json");
            myurlcon.setRequestProperty("Authorization", basic.concat(authen.toString().trim()));

            myurlcon.setConnectTimeout(CONN_TIMEOUT);
            myurlcon.setReadTimeout(CONN_TIMEOUT);
            myurlcon.setUseCaches(false);
            myurlcon.setDoInput(true);
            //   myurlcon.setDoOutput(true);
            myurlcon.connect();
            //  BufferedReader br = new BufferedReader(new InputStreamReader(myurlcon.getInputStream(),"utf-8"));

            String line = inputStreamToString(myurlcon.getInputStream());
            Log.d("response from server", "is" + line);
            return line;


        } catch (SocketTimeoutException s) {

            s.printStackTrace();
            return null;
        } catch (ConnectTimeoutException e) {

            e.printStackTrace();
        }
//        catch(JSONException e){
//            //  response=null;
//            e.printStackTrace();
//
//        }
        catch (UnsupportedEncodingException e) {
            // response=null;
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // response=null;
            e.printStackTrace();
        } catch (IOException e) {
            // response=null;
            e.printStackTrace();
        } catch (Exception e) {
            // response=null;
            Log.e("Buffer Error", "Error: " + e.toString());
        } finally {
            myurlcon.disconnect();
        }

        return null;

    }

    //*******************************************************************************************
    public String webservicefor_register_pin(String auth, Integer user_id, String passwd, String mac, String div_name,String token) {
        StringEntity se = null;
        jobj = new JSONObject();
        try {
            jobj.put("user_id", user_id);
            jobj.put("four_digit_pin", passwd);
            jobj.put("device_id", mac);
            jobj.put("device_name", div_name);
            jobj.put("device_type", 2);
            jobj.put("push_notification_reg_id",token);
            Log.d("jsonString", "" + jobj.toString());
            se = new StringEntity(jobj.toString());
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();

        }

        try {
            String basic = "Basic ";
            URL myurl = new URL("http://10.0.0.37/ihna_webapp/installations/add");
            myurlcon = (HttpURLConnection) myurl.openConnection();
            myurlcon.setRequestMethod("POST");
            myurlcon.setRequestProperty("Accept", "application/json");
            myurlcon.setRequestProperty("Content-type", "application/json");
            myurlcon.setRequestProperty("Authorization", basic.concat(auth.toString().trim()));

            myurlcon.setConnectTimeout(CONN_TIMEOUT);
            myurlcon.setReadTimeout(CONN_TIMEOUT);
            myurlcon.setUseCaches(false);
            myurlcon.setDoInput(true);
            myurlcon.setDoOutput(true);
            myurlcon.connect();
            OutputStreamWriter wr = new OutputStreamWriter(myurlcon.getOutputStream());
            wr.write(jobj.toString());
            wr.flush();
            wr.close();
            //  BufferedReader br = new BufferedReader(new InputStreamReader(myurlcon.getInputStream(),"utf-8"));

            String line = inputStreamToString(myurlcon.getInputStream());
            //  Log.d("response from server", "is" + line);
            return line;


        } catch (SocketTimeoutException s) {

            s.printStackTrace();
            return null;
        } catch (ConnectTimeoutException e) {

            e.printStackTrace();
        }
//        catch(JSONException e){
//            //  response=null;
//            e.printStackTrace();
//
//        }
        catch (UnsupportedEncodingException e) {
            // response=null;
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // response=null;
            e.printStackTrace();
        } catch (IOException e) {
            // response=null;
            e.printStackTrace();
        } catch (Exception e) {
            // response=null;

        } finally {
            myurlcon.disconnect();
        }

        return null;

    }

    //**************************************************************************************
    public String webservice_for_notification_db(String authn) {


        String basic = "Basic ";
        try {
            URL myurl = new URL("http://10.0.0.37/ihna_webapp/notifications");
            myurlcon = (HttpURLConnection) myurl.openConnection();
            myurlcon.setRequestMethod("GET");
            myurlcon.setRequestProperty("Accept", "application/json");
            myurlcon.setRequestProperty("Content-type", "application/json");
            myurlcon.setRequestProperty("Authorization", basic.concat(authn.toString().trim()));

            myurlcon.setUseCaches(false);
            myurlcon.setDoInput(true);
            // myurlcon.setDoOutput(true);
            myurlcon.connect();
            //  BufferedReader br = new BufferedReader(new InputStreamReader(myurlcon.getInputStream(),"utf-8"));

            String line = inputStreamToString(myurlcon.getInputStream());
            Log.d("response from server", "is" + line);
            return line;


        } catch (SocketTimeoutException s) {

            s.printStackTrace();
            return null;
        } catch (ConnectTimeoutException e) {

            e.printStackTrace();
        }
//        catch(JSONException e){
//            //  response=null;
//            e.printStackTrace();
//
//        }
        catch (UnsupportedEncodingException e) {
            // response=null;
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // response=null;
            e.printStackTrace();
        } catch (IOException e) {
            // response=null;
            e.printStackTrace();
        } catch (Exception e) {
            // response=null;
            Log.e("Buffer Error", "Error: " + e.toString());
        } finally {
            myurlcon.disconnect();
        }
        return null;
    }


    public String webservice_for_device_details(String auther_string) {

        String basic = "Basic ";
        try {
            URL myurl = new URL("http://10.0.0.37/ihna_webapp/installations");
            myurlcon = (HttpURLConnection) myurl.openConnection();
            myurlcon.setRequestMethod("GET");
            myurlcon.setRequestProperty("Accept", "application/json");
            myurlcon.setRequestProperty("Content-type", "application/json");
            myurlcon.setRequestProperty("Authorization", basic.concat(auther_string.toString().trim()));

            myurlcon.setUseCaches(false);
            myurlcon.setDoInput(true);
            // myurlcon.setDoOutput(true);
            myurlcon.connect();
            //  BufferedReader br = new BufferedReader(new InputStreamReader(myurlcon.getInputStream(),"utf-8"));

            String line = inputStreamToString(myurlcon.getInputStream());
            Log.d("response from server", "is" + line);
            return line;


        } catch (SocketTimeoutException s) {

            s.printStackTrace();
            return null;
        } catch (ConnectTimeoutException e) {

            e.printStackTrace();
        }
//        catch(JSONException e){
//            //  response=null;
//            e.printStackTrace();
//
//        }
        catch (UnsupportedEncodingException e) {
            // response=null;
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // response=null;
            e.printStackTrace();
        } catch (IOException e) {
            // response=null;
            e.printStackTrace();
        } catch (Exception e) {
            // response=null;
            Log.e("Buffer Error", "Error: " + e.toString());
        } finally {
            myurlcon.disconnect();
        }
        return null;

    }

    public String webserviceformobile_number(String authentcn, String number) {
        String basic = "Basic ";
        try {
            JSONObject j = new JSONObject();
            j.put("mobile", number);

            URL myurl = new URL("http://10.0.0.37/ihna_webapp/profiles/edit/".concat(Notification_variables.profile_id.toString()).trim());
            myurlcon = (HttpURLConnection) myurl.openConnection();
            myurlcon.setRequestMethod("PUT");
            myurlcon.setRequestProperty("Accept", "application/json");
            myurlcon.setRequestProperty("Content-type", "application/json");
            myurlcon.setRequestProperty("Authorization", basic.concat(authentcn.toString().trim()));

            myurlcon.setUseCaches(false);
            myurlcon.setDoInput(true);
            myurlcon.setDoOutput(true);
            myurlcon.connect();
            OutputStreamWriter wr = new OutputStreamWriter(myurlcon.getOutputStream());
            wr.write(j.toString());
            wr.flush();
            wr.close();
            //  BufferedReader br = new BufferedReader(new InputStreamReader(myurlcon.getInputStream(),"utf-8"));

            String line = inputStreamToString(myurlcon.getInputStream());
            Log.d("response from server", "is" + line);
            return line;


        } catch (SocketTimeoutException s) {

            s.printStackTrace();
            return null;
        } catch (ConnectTimeoutException e) {

            e.printStackTrace();
        }
//        catch(JSONException e){
//            //  response=null;
//            e.printStackTrace();
//
//        }
        catch (UnsupportedEncodingException e) {
            // response=null;
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // response=null;
            e.printStackTrace();
        } catch (IOException e) {
            // response=null;
            e.printStackTrace();
        } catch (Exception e) {
            // response=null;
            Log.e("Buffer Error", "Error: " + e.toString());
        } finally {
            myurlcon.disconnect();
        }
        return null;
    }

    public String webservice_for_read_flag(String ath, String id) {

        String basic = "Basic ";
        try {
            JSONObject j = new JSONObject();
            j.put("read_flag", 2);
            Log.d("idddddddddd", "" + id);
            URL myurl = new URL("http://10.0.0.37/ihna_webapp/notifications/edit/".concat(id.toString()).trim());
            myurlcon = (HttpURLConnection) myurl.openConnection();
            myurlcon.setRequestMethod("PUT");
            myurlcon.setRequestProperty("Accept", "application/json");
            myurlcon.setRequestProperty("Content-type", "application/json");
            myurlcon.setRequestProperty("Authorization", basic.concat(ath.toString().trim()));

            myurlcon.setUseCaches(false);
            myurlcon.setDoInput(true);
            myurlcon.setDoOutput(true);
            myurlcon.connect();
            OutputStreamWriter wr = new OutputStreamWriter(myurlcon.getOutputStream());
            wr.write(j.toString());
            wr.flush();
            wr.close();
            //  BufferedReader br = new BufferedReader(new InputStreamReader(myurlcon.getInputStream(),"utf-8"));

            String line = inputStreamToString(myurlcon.getInputStream());
            Log.d("response from server", "is" + line);
            return line;


        } catch (SocketTimeoutException s) {

            s.printStackTrace();
            return null;
        } catch (ConnectTimeoutException e) {

            e.printStackTrace();
        }
//        catch(JSONException e){
//            //  response=null;
//            e.printStackTrace();
//
//        }
        catch (UnsupportedEncodingException e) {
            // response=null;
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // response=null;
            e.printStackTrace();
        } catch (IOException e) {
            // response=null;
            e.printStackTrace();
        } catch (Exception e) {
            // response=null;
            Log.e("Buffer Error", "Error: " + e.toString());
        } finally {
            myurlcon.disconnect();
        }
        return null;
    }

    public String webservicefor_address(String authentcn, String address) {
        String basic = "Basic ";
        try {
            JSONObject j = new JSONObject();

            j.put("address", address);
            URL myurl = new URL("http://10.0.0.37/ihna_webapp/profiles/edit/".concat(Notification_variables.profile_id.toString()).trim());
            myurlcon = (HttpURLConnection) myurl.openConnection();
            myurlcon.setRequestMethod("PUT");
            myurlcon.setRequestProperty("Accept", "application/json");
            myurlcon.setRequestProperty("Content-type", "application/json");
            myurlcon.setRequestProperty("Authorization", basic.concat(authentcn.toString().trim()));

            myurlcon.setUseCaches(false);
            myurlcon.setDoInput(true);
            myurlcon.setDoOutput(true);
            myurlcon.connect();
            OutputStreamWriter wr = new OutputStreamWriter(myurlcon.getOutputStream());
            wr.write(j.toString());
            wr.flush();
            wr.close();
            //  BufferedReader br = new BufferedReader(new InputStreamReader(myurlcon.getInputStream(),"utf-8"));

            String line = inputStreamToString(myurlcon.getInputStream());
            Log.d("response from server", "is" + line);
            return line;


        } catch (SocketTimeoutException s) {

            s.printStackTrace();
            return null;
        } catch (ConnectTimeoutException e) {

            e.printStackTrace();
        }
//        catch(JSONException e){
//            //  response=null;
//            e.printStackTrace();
//
//        }
        catch (UnsupportedEncodingException e) {
            // response=null;
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // response=null;
            e.printStackTrace();
        } catch (IOException e) {
            // response=null;
            e.printStackTrace();
        } catch (Exception e) {
            // response=null;
            Log.e("Buffer Error", "Error: " + e.toString());
        } finally {
            myurlcon.disconnect();
        }
        return null;
    }


    public String webservicefor_reeset_pin(String authen, Integer install_id, String pin) {
        Log.d("ressssssssssssss", "" + pin);
        StringEntity se = null;
        jobj = new JSONObject();
        try {
            jobj.put("four_digit_pin", pin);

            se = new StringEntity(jobj.toString());
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();

        }

        try {
            String basic = "Basic ";
            URL myurl = new URL("http://10.0.0.37/ihna_webapp/installations/edit/".concat(install_id.toString().trim()));
            myurlcon = (HttpURLConnection) myurl.openConnection();
            myurlcon.setRequestMethod("PUT");
            myurlcon.setRequestProperty("Accept", "application/json");
            myurlcon.setRequestProperty("Content-type", "application/json");
            myurlcon.setRequestProperty("Authorization", basic.concat(authen.toString().trim()));

            myurlcon.setConnectTimeout(CONN_TIMEOUT);
            myurlcon.setReadTimeout(CONN_TIMEOUT);
            myurlcon.setUseCaches(false);
            myurlcon.setDoInput(true);
            myurlcon.setDoOutput(true);
            myurlcon.connect();
            OutputStreamWriter wr = new OutputStreamWriter(myurlcon.getOutputStream());
            wr.write(jobj.toString());
            wr.flush();
            wr.close();
            //  BufferedReader br = new BufferedReader(new InputStreamReader(myurlcon.getInputStream(),"utf-8"));

            String line = inputStreamToString(myurlcon.getInputStream());
            //  Log.d("response from server", "is" + line);
            return line;


        } catch (SocketTimeoutException s) {

            s.printStackTrace();
            return null;
        } catch (ConnectTimeoutException e) {

            e.printStackTrace();
        }
//        catch(JSONException e){
//            //  response=null;
//            e.printStackTrace();
//
//        }
        catch (UnsupportedEncodingException e) {
            // response=null;
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // response=null;
            e.printStackTrace();
        } catch (IOException e) {
            // response=null;
            e.printStackTrace();
        } catch (Exception e) {
            // response=null;

        } finally {
            myurlcon.disconnect();
        }

        return null;

    }

    public String webservice_library(String s) {

        String basic = "Basic ";
        try {
            URL myurl = new URL("http://10.0.0.37/ihna_webapp/libraries");
            myurlcon = (HttpURLConnection) myurl.openConnection();
            myurlcon.setRequestMethod("GET");
            myurlcon.setRequestProperty("Accept", "application/json");
            myurlcon.setRequestProperty("Content-type", "application/json");
            myurlcon.setRequestProperty("Authorization", basic.concat(s.toString().trim()));

            myurlcon.setConnectTimeout(CONN_TIMEOUT);
            myurlcon.setReadTimeout(CONN_TIMEOUT);
            myurlcon.setUseCaches(false);
            myurlcon.setDoInput(true);
            //   myurlcon.setDoOutput(true);
            myurlcon.connect();
            //  BufferedReader br = new BufferedReader(new InputStreamReader(myurlcon.getInputStream(),"utf-8"));

            String line = inputStreamToString(myurlcon.getInputStream());
            Log.d("response from server", "is" + line);
            return line;


        } catch (SocketTimeoutException e) {

            e.printStackTrace();
            return null;
        } catch (ConnectTimeoutException e) {

            e.printStackTrace();
        }
//        catch(JSONException e){
//            //  response=null;
//            e.printStackTrace();
//
//        }
        catch (UnsupportedEncodingException e) {
            // response=null;
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // response=null;
            e.printStackTrace();
        } catch (IOException e) {
            // response=null;
            e.printStackTrace();
        } catch (Exception e) {
            // response=null;
            Log.e("Buffer Error", "Error: " + e.toString());
        } finally {
            myurlcon.disconnect();
        }
        return null;
    }

    public String webservice_pin_login(String authen, Integer pin) {
//

        String basic = "Basic ";
        try {
            URL myurl = new URL("http://10.0.0.37/ihna_webapp/installations/edit/".concat(pin.toString().trim()));
            myurlcon = (HttpURLConnection) myurl.openConnection();
            myurlcon.setRequestMethod("PUT");
            myurlcon.setRequestProperty("Accept", "application/json");
            myurlcon.setRequestProperty("Content-type", "application/json");
            myurlcon.setRequestProperty("Authorization", basic.concat(authen.toString().trim()));

            myurlcon.setConnectTimeout(CONN_TIMEOUT);
            myurlcon.setReadTimeout(CONN_TIMEOUT);
            myurlcon.setUseCaches(false);
            myurlcon.setDoInput(true);
            //   myurlcon.setDoOutput(true);
            myurlcon.connect();
            //  BufferedReader br = new BufferedReader(new InputStreamReader(myurlcon.getInputStream(),"utf-8"));

            String line = inputStreamToString(myurlcon.getInputStream());
            Log.d("response from server", "is" + line);
            return line;


        } catch (SocketTimeoutException s) {

            s.printStackTrace();
            return null;
        } catch (ConnectTimeoutException e) {

            e.printStackTrace();
        }
//        catch(JSONException e){
//            //  response=null;
//            e.printStackTrace();
//
//        }
        catch (UnsupportedEncodingException e) {
            // response=null;
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // response=null;
            e.printStackTrace();
        } catch (IOException e) {
            // response=null;
            e.printStackTrace();
        } catch (Exception e) {
            // response=null;
            Log.e("Buffer Error", "Error: " + e.toString());
        } finally {
            myurlcon.disconnect();
        }

        return null;

    }


    public String webservice_for_notification_count(String authn) {


        String basic = "Basic ";

        try {
            URL myurl = new URL("http://10.0.0.37/ihna_webapp/notifications?read_flag=1&type=count");
            myurlcon = (HttpURLConnection) myurl.openConnection();
            myurlcon.setRequestMethod("GET");
            myurlcon.setRequestProperty("Accept", "application/json");
            myurlcon.setRequestProperty("Content-type", "application/json");
            myurlcon.setRequestProperty("Authorization", basic.concat(authn.toString().trim()));

            myurlcon.setConnectTimeout(CONN_TIMEOUT);
            myurlcon.setReadTimeout(CONN_TIMEOUT);
            myurlcon.setUseCaches(false);
            myurlcon.setDoInput(true);
            //   myurlcon.setDoOutput(true);
            myurlcon.connect();
            //  BufferedReader br = new BufferedReader(new InputStreamReader(myurlcon.getInputStream(),"utf-8"));

            String line = inputStreamToString(myurlcon.getInputStream());
            Log.d("response from server", "is" + line);
            return line;


        } catch (SocketTimeoutException e) {

            e.printStackTrace();
            return null;
        } catch (ConnectTimeoutException e) {

            e.printStackTrace();
        }
//        catch(JSONException e){
//            //  response=null;
//            e.printStackTrace();
//
//        }
        catch (UnsupportedEncodingException e) {
            // response=null;
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // response=null;
            e.printStackTrace();
        } catch (IOException e) {
            // response=null;
            e.printStackTrace();
        } catch (Exception e) {
            // response=null;
            Log.e("Buffer Error", "Error: " + e.toString());
        } finally {
            myurlcon.disconnect();
        }
        return null;
//        try {
//            HttpClient httpclient = new DefaultHttpClient(getHttpParams());
//
//            HttpResponse response = null;
//
//            HttpGet httpget = new HttpGet("http://10.0.0.37/ihna_webapp/notifications?read_flag=1&type=count");
//
//            StringEntity se = null;
//
//
//
//            // httpget.setEntity(se);
//            httpget.setHeader("Authorization",basic.concat(authn.toString().trim()));
//            httpget.setHeader("Accept", "application/json");
//            httpget.setHeader("Content-type", "application/json");
//
//            response = httpclient.execute(httpget);
//            return inputStreamToString(response.getEntity().getContent());
//        } catch (Exception e) {
//
//        }

    }
}



