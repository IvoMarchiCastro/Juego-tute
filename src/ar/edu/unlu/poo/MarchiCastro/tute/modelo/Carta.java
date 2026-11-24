
package ar.edu.unlu.poo.MarchiCastro.tute.modelo;


import java.util.Objects;

public class Carta implements ICarta{

    private Palo palo;
    private Valor valor;
    private int puntos;
    private int poder;
    public Carta(Palo unPalo,Valor unValor){

        this.palo = unPalo;
        this.valor = unValor;
        this.puntos = this.calcPuntos();
        this.poder = this.valor.getPoder();
    }

    public Palo getPalo(){
        return palo;
    }

    public Valor getValor(){
        return valor;
    }
    public int calcPuntos(){
        int puntaje = 0;
        switch (valor){
            case ANCHO:
                puntaje = 11;
            break;
            case TRES:
                puntaje = 10;
                break;
            case REY:
                puntaje  = 4;
                break;
            case CABALLO:
                puntaje = 3;
                break;
            case SOTA:
                puntaje = 2;
                break;
            default:
                puntaje = 0;
                break;
        }
        return puntaje;
    }
    public int getPuntos(){
        return this.puntos;
    }
    public int getPoder(){
        return this.poder;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Carta otra = (Carta) obj;
        return this.palo == otra.palo && this.valor == otra.valor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(palo, valor);
    }
}
