package ar.edu.unlu.poo.MarchiCastro.tute.test;

import ar.edu.unlu.poo.MarchiCastro.tute.modelo.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MazoTest {

    @Test
    @DisplayName("Creacion de las cuarenta Cartas")
    public void testSeCreanCuarentaCartas(){
        Mazo españolas = new Mazo();
        assertEquals(españolas.getCantCartas(),40);
    }

    @Test
    @DisplayName("Verifico creacion de cartas")
    public void testSeCrearonBien(){
        Mazo españolas = new Mazo();

        Carta anchoEspada = new Carta(Palo.ESPADAS,Valor.ANCHO);
        assertEquals(españolas.existeLaCarta(anchoEspada),true);

        Carta sotaOro = new Carta(Palo.ORO,Valor.SOTA);
        assertEquals(españolas.existeLaCarta(sotaOro),true);

        Carta tresCopas = new Carta(Palo.COPAS,Valor.TRES);
        assertEquals(españolas.existeLaCarta(tresCopas),true);

    }
    @Test
    @DisplayName("Obtencion y eliminacion de carta")
    public void testObtenerCarta(){
        Mazo españolas = new Mazo();
        assertEquals(españolas.getVerSigCarta(),españolas.getSigCarta());
        assertEquals(españolas.getCantCartas(),39);

    }
    @Test
    @DisplayName("mazo vacio")
    public void testMazoVacio(){
        Mazo españolas = new Mazo();
        for(int i = 0;i<40;i++){
            Carta carta = españolas.getSigCarta();
        }
        assertThrows(SInCartasException.class , ()->españolas.getSigCarta());
        assertEquals(0,españolas.getCantCartas());
    }
}
