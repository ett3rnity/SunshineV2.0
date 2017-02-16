package com.example.momspagetti.mysunshinev20;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

/**
 * Created by Саня on 18.01.2017.
 */

public class ListElements {

    private Bitmap img;
    private String day_stat;
    private String weather_stat;
    private String max_temperature;
    private String min_temperature;

    public ListElements(Bitmap img, String day_stat, String weather_stat, String max_temperature, String min_temperature){
        this.img=img;
        this.day_stat=day_stat;
        this.weather_stat=weather_stat;
        this.max_temperature=max_temperature;
        this.min_temperature=min_temperature;
    }

    public Bitmap getImage() { return img; }


    public String getWeather_stat(){
    return weather_stat;
    }

    public String getDay_stat(){
        return day_stat;
    }

    public String getMax_temperature(){
        return max_temperature;
    }

    public String getMin_temperature(){
        return min_temperature;
    }
}
