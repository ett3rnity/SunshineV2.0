package com.example.momspagetti.mysunshinev20;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Саня on 08.12.2016.
 */

public class NetworkUtils {

   public static final String BASE_LINK="http://api.openweathermap.org/data/2.5/forecast/daily";
   public static final String BASE_QUERY="q";
   public static final String BASE_CNT="cnt";
   public static final String BASE_UNITS="units";
   public static final String BASE_APPID="APPID";
   public static final String BASE_CODE="8701c8a619a00eb3bf67bbcb82356913";


    public static URL buildUrl(String city_name,String cnt,String units) {
        Uri buildUri = Uri.parse(BASE_LINK).buildUpon().appendQueryParameter(BASE_QUERY, city_name).appendQueryParameter(BASE_CNT, cnt)
                .appendQueryParameter(BASE_UNITS,units)
                .appendQueryParameter(BASE_APPID, BASE_CODE).build();

        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection httpsURLConnection=(HttpURLConnection) url.openConnection();
        try {
            InputStream stream = httpsURLConnection.getInputStream();
            Scanner sc = new Scanner(stream).useDelimiter("\\A");
            if(sc.hasNext()){
                return sc.next();
            }
            else {
                return null;
            }
        }finally {
            httpsURLConnection.disconnect();
        }
    }
}
