package es.cesalberca.hilos.ej2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by Cesar
 */
public class Caja {
    private Semaphore semaforo;
    private int maxCola;
    private List<Cliente> clientesComprando;

    public Caja(int maxCola) {
        this.maxCola = maxCola;
        semaforo = new Semaphore(this.maxCola);
        clientesComprando = new ArrayList<>();
    }
}
