package com.drproyects.nbafantasy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterListaMercado extends ArrayAdapter<ObjectJugador> {

    private final String puja;
    private final int posicion;
    private final List<JSONObject> listaPujas;
    private final ObjectUsuario usuario;
    private List<ObjectJugador> listaJugadores;

    public AdapterListaMercado(@NonNull Context context, String pujaa, List<ObjectJugador> jugadores, int posicionJugador, List<JSONObject> pujas, ObjectUsuario usuarioo) {
        super(context, R.layout.list_item_mercado, jugadores);
        this.puja = pujaa;
        this.listaJugadores = jugadores;
        this.posicion = posicionJugador;
        this.listaPujas = pujas;
        this.usuario = usuarioo;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        DecimalFormat formatea = new DecimalFormat("###,###.##");

        if(convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item_mercado, parent, false);
        }

        TextView nombreJugador = convertView.findViewById(R.id.nombreJugadorLista);
        TextView puntosJugador = convertView.findViewById(R.id.puntosJugador);
        TextView equipoJugador = convertView.findViewById(R.id.equipoJugador);
        TextView precioJugador = convertView.findViewById(R.id.precioJugador);
        TextView pujaJugador = convertView.findViewById(R.id.pujaJugador);

        nombreJugador.setText(listaJugadores.get(position).getNombreJugador());
        puntosJugador.setText(String.valueOf(listaJugadores.get(position).getPuntosJugador()));
        equipoJugador.setText(listaJugadores.get(position).getEquipoJugador());
        precioJugador.setText(formatea.format(listaJugadores.get(position).getPrecioJugador()));
        for(int i=0 ; i<listaPujas.size() ; i++){
            try {
                if(listaPujas.get(i).getString("idJugadorPuja").equals(String.valueOf(listaJugadores.get(position).getIdJugador())) &&
                        listaPujas.get(i).getString("idUsuarioPuja").equals(usuario.getIdUsuario())){
                    pujaJugador.setText(formatea.format(Integer.parseInt(listaPujas.get(i).getString("cantidadPuja"))));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(posicion==position){
            pujaJugador.setText(formatea.format(Integer.parseInt(puja)));
        }

        return convertView;
    }
}
