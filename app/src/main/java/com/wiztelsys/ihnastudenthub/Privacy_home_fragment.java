package com.wiztelsys.ihnastudenthub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Raju on 20-07-2015.
 */
public class Privacy_home_fragment extends Fragment {
Button reset,mobile_number,linked_dev;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.privacy_page, container, false);
        reset=(Button)v.findViewById(R.id.button2);
        mobile_number=(Button)v.findViewById(R.id.button3);
        linked_dev=(Button)v.findViewById(R.id.button4);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reset_pin=new Intent(getActivity(),Reset_pin_page.class);
                startActivity(reset_pin);
                getActivity().finish();
            }
        });
mobile_number.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent mobile=new Intent(getActivity(),Mobile_number_page.class);
        startActivity(mobile);
        getActivity().finish();
    }
});

        linked_dev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent linked=new Intent(getActivity(),Linked_devices_page.class);
                startActivity(linked);
                getActivity().finish();
            }
        });
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
