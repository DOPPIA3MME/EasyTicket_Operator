package com.example.maurizio.appminieri2;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.squareup.picasso.Picasso;

public class InfoEvento extends AppCompatActivity {

    public static MyListObject item;
    Snackbar snackbar;
    TextView tv;
    private CallbackManager callbackManager;
    android.support.v4.app.FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_evento);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        TextView data = (TextView) toolbar.findViewById(R.id.toolbar_data);
        TextView tipologia = (TextView) toolbar.findViewById(R.id.toolbar_tipologia);
        TextView localita = (TextView) toolbar.findViewById(R.id.toolbar_localita);
        TextView prezzo = (TextView) toolbar.findViewById(R.id.toolbar_prezzo);

        TextView indirizzo = (TextView) toolbar2.findViewById(R.id.toolbar2_indirizzo);

        TextView descrizione = (TextView) findViewById(R.id.tDescrizione);

        ImageView imageview= (ImageView) findViewById(R.id.image_event2);
        Picasso.get().load(item.getImage()).placeholder(R.drawable.logo).into(imageview);


        title.setText(item.getName());
        data.setText(item.getData());
        tipologia.setText(item.getTipologia()+" - "+item.getSotto_tipologia());
        localita.setText(item.getLocalita().toUpperCase());
        prezzo.setText(item.getCosto()+"€");

        indirizzo.setText(item.getIndirizzo());

        descrizione.setText(item.getDescrizione());


        toolbar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri gmmIntentUri = Uri.parse("google.navigation:q="+item.getIndirizzo());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null)
                startActivity(mapIntent);
            }
        });



    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}
