package com.drproyects.nbafantasy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ObjectUsuario implements Serializable {

    private int idUsuario;
    private String nombreUsuario;
    private String contrasenaUsuario;
    private String correoUsuario;
    private int puntosJornadaUsuario;
    private int puntosUsuario;
    private int dineroUsuario;

    public ObjectUsuario(int idUsuario, String nombreUsuario, String contrasenaUsuario, String correoUsuario, int puntosJornadaUsuario, int puntosUsuario, int dineroUsuario) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.correoUsuario = correoUsuario;
        this.contrasenaUsuario = contrasenaUsuario;
        this.puntosJornadaUsuario = puntosJornadaUsuario;
        this.puntosUsuario = puntosUsuario;
        this.dineroUsuario = dineroUsuario;
    }

    public ObjectUsuario(String nombreUsuario, int puntosJornadaUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.puntosJornadaUsuario = puntosJornadaUsuario;
    }

    public ObjectUsuario(JSONObject response) throws JSONException {
        this.idUsuario = response.getInt("idUsuario");
        this.nombreUsuario = response.getString("nombreUsuario");
        this.correoUsuario = response.getString("correoUsuario");
        this.contrasenaUsuario = response.getString("contrasenaUsuario");
        this.puntosJornadaUsuario = response.getInt("puntosJornadaUsuario");
        this.puntosUsuario = response.getInt("puntosUsuario");
        this.dineroUsuario = response.getInt("dineroUsuario");
    }


    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContraseñaUsuario() {
        return contrasenaUsuario;
    }

    public void setContraseñaUsuario(String contraseñaUsuario) {
        this.contrasenaUsuario = contraseñaUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public int getPuntosJornadaUsuario() {
        return puntosJornadaUsuario;
    }

    public void setPuntosJornadaUsuario(int puntosJornadaUsuario) {
        this.puntosJornadaUsuario = puntosJornadaUsuario;
    }

    public int getPuntosUsuario() {
        return puntosUsuario;
    }

    public void setPuntosUsuario(int puntosUsuario) {
        this.puntosUsuario = puntosUsuario;
    }

    public int getDineroUsuario() {
        return dineroUsuario;
    }

    public void setDineroUsuario(int dineroUsuario) {
        this.dineroUsuario = dineroUsuario;
    }
}
