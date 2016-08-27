package com.asdevel.bullscows2.game;

import android.util.Log;

public class TorosVacas {

    public static final int ES_GANADOR = 1;
    public static final int ES_PERDEDOR = 2;
    public static final int CONTINUA_JUEGO = 3;
    protected final int CANTIDAD_INTENTOS_POSIBLES = 10;
    protected NumeroJugado arregloNumerosJugados[];
    protected String numeroEncontrar;
    protected int cantidadJugadasRealizadas;
    protected int cantidadCifrasNumeroEncontrar;

    public TorosVacas(String aNumeroEncontrar) {
        numeroEncontrar = aNumeroEncontrar;
        cantidadCifrasNumeroEncontrar = aNumeroEncontrar.length();
        cantidadJugadasRealizadas = 0;
        arregloNumerosJugados = new NumeroJugado[CANTIDAD_INTENTOS_POSIBLES];

        Log.v("Numero a Encontrar", numeroEncontrar);
    }

    public static boolean inArray(String aArreglo, char aElemento) {
        for (int i = 0; i < aArreglo.length(); i++) {
            if (aArreglo.charAt(i) == aElemento) {
                return true;
            }
        }
        return false;
    }

    public int getCantidadCifrasNumero() {
        return cantidadCifrasNumeroEncontrar;
    }

    public NumeroJugado[] getArregloNumerosJugados() {
        return arregloNumerosJugados;
    }

    public int getCantidadJugadasRealizadas() {
        return cantidadJugadasRealizadas;
    }

    public String getNumeroEncontrar() {
        return numeroEncontrar;
    }

    public NumeroJugado getUltimoNumeroJugado() {
        return arregloNumerosJugados[cantidadJugadasRealizadas - 1];
    }

    public boolean fueEncontradoNumero() {
        return cantidadJugadasRealizadas != 0 && arregloNumerosJugados[cantidadJugadasRealizadas - 1].toros == cantidadCifrasNumeroEncontrar;
    }

    protected void comprobarCertezaJugada(String aNumeroUsuario) {
        int toros = 0;
        int vacas = 0;

        for (int i = 0; i < cantidadCifrasNumeroEncontrar; i++) {
            if (numeroEncontrar.charAt(i) == aNumeroUsuario.charAt(i)) {
                ++toros;
            } else if (inArray(numeroEncontrar, aNumeroUsuario.charAt(i))) {
                ++vacas;
            }
        }

        NumeroJugado numeroActual = new NumeroJugado();
        numeroActual.numero = aNumeroUsuario;
        numeroActual.toros = toros;
        numeroActual.vacas = vacas;

        arregloNumerosJugados[cantidadJugadasRealizadas] = numeroActual;
        ++cantidadJugadasRealizadas;
    }

    public boolean esValidaJugada(String aNumeroUsuario) {
        return aNumeroUsuario.length() == cantidadCifrasNumeroEncontrar;
    }
}
