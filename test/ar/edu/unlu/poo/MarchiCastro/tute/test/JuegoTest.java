package ar.edu.unlu.poo.MarchiCastro.tute.test;


import ar.edu.unlu.poo.MarchiCastro.tute.modelo.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JuegoTest {

    private Juego juegoConCuatroJugadores() {
        Juego juego = new Juego();
        juego.agregarJugador("Ivo");
        juego.agregarJugador("Adrian");
        juego.agregarJugador("Juan");
        juego.agregarJugador("Pedro");
        return juego;
    }

    @Test
    @DisplayName("test agregar jugadores")
    public void testAgregarJugadores() {
        Juego juego = new Juego();
        IJugador ivo = juego.agregarJugador("Ivo");

        assertEquals("Ivo", ivo.getNombre());
        assertEquals(1, juego.getListaJugadores().size());
    }

    @Test
    @DisplayName("test validaciones al agregar jugador")
    public void testValidacionesAgregar() {
        Juego juego = juegoConCuatroJugadores();

        // partida llena
        assertThrows(IllegalStateException.class, () -> juego.agregarJugador("Extra"));
        // nombre vacio
        assertThrows(IllegalArgumentException.class, () -> new Juego().agregarJugador("  "));
    }

    @Test
    @DisplayName("test no se puede iniciar sin 4 jugadores")
    public void testIniciarSinJugadores() {
        Juego juego = new Juego();
        juego.agregarJugador("Ivo");

        assertThrows(IllegalStateException.class, () -> juego.iniciarJuego());
    }

    @Test
    @DisplayName("test iniciar reparte 10 cartas a cada jugador")
    public void testIniciarReparte() {
        Juego juego = juegoConCuatroJugadores();
        juego.iniciarJuego();

        for (IJugador j : juego.getListaJugadores()) {
            assertEquals(10, j.getCantidadCartas());
        }
        // no se puede iniciar dos veces
        assertThrows(IllegalStateException.class, () -> juego.iniciarJuego());
    }

    @Test
    @DisplayName("test tirar carta en turno y fuera de turno")
    public void testJugarCarta() {
        Juego juego = juegoConCuatroJugadores();
        juego.iniciarJuego();

        IJugador primero = juego.getListaJugadores().get(0);
        IJugador segundo = juego.getListaJugadores().get(1);

        // el segundo no tiene el turno
        ICarta cartaDelSegundo = segundo.getMano().get(0);
        assertThrows(IllegalStateException.class, () -> juego.jugarCarta(segundo, cartaDelSegundo));

        // el primero abre con salida libre
        juego.jugarCarta(primero, primero.getMano().get(0));
        assertEquals(9, primero.getCantidadCartas());
    }

    @Test
    @DisplayName("test partida completa suma 120 puntos y termina")
    public void testPartidaCompleta() {
        Juego juego = juegoConCuatroJugadores();
        juego.iniciarJuego();

        // siempre tira la primera carta que las reglas le permiten
        for (int i = 0; i < 40; i++) {
            IJugador turno = juego.getJugadorEnTurno();
            juego.jugarCarta(turno, juego.getCartasJugables(turno).get(0));
        }

        for (IJugador j : juego.getListaJugadores()) {
            assertEquals(0, j.getCantidadCartas());
        }
        // 4 palos x (11+10+4+3+2) puntos
        assertEquals(120, juego.getPuntosPareja(0) + juego.getPuntosPareja(1));
        // gana una pareja (2 jugadores) o empatan (ninguno)
        assertTrue(juego.getGanadores().size() == 2 || juego.getGanadores().isEmpty());

        // ya terminó: no se puede seguir jugando
        IJugador primero = juego.getListaJugadores().get(0);
        assertThrows(IllegalStateException.class,
                () -> juego.jugarCarta(primero, new Carta(Palo.ORO, Valor.ANCHO)));
    }

    @Test
    @DisplayName("test el juego notifica a los observadores")
    public void testNotificaObservadores() {
        Juego juego = new Juego();
        List<Object> recibidos = new ArrayList<>();
        juego.agregarObservador((observable, evento) -> recibidos.add(evento));

        juego.agregarJugador("Ivo");

        assertEquals(1, recibidos.size());
        assertEquals(Eventos.AGREGAR_JUGADOR, recibidos.get(0));
    }
}