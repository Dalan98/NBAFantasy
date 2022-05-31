package com.drproyects.nbafantasy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlarmReceiver extends BroadcastReceiver {

    RequestQueue requestQueue;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"AlarmReceiver called",Toast.LENGTH_SHORT).show();
        requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "http://10.0.0.3/bddfantasy/mercado/getPujasMercado.php?",
                null,
                response -> {
                    try {
                        List<JSONObject> pujas = null;
                        pujas.clear();
                        for(int i=0 ; i<response.length() ; i++){
                            JSONObject jugador = response.getJSONObject(i);
                            pujas.add(jugador);
                        }
                        for(int j=0 ; j<response.length() ; j++){
                            if(Integer.parseInt(pujas.get(j).getString("cantidadPuja"))>Integer.parseInt(pujas.get(j+1).getString("cantidadPuja"))){
                                setJugadorAlineacion(pujas.get(j).getString("usuarioPuja"), pujas.get(j).getString("jugadorPuja"), context);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void setJugadorAlineacion(String idUsuario, String idJugador, Context context) {

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "http://10.0.0.3/bddfantasy/alineaciones/setJugadorAlineacion.php",
                response -> Toast.makeText(context, "Establecido el jugador", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(context, "Fallo al establecer el jugador", Toast.LENGTH_SHORT).show()
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idJugador", idJugador);
                params.put("idUsuario", idUsuario);
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

}
