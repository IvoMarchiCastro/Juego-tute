package ar.edu.unlu.poo.MarchiCastro.tute.modelo;

import ar.edu.unlu.poo.MarchiCastro.tute.observer.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class Juego extends Observable {

    private static final int CANTIDAD_JUGADORES = 4;
    private static final int BAZAS_POR_RONDA = 10;

    private final List<Jugador> jugadores ;   // orden de mesa
    private final Reglas reglas;
    private Mazo mazo;
    private Palo paloTriunfo;

    private Baza bazaActual;
    private final List<Baza> bazasJugadas = new ArrayList<>();
    private int turnoActual;                    // índice en jugadores
    private Jugador ganadorUltimaBaza;          // habilita cantar

    private final Set<Palo> cantesRealizados = new HashSet<>();
    private final List<Jugador> ganadores = new ArrayList<>();

    private boolean juegoTerminado = false ;

    public Juego(){
        this.jugadores= new ArrayList<>();
        this.reglas = new Reglas();
        this.mazo = new Mazo();
    }

    public IJugador agregarJugador(String nombre){
        if(jugadores.size() == CANTIDAD_JUGADORES){
            throw  new IllegalStateException("No se pueden agregar mas jugadores, partida llena");
        }
        if (nombre == null||nombre.trim().isEmpty()){
            throw new IllegalArgumentException("el nombre no puede ser vacio");
        }
        Jugador nuevo = new Jugador(nombre,new ArrayList<>());
        jugadores.add(nuevo);
        this.notificarObservadores(Eventos.AGREGAR_JUGADOR);
        return nuevo;

    }

    public void iniciarJuego(){
        if (bazaActual != null) {
            throw new IllegalStateException("El juego ya inicio");
        }

        if (jugadores.size() != CANTIDAD_JUGADORES){
            throw new IllegalStateException("No se puede iniciar el juego si no son 4 jugadores");
        }
        Carta ultimaRepartida = null;
        this.mazo = new Mazo();
        for(int i = 0; i<BAZAS_POR_RONDA;i++) {
            for (Jugador j : jugadores) {
                ultimaRepartida = mazo.getSigCarta();

                j.agregarCarta(ultimaRepartida);
            }
        }
        turnoActual = 0;
        bazaActual = new Baza();
        paloTriunfo = ultimaRepartida.getPalo();

        this.notificarObservadores(Eventos.INICIAR_EL_JUEGO);
    }

    private boolean estaEnCurso() {
        if (bazaActual != null && bazasJugadas.size() < BAZAS_POR_RONDA && !juegoTerminado){
            return true;
        }
        return false;
    }

    public IJugador getJugadorEnTurno() {
        return jugadores.get(turnoActual);
    }

    public IJugador getGanadorUltimaBaza(){
        return ganadorUltimaBaza;
    }

    public List<ICarta> getCartasJugables(IJugador j) {
        if (!estaEnCurso()){
            throw new IllegalStateException("todavia no se empezo el juego");
        }

        Jugador jugador = buscarJugador(j);

        return  new ArrayList<ICarta>(reglas.cartasJugables(bazaActual, jugador.getCartasDelJugador(), paloTriunfo));
    }
    public int getPuntosPareja(int pareja) {
        if (pareja != 0 && pareja != 1) {
            throw new IllegalArgumentException("la pareja debe ser 0 o 1");
        }
        return jugadores.get(pareja).getCantidadPuntos()
                + jugadores.get(pareja + 2).getCantidadPuntos();
    }
    public List<IJugador> getListaJugadores() {
        return new ArrayList<>(this.jugadores);
    }

    public Palo getPaloTriunfo(){
        return this.paloTriunfo;
    }


    private void terminarJuego() {
        int puntosPareja0 = getPuntosPareja(0);   // J1 + J3
        int puntosPareja1 = getPuntosPareja(1);   // J2 + J4

        ganadores.clear();
        if (puntosPareja0 > puntosPareja1) {
            ganadores.add(jugadores.get(0));
            ganadores.add(jugadores.get(2));
        } else if (puntosPareja1 > puntosPareja0) {
            ganadores.add(jugadores.get(1));
            ganadores.add(jugadores.get(3));
        }
        // si empatan, ganadores queda vacia
        juegoTerminado = true;
        this.notificarObservadores(Eventos.TERMINAR_EL_JUEGO);
    }

    public List<IJugador> getGanadores() {
        return new ArrayList<>(ganadores);
    }

    public ICarta getCartaTirada(IJugador jugador){
        Jugador j = buscarJugador(jugador);
        return bazaActual.getCartaDe(j);
    }
    private void resolverBaza(){
        Jugador ganador = reglas.decidirJugadorGanador(bazaActual,paloTriunfo);
        bazaActual.setGanador(ganador);
        int puntos = 0;
        for (Carta c : bazaActual.getCartas()) {
            puntos += c.getPuntos();
        }
        ganador.sumarCantidadPuntos(puntos);
        this.ganadorUltimaBaza = ganador;
        bazasJugadas.add(bazaActual);

        if (bazasJugadas.size() == BAZAS_POR_RONDA){
            terminarJuego();
        }else {
            this.bazaActual = new Baza();
            this.turnoActual = jugadores.indexOf(ganador);

        }
        notificarObservadores(Eventos.FIN_BAZA);
    }

    public void jugarCarta(IJugador jugador,ICarta carta) {
        if (!estaEnCurso()) {
            throw new IllegalStateException("El juego no esta en curso");
        }
        Jugador j = buscarJugador(jugador);
        Carta c = new Carta(carta.getPalo(),carta.getValor());
        if (j != jugadores.get(turnoActual)) {
            throw new IllegalStateException("No es el turno del jugador");
        }
        if (!getCartasJugables(j).contains(c)) {
            throw new IllegalArgumentException("Esa carta no cumple la obligacion");
        }

        j.tirarCarta(c);

        bazaActual.agregarJugada(j, c);
        this.notificarObservadores(Eventos.TIRAR_CARTA);
        if (bazaActual.estaCompleta()) {
            resolverBaza();
        } else {
            turnoActual = (turnoActual + 1) % CANTIDAD_JUGADORES;
            notificarObservadores(Eventos.CAMBIO_TURNO);
        }
    }
    private Jugador buscarJugador(IJugador jugador){
        if (jugador == null){
            throw new IllegalArgumentException("no se puede pasar un jugador nulo");
        }
        Jugador buscado = null;

        for (Jugador j: jugadores){
            if (jugador.getId() ==  j.getId()){
                buscado = j;
            }
        }
        if (buscado == null){
            throw new IllegalArgumentException("el jugador no existe");
        }
        return buscado;
    }
    public boolean cantar(IJugador j,Palo palo){

        if (!estaEnCurso()){
            throw new IllegalStateException("la partida no esta en curso");
        }

        Jugador jugador = buscarJugador(j);

        if (reglas.puedeCantar(jugador,ganadorUltimaBaza,bazaActual,palo,cantesRealizados)){
            jugador.sumarCantidadPuntos(reglas.puntosDeCante(palo,paloTriunfo));
            cantesRealizados.add(palo);
            this.notificarObservadores(Eventos.CANTAR);
            return true;
        }
        return false;
    }
    public boolean declacarTute(IJugador jugador,Valor valor){
        if (!estaEnCurso()){throw new IllegalStateException("la partida no esta en curso");}
        Jugador j = buscarJugador(jugador);

        if (reglas.puedeCantarTute(j,this.ganadorUltimaBaza,bazaActual,valor)){
            int i = jugadores.indexOf(j);
            ganadores.clear();
            ganadores.add(j);
            ganadores.add(jugadores.get((i + 2) % CANTIDAD_JUGADORES)); // su compañero
            juegoTerminado = true;
            notificarObservadores(Eventos.CANTAR_TUTE);
            notificarObservadores(Eventos.TERMINAR_EL_JUEGO);
            return true;
        }
        return false;

    }
}
