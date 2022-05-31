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

public class AdapterListaClasificacion extends ArrayAdapter<ObjectUsuario> {

    private ArrayList<ObjectUsuario> arrayList;


    public AdapterListaClasificacion(@NonNull Context context, ArrayList<ObjectUsuario> arrayList) {
        super(context, R.layout.list_item_clasificacion, arrayList);
        this.arrayList = arrayList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item_clasificacion, parent, false);
        }

        TextView nombreJugador = convertView.findViewById(R.id.nombreJugador);
        TextView puntoJugadorClasificacion = convertView.findViewById(R.id.puntosJugadorClasificacion);

        nombreJugador.setText(arrayList.get(position).getNombreUsuario());
        puntoJugadorClasificacion.setText(String.valueOf(arrayList.get(position).getPuntosJornadaUsuario()));

        return convertView;
    }
}
