package com.example.momspagetti.mysunshinev20;


import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.location.Location;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener
        , GoogleApiClient.ConnectionCallbacks {

    String city_name;
    String cnt = "10";
    String units = "metric";
    String retstr = null;
    List<ListElements> listElementsesList = new ArrayList<>();

    Toolbar toolbar;
    TextView tv_toolbar_appname;
    RelativeLayout main_pic;

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    private String mLatitude;
    private String mLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        Typeface appNameFont = Typeface.createFromAsset(getAssets(), "fonts/Handycheera.otf");
        tv_toolbar_appname = (TextView) findViewById(R.id.tv_toolbar_appname);
        tv_toolbar_appname.setTypeface(appNameFont);

        main_pic = (RelativeLayout) findViewById(R.id.main_pic);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);


    }


    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if(mLastLocation!=null){
            mLatitude=String.valueOf(mLastLocation.getLatitude());
            mLongitude=String.valueOf(mLastLocation.getLongitude());
            Log.i("Latitude: ",mLatitude);
            Log.i("Longitude: ",mLongitude);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        ErrorDialogFragment connectionFailed= new ErrorDialogFragment();
        Bundle args=new Bundle();
        args.putInt("connection error №",connectionResult.getErrorCode());
        connectionFailed.setArguments(args);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_settings:
                Intent intent=new Intent(this,SettingsActivity.class);
                startActivity(intent);
                return true;

            case R.id.menu_search:

                SettingsActivity getInfoForQuery=new SettingsActivity();
                city_name=getInfoForQuery.retCityName();
                GetInfo finalinfo=new GetInfo();
                finalinfo.execute();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void setTheAdapter(){
        ListView listView=(ListView)findViewById(R.id.lv_everyday_info);
        ListAdapter listAdapter=new ListAdapter(this,R.layout.everyday_info_listview,listElementsesList);
        listView.setAdapter(listAdapter);

    }


    String getQuery(){

        try {
            retstr=NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildUrl(city_name,cnt,units));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retstr;
    }

    void addCityInfo(String s){

        JSONObject forecast = null;

        try {

            forecast = new JSONObject(s);
            JSONArray list = forecast.getJSONArray("list");
            JSONObject day = list.getJSONObject(0);
            String dayNumb = day.getString("dt");
            long dv=Long.valueOf(dayNumb)*1000;
            dayNumb=String.valueOf(dv);
            dayNumb = new Date(Long.parseLong(dayNumb)).toString();
            dayNumb=dayNumb.substring(0,10);
            JSONObject temp = day.getJSONObject("temp");
            String min = temp.getString("min");
            String max  = temp.getString("max");
            JSONArray weather = day.getJSONArray("weather");
            JSONObject weather_status = weather.getJSONObject(0);
            String main=weather_status.getString("main");
            String description = weather_status.getString("description");
            String icon=weather_status.getString("icon");
            setMainPic(main);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    void setMainPic(String weather_stat){
        switch (weather_stat){
            case "Clear":
                 main_pic.setBackgroundResource(R.drawable.clear1);
                break;
            case "Clouds":
                main_pic.setBackgroundResource(R.drawable.clouds1);
                break;
            case "Mist":
                main_pic.setBackgroundResource(R.drawable.mist1);
                break;
            case "Rain":
                main_pic.setBackgroundResource(R.drawable.mist1);
                break;
            case "Snow":
                main_pic.setBackgroundResource(R.drawable.snow1);
                break;
            case "Thunderstorm":
                main_pic.setBackgroundResource(R.drawable.thunderstorm1);
                break;
            default:
                main_pic.setBackgroundResource(R.drawable.background);
                break;

        }
    }

    Bitmap retWeatherPic(String weather_stat){
        Bitmap result;
        switch (weather_stat){
            case "Clear":
                result=BitmapFactory.decodeResource(getResources(),R.drawable.clear);
               // main_pic.setBackgroundResource(R.drawable.clear1);
                break;
            case "Clouds":
                result=BitmapFactory.decodeResource(getResources(),R.drawable.clouds);
               break;
            case "Mist":
                result=BitmapFactory.decodeResource(getResources(),R.drawable.mist);
             break;
            case "Rain":
                result=BitmapFactory.decodeResource(getResources(),R.drawable.rain);
            break;
            case "Snow":
                result=BitmapFactory.decodeResource(getResources(),R.drawable.snow);
            break;
            case "Thunderstorm":
                result=BitmapFactory.decodeResource(getResources(),R.drawable.thunderstorm);
            break;
            default:
                result=BitmapFactory.decodeResource(getResources(),R.drawable.clear);
                break;

        }
        return result;
    }


    void addDayInfo(String s,int i){

        JSONObject forecast = null;

        try {

            forecast = new JSONObject(s);
            JSONArray list = forecast.getJSONArray("list");
            JSONObject day = list.getJSONObject(i);
            String dayNumb = day.getString("dt");
            long dv=Long.valueOf(dayNumb)*1000;
            dayNumb=String.valueOf(dv);
            dayNumb = new Date(Long.parseLong(dayNumb)).toString();
            dayNumb=dayNumb.substring(0,10);
            JSONObject temp = day.getJSONObject("temp");
            String min = temp.getString("min");
            String max = temp.getString("max");
            JSONArray weather = day.getJSONArray("weather");
            JSONObject weather_status = weather.getJSONObject(0);
            String main=weather_status.getString("main");
            String description = weather_status.getString("description");
            String icon=weather_status.getString("icon");

            Bitmap bitmap=retWeatherPic(main);
            listElementsesList.add(new ListElements(bitmap,dayNumb, description, max, min));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    class GetInfo extends AsyncTask<Void, Void, String>{
        @Override
        protected String doInBackground(Void... voids) {
            String retGetQuery=getQuery();

            return retGetQuery;
        }

        @Override
        protected void onPostExecute(String s) {


            if(listElementsesList.isEmpty()) {
                for (int i = 0; i < (Integer.parseInt(cnt)); i++) {
                    addDayInfo(s, i);
                }
            }
            else{
                listElementsesList.clear();
                for (int i = 0; i < (Integer.parseInt(cnt)); i++) {
                    addDayInfo(s, i);
                }
            }

            addCityInfo(s);

            setTheAdapter();

        }
    }






}




