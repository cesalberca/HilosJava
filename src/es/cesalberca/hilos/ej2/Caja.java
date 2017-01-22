package es.cesalberca.hilos.ej2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

/**
 * Created by Cesar
 */
public class Caja {
    private Semaphore mutex;
    private List<Cliente> clientesComprando;

    public Caja() {
        mutex = new Semaphore(1);
        clientesComprando = new ArrayList<>();
    }

    public void addCliente(Cliente cliente) throws InterruptedException {
        if (!clientesComprando.contains(cliente)) {
            clientesComprando.add(cliente);
            mutex.acquire();
        }
    }

    public void removeCliente(Cliente cliente) {
        if (clientesComprando.contains(cliente)) {
            clientesComprando.remove(cliente);
            mutex.release();
        }
    }

    @Override
    public String toString() {
        return "Caja{" +
                "clientesComprando=" + this.clientesComprando.stream().map(Cliente::toString)
                    .collect(Collectors.joining(", ")) +
                '}';
    }
}
