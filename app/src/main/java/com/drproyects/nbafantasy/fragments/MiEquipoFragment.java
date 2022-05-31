package com.drproyects.nbafantasy.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import com.drproyects.nbafantasy.AdapterListaMiEquipo;
import com.drproyects.nbafantasy.LoginActivity;
import com.drproyects.nbafantasy.ObjectJugador;
import com.drproyects.nbafantasy.ObjectUsuario;
import com.drproyects.nbafantasy.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MiEquipoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MiEquipoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "Log";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView miEquipoList;
    ArrayList<ObjectJugador> listaJugadores = new ArrayList<>();

    RequestQueue requestQueue;

    public MiEquipoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MiEquipoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MiEquipoFragment newInstance(String param1, String param2) {
        MiEquipoFragment fragment = new MiEquipoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        requestQueue = Volley.newRequestQueue(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_mi_equipo, container, false);

        miEquipoList = root.findViewById(R.id.listaEquipo);
        miEquipoList.setNestedScrollingEnabled(true);

        Bundle bundle = getArguments();

        ObjectUsuario usuario = (ObjectUsuario) getActivity().getIntent().getSerializableExtra("usuario");
        getJugadoresNoAlineados(usuario);

        miEquipoList.setOnItemClickListener((adapterView, view, position, id) -> {
            if(bundle.getBoolean("vender")){
                cuadroDialogoVender(listaJugadores, position, bundle, usuario);
            }
            else{
                cuadroDialogoPersonalizado(listaJugadores, position, bundle);
            }
        });

        return root;
    }


    private void getJugadoresNoAlineados(ObjectUsuario usuario) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "http://10.0.0.3/bddfantasy/alineaciones/getJugadoresNoAlineadosById.php?idUsuario=" + usuario.getIdUsuario(),
                null,
                response -> {
                    try {
                        for(int x = 0; x < response.length(); x++){
                            JSONObject jugador = response.getJSONObject(x);
                            listaJugadores.add(new ObjectJugador(jugador));
                        }
                        ArrayAdapter<ObjectJugador> arrayAdapter = new AdapterListaMiEquipo(getContext(), listaJugadores);
                        miEquipoList.setAdapter(arrayAdapter);

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

    private void cuadroDialogoPersonalizado(ArrayList<ObjectJugador> listaJugadores, int position, Bundle bundle){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_mi_equipo, null);

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        DecimalFormat formatea = new DecimalFormat("###,###.##");

        Button btnSeleccionar = view.findViewById(R.id.btnVender);
        Button btnCancelar = view.findViewById(R.id.btnCancel);

        TextView tvNombre = view.findViewById(R.id.nombreJugadorLista);
        TextView tvPosicion = view.findViewById(R.id.posicionJugador);
        TextView tvPuntos = view.findViewById(R.id.puntosJugador);
        TextView tvEquipo = view.findViewById(R.id.equipoJugador);
        TextView tvPrecio = view.findViewById(R.id.precioJugador);

        tvNombre.setText(listaJugadores.get(position).getNombreJugador());
        tvPosicion.setText(listaJugadores.get(position).getPosicionJugador());
        tvPuntos.setText(String.valueOf(listaJugadores.get(position).getPuntosJugador()));
        tvEquipo.setText(listaJugadores.get(position).getEquipoJugador());
        tvPrecio.setText(formatea.format(listaJugadores.get(position).getPrecioJugador()));

        btnSeleccionar.setOnClickListener(v -> {
            setJugadorAlineacion(listaJugadores.get(position), bundle);
            AlineacionFragment alineacionFragment = new AlineacionFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, alineacionFragment.getClass(), null);
            fragmentTransaction.commit();
            dialog.dismiss();
        });
        btnCancelar.setOnClickListener(v -> {
            Toast.makeText(getContext(), "btn CANCELAR", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
    }

    private void cuadroDialogoVender(ArrayList<ObjectJugador> listaJugadores, int position, Bundle bundle, ObjectUsuario usuario) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_vender, null);

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        DecimalFormat formatea = new DecimalFormat("###,###.##");

        Button btnVender = view.findViewById(R.id.btnVender);
        Button btnCancelar = view.findViewById(R.id.btnCancel);

        TextView tvNombre = view.findViewById(R.id.nombreJugadorLista);
        TextView tvPosicion = view.findViewById(R.id.posicionJugador);
        TextView tvPuntos = view.findViewById(R.id.puntosJugador);
        TextView tvEquipo = view.findViewById(R.id.equipoJugador);
        TextView tvPrecio = view.findViewById(R.id.precioJugador);

        tvNombre.setText(listaJugadores.get(position).getNombreJugador());
        tvPosicion.setText(listaJugadores.get(position).getPosicionJugador());
        tvPuntos.setText(String.valueOf(listaJugadores.get(position).getPuntosJugador()));
        tvEquipo.setText(listaJugadores.get(position).getEquipoJugador());
        tvPrecio.setText(formatea.format(listaJugadores.get(position).getPrecioJugador()));

        btnVender.setOnClickListener(v -> {
            venderJugador(listaJugadores.get(position), usuario);
            AlineacionFragment alineacionFragment = new AlineacionFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, alineacionFragment.getClass(), null);
            fragmentTransaction.commit();
            dialog.dismiss();
        });
        btnCancelar.setOnClickListener(v -> {
            dialog.dismiss();
        });
    }


    private void setJugadorAlineacion(ObjectJugador jugador, Bundle bundle) {

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "http://10.0.0.3/bddfantasy/alineaciones/setJugadorAlineacion.php",
                response -> Toast.makeText(getContext(), "Establecido el jugador" + jugador.getNombreJugador(), Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(getContext(), "Fallo al establecer el jugador" + jugador.getNombreJugador(), Toast.LENGTH_SHORT).show()
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idJugador1", bundle.getString("id"));
                params.put("posicionAlineacionJugador1", bundle.getString("pos"));
                params.put("idJugador2", String.valueOf(jugador.getIdJugador()));
                params.put("posicionAlineacionJugador2", String.valueOf(jugador.getPosicionAlineacionJugador()));
                if(params.get("idJugador1")==null){
                    params.put("idJugador1","id");
                }
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

    private void venderJugador(ObjectJugador jugador, ObjectUsuario usuario) {

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "http://10.0.0.3/bddfantasy/mercado/venderJugador.php",
                response -> {
                    Toast.makeText(getContext(), "Vendido el jugador" + jugador.getNombreJugador(), Toast.LENGTH_SHORT).show();

                },
                error -> Toast.makeText(getContext(), "Fallo al vender el jugador" + jugador.getNombreJugador(), Toast.LENGTH_SHORT).show()
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idJugador", String.valueOf(jugador.getIdJugador()));
                params.put("idUsuario", String.valueOf(usuario.getIdUsuario()));
                params.put("dineroUsuario", String.valueOf(jugador.getPrecioJugador() + usuario.getDineroUsuario()));

                return params;
            }
        };

        requestQueue.add(stringRequest);

    }


    private void cuadroDialogoSimple(ArrayList<String> listaJugadores, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Cuadro de " + listaJugadores.get(position));
        builder.setMessage("Mensaje del cuadro")
                .setPositiveButton("Si", (dialog, which) -> {
                    Toast.makeText(getContext(), "btn SI", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    Toast.makeText(getContext(), "btn CANCELAR", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                })
                .setCancelable(false)
                .show();
    }

}