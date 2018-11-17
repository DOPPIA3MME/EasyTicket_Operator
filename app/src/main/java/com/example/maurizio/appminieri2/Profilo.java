package com.example.maurizio.appminieri2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.maurizio.appminieri2.MainActivity.GET_FROM_GALLERY;

public class Profilo extends Fragment {

    View view;
    Dialog myDialog;
    public static  TextView textUsername;
    public static TextView textNome;
    public static TextView textCognome;
    public static ImageView imageViewProfilo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Messaggi", "Sto in Home");
        view= inflater.inflate(R.layout.activity_profilo, container, false);
        myDialog = new Dialog(getContext());

        textUsername=view.findViewById(R.id.usernameprofilo);
        textNome=view.findViewById(R.id.nomeprofilo);
        textCognome=view.findViewById(R.id.cognomeprofilo);
        imageViewProfilo = (ImageView)view.findViewById(R.id.imageViewProfilo);

        try {
            textUsername.setText(MainActivity.userUsername);
        }catch(NullPointerException e){  Log.i("Messaggi", "TEXTUSERNAME VUOTO");}
        try {
            textNome.setText(MainActivity.userNome);
        }catch(NullPointerException e){  Log.i("Messaggi", "TEXTNome VUOTO");}
        try {
            textCognome.setText(MainActivity.userCognome);
        }catch(NullPointerException e){  Log.i("Messaggi", "TEXTCognome VUOTO");}

        try {
            Home.textbenvenuto.setText("BUON LAVORO "+MainActivity.userNome.toUpperCase());
        }catch(NullPointerException e){  Log.i("Messaggi", "TEXTBenvenuto VUOTO");}
        Picasso.get().load(MainActivity.userImage).noPlaceholder().centerCrop().fit().into(imageViewProfilo);

        Button buttonLogout=view.findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopupLogout();
            }
        });

        return view;
    }


    public void ShowPopupLogout(){
        Button btnYes;

        myDialog.setContentView(R.layout.activity_popup_exit);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();




        btnYes=(Button) myDialog.findViewById(R.id.buttonYes);
        btnYes.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                String logoutRequest="http://ingsoftw.eu-central-1.elasticbeanstalk.com/logout";
                Request request = new Request.Builder().url(logoutRequest).build();
                Accesso.client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        Log.i("messaggi", "FAILURE");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            Log.i("messaggi", "response ACCETTATO : " + response);
                        } else {
                            Log.i("messaggi", "response FALLITO : " + response);
                        }
                    }
                });

                Intent i=new Intent(getContext(),Accesso.class);
                startActivity(i);
            }
        });
    }


}


