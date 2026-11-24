package ar.edu.unlu.poo.MarchiCastro.tute.test;

import ar.edu.unlu.poo.MarchiCastro.tute.modelo.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BazaTest {

    private Jugador crearJugadorPrueba(String nombre) {
        return new Jugador(nombre, new ArrayList<>());
    }

    @Test
    @DisplayName("test constructor Baza")
    public void testConstructor() {
        Baza baza = new Baza();

        assertNull(baza.getPaloDelaBaza());
        assertNull(baza.getGanador());
        assertTrue(baza.getCartas().isEmpty());
        assertTrue(baza.getCartasJugadas().isEmpty());
    }

    @Test
    @DisplayName("test agregar primera jugada fija el palo de la baza")
    public void testPrimeraJugadaFijaPalo() {
        Baza baza = new Baza();
        Jugador j1 = crearJugadorPrueba("Ivo");
        Jugador j2 = crearJugadorPrueba("Adrian");

        Carta anchoEspada = new Carta(Palo.ESPADAS, Valor.ANCHO);
        Carta reyOro = new Carta(Palo.ORO, Valor.REY);

        baza.agregarJugada(j1, anchoEspada);
        assertEquals(Palo.ESPADAS, baza.getPaloDelaBaza());

        // La segunda carta no debe cambiar el palo de salida de la baza
        baza.agregarJugada(j2, reyOro);
        assertEquals(Palo.ESPADAS, baza.getPaloDelaBaza());
    }

    @Test
    @DisplayName("test baza completa con 4 jugadores")
    public void testBazaCompleta() {
        Baza baza = new Baza();
        Jugador j1 = crearJugadorPrueba("J1");
        Jugador j2 = crearJugadorPrueba("J2");
        Jugador j3 = crearJugadorPrueba("J3");
        Jugador j4 = crearJugadorPrueba("J4");

        baza.agregarJugada(j1, new Carta(Palo.BASTOS, Valor.ANCHO));
        assertFalse(baza.estaCompleta());

        baza.agregarJugada(j2, new Carta(Palo.BASTOS, Valor.TRES));
        assertFalse(baza.estaCompleta());

        baza.agregarJugada(j3, new Carta(Palo.BASTOS, Valor.REY));
        assertFalse(baza.estaCompleta());

        baza.agregarJugada(j4, new Carta(Palo.BASTOS, Valor.SOTA));
        assertTrue(baza.estaCompleta());
        assertEquals(4, baza.getCartas().size());
    }

    @Test
    @DisplayName("test setear y obtener ganador")
    public void testGanador() {
        Baza baza = new Baza();
        Jugador j1 = crearJugadorPrueba("Ivo");

        assertNull(baza.getGanador());
        baza.setGanador(j1);
        assertEquals(j1, baza.getGanador());
    }

    @Test
    @DisplayName("test modificar copia de cartas o mapa no modifica la baza")
    public void testModCopia() {
        Baza baza = new Baza();
        Jugador j1 = crearJugadorPrueba("Ivo");
        baza.agregarJugada(j1, new Carta(Palo.COPAS, Valor.CABALLO));

        List<Carta> copiaCartas = baza.getCartas();
        copiaCartas.clear();
        assertEquals(1, baza.getCartas().size());

        Map<Jugador, Carta> copiaMapa = baza.getCartasJugadas();
        copiaMapa.clear();
        assertEquals(1, baza.getCartasJugadas().size());
    }

    @Test
    @DisplayName("test validaciones al agregar jugada")
    public void testValidacionesJugada() {
        Baza baza = new Baza();
        Jugador j1 = crearJugadorPrueba("J1");
        Jugador j2 = crearJugadorPrueba("J2");
        Jugador j3 = crearJugadorPrueba("J3");
        Jugador j4 = crearJugadorPrueba("J4");
        Jugador j5 = crearJugadorPrueba("J5");

        // No permite jugador o carta null
        assertThrows(IllegalArgumentException.class, () -> baza.agregarJugada(null, new Carta(Palo.ORO, Valor.DOS)));
        assertThrows(IllegalArgumentException.class, () -> baza.agregarJugada(j1, null));

        // No permite que el mismo jugador tire dos veces en la misma baza
        baza.agregarJugada(j1, new Carta(Palo.ORO, Valor.DOS));
        assertThrows(IllegalStateException.class, () -> baza.agregarJugada(j1, new Carta(Palo.ORO, Valor.TRES)));

        // Completamos la baza
        baza.agregarJugada(j2, new Carta(Palo.ORO, Valor.CUATRO));
        baza.agregarJugada(j3, new Carta(Palo.ORO, Valor.CINCO));
        baza.agregarJugada(j4, new Carta(Palo.ORO, Valor.SEIS));

        // No permite agregar una quinta carta cuando ya esta completa
        assertThrows(IllegalStateException.class, () -> baza.agregarJugada(j5, new Carta(Palo.ORO, Valor.SIETE)));
    }
}