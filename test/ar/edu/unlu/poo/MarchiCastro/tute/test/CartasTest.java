package ar.edu.unlu.poo.MarchiCastro.tute.test;

import ar.edu.unlu.poo.MarchiCastro.tute.modelo.Carta;
import ar.edu.unlu.poo.MarchiCastro.tute.modelo.Palo;
import ar.edu.unlu.poo.MarchiCastro.tute.modelo.Valor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartasTest {

    @Test
    @DisplayName("Instanciar Cartas")
    public void testInstanciarCarta(){

        Carta Ancho = new Carta(Palo.BASTOS,Valor.ANCHO);
        assertEquals(Ancho.getPuntos(), 11);
        assertEquals(Ancho.getPalo() , Palo.BASTOS);
        assertEquals(Ancho.getValor(), Valor.ANCHO);

        Carta reyEspada = new Carta(Palo.ESPADAS,Valor.REY);
        assertEquals(reyEspada.getPuntos(), 4);
        assertEquals(reyEspada.getPalo() , Palo.ESPADAS);
        assertEquals(reyEspada.getValor(), Valor.REY);

        Carta tresOro = new Carta(Palo.ORO,Valor.TRES);
        assertEquals(tresOro.getPuntos(), 10);
        assertEquals(tresOro.getPalo() , Palo.ORO);
        assertEquals(tresOro.getValor(), Valor.TRES);
    }

    @Test
    @DisplayName("Prueba getPuntos")
    public void testgetPuntuacion(){
        Carta Ancho = new Carta(Palo.BASTOS,Valor.ANCHO);
        assertEquals(Ancho.getPuntos(), 11);

        Carta Rey = new Carta(Palo.BASTOS,Valor.REY);
        assertEquals(Rey.getPuntos(), 4);

        Carta Tres = new Carta(Palo.BASTOS,Valor.TRES);
        assertEquals(Tres.getPuntos(), 10);

        Carta siete = new Carta(Palo.BASTOS,Valor.SIETE);
        assertEquals(siete.getPuntos(), 0);

        Carta cuatro = new Carta(Palo.ORO,Valor.CUATRO);
        assertEquals(cuatro.getPuntos(), 0);
    }
    @Test
    @DisplayName("Prueba getPoder")
    public void testGetPoder(){
        Carta Ancho = new Carta(Palo.BASTOS,Valor.ANCHO);
        assertEquals(10,Ancho.getPoder());

        Carta Rey = new Carta(Palo.BASTOS,Valor.REY);
        assertEquals(8,Rey.getPoder());

        Carta Tres = new Carta(Palo.BASTOS,Valor.TRES);
        assertEquals(9,Tres.getPoder());

        Carta siete = new Carta(Palo.BASTOS,Valor.SIETE);
        assertEquals(5,siete.getPoder());

        Carta cuatro = new Carta(Palo.ORO,Valor.CUATRO);
        assertEquals(2,cuatro.getPoder());
    }
    @Test
    @DisplayName("Prueba de Jerarquia de poder")
    public void testNivelesPoder() {
        Carta ancho = new Carta(Palo.BASTOS, Valor.ANCHO);
        Carta rey = new Carta(Palo.BASTOS, Valor.REY);
        Carta tres = new Carta(Palo.BASTOS, Valor.TRES);
        Carta siete = new Carta(Palo.BASTOS, Valor.SIETE);
        Carta cuatro = new Carta(Palo.ORO, Valor.CUATRO);

        assertTrue(ancho.getPoder()>tres.getPoder());
        assertTrue(ancho.getPoder()>cuatro.getPoder());
        assertTrue(siete.getPoder()>cuatro.getPoder());
        assertTrue(rey.getPoder()>siete.getPoder());
    }
    @Test
    @DisplayName("Comparar Cartas true")
    public void testEqualsTrue(){
        Carta anchoEspada = new Carta(Palo.ESPADAS,Valor.ANCHO);
        Carta anchoEspadaDup = new Carta(Palo.ESPADAS,Valor.ANCHO);
        assertEquals(true,anchoEspada.equals(anchoEspadaDup));
    }
    @Test
    @DisplayName("Comparar Cartas false")
    public void testEqualsFalse(){
        Carta anchoEspada = new Carta(Palo.ESPADAS,Valor.ANCHO);
        Carta reyEspada = new Carta(Palo.ESPADAS,Valor.REY);
        assertEquals(false,anchoEspada.equals(reyEspada));

        Carta reyBasto = new Carta(Palo.BASTOS,Valor.REY);
        assertEquals(false,reyBasto.equals(reyEspada));
        assertEquals(false,reyBasto.equals(null));
        assertEquals(false,reyBasto.equals("helo"));
    }
}