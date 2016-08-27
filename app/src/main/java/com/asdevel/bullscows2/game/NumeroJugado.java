package com.asdevel.bullscows2.game;

/**
 *
 * Created by Fredy on 25/12/2014.
 */
public class NumeroJugado {
    public String numero;
    public int toros;
    public int vacas;

    public NumeroJugado() {
    }

    public NumeroJugado copy() {
        NumeroJugado nj = new NumeroJugado();
        nj.vacas = this.vacas;
        nj.toros = this.toros;
        nj.numero = this.numero;
        return nj;
    }

}
