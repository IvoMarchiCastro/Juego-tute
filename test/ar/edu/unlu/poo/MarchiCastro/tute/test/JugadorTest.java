package ar.edu.unlu.poo.MarchiCastro.tute.test;

import ar.edu.unlu.poo.MarchiCastro.tute.modelo.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class JugadorTest {
    public List<Carta> repartirCartaConocida(){
        List<Carta> cartas = new ArrayList<>();

        Carta anchoEspada = new Carta(Palo.ESPADAS, Valor.ANCHO);
        cartas.add(anchoEspada);

        Carta sieteBastos= new Carta(Palo.BASTOS, Valor.SIETE);
        cartas.add(sieteBastos);

        Carta reyCopas= new Carta(Palo.COPAS, Valor.REY);
        cartas.add(reyCopas);

        Carta sotaOro= new Carta(Palo.ORO, Valor.SOTA);
        cartas.add(sotaOro);
        return cartas;
    }
    @Test
    @DisplayName("test constructor" +
            " jugador")
    public void testConstructor(){
        Mazo españolas = new Mazo();

        //simulo repartir
        List<Carta> cartas = new ArrayList<>();
        for(int i = 0; i<10;i++){
            cartas.add(españolas.getSigCarta());
        }

        Jugador ivo = new Jugador("ivo",cartas);

        assertEquals("ivo",ivo.getNombre());
        assertEquals(10,ivo.getCantidadCartas());
        assertEquals(0,ivo.getCantidadPuntos());
    }

    @Test
    @DisplayName("test getNombre")
    public void testGetNombre(){
        Mazo españolas = new Mazo();
        //simulo repartir
        List<Carta> cartas = new ArrayList<>();
        for(int i = 0; i<10;i++){
            cartas.add(españolas.getSigCarta());
        }

        Jugador adrian = new Jugador("Adrian",cartas);
        Jugador ivo = new Jugador("ivo",cartas);
        Jugador juancito = new Jugador("123",cartas);

        assertNotEquals(adrian.getNombre(), ivo.getNombre());
        assertEquals("Adrian",adrian.getNombre());

        assertNotEquals("juancito",juancito.getNombre());
        assertEquals("123",juancito.getNombre());

        assertEquals("ivo",ivo.getNombre());
    }

    @Test
    @DisplayName("test Obtener cartas y getCantidad")
    public void testGetCantidadCartas(){
        //simulo repartir
        List<Carta> cartas = repartirCartaConocida();

        Carta reyCopas= new Carta(Palo.COPAS, Valor.REY);


        Carta sotaOro= new Carta(Palo.ORO, Valor.SOTA);


        Carta sotaEspada= new Carta(Palo.ESPADAS, Valor.SOTA);

        Jugador juancito = new Jugador("juan",cartas);
        //las cartas repartidas son las que tiene el jugador
        assertEquals(cartas,juancito.getCartasDelJugador());

        //la cantidad inicial del test debe ser 4 cartas del jugador
        assertEquals(4,juancito.getCantidadCartas());

        //Pruebo tirar una carta que no tiene, debe tirar un IllegalState y no debe mod la cant cartas
        assertThrows(IllegalArgumentException.class ,() ->juancito.tirarCarta(sotaEspada));
        assertEquals(4,juancito.getCantidadCartas());

        //Pruebo tirar una carta que si tengo, ahora debe tener 3 cartas
        juancito.tirarCarta(sotaOro);
        assertEquals(3,juancito.getCantidadCartas());
    }

    @Test
    @DisplayName("tirar carta con mano vacia")
    public void testManoVacia(){
        List<Carta> cart = new ArrayList<>();
        Jugador fulano = new Jugador("fulano",cart);

        assertEquals(0,fulano.getCantidadCartas());

        Carta reyCopas= new Carta(Palo.COPAS, Valor.REY);
        assertThrows(IllegalArgumentException.class,()->fulano.tirarCarta(reyCopas));

    }
    @Test
    @DisplayName("test ir tirando cartas")
    public void testTirarCartas(){
        int i = 4;
        List<Carta> cartas = repartirCartaConocida();
        Jugador fulano = new Jugador("fulano",cartas);

        List<Carta> cartaJugador = fulano.getCartasDelJugador();

        for(Carta c: cartaJugador ){
            assertEquals(i,fulano.getCantidadCartas());
            fulano.tirarCarta(c);
            i--;
        }
        assertEquals(0,fulano.getCantidadCartas());
     }
     @Test
     @DisplayName("test modificar la copia de las cartas no mod la del jugador")
    public void testModCopia(){
         List<Carta> cartas = repartirCartaConocida();
         Jugador fulano = new Jugador("fulano",cartas);

         List<Carta> cartaJugador = fulano.getCartasDelJugador();

         cartaJugador.clear();
         assertNotEquals(cartaJugador.size(),fulano.getCantidadCartas());

     }

     @Test
     @DisplayName("eliminar 2 veces la misma carta da error")
    public void testEliminarDosVecesLaMismaCarta(){
         List<Carta> cartas = repartirCartaConocida();
         Jugador fulano = new Jugador("fulano",cartas);
         List<Carta> cartaJugador = fulano.getCartasDelJugador();

         Carta anchoEspada  = cartaJugador.getFirst();

         fulano.tirarCarta(anchoEspada);
        assertEquals(3,fulano.getCantidadCartas());
         assertThrows(IllegalArgumentException.class, ()-> fulano.tirarCarta(anchoEspada) );
         assertEquals(3,fulano.getCantidadCartas());
    }

    @Test
    @DisplayName("test Sumar puntos")
    public void testSumarPuntos(){

        List<Carta> cartas = repartirCartaConocida();
        Jugador fulano = new Jugador("fulano",cartas);

        assertEquals(0,fulano.getCantidadPuntos());
        fulano.sumarCantidadPuntos(10);

        assertEquals(10,fulano.getCantidadPuntos());
        assertThrows(IllegalArgumentException.class,()->fulano.sumarCantidadPuntos(-1));

        fulano.sumarCantidadPuntos(5);
        assertEquals(15,fulano.getCantidadPuntos());

    }
    @Test
    @DisplayName("test agregar cartas")
    public void testAgregarCartas(){

        List<Carta> cartas = repartirCartaConocida();
        Jugador fulano = new Jugador("fulano",cartas);

        Carta nuevaCarta = new Carta(Palo.ORO,Valor.REY);

        assertEquals(4,fulano.getCantidadCartas());

        fulano.agregarCarta(nuevaCarta);
        assertEquals(5,fulano.getCantidadCartas());

        fulano.tirarCarta(nuevaCarta);
        assertEquals(4,fulano.getCantidadCartas());
    }

    @Test
    @DisplayName("test Cante de los 4 palos")
    public void testTodosLosCantes(){

        Set<Palo> palosEsperados = new HashSet<>();
        for (Palo palo: Palo.values()){
            palosEsperados.add(palo);
        }
        List<Carta> cartas = new ArrayList<>();

        Carta reyEspada =  new Carta(Palo.ESPADAS,Valor.REY);
        cartas.add(reyEspada);
        Carta caballoEspada =  new Carta(Palo.ESPADAS,Valor.CABALLO);
        cartas.add(caballoEspada);
        Carta reyOro =  new Carta(Palo.ORO,Valor.REY);
        cartas.add(reyOro);
        Carta caballoOro =  new Carta(Palo.ORO,Valor.CABALLO);
        cartas.add(caballoOro);
        Carta reyCopas =  new Carta(Palo.COPAS,Valor.REY);
        cartas.add(reyCopas);
        Carta caballoCopas =  new Carta(Palo.COPAS,Valor.CABALLO);
        cartas.add(caballoCopas);
        Carta reyBastos =  new Carta(Palo.BASTOS,Valor.REY);
        cartas.add(reyBastos);
        Carta caballoBastos =  new Carta(Palo.BASTOS,Valor.CABALLO);
        cartas.add(caballoBastos);

        Jugador fulano = new Jugador("fulano",cartas);

        assertEquals(palosEsperados,fulano.getCantes());
        assertEquals(4,fulano.getCantes().size());
    }
    @Test
    @DisplayName("test jugador sin cante")
    public void testCanteVacio(){
        List<Carta> cartas = repartirCartaConocida();
        Jugador fulano = new Jugador("fulnao",cartas);

        assertTrue(fulano.getCantes().isEmpty());

    }
    @Test
    @DisplayName("test jugador con 2 cantes")
    public void testDosCantes(){

        Set<Palo> cantesEsperados = new HashSet<>();
        cantesEsperados.add(Palo.ORO);
        cantesEsperados.add(Palo.ESPADAS);
        Carta reyEspada =  new Carta(Palo.ESPADAS,Valor.REY);
        Carta caballoEspada =  new Carta(Palo.ESPADAS,Valor.CABALLO);

        Carta reyOro =  new Carta(Palo.ORO,Valor.REY);
        Carta caballoOro =  new Carta(Palo.ORO,Valor.CABALLO);

        List<Carta> cartas  = new ArrayList<>();
        Jugador  fulano = new Jugador("fulnao",cartas);
        fulano.agregarCarta(reyEspada);
        fulano.agregarCarta(reyOro);
        fulano.agregarCarta(caballoEspada);
        fulano.agregarCarta(caballoOro);

        assertFalse(fulano.getCantes().isEmpty());
        assertEquals(2,fulano.getCantes().size());
        assertEquals(cantesEsperados,fulano.getCantes());

    }

    @Test
    @DisplayName("test tute Reyes")
    public void testTuteRey(){
        Set<Valor> valorEsperado = new HashSet<>();
        valorEsperado.add(Valor.REY);

        List<Carta> cartas = new ArrayList<>();
        for (Palo palo: Palo.values()){
            cartas.add(new Carta(palo,Valor.REY));
        }
        Jugador  fulano = new Jugador("fulnao",cartas);

        assertTrue(fulano.getTute().contains(Valor.REY));
    }
}