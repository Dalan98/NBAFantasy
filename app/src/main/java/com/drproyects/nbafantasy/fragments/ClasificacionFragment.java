package com.drproyects.nbafantasy.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.drproyects.nbafantasy.AdapterListaClasificacion;
import com.drproyects.nbafantasy.ObjectUsuario;
import com.drproyects.nbafantasy.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClasificacionFragment extends Fragment {

    ListView clasificacionList;
    ArrayList<ObjectUsuario> listaUsuarios = new ArrayList<>();

    RequestQueue requestQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestQueue = Volley.newRequestQueue(getContext());

        cargarMercado();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_clasificacion, container, false);

        clasificacionList = root.findViewById(R.id.listaClasificacion);
        clasificacionList.setNestedScrollingEnabled(true);
        ObjectUsuario usuario = (ObjectUsuario) getActivity().getIntent().getSerializableExtra("usuario");

        ArrayAdapter<ObjectUsuario> arrayAdapter = new AdapterListaClasificacion(getContext(), listaUsuarios);
        clasificacionList.setAdapter(arrayAdapter);

        return root;
    }

    private void cargarMercado() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "http://10.0.0.3/bddfantasy/clasificacion/getClasificacionJornada.php?",
                null,
                response -> {
                    try {
                        for(int x = 0; x < response.length(); x++){
                            JSONObject usuario = response.getJSONObject(x);
                            listaUsuarios.add(new ObjectUsuario(usuario));
                        }
                        ArrayAdapter<ObjectUsuario> arrayAdapter = new AdapterListaClasificacion(getContext(), listaUsuarios);
                        clasificacionList.setAdapter(arrayAdapter);

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

    
}