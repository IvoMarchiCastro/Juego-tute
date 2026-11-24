package ar.edu.unlu.poo.MarchiCastro.tute.modelo;
import java.util.*;

// Debido a la necesidad de barajar un mazo de 40 cartas creare 40 cartas de forma aleatorios
// creare una lista y la desordenare con collections.shuffle(lista)
public class Mazo {

    private List<Carta> listCartas;

    public Mazo() {
        this.listCartas = new ArrayList<>();
        this.llenarMazo();
        this.barajarMazo();
    }

    private void llenarMazo() {
        for (Palo palo : Palo.values()) {
            for (Valor valor : Valor.values()) {
                Carta aAgregar = new Carta(palo, valor);
                listCartas.add(aAgregar);
            }
        }
    }

    public int getCantCartas() {
        return listCartas.size();
    }

    public boolean existeLaCarta(Carta card) {
        for (Carta carta : listCartas) {
            if (carta.getPalo() == card.getPalo() && card.getValor() == carta.getValor()) {
                return true;
            }
        }
        return false;
    }

    private void barajarMazo() {
        Collections.shuffle(this.listCartas);
    }

    public Carta getSigCarta() {
        if (this.getCantCartas() <1){
            throw new SInCartasException("no se puede sacar una carta del mazo vacio");
        }
        Carta sigCarta = listCartas.getLast();
        listCartas.remove(sigCarta);
        return sigCarta;
    }

    public Carta getVerSigCarta() {
        Carta sigCarta = listCartas.getLast();
        return sigCarta;

    }

}