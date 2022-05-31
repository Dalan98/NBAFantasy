package com.drproyects.nbafantasy.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.drproyects.nbafantasy.AdapterListaPerfil;
import com.drproyects.nbafantasy.LoginActivity;
import com.drproyects.nbafantasy.MainActivity;
import com.drproyects.nbafantasy.ObjectUsuario;
import com.drproyects.nbafantasy.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PerfilFragment extends Fragment {

    ListView perfilList;
    TextView tvNombre, tvCorreo, tvPuntos, tvDinero;
    ArrayList<ObjectUsuario> listaUsuarios = new ArrayList<>();

    RequestQueue requestQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestQueue = Volley.newRequestQueue(getContext());

        cargarPerfil();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_perfil, container, false);

        perfilList = root.findViewById(R.id.listaPerfil);
        perfilList.setNestedScrollingEnabled(true);
        ObjectUsuario usuario = (ObjectUsuario) getActivity().getIntent().getSerializableExtra("usuario");


        Button btnCerrarSesion = root.findViewById(R.id.cerrarSesion);

        TextView tvNombre = root.findViewById(R.id.nombreUsuario);
        TextView tvCorreo = root.findViewById(R.id.correoUsuario);
        TextView tvPuntos = root.findViewById(R.id.puntosUsuario);
        TextView tvDinero = root.findViewById(R.id.saldoUsuario);

        tvNombre.setText(usuario.getNombreUsuario());
        tvCorreo.setText(usuario.getCorreoUsuario());
        tvPuntos.setText(String.valueOf(usuario.getPuntosUsuario()));
        tvDinero.setText(String.valueOf(usuario.getDineroUsuario()));

        ArrayAdapter<ObjectUsuario> arrayAdapter = new AdapterListaPerfil(getContext(), listaUsuarios);
        perfilList.setAdapter(arrayAdapter);

        actualizarDinero(usuario, tvDinero);

        btnCerrarSesion.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.putExtra("usuario", usuario);
            Toast.makeText(getContext(), "Cerrando Sesión...", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });

        return root;
    }

    private void actualizarDinero(ObjectUsuario usuario, TextView tvDinero) {
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                "http://10.0.0.3/bddfantasy/usuarios/getUsuarioById.php?idUsuario=" + usuario.getIdUsuario(),
                null,
                response -> {
                    try {
                        tvDinero.setText(response.getString("dineroUsuario"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(getContext(), "Error al obtener el perfil", Toast.LENGTH_SHORT).show();
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void cargarPerfil() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "http://10.0.0.3/bddfantasy/clasificacion/getClasificacionTotal.php?",
                null,
                response -> {
                    try {
                        for(int x = 0; x < response.length(); x++){
                            JSONObject usuario = response.getJSONObject(x);
                            listaUsuarios.add(new ObjectUsuario(usuario));
                        }
                        ArrayAdapter<ObjectUsuario> arrayAdapter = new AdapterListaPerfil(getContext(), listaUsuarios);
                        perfilList.setAdapter(arrayAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    //Toast.makeText(getContext(), "Error al obtener la alineación", Toast.LENGTH_SHORT).show();
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
}