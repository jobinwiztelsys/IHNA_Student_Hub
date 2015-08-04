package com.wiztelsys.ihnastudenthub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Raju on 17-07-2015.
 */
public class Library_page extends Home_page {

GridView gridView;

    int[] imageId;
    String[] web = {
            "IHNA",
            "IHNA",
            "IHNA",
            "IHNA",
            "IHNA",
            "IHNA",
            "IHNA",
            "IHNA",
            "IHNA",
            "IHNA",
            "IHNA",
            "IHNA",
            "IHNA",
            "IHNA",
            "IHNA",


    } ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_view_xml);
        gridView=(GridView)findViewById(R.id.gridView);
        CustomGrid adapter = new CustomGrid(Library_page.this, web,imageId);

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(Library_page.this, "You Clicked " , Toast.LENGTH_SHORT).show();

            }
        });
        initializeviews();
        addDrawerItems();
    }

    public class CustomGrid extends BaseAdapter {
        private Context mContext;
        private final String[] web;
        private final int[] Imageid;

        public CustomGrid(Context c,String[] web,int[] Imageid ) {
            mContext = c;
            this.Imageid = Imageid;
            this.web = web;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return web.length;

        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View grid;
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {

                grid = new View(mContext);
                grid = inflater.inflate(R.layout.library_listview_root_element, null);
                TextView textView = (TextView) grid.findViewById(R.id.library_listview_icon_nameTV);
                ImageView imageView = (ImageView)grid.findViewById(R.id.library_listview_icon_imageview);
                textView.setText(web[position]);
             //   imageView.setImageResource(Imageid[position]);
            } else {
                grid = (View) convertView;
            }

            return grid;
        }
    }

    @Override
    public void onBackPressed() {
        Intent home=new Intent(getApplicationContext(),Home_page.class);
        startActivity(home);
        finish();
    }
}
