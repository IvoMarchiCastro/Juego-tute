package ar.edu.unlu.poo.MarchiCastro.tute.controlador;

import ar.edu.unlu.poo.MarchiCastro.tute.modelo.*;
import ar.edu.unlu.poo.MarchiCastro.tute.observer.IObservable;
import ar.edu.unlu.poo.MarchiCastro.tute.observer.IObservador;
import ar.edu.unlu.poo.MarchiCastro.tute.vista.IVista;

import java.util.List;

public class Controlador implements IObservador {
    private IVista vista;
    private final Juego juego;
    //private IJugador jugador;
    private boolean finMostrado = false;

    public Controlador(Juego juego) {
        if (juego == null) throw new IllegalArgumentException("el juego no existe");
        this.juego = juego;
        this.juego.agregarObservador(this);
    }

    public void setVista(IVista vista) { this.vista = vista; }


    public void agregarJugador(String nombre) {
        try {  juego.agregarJugador(nombre); }
        catch (RuntimeException ex) { error(ex); }
    }
    public void iniciarJuego() {
        finMostrado = false;
        try { juego.iniciarJuego(); } catch (RuntimeException ex) { error(ex); }
    }
    public void jugarCarta(ICarta carta) {
        try { juego.jugarCarta(juego.getJugadorEnTurno(), carta); } catch (RuntimeException ex) { error(ex); }
    }
    public void cantar(Palo palo) {
        try {
            if (!juego.cantar(juego.getJugadorEnTurno(), palo)) vista.mostrarError("No podés cantar ese palo ahora");
        } catch (RuntimeException ex) { error(ex); }
    }
    public void declararTute(Valor valor) {
        try {
            if (!juego.declacarTute(juego.getJugadorEnTurno(), valor)) {
                vista.mostrarError("No podés declarar tute ahora");
            }
        } catch (RuntimeException ex) {
            error(ex);
        }
    }
    public Palo getPaloTriunfo(){
        return  juego.getPaloTriunfo();
    }

    public int getPuntosPareja(int pareja){
        return juego.getPuntosPareja(pareja);
    }

    public List<ICarta> getCartasJugables() { return juego.getCartasJugables(juego.getJugadorEnTurno()); }


    // ---- eventos del modelo ----
    @Override
    public void actualizar(IObservable observable, Object e) {
        if (vista == null || !(e instanceof Eventos evento)) return;
        switch (evento) {
            case AGREGAR_JUGADOR:
                vista.mostrarJugadores(juego.getListaJugadores());
                break;
            case INICIAR_EL_JUEGO :
                vista.mostrarTurno(juego.getJugadorEnTurno());
                vista.mostrarMano(juego.getJugadorEnTurno().getMano());
                break;
            case TIRAR_CARTA :
                vista.mostrarCartaTirada(juego.getJugadorEnTurno(),juego.getCartaTirada(juego.getJugadorEnTurno()));
                break;
            case CAMBIO_TURNO:
                vista.mostrarTurno(juego.getJugadorEnTurno());
                vista.mostrarMano(juego.getJugadorEnTurno().getMano());
                break;
            case CANTAR:
                vista.mostrarCante(juego.getJugadorEnTurno(),juego.getCartaTirada(juego.getJugadorEnTurno()).getPalo());
                break;
            case FIN_BAZA :
                vista.mostrarFinBaza(juego.getGanadorUltimaBaza());
                vista.mostrarJugadores(juego.getListaJugadores());
                vista.mostrarTurno(juego.getJugadorEnTurno());
                vista.mostrarMano(juego.getJugadorEnTurno().getMano());
             break;
            case CANTAR_TUTE, TERMINAR_EL_JUEGO :
                if (!finMostrado){
                    finMostrado = true;
                    vista.mostrarCanteTute(juego.getJugadorEnTurno());
                    vista.mostrarFinJuego(juego.getGanadores());
                }
                break;
        }
    }
    private void error(RuntimeException ex) {
        if (vista != null) vista.mostrarError(ex.getMessage());
    }
}