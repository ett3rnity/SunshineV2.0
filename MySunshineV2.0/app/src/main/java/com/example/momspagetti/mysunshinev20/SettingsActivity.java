package com.example.momspagetti.mysunshinev20;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Саня on 19.12.2016.
 */

public class SettingsActivity extends AppCompatActivity implements LocationDialiog.OnLocationDialogSelectedListener {

    Button btn_location_chose;
    Button btn_units_chose;

    TextView tv_city_chose;

    static private String ret_city_name="";

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        btn_location_chose=(Button)findViewById(R.id.btn_location_chose);
        btn_units_chose=(Button)findViewById(R.id.btn_units_chose);
        tv_city_chose=(TextView) findViewById(R.id.tv_location);

        btn_location_chose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LocationDialiog newLocation=new LocationDialiog();

                newLocation.show(getFragmentManager(),"Location");
            }

        });
        //btn_units_chose.setOnClickListener(this);


    }

    public void onLocationDialogSelected(DialogFragment dialog){

        //проверить  не пустой едиттекст
        EditText et_cityname;
       et_cityname=(EditText)dialog.getDialog().findViewById(R.id.et_city_location_fragment);
        tv_city_chose.setText(et_cityname.getText().toString());
        ret_city_name=tv_city_chose.getText().toString();
    }

    public String retCityName(){
        return ret_city_name;
    }



}
