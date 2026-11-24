package ar.edu.unlu.poo.MarchiCastro.tute.modelo;

import java.util.List;
import java.util.Set;

public interface IJugador {
    int getId();
    String getNombre();
    int getCantidadPuntos();
    int getCantidadCartas();
    List<ICarta> getMano();
    Set<Palo> getCantes();
    Set<Valor> getTute();
}
