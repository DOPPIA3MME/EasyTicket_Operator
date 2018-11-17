package com.example.maurizio.appminieri2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter_List extends ArrayAdapter<MyListObject> {

    private final Context context;
    private final List<MyListObject> values;
    private int resourceID;

    public MyAdapter_List(Context context, int resourceID, List<MyListObject> values) {
        super(context, resourceID, values);

        this.context = context;
        this.resourceID = resourceID;
        this.values = values;

    }

    private class ViewHolder {
        TextView nome;
        TextView costo;
        TextView data;
        TextView localita;
        TextView tipologia;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceID, null);
            holder = new ViewHolder();
            holder.nome = (TextView) convertView.findViewById(R.id.nome);
            holder.costo = (TextView) convertView.findViewById(R.id.costo);
            holder.data = (TextView) convertView.findViewById(R.id.data);
            holder.localita = (TextView) convertView.findViewById(R.id.localita);
            holder.tipologia = (TextView) convertView.findViewById(R.id.tipologia);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        MyListObject rowItem = (MyListObject) values.get(position);
        holder.nome.setText(rowItem.getName());
        holder.data.setText(rowItem.getData());
        holder.tipologia.setText(rowItem.getTipologia());
        holder.costo.setText(rowItem.getCosto()+" €");
        holder.localita.setText(rowItem.getLocalita().toUpperCase());

        return convertView;
    }
}