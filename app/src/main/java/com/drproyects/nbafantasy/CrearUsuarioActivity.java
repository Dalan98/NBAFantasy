package com.drproyects.nbafantasy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CrearUsuarioActivity extends AppCompatActivity {

    private static final String TAG = "Log";

    EditText etNombre, etCorreo, etContrasena;
    Button btnCrearCuenta, btnCancelar;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);

        requestQueue = Volley.newRequestQueue(this);

        etNombre = findViewById(R.id.usuario);
        etCorreo = findViewById(R.id.correo);
        etContrasena = findViewById(R.id.contrasena);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);

        btnCrearCuenta.setOnClickListener(v ->{
            if(etNombre.getText().toString().isEmpty() || etCorreo.getText().toString().isEmpty() || etContrasena.getText().toString().isEmpty()){
                Toast.makeText(this, "Introduce Usuario, Correo y Contraseña", Toast.LENGTH_SHORT).show();
            }
            else {
                getUsuarios();
            }
        });

        btnCancelar.setOnClickListener(v -> {
            Intent intent = new Intent(CrearUsuarioActivity.this, LoginActivity.class);
            startActivity(intent);
        });

    }

    private ArrayList<JSONObject> getUsuarios() {
        Exception exception = new Exception();
        Boolean validar = false;
        ArrayList<JSONObject> arrayUsuarios = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "http://10.0.0.3/bddfantasy/usuarios/getAllUsuarios.php",
                null,
                response -> {
                    try {
                        for(int i = 0; i < response.length(); i++){
                            JSONObject usuarioBdd = response.getJSONObject(i);
                            String nombreUsuario = usuarioBdd.getString("nombreUsuario").toLowerCase();
                            String correoUsuario = usuarioBdd.getString("correoUsuario").toLowerCase();
                            if (etNombre.getText().toString().equalsIgnoreCase(nombreUsuario)){
                                Toast.makeText(this, "Ya existe una cuenta con el nombre de usuario " + nombreUsuario, Toast.LENGTH_SHORT).show();
                                throw exception;
                            }
                            if (etNombre.getText().toString().equalsIgnoreCase(nombreUsuario)){
                                Toast.makeText(this, "Ya existe una cuenta con la dirección de correo " + correoUsuario, Toast.LENGTH_SHORT).show();
                                throw exception;
                            }
                        }
                        crearCuenta(etNombre.getText().toString().toLowerCase(), etCorreo.getText().toString().toLowerCase(), etContrasena.getText().toString());

                    } catch (Exception e){
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(this, "Error al crear el usuario", Toast.LENGTH_SHORT).show();
                }
        );
        requestQueue.add(jsonArrayRequest);

        return arrayUsuarios;
    }

    private void crearCuenta(String nombreUsuario, String correoUsuario, String contrasenaUsuario) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "http://10.0.0.3/bddfantasy/usuarios/insertarUsuario.php",
                response -> {
                    //crearAlineacion(response);
                    Toast.makeText(this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                },
                error ->  {
                    //Toast.makeText(this, "Error al crear el usuario", Toast.LENGTH_SHORT).show();
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nombreUsuario", nombreUsuario);
                params.put("contrasenaUsuario", contrasenaUsuario);
                params.put("correoUsuario", correoUsuario);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void crearAlineacion(String idUsuario) {
        StringRequest stringRequest1 = new StringRequest(
                Request.Method.POST,
                "http://10.0.0.3/bddfantasy/alineaciones/insertarAlineacion.php",
                response -> {
                    Log.i(TAG, "Alineación creada correctamente");
                },
                error -> {
                    Log.i(TAG, "Error al crear la alineación");
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idUsuarioAlineacion", idUsuario);
                return params;
            }
        };
        requestQueue.add(stringRequest1);
    }
}