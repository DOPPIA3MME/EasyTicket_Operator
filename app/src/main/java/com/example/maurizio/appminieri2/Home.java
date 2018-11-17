package com.example.maurizio.appminieri2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.google.android.gms.internal.zzhu.runOnUiThread;


public class Home extends Fragment {
    View view;
    public static TextView textbenvenuto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Messaggi", "Sto in Home");
        view= inflater.inflate(R.layout.activity_home, container, false);

        //definisco il bottone presente nella home
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               GotoQRCOde();

            }
        });

        textbenvenuto=view.findViewById(R.id.textbenvenuto);


        return view;
    }

    public void GotoQRCOde() {
        Intent i = new Intent(getContext(), QRCode.class);
        startActivity(i);
    }


}

