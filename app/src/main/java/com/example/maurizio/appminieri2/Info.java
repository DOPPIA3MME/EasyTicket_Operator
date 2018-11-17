package com.example.maurizio.appminieri2;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Info extends AppCompatActivity {

    Dialog myDialog;
    String nome_app="EasyTicket";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        myDialog = new Dialog(this);

        ImageView image = (ImageView) findViewById(R.id.image_info);
        Picasso.get().load(R.drawable.logo).fit().into(image);


        TextView privacy = (TextView) findViewById(R.id.privacy2);
        if(privacy!=null) {
            privacy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowPopup_privacy();
                    //Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_LONG).show();
                }
            });
        }

        TextView privacy1 = (TextView) findViewById(R.id.privacy);
        if(privacy1!=null) {
            privacy1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowPopup_privacy();
                    //Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_LONG).show();
                }
            });
        }

       ImageView imageprivacy=(ImageView) findViewById(R.id.image_privacy);
        imageprivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup_privacy();
            }
        });
    }





    public void ShowPopup_privacy(){
        Log.i("messaggi","Entrato in popup");
        TextView txtOK;



        myDialog.setContentView(R.layout.activity_popup_privacy);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();


        TextView p=myDialog.findViewById(R.id.privacy);
        p.setText(Html.fromHtml("In conformità al GDPR dell'UE, dal 25 maggio 2018 è in vigore la nuova " +
                ""+ "<u>"+"<font color=\"red\">"+"<a href=\"https://www.freeprivacypolicy.com/privacy/view/c54a8af754d438763a9093a961085d4f\">politica sulla privacy</a>"+"</font>"+"</u>"+" di "+nome_app+". Ti invito a leggerla per comprendere in che modo "+nome_app+" gestisce le tue informazioni " +
                "personali."+"<br />"+"<br />"+" Accettandola, confermi di avere almeno 16 anni e di comprendere il contenuto. Rifiutandola, le informazioni" +
                " presenti all'interno di "+nome_app+" saranno comunque presenti ma potrebbero non risultare pertinenti alla tua esperienza."
                +"<br />"+"<br />"+"Potrai rivedere questa decisione in qualsiasi momento visitando la schermata \"Info\" all'interno dell'app."));
        p.setClickable(true);
        p.setMovementMethod(LinkMovementMethod.getInstance());

        txtOK=(TextView)myDialog.findViewById(R.id.txtOK);
        if(txtOK!=null) {
            txtOK.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });
        }

    }

}
