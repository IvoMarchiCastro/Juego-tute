package ar.edu.unlu.poo.MarchiCastro.tute.modelo;

public enum Obligacion {
    // La baza está vacía: el jugador abre con la carta que quiera
    SALIDA_LIBRE,

    // Tiene el palo de la baza y una carta más alta que la que va ganando
    MONTAR,

    // Tiene el palo de la baza pero no puede superar (o ya hay un triunfo ganando):
    // tiene que tirar una carta del palo
    ASISTIR,

    // No tiene el palo, tiene triunfo y no hay triunfo en la mesa: tiene que tirar triunfo
    FALLAR,

    // No tiene el palo, hay triunfo en la mesa y tiene uno más alto: tiene que superarlo
    PISAR,

    // No puede cumplir ninguna obligación (sin palo ni triunfo, o contrafallo):
    // tira cualquier carta y no puede ganar
    DESCARTE
}