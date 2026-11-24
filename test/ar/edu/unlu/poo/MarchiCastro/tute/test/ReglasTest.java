
package ar.edu.unlu.poo.MarchiCastro.tute.test;

import ar.edu.unlu.poo.MarchiCastro.tute.modelo.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ReglasTest {

    private Jugador crearJugadorPrueba(String nombre) {
        return new Jugador(nombre, new ArrayList<>());
    }
    // jugador que tiene rey y caballo de oro (cante de oro) mas dos cartas sueltas
    private Jugador crearJugadorConCanteDeOro(String nombre) {
        List<Carta> mano = new ArrayList<>();
        mano.add(new Carta(Palo.ORO, Valor.REY));
        mano.add(new Carta(Palo.ORO, Valor.CABALLO));
        mano.add(new Carta(Palo.ESPADAS, Valor.ANCHO));
        mano.add(new Carta(Palo.COPAS, Valor.DOS));
        return new Jugador(nombre, mano);
    }
    @Test
    @DisplayName("test decidirCartaGanadora con baza vacia")
    public void testGanadoraBazaVacia() {
        Reglas reglas = new Reglas();
        Baza baza = new Baza();

        assertThrows(IllegalStateException.class, () -> reglas.decidirJugadorGanador(baza, Palo.ORO));
    }

    @Test
    @DisplayName("test gana la carta mas alta del palo de salida")
    public void testGanaMasAltaDelPalo() {
        Reglas reglas = new Reglas();
        Palo triunfo = Palo.ORO;
        Baza baza = new Baza();
        Jugador j1 = crearJugadorPrueba("J1");
        Jugador j2 = crearJugadorPrueba("J2");
        Jugador j3 = crearJugadorPrueba("J3");
        Jugador j4 = crearJugadorPrueba("J4");

        baza.agregarJugada(j1, new Carta(Palo.ESPADAS, Valor.CUATRO));
        baza.agregarJugada(j2, new Carta(Palo.ESPADAS, Valor.REY));
        baza.agregarJugada(j3, new Carta(Palo.ESPADAS, Valor.SOTA));
        baza.agregarJugada(j4, new Carta(Palo.ESPADAS, Valor.DOS));

        assertEquals(j2, reglas.decidirJugadorGanador(baza, triunfo));
    }

    @Test
    @DisplayName("test el poder define al ganador, no los puntos")
    public void testPoderNoPuntos() {
        Reglas reglas = new Reglas();
        Palo triunfo = Palo.ORO;
        Baza baza = new Baza();
        Jugador j1 = crearJugadorPrueba("J1");
        Jugador j2 = crearJugadorPrueba("J2");

        // el rey vale 4 puntos y el tres vale 10, pero ademas el tres tiene mas poder (9 vs 8)
        baza.agregarJugada(j1, new Carta(Palo.COPAS, Valor.REY));
        baza.agregarJugada(j2, new Carta(Palo.COPAS, Valor.TRES));

        assertEquals(j2, reglas.decidirJugadorGanador(baza, triunfo));
    }

    @Test
    @DisplayName("test una carta de otro palo que no es triunfo no gana")
    public void testOtroPaloNoGana() {
        Reglas reglas = new Reglas();
        Palo triunfo = Palo.ORO;
        Baza baza = new Baza();
        Jugador j1 = crearJugadorPrueba("J1");
        Jugador j2 = crearJugadorPrueba("J2");
        Jugador j3 = crearJugadorPrueba("J3");
        Jugador j4 = crearJugadorPrueba("J4");

        baza.agregarJugada(j1, new Carta(Palo.ESPADAS, Valor.CUATRO));
        baza.agregarJugada(j2, new Carta(Palo.BASTOS, Valor.ANCHO));
        baza.agregarJugada(j3, new Carta(Palo.COPAS, Valor.TRES));
        baza.agregarJugada(j4, new Carta(Palo.ESPADAS, Valor.CINCO));

        // el ancho de bastos y el tres de copas no son del palo ni triunfo
        assertEquals(j4, reglas.decidirJugadorGanador(baza, triunfo));
    }

    @Test
    @DisplayName("test el triunfo le gana al palo de salida")
    public void testTriunfoGana() {
        Reglas reglas = new Reglas();
        Palo triunfo = Palo.ORO;
        Baza baza = new Baza();
        Jugador j1 = crearJugadorPrueba("J1");
        Jugador j2 = crearJugadorPrueba("J2");
        Jugador j3 = crearJugadorPrueba("J3");

        baza.agregarJugada(j1, new Carta(Palo.ESPADAS, Valor.ANCHO));
        baza.agregarJugada(j2, new Carta(Palo.ORO, Valor.DOS));
        baza.agregarJugada(j3, new Carta(Palo.ESPADAS, Valor.TRES));

        assertEquals(j2, reglas.decidirJugadorGanador(baza, triunfo));
    }

    @Test
    @DisplayName("test entre varios triunfos gana el mas alto")
    public void testMasAltoEntreTriunfos() {
        Reglas reglas = new Reglas();
        Palo triunfo = Palo.ORO;
        Baza baza = new Baza();
        Jugador j1 = crearJugadorPrueba("J1");
        Jugador j2 = crearJugadorPrueba("J2");
        Jugador j3 = crearJugadorPrueba("J3");
        Jugador j4 = crearJugadorPrueba("J4");

        baza.agregarJugada(j1, new Carta(Palo.ESPADAS, Valor.ANCHO));
        baza.agregarJugada(j2, new Carta(Palo.ORO, Valor.CUATRO));
        baza.agregarJugada(j3, new Carta(Palo.ORO, Valor.REY));
        baza.agregarJugada(j4, new Carta(Palo.ORO, Valor.SOTA));

        assertEquals(j3, reglas.decidirJugadorGanador(baza, triunfo));
    }

    @Test
    @DisplayName("test el palo de salida es el triunfo")
    public void testSalidaConTriunfo() {
        Reglas reglas = new Reglas();
        Palo triunfo = Palo.ORO;
        Baza baza = new Baza();
        Jugador j1 = crearJugadorPrueba("J1");
        Jugador j2 = crearJugadorPrueba("J2");
        Jugador j3 = crearJugadorPrueba("J3");

        baza.agregarJugada(j1, new Carta(Palo.ORO, Valor.CINCO));
        baza.agregarJugada(j2, new Carta(Palo.ESPADAS, Valor.ANCHO));
        baza.agregarJugada(j3, new Carta(Palo.ORO, Valor.CABALLO));

        assertEquals(j3, reglas.decidirJugadorGanador(baza, triunfo));
    }

    @Test
    @DisplayName("test baza vacia es salida libre")
    public void testSalidaLibre() {
        Reglas reglas = new Reglas();
        Palo triunfo = Palo.ORO;
        Baza baza = new Baza();

        List<Carta> mano = new ArrayList<>();
        mano.add(new Carta(Palo.ESPADAS, Valor.ANCHO));
        mano.add(new Carta(Palo.ORO, Valor.DOS));

        assertEquals(Obligacion.SALIDA_LIBRE, reglas.determinarObligacion(baza, mano, triunfo));
    }

    @Test
    @DisplayName("test montar: tiene el palo y puede superar")
    public void testMontar() {
        Reglas reglas = new Reglas();
        Palo triunfo = Palo.ORO;
        Baza baza = new Baza();
        Jugador j1 = crearJugadorPrueba("J1");
        Jugador j2 = crearJugadorPrueba("J2");

        baza.agregarJugada(j1, new Carta(Palo.ESPADAS, Valor.CUATRO));
        baza.agregarJugada(j2, new Carta(Palo.ESPADAS, Valor.SOTA));

        List<Carta> mano = new ArrayList<>();
        mano.add(new Carta(Palo.ESPADAS, Valor.DOS));   // no supera
        mano.add(new Carta(Palo.ESPADAS, Valor.REY));   // supera a la sota
        mano.add(new Carta(Palo.ORO, Valor.ANCHO));

        assertEquals(Obligacion.MONTAR, reglas.determinarObligacion(baza, mano, triunfo));
    }

    @Test
    @DisplayName("test asistir: tiene el palo pero no puede superar")
    public void testAsistir() {
        Reglas reglas = new Reglas();
        Palo triunfo = Palo.ORO;
        Baza baza = new Baza();
        Jugador j1 = crearJugadorPrueba("J1");

        baza.agregarJugada(j1, new Carta(Palo.ESPADAS, Valor.TRES));

        List<Carta> mano = new ArrayList<>();
        mano.add(new Carta(Palo.ESPADAS, Valor.REY));
        mano.add(new Carta(Palo.ESPADAS, Valor.DOS));
        mano.add(new Carta(Palo.ORO, Valor.ANCHO));

        assertEquals(Obligacion.ASISTIR, reglas.determinarObligacion(baza, mano, triunfo));
    }

    @Test
    @DisplayName("test asistir: tiene el palo pero va ganando un triunfo")
    public void testAsistirConTriunfoGanando() {
        Reglas reglas = new Reglas();
        Palo triunfo = Palo.ORO;
        Baza baza = new Baza();
        Jugador j1 = crearJugadorPrueba("J1");
        Jugador j2 = crearJugadorPrueba("J2");

        baza.agregarJugada(j1, new Carta(Palo.ESPADAS, Valor.CUATRO));
        baza.agregarJugada(j2, new Carta(Palo.ORO, Valor.DOS));

        List<Carta> mano = new ArrayList<>();
        mano.add(new Carta(Palo.ESPADAS, Valor.ANCHO));
        mano.add(new Carta(Palo.ORO, Valor.ANCHO));

        // aunque tenga el ancho de espadas no puede ganarle a un triunfo con el palo
        assertEquals(Obligacion.ASISTIR, reglas.determinarObligacion(baza, mano, triunfo));
    }

    @Test
    @DisplayName("test fallar: sin el palo, con triunfo y sin triunfo en la mesa")
    public void testFallar() {
        Reglas reglas = new Reglas();
        Palo triunfo = Palo.ORO;
        Baza baza = new Baza();
        Jugador j1 = crearJugadorPrueba("J1");
        Jugador j2 = crearJugadorPrueba("J2");

        baza.agregarJugada(j1, new Carta(Palo.ESPADAS, Valor.ANCHO));
        baza.agregarJugada(j2, new Carta(Palo.ESPADAS, Valor.TRES));

        List<Carta> mano = new ArrayList<>();
        mano.add(new Carta(Palo.ORO, Valor.CUATRO));
        mano.add(new Carta(Palo.COPAS, Valor.REY));
        mano.add(new Carta(Palo.BASTOS, Valor.DOS));

        assertEquals(Obligacion.FALLAR, reglas.determinarObligacion(baza, mano, triunfo));
    }

    @Test
    @DisplayName("test pisar: sin el palo, hay triunfo en mesa y tiene uno mas alto")
    public void testPisar() {
        Reglas reglas = new Reglas();
        Palo triunfo = Palo.ORO;
        Baza baza = new Baza();
        Jugador j1 = crearJugadorPrueba("J1");
        Jugador j2 = crearJugadorPrueba("J2");

        baza.agregarJugada(j1, new Carta(Palo.ESPADAS, Valor.CUATRO));
        baza.agregarJugada(j2, new Carta(Palo.ORO, Valor.CINCO));

        List<Carta> mano = new ArrayList<>();
        mano.add(new Carta(Palo.ORO, Valor.REY));
        mano.add(new Carta(Palo.COPAS, Valor.ANCHO));

        assertEquals(Obligacion.PISAR, reglas.determinarObligacion(baza, mano, triunfo));
    }

    @Test
    @DisplayName("test descarte: contrafallo, tiene triunfo pero mas bajo que el de la mesa")
    public void testContrafallo() {
        Reglas reglas = new Reglas();
        Palo triunfo = Palo.ORO;
        Baza baza = new Baza();
        Jugador j1 = crearJugadorPrueba("J1");
        Jugador j2 = crearJugadorPrueba("J2");

        baza.agregarJugada(j1, new Carta(Palo.ESPADAS, Valor.CUATRO));
        baza.agregarJugada(j2, new Carta(Palo.ORO, Valor.REY));

        List<Carta> mano = new ArrayList<>();
        mano.add(new Carta(Palo.ORO, Valor.DOS));
        mano.add(new Carta(Palo.ORO, Valor.CUATRO));
        mano.add(new Carta(Palo.COPAS, Valor.ANCHO));

        assertEquals(Obligacion.DESCARTE, reglas.determinarObligacion(baza, mano, triunfo));
    }

    @Test
    @DisplayName("test descarte: sin el palo y sin triunfo")
    public void testDescarteSinPaloNiTriunfo() {
        Reglas reglas = new Reglas();
        Palo triunfo = Palo.ORO;
        Baza baza = new Baza();
        Jugador j1 = crearJugadorPrueba("J1");

        baza.agregarJugada(j1, new Carta(Palo.ESPADAS, Valor.CUATRO));

        List<Carta> mano = new ArrayList<>();
        mano.add(new Carta(Palo.COPAS, Valor.ANCHO));
        mano.add(new Carta(Palo.BASTOS, Valor.TRES));

        assertEquals(Obligacion.DESCARTE, reglas.determinarObligacion(baza, mano, triunfo));
    }

    @Test
    @DisplayName("test descarte: mano vacia")
    public void testManoVacia() {
        Reglas reglas = new Reglas();
        Palo triunfo = Palo.ORO;
        Baza baza = new Baza();
        Jugador j1 = crearJugadorPrueba("J1");

        baza.agregarJugada(j1, new Carta(Palo.ESPADAS, Valor.CUATRO));

        List<Carta> mano = new ArrayList<>();

        assertEquals(Obligacion.DESCARTE, reglas.determinarObligacion(baza, mano, triunfo));
    }

    @Test
    @DisplayName("test palo de salida igual al triunfo: montar y asistir")
    public void testSalidaTriunfoMontarYAsistir() {
        Reglas reglas = new Reglas();
        Palo triunfo = Palo.ORO;
        Baza baza = new Baza();
        Jugador j1 = crearJugadorPrueba("J1");

        baza.agregarJugada(j1, new Carta(Palo.ORO, Valor.CINCO));

        // tiene un triunfo mas alto que el cinco, debe montar
        List<Carta> manoMonta = new ArrayList<>();
        manoMonta.add(new Carta(Palo.ORO, Valor.ANCHO));
        manoMonta.add(new Carta(Palo.COPAS, Valor.DOS));
        assertEquals(Obligacion.MONTAR, reglas.determinarObligacion(baza, manoMonta, triunfo));

        // solo tiene triunfos mas bajos que el cinco, debe asistir
        List<Carta> manoAsiste = new ArrayList<>();
        manoAsiste.add(new Carta(Palo.ORO, Valor.DOS));
        manoAsiste.add(new Carta(Palo.COPAS, Valor.ANCHO));
        assertEquals(Obligacion.ASISTIR, reglas.determinarObligacion(baza, manoAsiste, triunfo));
    }

    @Test
    @DisplayName("test cartasJugables salida libre devuelve toda la mano")
    public void testJugablesSalidaLibre() {
        Reglas reglas = new Reglas();
        Palo triunfo = Palo.ORO;
        Baza baza = new Baza();

        List<Carta> mano = new ArrayList<>();
        mano.add(new Carta(Palo.ESPADAS, Valor.ANCHO));
        mano.add(new Carta(Palo.ORO, Valor.DOS));
        mano.add(new Carta(Palo.COPAS, Valor.REY));

        List<Carta> jugables = reglas.cartasJugables(baza, mano, triunfo);

        assertEquals(mano, jugables);
    }

    @Test
    @DisplayName("test cartasJugables montar devuelve solo las que superan")
    public void testJugablesMontar() {
        Reglas reglas = new Reglas();
        Palo triunfo = Palo.ORO;
        Baza baza = new Baza();
        Jugador j1 = crearJugadorPrueba("J1");
        Jugador j2 = crearJugadorPrueba("J2");

        baza.agregarJugada(j1, new Carta(Palo.ESPADAS, Valor.CUATRO));
        baza.agregarJugada(j2, new Carta(Palo.ESPADAS, Valor.SOTA));

        List<Carta> mano = new ArrayList<>();
        mano.add(new Carta(Palo.ESPADAS, Valor.DOS));       // no supera a la sota
        mano.add(new Carta(Palo.ESPADAS, Valor.REY));       // supera
        mano.add(new Carta(Palo.ESPADAS, Valor.CABALLO));   // supera
        mano.add(new Carta(Palo.ORO, Valor.ANCHO));         // no es del palo

        List<Carta> esperadas = new ArrayList<>();
        esperadas.add(new Carta(Palo.ESPADAS, Valor.REY));
        esperadas.add(new Carta(Palo.ESPADAS, Valor.CABALLO));

        assertEquals(esperadas, reglas.cartasJugables(baza, mano, triunfo));
    }

    @Test
    @DisplayName("test cartasJugables asistir devuelve todas las del palo")
    public void testJugablesAsistir() {
        Reglas reglas = new Reglas();
        Palo triunfo = Palo.ORO;
        Baza baza = new Baza();
        Jugador j1 = crearJugadorPrueba("J1");

        baza.agregarJugada(j1, new Carta(Palo.ESPADAS, Valor.TRES));

        List<Carta> mano = new ArrayList<>();
        mano.add(new Carta(Palo.ESPADAS, Valor.REY));
        mano.add(new Carta(Palo.ESPADAS, Valor.DOS));
        mano.add(new Carta(Palo.ORO, Valor.ANCHO));

        List<Carta> esperadas = new ArrayList<>();
        esperadas.add(new Carta(Palo.ESPADAS, Valor.REY));
        esperadas.add(new Carta(Palo.ESPADAS, Valor.DOS));

        assertEquals(esperadas, reglas.cartasJugables(baza, mano, triunfo));
    }

    @Test
    @DisplayName("test cartasJugables asistir con triunfo ganando devuelve las del palo aunque sean mas altas")
    public void testJugablesAsistirConTriunfoGanando() {
        Reglas reglas = new Reglas();
        Palo triunfo = Palo.ORO;
        Baza baza = new Baza();
        Jugador j1 = crearJugadorPrueba("J1");
        Jugador j2 = crearJugadorPrueba("J2");

        baza.agregarJugada(j1, new Carta(Palo.ESPADAS, Valor.CUATRO));
        baza.agregarJugada(j2, new Carta(Palo.ORO, Valor.DOS));

        List<Carta> mano = new ArrayList<>();
        mano.add(new Carta(Palo.ESPADAS, Valor.ANCHO));
        mano.add(new Carta(Palo.ESPADAS, Valor.CINCO));
        mano.add(new Carta(Palo.ORO, Valor.ANCHO));

        List<Carta> esperadas = new ArrayList<>();
        esperadas.add(new Carta(Palo.ESPADAS, Valor.ANCHO));
        esperadas.add(new Carta(Palo.ESPADAS, Valor.CINCO));

        // el ancho de oro no se puede tirar porque tiene el palo
        assertEquals(esperadas, reglas.cartasJugables(baza, mano, triunfo));
    }

    @Test
    @DisplayName("test cartasJugables fallar devuelve solo los triunfos")
    public void testJugablesFallar() {
        Reglas reglas = new Reglas();
        Palo triunfo = Palo.ORO;
        Baza baza = new Baza();
        Jugador j1 = crearJugadorPrueba("J1");
        Jugador j2 = crearJugadorPrueba("J2");

        baza.agregarJugada(j1, new Carta(Palo.ESPADAS, Valor.ANCHO));
        baza.agregarJugada(j2, new Carta(Palo.ESPADAS, Valor.TRES));

        List<Carta> mano = new ArrayList<>();
        mano.add(new Carta(Palo.ORO, Valor.CUATRO));
        mano.add(new Carta(Palo.COPAS, Valor.REY));
        mano.add(new Carta(Palo.ORO, Valor.REY));
        mano.add(new Carta(Palo.BASTOS, Valor.DOS));

        List<Carta> esperadas = new ArrayList<>();
        esperadas.add(new Carta(Palo.ORO, Valor.CUATRO));
        esperadas.add(new Carta(Palo.ORO, Valor.REY));

        assertEquals(esperadas, reglas.cartasJugables(baza, mano, triunfo));
    }

    @Test
    @DisplayName("test cartasJugables pisar devuelve solo los triunfos mas altos")
    public void testJugablesPisar() {
        Reglas reglas = new Reglas();
        Palo triunfo = Palo.ORO;
        Baza baza = new Baza();
        Jugador j1 = crearJugadorPrueba("J1");
        Jugador j2 = crearJugadorPrueba("J2");

        baza.agregarJugada(j1, new Carta(Palo.ESPADAS, Valor.CUATRO));
        baza.agregarJugada(j2, new Carta(Palo.ORO, Valor.CINCO));

        List<Carta> mano = new ArrayList<>();
        mano.add(new Carta(Palo.ORO, Valor.DOS));       // no supera al cinco
        mano.add(new Carta(Palo.ORO, Valor.REY));       // supera
        mano.add(new Carta(Palo.ORO, Valor.SIETE));     // supera
        mano.add(new Carta(Palo.COPAS, Valor.ANCHO));   // no es triunfo

        List<Carta> esperadas = new ArrayList<>();
        esperadas.add(new Carta(Palo.ORO, Valor.REY));
        esperadas.add(new Carta(Palo.ORO, Valor.SIETE));

        assertEquals(esperadas, reglas.cartasJugables(baza, mano, triunfo));
    }

    @Test
    @DisplayName("test cartasJugables contrafallo devuelve toda la mano")
    public void testJugablesContrafallo() {
        Reglas reglas = new Reglas();
        Palo triunfo = Palo.ORO;
        Baza baza = new Baza();
        Jugador j1 = crearJugadorPrueba("J1");
        Jugador j2 = crearJugadorPrueba("J2");

        baza.agregarJugada(j1, new Carta(Palo.ESPADAS, Valor.CUATRO));
        baza.agregarJugada(j2, new Carta(Palo.ORO, Valor.REY));

        List<Carta> mano = new ArrayList<>();
        mano.add(new Carta(Palo.ORO, Valor.DOS));
        mano.add(new Carta(Palo.ORO, Valor.CUATRO));
        mano.add(new Carta(Palo.COPAS, Valor.ANCHO));

        assertEquals(mano, reglas.cartasJugables(baza, mano, triunfo));
    }

    @Test
    @DisplayName("test cartasJugables sin el palo y sin triunfo devuelve toda la mano")
    public void testJugablesDescarte() {
        Reglas reglas = new Reglas();
        Palo triunfo = Palo.ORO;
        Baza baza = new Baza();
        Jugador j1 = crearJugadorPrueba("J1");

        baza.agregarJugada(j1, new Carta(Palo.ESPADAS, Valor.CUATRO));

        List<Carta> mano = new ArrayList<>();
        mano.add(new Carta(Palo.COPAS, Valor.ANCHO));
        mano.add(new Carta(Palo.BASTOS, Valor.TRES));

        assertEquals(mano, reglas.cartasJugables(baza, mano, triunfo));
    }

    @Test
    @DisplayName("test cartasJugables con mano vacia")
    public void testJugablesManoVacia() {
        Reglas reglas = new Reglas();
        Palo triunfo = Palo.ORO;
        Baza baza = new Baza();
        Jugador j1 = crearJugadorPrueba("J1");

        baza.agregarJugada(j1, new Carta(Palo.ESPADAS, Valor.CUATRO));

        List<Carta> mano = new ArrayList<>();

        assertTrue(reglas.cartasJugables(baza, mano, triunfo).isEmpty());
    }

    @Test
    @DisplayName("test modificar la lista de jugables no modifica la mano")
    public void testJugablesCopia() {
        Reglas reglas = new Reglas();
        Palo triunfo = Palo.ORO;
        Baza baza = new Baza();

        List<Carta> mano = new ArrayList<>();
        mano.add(new Carta(Palo.ESPADAS, Valor.ANCHO));
        mano.add(new Carta(Palo.ORO, Valor.DOS));

        List<Carta> jugables = reglas.cartasJugables(baza, mano, triunfo);
        jugables.clear();

        assertEquals(2, mano.size());
    }
    @Test
    @DisplayName("test puntos de cante: 40 en el triunfo y 20 en los demas")
    public void testPuntosDeCante() {
        Reglas reglas = new Reglas();

        assertEquals(40, reglas.puntosDeCante(Palo.ORO, Palo.ORO));
        assertEquals(20, reglas.puntosDeCante(Palo.COPAS, Palo.ORO));
    }

    @Test
    @DisplayName("test puede cantar cuando se cumplen todas las condiciones")
    public void testPuedeCantar() {
        Reglas reglas = new Reglas();
        Jugador j1 = crearJugadorConCanteDeOro("J1");
        Baza baza = new Baza();
        Set<Palo> cantesRealizados = new HashSet<>();

        // j1 gano la baza anterior, la baza esta vacia, tiene el cante y no se canto
        assertTrue(reglas.puedeCantar(j1, j1, baza, Palo.ORO, cantesRealizados));
    }

    @Test
    @DisplayName("test no puede cantar en la primera baza")
    public void testNoPuedeCantarPrimeraBaza() {
        Reglas reglas = new Reglas();
        Jugador j1 = crearJugadorConCanteDeOro("J1");
        Baza baza = new Baza();
        Set<Palo> cantesRealizados = new HashSet<>();

        // nadie gano una baza anterior
        assertFalse(reglas.puedeCantar(j1, null, baza, Palo.ORO, cantesRealizados));
    }

    @Test
    @DisplayName("test no puede cantar un palo del que no tiene rey y caballo")
    public void testNoPuedeCantarSinElCante() {
        Reglas reglas = new Reglas();
        Jugador j1 = crearJugadorConCanteDeOro("J1");
        Baza baza = new Baza();
        Set<Palo> cantesRealizados = new HashSet<>();

        // tiene el cante de oro, pero no el de copas
        assertFalse(reglas.puedeCantar(j1, j1, baza, Palo.COPAS, cantesRealizados));
    }

    @Test
    @DisplayName("test no puede cantar un palo que ya se canto")
    public void testNoPuedeCantarPaloYaCantado() {
        Reglas reglas = new Reglas();
        Jugador j1 = crearJugadorConCanteDeOro("J1");
        Baza baza = new Baza();
        Set<Palo> cantesRealizados = new HashSet<>();
        cantesRealizados.add(Palo.ORO);

        assertFalse(reglas.puedeCantar(j1, j1, baza, Palo.ORO, cantesRealizados));
    }
}