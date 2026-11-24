package ar.edu.unlu.poo.MarchiCastro.tute.modelo;

public enum Valor {
    //valor = Numero de carta, (FUERZA) = que peso tiene para ganarle a otra carta
    ANCHO(10),
    DOS(1),
    TRES(9),
    CUATRO(2),
    CINCO(3),
    SEIS(4),
    SIETE(5),
    SOTA(6),
    CABALLO(7),
    REY(8);

    private final int poder;
    private Valor(int poder){
        this.poder = poder;
    }
    public int getPoder(){
    return this.poder;
    }
    }
