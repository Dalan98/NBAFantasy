package com.drproyects.nbafantasy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AdapterListaMiEquipo extends ArrayAdapter<ObjectJugador> {

    private ArrayList<ObjectJugador> arrayList;


    public AdapterListaMiEquipo(@NonNull Context context, ArrayList<ObjectJugador> arrayList) {
        super(context, R.layout.list_item_mi_equipo, arrayList);
        this.arrayList = arrayList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item_mi_equipo, parent, false);
        }

        TextView nombreJugador = convertView.findViewById(R.id.nombreJugadorLista);
        TextView puntosJugador = convertView.findViewById(R.id.puntosJugador);
        TextView equipoJugador = convertView.findViewById(R.id.equipoJugador);

        nombreJugador.setText(arrayList.get(position).getNombreJugador());
        puntosJugador.setText(String.valueOf(arrayList.get(position).getPuntosJugador()));
        equipoJugador.setText(arrayList.get(position).getEquipoJugador());


        return convertView;
    }
}
