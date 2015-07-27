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
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


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
        int settings = EditorInfo.TYPE_CLASS_TEXT;
        username_et.setInputType(settings);
        username_et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
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
              /*  if(first_time_login){
                    Intent register_pin=new Intent(LoginStudentHub.this,Home_page.class);
                    startActivity(register_pin);
                    finish();
                }  */
                callinggwebservice();
                break;

        }
  /*    if(first_time_login){
          Intent register_pin=new Intent(LoginStudentHub.this,Register_Pin_Class.class);
          startActivity(register_pin);
          finish();
          }
      }  */



    }
public void callinggwebservice(){
    byte[] data = null;
    authorization = Username + ":" + Password;
    try {
        data = authorization.getBytes("UTF-8");
    }
    catch (UnsupportedEncodingException e1) {
        e1.printStackTrace();
    }


   String output=Base64.encodeToString(data, Base64.DEFAULT);

    Log.d("11111111111111",""+output);

    new AsyncTask<String,Void,String>(){

        @Override
        protected String doInBackground(String...strings) {
           return server_utilities.login_webservice(strings[0]);

        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("response from server","is"+s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                Integer user_id=jsonObject.getInt("user_id");
                Log.d("response from server","is"+user_id);

                sharedPreferences = getSharedPreferences("IHNA_STUDENTHUB", Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();

                editor.putInt("user_id", user_id);
                editor.commit();
                if(user_id!=null){

                    if(first_time_login){
                        Intent register_pin=new Intent(LoginStudentHub.this,Register_Pin_Class.class);
                        register_pin.putExtra("user_id",user_id);
                        register_pin.putExtra("username",Username);
                        register_pin.putExtra("password",Password);
                        startActivity(register_pin);
                        finish();
                    }
                  /*  Intent home=new Intent(LoginStudentHub.this,Home_page.class);
                    home.putExtra("user_id",user_id);
                    startActivity(home);
                    finish();  */
                }

            }catch(JSONException e){
                e.printStackTrace();
            }

        }

    }.execute(output);
}

}
