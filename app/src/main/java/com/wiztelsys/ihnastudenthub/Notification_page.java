package com.wiztelsys.ihnastudenthub;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Raju on 21-07-2015.
 */
public class Notification_page extends Home_page {

    static ArrayList<String>notification_message=new ArrayList<>();
    ListView notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_page_xml);
        initializeviews();
        addDrawerItems();
        notification=(ListView)findViewById(R.id.notification_listView);
        notification.setAdapter(new Base(getApplicationContext(),notification_message));
    }

    public static void displaymessage(String message){
notification_message.add(message);
        Log.d("222222222222222222222", "Message: " + message);
    }

    private class Viewholder{
        TextView notification_desc;

    }

    public class Base extends BaseAdapter {
        ArrayList<String>notification=new ArrayList<String>();

        Context context;

        public Base(Context con, ArrayList<String> ntfy) {
            // TODO Auto-generated constructor stub
            this.context=con;

            this.notification=ntfy;


        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return notification.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return notification.get(position);
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
                convertView = inflater.inflate(R.layout.notification_listview_root_element, null);

                holder.notification_desc=(TextView)convertView.findViewById(R.id.notificationdescriptionTV);

                convertView.setTag(holder);

            }

            holder=(Viewholder)convertView.getTag();



            holder.notification_desc.setText("" + notification.get(position));



            return convertView;
        }

    }
}
