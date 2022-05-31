package com.drproyects.nbafantasy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Log";

    EditText etNombre, etContrasena;
    Button btnIniciarSesion, btnRegistrarse;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        requestQueue = Volley.newRequestQueue(this);

        etNombre = findViewById(R.id.usuario);
        etContrasena = findViewById(R.id.contrasena);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);

        btnIniciarSesion.setOnClickListener(v->{

            String campoNombre = etNombre.getText().toString().toLowerCase();
            String campoContrasena = etContrasena.getText().toString();

            if(campoNombre.isEmpty() || campoContrasena.isEmpty()){
                Toast.makeText(this, "Introduce Usuario y Contraseña", Toast.LENGTH_SHORT).show();
            }
            else {
                iniciarSesion(campoNombre, campoContrasena);

            }
        });

        btnRegistrarse.setOnClickListener(v -> {
            Intent intent = new Intent(this, CrearUsuarioActivity.class);
            startActivity(intent);
        });

    }

    public void iniciarSesion(String campoNombre, String campoContrasena) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                "http://10.0.0.3/bddfantasy/usuarios/getUsuario.php?nombreUsuario=" + campoNombre + "&contrasenaUsuario=" + campoContrasena,
                null,
                response -> {
                    try {
                        ObjectUsuario usuario = new ObjectUsuario(response);
                        if (campoNombre.equals(usuario.getNombreUsuario()) && campoContrasena.equals(usuario.getContraseñaUsuario())) {
                            Intent intent = new Intent(this, MainActivity.class);
                            intent.putExtra("usuario", usuario);
                                Toast.makeText(this, "Iniciando...", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}