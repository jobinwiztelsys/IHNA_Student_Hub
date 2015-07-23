package com.wiztelsys.ihnastudenthub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginStudentHub extends Activity implements View.OnClickListener {

    Button sigin,call;         // for login button
    EditText username_et; //edittext for username
    EditText password_et; //edittext for password
    Boolean first_time_login; // for checking whether the user is using the app for the first time
    SharedPreferences sharedPreferences; // For storing the first time login boolean and intermediate data
    SharedPreferences.Editor editor;

    String Username,Password; // to save the edittext name and password
    String authorization = "";

    Server_utilities server_utilities=new Server_utilities();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("IHNA_STUDENTHUB", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        first_time_login = sharedPreferences.getBoolean("firstlogin", true);

//****if the user has registered he will be directed to pin login class else to Register pin class

if(!first_time_login){
    Intent pin_login=new Intent(LoginStudentHub.this,Pin_Login.class);
    startActivity(pin_login);
    finish();
}


else {
    setContentView(R.layout.activity_login_student_hub);
    initializeviews();
}



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_student_hub, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initializeviews(){
        sigin=(Button)findViewById(R.id.login_signin_btn);
        username_et=(EditText)findViewById(R.id.login_unameET);
        password_et=(EditText)findViewById(R.id.login_passwordET);
        call=(Button)findViewById(R.id.call_ihnaBtn);
        call.setOnClickListener(this);
        sigin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Username=username_et.getText().toString();
        Password=password_et.getText().toString();
        switch (view.getId()){
            case R.id.call_ihnaBtn:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "1800225283"));
                startActivity(intent);
                break;
            case R.id.login_signin_btn:
                callinggwebservice();
                break;

        }
  /*    if(first_time_login){
          Intent register_pin=new Intent(LoginStudentHub.this,Register_Pin_Class.class);
          startActivity(register_pin);
          finish();
      }  */



    }
public void callinggwebservice(){

    authorization = Username + ":" + Password;
    byte[] encodedBytes;
    encodedBytes = Base64.encode(authorization.getBytes(), 0);
    authorization = "Basic " + encodedBytes;

    new AsyncTask<String,Void,String>(){

        @Override
        protected String doInBackground(String... strings) {
           return server_utilities.login_webservice(strings[0]);

        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("response from server","is"+s);
            Toast.makeText(getApplicationContext(),"response",Toast.LENGTH_LONG).show();
        }

    }.execute(authorization);
}

}
