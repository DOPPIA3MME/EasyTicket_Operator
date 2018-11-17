package com.example.maurizio.appminieri2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;


public class FragmentListSearch extends Fragment {

    private ListView lv;
    MyListObject myObject;
    List<MyListObject> eventListSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view= inflater.inflate(R.layout.fragment_list, container, false);

        if(CercaEvento.pagina==0)
           eventListSearch = CercaEvento.eventListSearchNome;
        else if(CercaEvento.pagina==1)
           eventListSearch = CercaEvento.eventListSearchTipologia;


        lv = (ListView) view.findViewById(R.id.list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> adapterView,View view,int position,long l) {
                myObject= (MyListObject) lv.getItemAtPosition(position);
                InfoEvento.item=myObject;
                GoToInfoEvento();
            }
        });

        MyAdapter_List adapter=new MyAdapter_List(getActivity().getApplicationContext(), R.layout.list_events_linear,eventListSearch);
        lv.setAdapter(adapter);

        return view;
    }

    public void GoToInfoEvento() {
        Log.i("Messaggi", "Entrato in InfoEvento da linear");
        Intent i = new Intent(getActivity().getApplicationContext(), InfoEvento.class);
        startActivity(i);
    }
}
