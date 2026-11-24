package ar.edu.unlu.poo.MarchiCastro.tute.modelo;

import java.util.*;


public class Reglas {

    public Obligacion determinarObligacion( Baza b, List<Carta> mano,Palo triunfo) {

        LinkedHashMap<Jugador, Carta> jugadasDeLaBaza = (LinkedHashMap<Jugador, Carta>) b.getCartasJugadas();

        if (jugadasDeLaBaza.isEmpty()) {
            return Obligacion.SALIDA_LIBRE;
        }
        Carta cartaVaGanando = b.getCartaDe(decidirJugadorGanador(b, triunfo));

        Palo paloBaza = b.getPaloDelaBaza();

        List<Carta> delPaloTriunfo = buscarPorPalo(triunfo, mano);
        List<Carta> delPaloBaza = buscarPorPalo(paloBaza, mano);

        if (!(delPaloBaza.isEmpty())) {
            boolean puedeSuperar = false;
            if (cartaVaGanando.getPalo() == paloBaza) {
                for (Carta c : delPaloBaza)
                    if (c.getPoder() > cartaVaGanando.getPoder()) {
                        puedeSuperar  =true;
                    }
                }
                if (puedeSuperar) {
                    return Obligacion.MONTAR;
                }
                return Obligacion.ASISTIR;
            }
        else if (!(delPaloTriunfo.isEmpty())) {
            for (Carta c : delPaloTriunfo) {
                if (cartaVaGanando.getPalo() != triunfo) {
                    return Obligacion.FALLAR;
                }
                if (cartaVaGanando.getPalo() == triunfo && c.getPoder() > cartaVaGanando.getPoder()) {
                    return Obligacion.PISAR;
                }
            }
        }
        return Obligacion.DESCARTE;
      }

    public Jugador decidirJugadorGanador(Baza b,Palo triunfo){
        LinkedHashMap<Jugador,Carta> jugadasDeLaBaza = (LinkedHashMap<Jugador, Carta>) b.getCartasJugadas();

        if (jugadasDeLaBaza.isEmpty()){
            throw new IllegalStateException("no se jugaron cartas en la baza todavia");
        }

        Jugador ganador= null ;
        Carta cartaAlta = null;

        for (Map.Entry<Jugador,Carta> jugada:  jugadasDeLaBaza.entrySet()){
            if (ganador == null && cartaAlta == null){
                ganador = jugada.getKey();
                cartaAlta = jugada.getValue();
            }
            if (jugada.getValue().getPalo() == cartaAlta.getPalo()){
                if (jugada.getValue().getPoder()> cartaAlta.getPoder()){
                    cartaAlta = jugada.getValue();
                    ganador = jugada.getKey();
                }
            } else if (jugada.getValue().getPalo() == triunfo && cartaAlta.getPalo() !=  triunfo) {
                ganador = jugada.getKey();
                cartaAlta = jugada.getValue();

            }
        }

        return ganador;
    }
    private List<Carta> buscarPorPalo(Palo palo,List<Carta> mano){
        List<Carta> cartasDelPalo = new ArrayList<>();

        for (Carta c: mano){
            if(c.getPalo()==palo){
                cartasDelPalo.add(c);
            }
        }
        return cartasDelPalo;
    }
    public List<Carta> cartasJugables(Baza b, List<Carta> mano, Palo triunfo) {
        Obligacion obligacion = determinarObligacion(b, mano, triunfo);

        // salida libre y descarte: puede tirar cualquiera
        if (obligacion == Obligacion.SALIDA_LIBRE || obligacion == Obligacion.DESCARTE) {
            return new ArrayList<>(mano);
        }

        Palo paloBaza = b.getPaloDelaBaza();
        Carta cartaVaGanando = b.getCartaDe(decidirJugadorGanador(b, triunfo));

        if (obligacion == Obligacion.ASISTIR) {
            return buscarPorPalo(paloBaza, mano);
        }
        if (obligacion == Obligacion.FALLAR) {
            return buscarPorPalo(triunfo, mano);
        }
        if (obligacion == Obligacion.MONTAR) {
            return cartasQueSuperan(buscarPorPalo(paloBaza, mano), cartaVaGanando);
        }
        // PISAR
        return cartasQueSuperan(buscarPorPalo(triunfo, mano), cartaVaGanando);
    }

    private List<Carta> cartasQueSuperan(List<Carta> cartas, Carta referencia) {
        List<Carta> superan = new ArrayList<>();

        for (Carta c : cartas) {
            if (c.getPoder() > referencia.getPoder()) {
                superan.add(c);
            }
        }
        return superan;
    }
    public int puntosDeCante(Palo palo, Palo triunfo){
        if (palo == triunfo){
            return 40;
        }else{
            return 20;
        }
    }
    public boolean puedeCantar(Jugador j, Jugador ganadorUltimaBaza, Baza b, Palo palo, Set<Palo> cantesRealizados){

        //para poder cantar debe Haber ganado la mano
        //Tenere el cante de ese palo
        //que ese palo no halla sido cantado
        //y que sea antes de jugar

        if (ganadorUltimaBaza != null){
            if (j == ganadorUltimaBaza && b.getCartasJugadas().isEmpty()
                    &&j.getCantes().contains(palo) && !cantesRealizados.contains(palo)){
                return true;
            }
        }
        return false;
    }

    public boolean puedeCantarTute(Jugador j, Jugador ganadorUltimaBaza, Baza b, Valor valor){
        if (ganadorUltimaBaza != null){
            if (j == ganadorUltimaBaza && b.getCartasJugadas().isEmpty()
                    &&j.getTute().contains(valor)){
                return true;
            }
        }
        return false;
    }

 }
