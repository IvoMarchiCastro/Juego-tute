package ar.edu.unlu.poo.MarchiCastro.tute;

import ar.edu.unlu.poo.MarchiCastro.tute.controlador.Controlador;
import ar.edu.unlu.poo.MarchiCastro.tute.modelo.Juego;
import ar.edu.unlu.poo.MarchiCastro.tute.vista.IVista;
import ar.edu.unlu.poo.MarchiCastro.tute.vista.VistaConsola;

public class Tute {

    public static void main(String[] args){

        //instancio el modelo
        Juego modelo = new Juego();

        //creacion del controlador
        Controlador controlador = new Controlador(modelo);

        //creo la visa
        IVista vist = new VistaConsola(controlador);

        vist.iniciar();
    }
}
