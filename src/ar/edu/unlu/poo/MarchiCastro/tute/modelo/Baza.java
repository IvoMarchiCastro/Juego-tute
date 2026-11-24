package ar.edu.unlu.poo.MarchiCastro.tute.modelo;

import java.util.*;

public class Baza {

    private Map<Jugador,Carta> cartasJugadas  = new LinkedHashMap<>();
    private Palo paloDelaBaza;
    private Jugador ganador;

    public Palo getPaloDelaBaza(){
        return this.paloDelaBaza;
    }

    public Jugador getGanador(){
        return this.ganador;
    }

    public Map<Jugador,Carta> getCartasJugadas(){
        return new LinkedHashMap<>(cartasJugadas);
    }

    public void setGanador(Jugador j){
        this.ganador = j;
    }
    public void agregarJugada(Jugador jugador, Carta carta) {
        if (jugador == null || carta == null) throw new IllegalArgumentException("ni el jugador ni la carta pueden ser null");
        if (cartasJugadas.containsKey(jugador)) throw new IllegalStateException("un jugador no puede tirar 2 veces en la misma baza");
        if (estaCompleta()) throw new IllegalStateException("NO se puede tirar una carta si la baza ya esta completa");

        if (cartasJugadas.isEmpty()) {

            this.paloDelaBaza = carta.getPalo();
        }
        cartasJugadas.put(jugador, carta);
    }
    public List<Carta> getCartas() {
        return new ArrayList<>(cartasJugadas.values());
    }

    public boolean estaCompleta(){
        if(cartasJugadas.size() == 4){
            return true;
        }
        return false;
    }
    public Carta getCartaDe(Jugador j){
        return cartasJugadas.get(j);
    }

}
