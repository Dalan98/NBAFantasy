package com.drproyects.nbafantasy.fragments;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.drproyects.nbafantasy.AdapterListaMercado;
import com.drproyects.nbafantasy.AlarmReceiver;
import com.drproyects.nbafantasy.ObjectJugador;
import com.drproyects.nbafantasy.ObjectUsuario;
import com.drproyects.nbafantasy.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MercadoFragment extends Fragment {

    private static final String TAG = "Log:";

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    ListView mercadoList;
    List<ObjectJugador> listaJugadores = new ArrayList<>();
    List<JSONObject> listaPujas = new ArrayList<>();

    RequestQueue requestQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestQueue = Volley.newRequestQueue(getContext());

        ObjectUsuario usuario = (ObjectUsuario) getActivity().getIntent().getSerializableExtra("usuario");
        jugadoresMercado(usuario);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_mercado, container, false);

        mercadoList = root.findViewById(R.id.listaMercado);
        ObjectUsuario usuario = (ObjectUsuario) getActivity().getIntent().getSerializableExtra("usuario");

        ArrayAdapter<ObjectJugador> arrayAdapter = new AdapterListaMercado(getContext(), "0", listaJugadores, -1, listaPujas, usuario);
        mercadoList.setAdapter(arrayAdapter);

        mercadoList.setOnItemClickListener((adapterView, view, position, id) -> {
            cuadroDialogoPersonalizado(listaJugadores, position, usuario);
        });


        alarmMgr = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);

        // Alarma a las 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 30);

        // Repeticiones en intervalos de 20 minutos
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 1440, alarmIntent);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "http://10.0.0.3/bddfantasy/mercado/getPujasMercado.php?",
                null,
                response -> {
                    try {
                        listaPujas.clear();
                        for(int i=0 ; i<response.length() ; i++){
                            JSONObject puja = response.getJSONObject(i);
                            listaPujas.add(puja);
                        }
                        ArrayAdapter<ObjectJugador> arrayAdapterr = new AdapterListaMercado(getContext(), "0", listaJugadores, -1, listaPujas, usuario);
                        mercadoList.setAdapter(arrayAdapterr);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                    //Toast.makeText(getContext(), "Error al obtener las pujas", Toast.LENGTH_SHORT).show();
                }
        );
        requestQueue.add(jsonArrayRequest);

        return root;
    }

    private void jugadoresMercado(ObjectUsuario usuario) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "http://10.0.0.3/bddfantasy/mercado/getJugadoresMercado.php?",
                null,
                response -> {
                    try {
                        listaJugadores.clear();
                        for(int i=0 ; i<response.length() ; i++){
                            JSONObject jugador = response.getJSONObject(i);
                            listaJugadores.add(new ObjectJugador(jugador));
                        }
                        ArrayAdapter<ObjectJugador> arrayAdapter = new AdapterListaMercado(getContext(), "0", listaJugadores, -1, listaPujas, usuario);
                        mercadoList.setAdapter(arrayAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(getContext(), "Error al obtener la alineación", Toast.LENGTH_SHORT).show();
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void cuadroDialogoPersonalizado(List<ObjectJugador> listaJugadores, int position, ObjectUsuario usuario){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_mercado, null);

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        DecimalFormat formatea = new DecimalFormat("###,###.##");

        Button btnPujar = view.findViewById(R.id.btnVender);
        Button btnCancelar = view.findViewById(R.id.btnCancel);

        TextView nombre = view.findViewById(R.id.nombreJugadorLista);
        TextView puntosJugador = view.findViewById(R.id.puntosJugador);
        TextView equipoJugador = view.findViewById(R.id.equipoJugador);
        TextView precioJugador = view.findViewById(R.id.precioJugador);
        TextView posicionJugador = view.findViewById(R.id.posicionJugador);
        TextView pujaJugador = view.findViewById(R.id.editTextNumber);

        nombre.setText(listaJugadores.get(position).getNombreJugador());
        puntosJugador.setText(String.valueOf(listaJugadores.get(position).getPuntosJugador()));
        precioJugador.setText(formatea.format(listaJugadores.get(position).getPrecioJugador()));
        equipoJugador.setText(listaJugadores.get(position).getEquipoJugador());
        posicionJugador.setText(listaJugadores.get(position).getPosicionJugador());


        btnPujar.setOnClickListener(v -> {
            if(!pujaJugador.getText().toString().isEmpty() &&
                    (Integer.parseInt(pujaJugador.getText().toString().trim())>listaJugadores.get(position).getPrecioJugador() || Integer.parseInt(pujaJugador.getText().toString().trim())==0)){
                setPuja(usuario, listaJugadores.get(position).getIdJugador(), pujaJugador.getText().toString(), position);
                dialog.dismiss();
            }
            else
                Toast.makeText(getContext(), "La puja debe ser mayor que el precio del jugador", Toast.LENGTH_SHORT).show();
        });
        btnCancelar.setOnClickListener(v -> dialog.dismiss());
    }

    private void setPuja(ObjectUsuario usuario, int idJugador, String puja, int position) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "http://10.0.0.3/bddfantasy/mercado/setPuja.php",
                response -> {
                    Toast.makeText(getContext(), "OK", Toast.LENGTH_SHORT).show();
                    ArrayAdapter<ObjectJugador> arrayAdapter = new AdapterListaMercado(getContext(), puja, listaJugadores, position, listaPujas, usuario);
                    mercadoList.setAdapter(arrayAdapter);
                },
                error -> Toast.makeText(getContext(), "KO", Toast.LENGTH_SHORT).show()
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idUsuarioPuja", String.valueOf(usuario.getIdUsuario()));
                params.put("idJugadorPuja", String.valueOf(idJugador));
                params.put("cantidadPuja", puja);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}