package com.asdevel.bullscows2.game;

import java.util.Random;

public class TorosVacasVsPc extends TorosVacas {

    public TorosVacasVsPc(int aCantidadCifrasNumeroEncontrar) {
        super(generarNumeroAleatorio(aCantidadCifrasNumeroEncontrar));
    }

    private static String generarNumeroAleatorio(int aCantidadCifrasNumeroEncontrar) {
        String numeroAleatorioGenerado = "";
        Random aleatorio = new Random();
        int cifraAleatoria;
        char caracterCifra;

        while (numeroAleatorioGenerado.length() != aCantidadCifrasNumeroEncontrar) {
            cifraAleatoria = aleatorio.nextInt(10);
            caracterCifra = Character.forDigit(cifraAleatoria, 10);

            if (!inArray(numeroAleatorioGenerado, caracterCifra)) {
                numeroAleatorioGenerado += caracterCifra;
            }
        }
        return numeroAleatorioGenerado;
    }

    public void Jugada(String aNumero) {
        if (arregloNumerosJugados.length < CANTIDAD_INTENTOS_POSIBLES || !fueEncontradoNumero()) {
            comprobarCertezaJugada(aNumero);
        }
    }

    public int resultadosJuego() {
        if (fueEncontradoNumero()) {
            return ES_GANADOR;
        } else if (cantidadJugadasRealizadas == CANTIDAD_INTENTOS_POSIBLES) {
            return ES_PERDEDOR;
        }

        return CONTINUA_JUEGO;
    }

}
