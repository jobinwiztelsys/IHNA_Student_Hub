package com.wiztelsys.ihnastudenthub;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Raju on 20-07-2015.
 */
public class Profile_home_fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_profile_fragmentxml, container, false);



        return v;
    }

    public static Profile_home_fragment newInstance(String text) {

        Profile_home_fragment f = new Profile_home_fragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}
