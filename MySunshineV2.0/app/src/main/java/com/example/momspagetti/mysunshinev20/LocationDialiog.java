package com.example.momspagetti.mysunshinev20;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import static com.example.momspagetti.mysunshinev20.R.id.et_city_location_fragment;

/**
 * Created by Саня on 16.12.2016.
 */

public class LocationDialiog extends DialogFragment  {



    public interface OnLocationDialogSelectedListener{
        public void onLocationDialogSelected(DialogFragment dialog);
    }

    OnLocationDialogSelectedListener mCallback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);


        mCallback=(OnLocationDialogSelectedListener) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setView(R.layout.location_activity)
                .setTitle("Location")
                .setPositiveButton("Ok",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mCallback.onLocationDialogSelected(LocationDialiog.this);
                        dismiss();
                    }
                })
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int whichButton){

                        dismiss();
                    }
                })
                .create();
    }
}
