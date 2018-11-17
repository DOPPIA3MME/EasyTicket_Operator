package com.example.maurizio.appminieri2;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.SearchView;

import java.util.ArrayList;


public class CercaEvento extends AppCompatActivity {

    public static ArrayList<MyListObject> eventListSearchNome;
    public static ArrayList<MyListObject> eventListSearchTipologia;
    FragmentListSearch fragment;
    android.support.v4.app.FragmentTransaction transaction;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabAdapter tabAdapter;
    public static int pagina;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerca_evento);
        eventListSearchNome=new ArrayList();
        eventListSearchTipologia=new ArrayList();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewPager_search);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout_search);
        tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new FragmentNome(), "NOME");
        tabAdapter.addFragment(new FragmentLuogo(), "LUOGO");
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(ColorStateList.valueOf(Color.WHITE));
        viewPager.setOffscreenPageLimit(2);  //carica tutte le 2 pages presenti nel tab

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pagina = tabLayout.getSelectedTabPosition();
                Log.i("Messaggi", "PAGINA = " + pagina);
            }

           @Override
           public void onTabUnselected(TabLayout.Tab tab) {
           }

           @Override
           public void onTabReselected(TabLayout.Tab tab) {
           }
        });


        SearchView simpleSearchView = (SearchView) findViewById(R.id.search); // inititate a search view
        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                switch (pagina) {
                    case 0:
                        eventListSearchNome.clear();
                        for (MyListObject o : MainActivity.eventList) {
                            if (o.getName().toLowerCase().contains(query) || o.getName().toUpperCase().contains(query))
                                eventListSearchNome.add(o);
                        }
                        fragment = new FragmentListSearch();
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_search_nome, fragment).commit();
                        break;

                    case 1:
                        eventListSearchTipologia.clear();
                        for (MyListObject o : MainActivity.eventList) {
                            if (o.getLocalita().toLowerCase().contains(query) || o.getLocalita().toUpperCase().contains(query))
                                eventListSearchTipologia.add(o);
                        }
                        fragment = new FragmentListSearch();
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_search_luogo, fragment).commit();
                        break;

                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                switch (pagina) {
                    case 0:
                        eventListSearchNome.clear();
                        for (MyListObject o : MainActivity.eventList) {
                            if (o.getName().toLowerCase().contains(newText) || o.getName().toUpperCase().contains(newText))
                                eventListSearchNome.add(o);
                        }
                        fragment = new FragmentListSearch();
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_search_nome, fragment).commit();
                        break;

                    case 1:
                        eventListSearchTipologia.clear();
                        for (MyListObject o : MainActivity.eventList) {
                            if (o.getLocalita().toLowerCase().contains(newText) || o.getLocalita().toUpperCase().contains(newText))
                                eventListSearchTipologia.add(o);
                        }
                        fragment = new FragmentListSearch();
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_search_luogo, fragment).commit();
                        break;

                }

                return false;
            }
        });
    }


}
