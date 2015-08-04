package com.wiztelsys.ihnastudenthub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


/**
 * Created by Raju on 20-07-2015.
 */
public class Profile_home_fragment extends Fragment {
    TextView f_name, t_name, l_name, p_name, u_name;
    EditText Profile_page_user_addressTV;
    String password;
    Integer user_id1;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Server_utilities server_utilities = new Server_utilities();
    String authorization;
    String output;
    JSONObject jobj;
    JSONArray result = null;
    JSONObject jsonObject;
    String Profile_name;
    ProgressBar progressBar;
    ImageView imageView2;
    public static String timeStamp;
    static String dirname = "IHNA";
    String filename;
    Bitmap bitmap;
    String picturePath;
    ArrayList<String> camera_image_path=new ArrayList<>();
    Uri photoUri;
    ImageButton home_edit_btn;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.home_profile_fragmentxml, container, false);

        sharedPreferences = getActivity().getSharedPreferences("IHNA_STUDENTHUB", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        password = sharedPreferences.getString("password", null);
        user_id1 = sharedPreferences.getInt("installation_id", 0);
        Log.d("useridinhome", "111111111111:" + password + "" + user_id1);

        progressBar = (ProgressBar) v.findViewById(R.id.pbHeaderProgress);
        f_name = (TextView) v.findViewById(R.id.Profile_page_first_nametv);
        t_name = (TextView) v.findViewById(R.id.Profile_page_titleTV);
        l_name = (TextView) v.findViewById(R.id.Profile_page_last_nametv);
        p_name = (TextView) v.findViewById(R.id.home_page_username1);
        u_name = (TextView) v.findViewById(R.id.Profile_page_user_nametv);


        home_edit_btn=(ImageButton)v.findViewById(R.id.home_edit_btn);


        home_edit_btn.setTag(R.drawable.home_edit_btn);
        Profile_page_user_addressTV=(EditText)v.findViewById(R.id.Profile_page_user_addressTV);

        Profile_page_user_addressTV.setEnabled(false);
        Profile_page_user_addressTV.setClickable(false);
        Profile_page_user_addressTV.setFocusableInTouchMode(false);
        Profile_page_user_addressTV.setFocusable(false);

        int settings = EditorInfo.TYPE_CLASS_TEXT;
        Profile_page_user_addressTV.setInputType(settings);
        Profile_page_user_addressTV.setImeOptions(EditorInfo.IME_ACTION_DONE);





        imageView2 = (ImageView) v.findViewById(R.id.imageView2);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
            }
        });
        home_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("taggggg",""+home_edit_btn.getTag());

                if(home_edit_btn.getTag().equals(R.drawable.home_edit_btn)){
                    home_edit_btn.setTag(R.drawable.ok_button_edit);
                    home_edit_btn.setImageResource(R.drawable.ok_button_edit);
                    Profile_page_user_addressTV.setEnabled(true);
                    Profile_page_user_addressTV.setClickable(true);
                    Profile_page_user_addressTV.setFocusableInTouchMode(true);
                    Profile_page_user_addressTV.setFocusable(true);
                    Profile_page_user_addressTV.setBackgroundResource(R.drawable.profile_address_background);
                }
                else if(home_edit_btn.getTag().equals(R.drawable.ok_button_edit)){

                    calltowebservice_address(Profile_page_user_addressTV.getText().toString());
                    home_edit_btn.setImageResource(R.drawable.home_edit_btn);
                    home_edit_btn.setTag(R.drawable.home_edit_btn);
                    Profile_page_user_addressTV.setEnabled(false);
                    Profile_page_user_addressTV.setClickable(false);
                    Profile_page_user_addressTV.setFocusableInTouchMode(false);
                    Profile_page_user_addressTV.setFocusable(false);
                    Profile_page_user_addressTV.setBackgroundResource(R.color.transparent);
                }
            }
        });

        calltowebservice();
        return v;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public static Profile_home_fragment newInstance(String text) {

        Profile_home_fragment f = new Profile_home_fragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    public void calltowebservice() {

        byte[] data = null;
        authorization = user_id1 + ":" + password;
        try {
            data = authorization.getBytes("UTF-8");
            output = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        new AsyncTask<String, Void, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected String doInBackground(String... S) {

                return server_utilities.webservice_home_profile(S[0]);

            }

            @Override
            protected void onPostExecute(String s) {


                Log.d("inside homeeeeeeeeeeee", "is" + s);
                progressBar.setVisibility(View.INVISIBLE);
                if (s == null) {
                    Toast.makeText(getActivity().getApplicationContext(), "Connection Timed Out...", Toast.LENGTH_LONG).show();
                    return;
                }
                try {

                    jsonObject = new JSONObject(s);
                    result = jsonObject.getJSONArray("profiles");
                    for (Integer i = 0; i < result.length(); i++) {
                        jobj = result.getJSONObject(i);

                        Profile_name = jobj.getString("first_name");
                        Log.d("999999999999", "" + jobj.getString("first_name"));

                        f_name.setText(Profile_name);
                        t_name.setText(jobj.getString("title"));
                        l_name.setText(jobj.getString("last_name"));
                        p_name.setText(jobj.getString("prefer_name"));
                        u_name.setText(jobj.getString("institute_email"));
                        Profile_page_user_addressTV.setText(jobj.getString("address"));
                        Notification_variables.mobile_number = jobj.getString("mobile");
                        Notification_variables.profile_id = jobj.getInt("id");
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }


            }

        }.execute(output);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 0) {
            //opencamera();
            return;
        } else if (requestCode == 1) {


            photoUri = data.getData();
            String[] projection1 = {MediaStore.Images.Media.DATA};
            try {
                Cursor cursor1 = getActivity().getContentResolver().query(photoUri, projection1, null, null, null);
                cursor1.moveToFirst();

                int columnIndex1 = cursor1.getColumnIndex(projection1[0]);
                picturePath = cursor1.getString(columnIndex1);
                camera_image_path.add(compressImage(picturePath));
                cursor1.close();
                Log.d("Picture Path camere", picturePath);
            } catch (Exception e) {
                Log.e("Path Error", e.toString());
            }

            //   camera_image_path.add(compressImage(fileUri.getPath()));


            // downsizing image as it throws OutOfMemory Exception for larger
            // images

            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 5;
                //   bitmap =MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);// BitmapFactory.decodeFile(picturePath,options);
                //   bitmap=BitmapFactory.decodeFile(picturePath,options);

                // Bitmap b = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                bitmap = (Bitmap) data.getExtras().get("data");
                Drawable drawable=new BitmapDrawable(getResources(),bitmap);
                imageView2.setBackground(drawable);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public Uri getOutputMediaFileUri()
    {
        Log.d("jobin", "in the getOutputMediaFileUri");
        return Uri.fromFile(getOutputMediaFile());
    }

    private static File getOutputMediaFile()
    {
        Log.d("jobin", "in the getOutputMediaFile");
        File mediaStorageDir = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),dirname);
        if (!mediaStorageDir.exists())
        {
            Log.d("jobin", "in creating directory");
            if (!mediaStorageDir.mkdir())
            {
                mediaStorageDir.mkdir();

            }
        }
        Log.d("jobin", "in making timestamp");
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new java.util.Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");
        Log.d("jobin", "file name uri is: "+mediaFile.toString());
        return mediaFile;
    }


    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    // compressing the image from camera//

    public String compressImage(String imageUri) {

        String filePath =imageUri;
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath,options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;
        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

        options.inSampleSize =calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16*1024];

        try{
            bmp = BitmapFactory.decodeFile(filePath,options);
        }
        catch(OutOfMemoryError exception){
            exception.printStackTrace();

        }
        try{
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        }
        catch(OutOfMemoryError exception){
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float)options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth()/2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));


        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream out = null;
        filename =getOutputMediaFileUri().getPath();
        try {
            out = new FileOutputStream(filename);
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Log.d("final path","111111"+filename);
        return filename;

    }


    public void calltowebservice_address(final String address){

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
            protected void onPreExecute() {

                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected String doInBackground(String...S) {

                return server_utilities.webservicefor_address(S[0],address);

            }

            @Override
            protected void onPostExecute(String s) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.d("inside notificationss", "is" + s);
                //  progressBar.setVisibility(View.INVISIBLE);
                try {

                    jsonObject = new JSONObject(s);
                    if (jsonObject.getString("message").contains("Saved")) {

                       Toast.makeText(getActivity().getApplicationContext(),"Saved",Toast.LENGTH_LONG).show();
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
