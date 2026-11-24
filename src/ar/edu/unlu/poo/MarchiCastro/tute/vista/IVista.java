package ar.edu.unlu.poo.MarchiCastro.tute.vista;

import ar.edu.unlu.poo.MarchiCastro.tute.modelo.ICarta;
import ar.edu.unlu.poo.MarchiCastro.tute.modelo.IJugador;
import ar.edu.unlu.poo.MarchiCastro.tute.modelo.Palo;

import java.util.List;

public interface IVista {
    void mostrarJugadores(List<IJugador> jugadores);
    void mostrarMano(List<ICarta> mano);
    void mostrarTurno(IJugador jugador);
    void mostrarCartaTirada(IJugador jugador, ICarta carta);
    void mostrarFinBaza(IJugador ganador);
    void mostrarFinJuego(List<IJugador> ganadores);
    void mostrarError(String mensaje);
    void mostrarCante(IJugador jugador, Palo palo);
    void mostrarCanteTute(IJugador jugador);
    void iniciar();
}
