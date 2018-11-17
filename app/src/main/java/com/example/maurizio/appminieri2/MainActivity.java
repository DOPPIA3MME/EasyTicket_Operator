package com.example.maurizio.appminieri2;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.google.android.gms.internal.zzhu.runOnUiThread;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private String TAG = MainActivity.class.getSimpleName();
    private int eventiTotali=0;
    private static String url = "http://ingsoftw.eu-central-1.elasticbeanstalk.com/get/eventi";
    // URL to get user info JSON
    private static String userRequest="http://ingsoftw.eu-central-1.elasticbeanstalk.com/api/get/operatoreinfo";
    private ProgressDialog pDialog;
    public static ArrayList<MyListObject> eventList;
    GetEvents g;



    Dialog myDialog;
    Menu menu;
    Menu menu_items;
    MenuItem nav_categorie;
    MenuItem nav_cercaevento;
    MenuItem nav_share;
    MenuItem nav_info;
    MenuItem nav_impostazioni;
    NavigationView navigationView;
    View headerView;
    TextView nav_title;
    TextView nav_mail;
    public static int pagina;
    public static final int GET_FROM_GALLERY = 3;
    public static String userNome="OPERATORE";
    public static String userCognome;
    public static String userUsername;
    public static Uri userImage;




    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabAdapter tabAdapter;
    Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventList = new ArrayList();



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new Home(), "HOME");
        tabAdapter.addFragment(new Profilo(), "PROFILO");


        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setTabTextColors(ColorStateList.valueOf(Color.WHITE));
        viewPager.setOffscreenPageLimit(2);  //carica tutte le 6 pages presenti nel tab

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pagina = tabLayout.getSelectedTabPosition();
                switch (pagina) {
                    case 0:
                        menu.findItem(R.id.impostazioni_profilo).setVisible(false);
                        break;
                    case 1:
                        Picasso.get().load(userImage).noPlaceholder().centerCrop().fit().into(Profilo.imageViewProfilo);
                        menu.findItem(R.id.impostazioni_profilo).setVisible(true);
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        g = new GetEvents();
        g.execute();
        myDialog = new Dialog(this);

        //Definisco il tasto sulla toolbar per aprire la navigation view
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        initNavigationView();


        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 0);


        //RICHIESTA GET info user
        final Request request=new Request.Builder().url(userRequest).build();
        Accesso.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.i("messaggi","FAILURE");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                    Log.i("messaggi", "response user ACCETTATO : " + response);
                    String jsonData = response.body().string();
                    getUser(jsonData);
                }
                else {
                    Log.i("messaggi", "response user FALLITO : " + response);
                }
            }
        });




    }




    public void initNavigationView(){
        //prendo la navigation view(il menu a tendina)
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //prendo il menu degli items nella navigation view (categorie,aggiungi evento...login)
        menu_items = navigationView.getMenu();
        //e tutti gli items

        nav_share=menu_items.findItem(R.id.nav_share);

        nav_info=menu_items.findItem(R.id.info);
        nav_impostazioni=menu_items.findItem(R.id.impostazioni);


        //prendo l'header della navigation view
        headerView = navigationView.getHeaderView(0);
        nav_title = (TextView) headerView.findViewById(R.id.nav_header_title);
        nav_mail = (TextView) headerView.findViewById(R.id.nav_header_mail);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void getUser(String jsonData){
        Log.i("Messaggi", "jsonData : "+jsonData);

        if (jsonData != null) {
            JSONObject j = null;
            try {
                j = new JSONObject(jsonData);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                userCognome=(j.getString("cognome"));
                userNome=(j.getString("nome"));
                userUsername=(j.getString("username"));
                nav_title.setText(userUsername);
                nav_mail.setText(userNome);
                //userImage=(j.getString("immagine"));    //una uri
                try {
                    Profilo.textUsername.setText(userUsername);
                }catch(NullPointerException e){  Log.i("Messaggi", "TEXTUSERNAME VUOTO");}
                try {
                Profilo.textNome.setText(userNome);
            }catch(NullPointerException e){  Log.i("Messaggi", "TEXTNome VUOTO");}
                try {
                Profilo.textCognome.setText(userCognome);
        }catch(NullPointerException e){  Log.i("Messaggi", "TEXTCognome VUOTO");}

                try {
                Home.textbenvenuto.setText("BUON LAVORO "+userNome.toUpperCase());
                }catch(NullPointerException e){  Log.i("Messaggi", "TEXTBenvenuto VUOTO");}

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            Log.e("messaggi", "Errore.");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Impossibile caricare i dati utente. Controlla la tua connessione internet",
                            Toast.LENGTH_LONG)
                            .show();
                }
            });

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        //inserisce il menu con i tasti refresh,ordina e changeview
        getMenuInflater().inflate(R.menu.toolbar_home, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        this.menu=menu;

        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==R.id.impostazioni_profilo){
            ShowPopupPhoto();
        }

        return super.onOptionsItemSelected(item);
    }


    public void ShowPopupPhoto(){

        Log.i("messaggi","STO IN POPUPPHOTO");
        myDialog.setContentView(R.layout.activity_popup_profilo);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();


        TextView changePI=myDialog.findViewById(R.id.changeprofileimage);
        changePI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
                myDialog.dismiss();
            }});
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {

            Uri selectedImage = data.getData();
            userImage=selectedImage;
            Picasso.get().load(selectedImage).noPlaceholder().centerCrop().fit().into(Profilo.imageViewProfilo);
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Toast my_Toast;
        TextView v;
        View view;

        int id = item.getItemId();

        if (id == R.id.nav_categorie) {
            my_Toast = Toast.makeText(this, "Cinema\nConcerto\nFiera\nSport\nTeatro", Toast.LENGTH_LONG);
            v = (TextView) my_Toast.getView().findViewById(android.R.id.message);
            view = my_Toast.getView();
            view.getBackground().setColorFilter(getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
            v.setTextColor(Color.WHITE);
            my_Toast.show();

        } else if (id == R.id.nav_cercaevento) {
            GoToCercaEvento();
        }
        else if(id==R.id.impostazioni){
                GoToImpostazioni();
            }
        else if(id==R.id.info){
            GoToInfo();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void GoToInfo() {
        Intent i = new Intent(MainActivity.this,Info.class);
        startActivity(i);
    }

    public void GoToImpostazioni() {
        Intent i = new Intent(MainActivity.this,Impostazioni.class);
        startActivity(i);
    }

    public void GoToCercaEvento() {
        Intent i = new Intent(MainActivity.this,CercaEvento.class);
        startActivity(i);
    }




    /**
     * Async task class to get json by making HTTP call
     */
    private class GetEvents extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Log.i("messaggi", "MAINACTIVITY : BackGround");

            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);
            //String jsonStr= "[{id:1,nome:VASCOROSSI,localita:ITALIA,data:2018-08-1,costo:0.0,tipologia:concerto},{id:2,nome:EMINEM,localita:AMERICA,data:2018-08-2,costo:0.0,tipologia:concerto},{id:3,nome:ALEXBRITTI,localita:LONDRA,data:2018-08-3,costo:0.0,tipologia:incontro},{id:4,nome:CAPAREZZA,localita:LONDRA,data:2018-08-4,costo:40.0,tipologia:fiera}]";


            if (jsonStr != null) {
                try {

                    JSONArray events = new JSONArray(jsonStr);
                    eventiTotali = events.length();
                    // looping through All Events
                    for (int i = 0; i < eventiTotali; i++) {

                        JSONObject c = events.getJSONObject(i);

                        //prendo tutte le info dell'evento
                        String id = c.getString("id");
                        String nome = c.getString("nome");
                        String localita = c.getString("localita");
                        String data = c.getString("data");
                        String costo_s = c.getString("costo");
                        float costo=Float.parseFloat(costo_s);
                        String tipologia = c.getString("tipologia");
                        String image= c.getString("img");
                        String indirizzo = c.getString("indirizzo");
                        String descrizione = c.getString("descrizione");
                        String sotto_tipologia = c.getString("sotto_tipologia");

                        MyListObject item = new MyListObject();
                        item.setName(nome);
                        item.setLocalita(localita);
                        item.setData(data);
                        item.setCosto(costo);
                        item.setId(id);
                        item.setTipologia(tipologia);
                        item.setImage(image);
                        item.setIndirizzo(indirizzo);
                        item.setDescrizione(descrizione);
                        item.setSotto_tipologia(sotto_tipologia);
                        item.setPutShop(false);

                        eventList.add(item);
                    }

                    Log.i("messaggi", "Lista creata dopo aver scorso tutti gli eventi");

                    // Log.i("messaggi", "Lista : "+eventList);
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Errore.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Impossibile caricare gli eventi. Controlla la tua connessione internet",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();


        }
    }





}
