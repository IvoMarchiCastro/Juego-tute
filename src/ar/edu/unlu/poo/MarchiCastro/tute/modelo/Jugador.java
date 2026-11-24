package ar.edu.unlu.poo.MarchiCastro.tute.modelo;

import java.util.*;

public class Jugador implements IJugador {
    private String nombre;
    private List<Carta> cartasDelJugador;
    private int cantidadPuntos = 0;
    private  static int numeroDeJugadoresCreados;
    private int id;

    public Jugador(String unNombre,List<Carta> cartas){
        this.nombre = unNombre;
        this.cartasDelJugador = new ArrayList<>(cartas);
        this.id = ++numeroDeJugadoresCreados;

    }

    public String getNombre(){
        return this.nombre;
    }
    public  List<Carta> getCartasDelJugador(){
        return new ArrayList<>(this.cartasDelJugador);
    }

    public int getId() {
        return this.id;
    }

    public int getCantidadPuntos(){
        return this.cantidadPuntos;
    }

    public int getCantidadCartas(){
        return cartasDelJugador.size();
    }

    public void tirarCarta(Carta laCartaAtirar){
        if (!(this.cartasDelJugador.remove(laCartaAtirar))){
            throw new IllegalArgumentException("No se pudo sacar la carta de la mano del jugador porque no la tiene" + nombre);
        }
    }
    // suma los puntos nuevos a los que ya tenia
    public void sumarCantidadPuntos(int unaCantidadPuntos){

        if(unaCantidadPuntos<0){
            throw new IllegalArgumentException("la cantidad de puntos a sumar no puede ser negativa para el jugador "+nombre);
        }

        this.cantidadPuntos += unaCantidadPuntos;
    }

    public void agregarCarta(Carta aAgregar) {
        // NO VERIFICO SI YA LA TIENE PORQUE LAS CARTAS SE INSTACIAN DE TAL FORMA QUE NO SE REPITAN
        this.cartasDelJugador.add(aAgregar);

    }

    public Set<Palo> getCantes(){
        Set<Palo> Cantes = new HashSet<>();


        for (Palo palo : Palo.values()){
            if (this.cartasDelJugador.contains(new Carta(palo,Valor.CABALLO))
                && this.cartasDelJugador.contains(new Carta(palo,Valor.REY))){
                    Cantes.add(palo);
                }
            }
        return Cantes;
        }
    public Set<Valor> getTute(){
        Set<Valor> tute = new HashSet<>();

        if (tieneCuatroCartasDelMismoValor(Valor.CABALLO)){
            tute.add(Valor.CABALLO);
        }
        if (tieneCuatroCartasDelMismoValor(Valor.REY)){
            tute.add(Valor.REY);
        }

        return tute;
    }
    private boolean tieneCuatroCartasDelMismoValor(Valor v){
        boolean resultado = true;
        int contadorAparicion = 0;

        for (Palo palo : Palo.values()){
            if (this.cartasDelJugador.contains(new Carta(palo,v))) {
                contadorAparicion++;
            }
        }

        if (contadorAparicion != 4){
            resultado = false;
        }
        return resultado;
    }
    @Override
    public List<ICarta> getMano(){
        return new ArrayList<>(cartasDelJugador);
    }
}




