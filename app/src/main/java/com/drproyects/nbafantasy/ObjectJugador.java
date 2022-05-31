package com.drproyects.nbafantasy;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

public class ObjectJugador {

    private int idJugador;
    private String nombreJugador;
    private String posicionJugador;
    private int posicionAlineacionJugador;
    private int puntosJugador;
    private String equipoJugador;
    private int propietarioJugador;
    private int precioJugador;
    private String edadJugador;

    public ObjectJugador(@Nullable int idJugador, String nombreJugador, @Nullable int puntosJugador, @Nullable String posicionJugador, @Nullable String equipoJugador,
                         @Nullable int propietarioJugador, @Nullable int precioJugador, @Nullable String edadJugador) {
        this.idJugador = idJugador;
        this.nombreJugador = nombreJugador;
        this.puntosJugador = puntosJugador;
        this.posicionJugador = posicionJugador;
        this.equipoJugador = equipoJugador;
        this.propietarioJugador = propietarioJugador;
        this.precioJugador = precioJugador;
        this.edadJugador = edadJugador;
    }

    public ObjectJugador(String nombreJugador, @Nullable int puntosJugador, @Nullable String equipoJugador) {
        this.nombreJugador = nombreJugador;
        this.puntosJugador = puntosJugador;
        this.equipoJugador = equipoJugador;
    }

    public ObjectJugador(JSONObject jugador) throws JSONException {
        this.idJugador = jugador.getInt("idJugador");
        this.nombreJugador = jugador.getString("nombreJugador");
        this.puntosJugador = jugador.getInt("puntosJugador");
        this.posicionAlineacionJugador = jugador.getInt("posicionAlineacionJugador");
        this.posicionJugador = jugador.getString("posicionJugador");
        this.equipoJugador = jugador.getString("equipoJugador");
        this.propietarioJugador = jugador.getInt("idPropietarioJugador");
        this.precioJugador = jugador.getInt("precioJugador");
        this.edadJugador = jugador.getString("edadJugador");
    }


    public int getPosicionAlineacionJugador() {
        return posicionAlineacionJugador;
    }

    public void setPosicionAlineacionJugador(int posicionAlineacionJugador) {
        this.posicionAlineacionJugador = posicionAlineacionJugador;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public int getPuntosJugador() {
        return puntosJugador;
    }

    public void setPuntosJugador(int puntosJugador) {
        this.puntosJugador = puntosJugador;
    }

    public String getPosicionJugador() {
        return posicionJugador;
    }

    public void setPosicionJugador(String posicionJugador) {
        this.posicionJugador = posicionJugador;
    }

    public String getEquipoJugador() {
        return equipoJugador;
    }

    public void setEquipoJugador(String equipoJugador) {
        this.equipoJugador = equipoJugador;
    }

    public int getPropietarioJugador() {
        return propietarioJugador;
    }

    public void setPropietarioJugador(int propietarioJugador) {
        this.propietarioJugador = propietarioJugador;
    }

    public int getPrecioJugador() {
        return precioJugador;
    }

    public void setPrecioJugador(int precioJugador) {
        this.precioJugador = precioJugador;
    }

    public String getEdadJugador() {
        return edadJugador;
    }

    public void setEdadJugador(String edadJugador) {
        this.edadJugador = edadJugador;
    }

    @Override
    public String toString() {
        return "ObjectJugador{" +
                "idJugador=" + idJugador +
                ", nombreJugador='" + nombreJugador + '\'' +
                ", posicionJugador='" + posicionJugador + '\'' +
                ", posicionAlineacionJugador=" + posicionAlineacionJugador +
                ", puntosJugador=" + puntosJugador +
                ", equipoJugador='" + equipoJugador + '\'' +
                ", propietarioJugador=" + propietarioJugador +
                ", precioJugador=" + precioJugador +
                ", edadJugador='" + edadJugador + '\'' +
                '}';
    }
}
