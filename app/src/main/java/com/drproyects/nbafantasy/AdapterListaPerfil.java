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

public class AdapterListaPerfil extends ArrayAdapter<ObjectUsuario> {

    private ArrayList<ObjectUsuario> arrayList;


    public AdapterListaPerfil(@NonNull Context context, ArrayList<ObjectUsuario> arrayList) {
        super(context, R.layout.list_item_perfil, arrayList);
        this.arrayList = arrayList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item_perfil, parent, false);
        }

        TextView nombreUsuario = convertView.findViewById(R.id.nombreUsuario);
        TextView puntosUsuarioPerfil = convertView.findViewById(R.id.puntosUsuarioPerfil);

        nombreUsuario.setText(arrayList.get(position).getNombreUsuario());
        puntosUsuarioPerfil.setText(String.valueOf(arrayList.get(position).getPuntosUsuario()));

        return convertView;
    }
}
