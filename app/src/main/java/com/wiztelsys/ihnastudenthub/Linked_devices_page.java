package com.wiztelsys.ihnastudenthub;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Raju on 25-07-2015.
 */
public class Linked_devices_page extends Home_page {
    ListView listView;
    ArrayList<String>al=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linked_devices_xml_page);
        initializeviews();
        addDrawerItems();
        al.add("device 1");
        al.add("device 2");
        listView=(ListView)findViewById(R.id.linked_listview);
        listView.setAdapter(new Base_view(getApplicationContext(),al));
    }

    public class Viewholder{
        ImageView imageView;
        TextView id;
        TextView cmphd;
        TextView stas;
    }



    public class Base_view extends BaseAdapter {

        Context context;
ArrayList<String>al=new ArrayList<>();
        public Base_view(Context con ,ArrayList<String> al) {
            // TODO Auto-generated constructor stub
            this.context=con;
            this.al=al;


        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return al.size();

        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return al.get(position);

        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
           return position;

        }

        @SuppressWarnings("unused")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            Viewholder holder=new Viewholder();

            //holder.tname.setText(act.get(position));

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.linked_devices_listview_root_element, null);

                holder.cmphd=(TextView)convertView.findViewById(R.id.linked_device_nameTV);
                holder.imageView=(ImageView)findViewById(R.id.imageView);
                holder.id=(TextView)convertView.findViewById(R.id.tv1);
                holder.stas=(TextView)convertView.findViewById(R.id.tv2);
                convertView.setTag(holder);

            }

            holder=(Viewholder)convertView.getTag();

holder.id.setText(al.get(position));
            holder.cmphd.setText(al.get(position));

            holder.id.setText("devices");

            return convertView;
        }

    }
}
