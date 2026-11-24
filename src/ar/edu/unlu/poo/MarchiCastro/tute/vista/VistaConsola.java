
package ar.edu.unlu.poo.MarchiCastro.tute.vista;

import ar.edu.unlu.poo.MarchiCastro.tute.controlador.Controlador;
import ar.edu.unlu.poo.MarchiCastro.tute.modelo.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class VistaConsola implements IVista {

    private static final int CANTIDAD_JUGADORES = 4;

    private final Controlador controlador;
    private final Scanner scanner;

    // estado propio de la vista (solo para mostrar)
    private List<IJugador> jugadores = new ArrayList<>();
    private List<ICarta> manoActual = new ArrayList<>();
    private final List<String> mesa = new ArrayList<>();
    private IJugador jugadorEnTurno;
    private boolean primerTurno = true;
    private boolean juegoTerminado = false;

    public VistaConsola(Controlador controlador) {
        this.controlador = controlador;
        this.scanner = new Scanner(System.in);
        this.controlador.setVista(this);
    }

    @Override
    public void iniciar() {
        System.out.println("===============================");
        System.out.println("            T U T E            ");
        System.out.println("===============================");

        // 1. registro: el controlador avisa por mostrarJugadores y eso actualiza la lista
        while (jugadores.size() < CANTIDAD_JUGADORES) {
            System.out.print("Nombre del jugador " + (jugadores.size() + 1) + ": ");
            controlador.agregarJugador(scanner.nextLine());
        }

        // 2. reparto: los eventos del modelo muestran el primer turno y la mano
        controlador.iniciarJuego();

        // 3. bucle de turnos hasta que el modelo avise el fin del juego
        while (!juegoTerminado) {
            procesarOpcion(pedirOpcion());
        }
        System.out.println("Gracias por jugar.");
    }

    private String pedirOpcion() {
        System.out.println("\n[1: Jugar carta | 2: Cantar | 3: Declarar tute | 4: Ver mi mano]");
        System.out.print("Opcion: ");
        return scanner.nextLine();//.trim();
    }

    private void procesarOpcion(String opcion) {
        switch (opcion) {
            case "1":
                jugarCarta();
                break;
            case "2":
                cantar();
                break;
            case "3":
                declararTute();
                break;
            case "4":
                mostrarMano(manoActual);
                break;
            default:
                mostrarError("Opcion no reconocida");
                break;
        }
    }

    private void jugarCarta() {
        Integer numero = pedirNumero("Numero de carta: ", manoActual.size());
        if (numero == null) {
            return;
        }
        controlador.jugarCarta(manoActual.get(numero - 1));
    }

    private void cantar() {
        List<Palo> palos = new ArrayList<>(jugadorEnTurno.getCantes());
        if (palos.isEmpty()) {
            mostrarError("No tenes rey y caballo de ningun palo");
            return;
        }
        for (int i = 0; i < palos.size(); i++) {
            System.out.println("  " + (i + 1) + ") " + palos.get(i).toString().toLowerCase());
        }
        Integer numero = pedirNumero("Palo a cantar: ", palos.size());
        if (numero == null) {
            return;
        }
        controlador.cantar(palos.get(numero - 1));
    }

    private void declararTute() {
        List<Valor> tutes = new ArrayList<>(jugadorEnTurno.getTute());
        if (tutes.isEmpty()) {
            mostrarError("No tenes los 4 reyes ni los 4 caballos");
            return;
        }
        for (int i = 0; i < tutes.size(); i++) {
            System.out.println("  " + (i + 1) + ") 4 " + tutes.get(i).toString().toLowerCase());
        }
        Integer numero = pedirNumero("Tute de: ", tutes.size());
        if (numero == null) {
            return;
        }
        controlador.declararTute(tutes.get(numero - 1));
    }

    // devuelve un numero entre 1 y max, o null si la entrada no es valida
    private Integer pedirNumero(String mensaje, int max) {
        System.out.print(mensaje);
        String linea = scanner.nextLine().trim();
        try {
            int numero = Integer.parseInt(linea);
            if (numero < 1 || numero > max) {
                mostrarError("Ingrese un numero entre 1 y " + max);
                return null;
            }
            return numero;
        } catch (NumberFormatException e) {
            mostrarError("Ingrese un numero valido");
            return null;
        }
    }

    @Override
    public void mostrarJugadores(List<IJugador> jugadores) {
        // el controlador tambien llama a esto al terminar cada baza: solo imprimo si cambio la lista
        boolean cambio = jugadores.size() != this.jugadores.size();
        this.jugadores = new ArrayList<>(jugadores);
        if (!cambio) {
            return;
        }
        System.out.println("\nJugadores:");
        for (int i = 0; i < this.jugadores.size(); i++) {
            System.out.println("  " + (i + 1) + ") " + this.jugadores.get(i).getNombre());
        }
        if (this.jugadores.size() == CANTIDAD_JUGADORES) {
            System.out.println("Pareja A: " + nombresPareja(0));
            System.out.println("Pareja B: " + nombresPareja(1));
        }
    }

    @Override
    public void mostrarTurno(IJugador jugador) {
        // pausa para que se lea lo que paso antes de limpiar la pantalla
        if (!primerTurno) {
            System.out.println("\n--- Presiona Enter para pasar el turno ---");
            scanner.nextLine();
        }
        primerTurno = false;
        this.jugadorEnTurno = jugador;

        limpiarPantalla();
        System.out.println("===== Turno de " + jugador.getNombre()
                + " (jugador " + numeroDeAsiento(jugador) + ") =====");
        System.out.println("Pasale la consola a " + jugador.getNombre() + " y presiona Enter para ver tu mano");
        scanner.nextLine();

        System.out.println("Triunfo: " + controlador.getPaloTriunfo().toString().toLowerCase());
        if (mesa.isEmpty()) {
            System.out.println("Mesa: vacia (abris vos)");
        } else {
            System.out.println("Mesa:");
            for (String linea : mesa) {
                System.out.println("  " + linea);
            }
        }
    }

    @Override
    public void mostrarMano(List<ICarta> mano) {
        this.manoActual = new ArrayList<>(mano);
        List<ICarta> jugables = controlador.getCartasJugables();

        System.out.println("\nTu mano (* = podes tirarla):");
        for (int i = 0; i < manoActual.size(); i++) {
            ICarta carta = manoActual.get(i);
            String marca = jugables.contains(carta) ? "*" : " ";
            System.out.println("  " + (i + 1) + ") " + marca + " " + descripcion(carta));
        }
        if (jugadorEnTurno != null) {
            if (!jugadorEnTurno.getCantes().isEmpty()) {
                System.out.println("Tenes cante en: " + jugadorEnTurno.getCantes());
            }
            if (!jugadorEnTurno.getTute().isEmpty()) {
                System.out.println("Tenes tute de: " + jugadorEnTurno.getTute());
            }
        }
    }

    @Override
    public void mostrarCartaTirada(IJugador jugador, ICarta carta) {
        String linea = jugador.getNombre() + " tiro " + descripcion(carta);
        mesa.add(linea);
        System.out.println("\n" + linea);
    }

    @Override
    public void mostrarFinBaza(IJugador ganador) {
        System.out.println(">>> Se lleva la baza " + ganador.getNombre() + " <<<");
        mesa.clear();
    }

    @Override
    public void mostrarCante(IJugador jugador, Palo palo) {
        String extra = (palo == controlador.getPaloTriunfo()) ? " (palo de triunfo)" : "";
        System.out.println("\n*** " + jugador.getNombre() + " canta en "
                + palo.toString().toLowerCase() + extra + " ***");
    }

    @Override
    public void mostrarCanteTute(IJugador jugador) {
        System.out.println("\n*** TUTE! " + jugador.getNombre() + " declara tute ***");
    }

    @Override
    public void mostrarFinJuego(List<IJugador> ganadores) {
        juegoTerminado = true;
        System.out.println("\n===============================");
        System.out.println("        FIN DEL JUEGO");
        System.out.println("===============================");
        System.out.println("Pareja A (" + nombresPareja(0) + "): " + controlador.getPuntosPareja(0) + " puntos");
        System.out.println("Pareja B (" + nombresPareja(1) + "): " + controlador.getPuntosPareja(1) + " puntos");
        if (ganadores.isEmpty()) {
            System.out.println("Resultado: EMPATE");
        } else {
            StringBuilder nombres = new StringBuilder();
            for (IJugador g : ganadores) {
                if (nombres.length() > 0) {
                    nombres.append(" y ");
                }
                nombres.append(g.getNombre());
            }
            System.out.println("Ganan: " + nombres);
        }
    }

    @Override
    public void mostrarError(String mensaje) {
        System.out.println(" [!] ERROR: " + mensaje);
    }

    private String descripcion(ICarta carta) {
        return carta.getValor().toString().toLowerCase() + " de "
                + carta.getPalo().toString().toLowerCase();
    }

    private int numeroDeAsiento(IJugador jugador) {
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getId() == jugador.getId()) {
                return i + 1;
            }
        }
        return 0;
    }

    // pareja 0 = jugadores 1 y 3, pareja 1 = jugadores 2 y 4
    private String nombresPareja(int pareja) {
        if (jugadores.size() != CANTIDAD_JUGADORES) {
            return "";
        }
        return jugadores.get(pareja).getNombre() + " y " + jugadores.get(pareja + 2).getNombre();
    }

    private void limpiarPantalla() {
        for (int i = 0; i < 5; i++) {
            System.out.println();
        }
    }
}