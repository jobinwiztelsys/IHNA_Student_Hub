package com.wiztelsys.ihnastudenthub;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by Raju on 21-07-2015.
 */
public class MyInstanceIDListenerService extends InstanceIDListenerService {

    private static final String TAG = "MyInstanceIDLS";
    Register_Pin_Class register_pin_class=new Register_Pin_Class();

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. This call is initiated by the
     * InstanceID provider.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
        Intent intent = new Intent(this,RegistrationIntentService.class);
        startService(intent);
        register_pin_class.callwebservice();

    }
    // [END refresh_token]
}
