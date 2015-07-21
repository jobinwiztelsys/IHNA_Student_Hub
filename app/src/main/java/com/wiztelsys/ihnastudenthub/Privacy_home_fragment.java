package com.wiztelsys.ihnastudenthub;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Raju on 20-07-2015.
 */
public class Privacy_home_fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.privacy_page, container, false);



        return v;
    }

    public static Privacy_home_fragment newInstance(String text) {

        Privacy_home_fragment f = new Privacy_home_fragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}
