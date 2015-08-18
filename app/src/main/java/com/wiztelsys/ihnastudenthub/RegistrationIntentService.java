package com.wiztelsys.ihnastudenthub;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;


import org.json.JSONObject;


import java.io.IOException;

import java.net.HttpURLConnection;


/**
 * Created by Raju on 21-07-2015.
 */
public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};
    static final String SENDER_ID = "880618443660";
    static final String SERVER_URL = "http://220.227.57.26/push_notification_android/gcm_server_php/register.php";//220.227.57.26/push_notification_android/gcm_server_php/register.php
    SharedPreferences.Editor editor;
    HttpURLConnection myurlcon;
    JSONObject jobj;
    private static final int CONN_TIMEOUT = 10 * 1000;
SharedPreferences sharedPreferences;
    // socket timeout, in milliseconds (waiting for data)
    private static final int SOCKET_TIMEOUT = 60 * 1000;
    String token;
    public RegistrationIntentService()
    {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
      //  SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {
            // In the (unlikely) event that multiple refresh operations occur simultaneously,
            // ensure that they are processed sequentially.
            synchronized (TAG) {
                // [START register_for_gcm]
                // Initially this call goes out to the network to retrieve the token, subsequent calls
                // are local.
                // [START get_token]
                InstanceID instanceID = InstanceID.getInstance(this);
                token = instanceID.getToken(SENDER_ID,
                        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                // [END get_token]
                Log.d("iddddddddddddddd", "GCM Registration Token: " + token);

               //  webservicefor_register_pin(token);
           // ****** //   sendRegistrationToServer(SERVER_URL,token);
                // Subscribe to topic channels
                sharedPreferences = getSharedPreferences("notification", Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putBoolean("server_reg", false);
                editor.putString("token",token);
                editor.commit();

                subscribeTopics(token);

                // You should store a boolean that indicates whether the generated token has been
                // sent to your server. If the boolean is false, send the token to your server,
                // otherwise your server should have already received the token.
                sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, true).apply();
                // [END register_for_gcm]
            }
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            sharedPreferences = getSharedPreferences("notification", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putBoolean("server_reg", false);
            editor.commit();
            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false).apply();
        }
        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(QuickstartPreferences.REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void subscribeTopics(String token) throws IOException {
        for (String topic : TOPICS) {
            GcmPubSub pubSub = GcmPubSub.getInstance(this);
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }
    //*************************************************************************************************
//
//    private void sendRegistrationToServer(String endpoint,String token)throws IOException {
//        // Add custom implementation, as needed.
//
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("regId", token);
//        URL url;
//        try {
//            url = new URL(endpoint);
//        } catch (MalformedURLException e) {
//            throw new IllegalArgumentException("invalid url: " + endpoint);
//        }
//        StringBuilder bodyBuilder = new StringBuilder();
//        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
//        // constructs the POST body using the parameters
//        while (iterator.hasNext()) {
//            Map.Entry<String, String> param = iterator.next();
//            bodyBuilder.append(param.getKey()).append('=')
//                    .append(param.getValue());
//            if (iterator.hasNext()) {
//                bodyBuilder.append('&');
//            }
//        }
//        String body = bodyBuilder.toString();
//        Log.v(TAG, "Posting '" + body + "' to " + url);
//        byte[] bytes = body.getBytes();
//        HttpURLConnection conn = null;
//        try {
//            Log.e("URL", "> " + url);
//            conn = (HttpURLConnection) url.openConnection();
//            conn.setDoOutput(true);
//            conn.setUseCaches(false);
//            conn.setFixedLengthStreamingMode(bytes.length);
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Type",
//                    "application/x-www-form-urlencoded;charset=UTF-8");
//            // post the request
//            OutputStream out = conn.getOutputStream();
//            out.write(bytes);
//            out.close();
//            // handle the response
//            int status = conn.getResponseCode();
//            if (status != 200) {
//                throw new IOException("Post failed with error code " + status);
//            }
//        } finally {
//            if (conn != null) {
//                conn.disconnect();
//            }
//        }
//    }
    //***********************************************************************************************


}
