package com.example.maurizio.appminieri2;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Impostazioni extends AppCompatActivity {

    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impostazioni);
        myDialog = new Dialog(this);
    }

    public void ShowPopup(View v){
        TextView txtclose;
        Button btnYes;
        Button btnNo;

        myDialog.setContentView(R.layout.activity_popup);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();



        txtclose=(TextView) myDialog.findViewById(R.id.txtclose);
        btnYes=(Button) myDialog.findViewById(R.id.buttonYes);
        btnNo=(Button) myDialog.findViewById(R.id.buttonNo);


        txtclose.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                myDialog.dismiss();
            }
        });


        btnYes.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.i("Messaggi","Entrato in Principale");
                Intent i1=new Intent(Impostazioni.this,MainActivity.class);
                startActivity(i1);
            }
        });


        btnNo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                myDialog.dismiss();
            }
        });




    }




}
