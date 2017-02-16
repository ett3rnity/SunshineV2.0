package com.example.momspagetti.mysunshinev20;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;


/**
 * Created by Саня on 18.01.2017.
 */

public class ListAdapter extends ArrayAdapter<ListElements> {

    public ListAdapter(Context context, int position, List<ListElements>listElements){
        super(context,position,listElements);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;

        if(view==null){
            LayoutInflater layoutInflater=LayoutInflater.from(getContext());
            view=layoutInflater.inflate(R.layout.everyday_info_listview,null);
        }
        ListElements listElements=getItem(position);
        //if (listElements!=null)-proverit

        Typeface weatherFont = Typeface.createFromAsset( getContext().getAssets(),"fonts/CameronSansLight.ttf" );



            ImageView img=(ImageView)view.findViewById(R.id.img_info_listview);
            TextView day_status=(TextView)view.findViewById(R.id.tv_daystatus_listview);
            TextView weather_status=(TextView)view.findViewById(R.id.tv_weatherstatus_listview);
            TextView max_temp=(TextView)view.findViewById(R.id.tv_maxtemp_listview);
            TextView min_temp=(TextView)view.findViewById(R.id.tv_mintemp_listview);

            day_status.setTypeface(weatherFont);
            weather_status.setTypeface(weatherFont);
            max_temp.setTypeface(weatherFont);
            min_temp.setTypeface(weatherFont);


                img.setImageBitmap(listElements.getImage());

                day_status.setText(listElements.getDay_stat());

                weather_status.setText(listElements.getWeather_stat());

                max_temp.setText(listElements.getMax_temperature());

                min_temp.setText(listElements.getMin_temperature());



        return view;
    }
}
