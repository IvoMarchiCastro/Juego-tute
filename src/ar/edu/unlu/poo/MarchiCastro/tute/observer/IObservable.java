package ar.edu.unlu.poo.MarchiCastro.tute.observer;

public interface IObservable {
    void agregarObservador(IObservador o);
    void removerObservador(IObservador o);
    void notificarObservadores(Object evento);
}

