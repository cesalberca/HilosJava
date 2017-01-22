package es.cesalberca.hilos.ej2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

/**
 * Created by Cesar
 */
public class Caja {
    private int id;
    private Semaphore mutex;
    private List<Cliente> clientesEnCola;

    public Caja(int id) {
        this.id = id;
        mutex = new Semaphore(1);
        clientesEnCola = new ArrayList<>();
    }

    public void addClienteACola(Cliente cliente) throws InterruptedException {
        if (!clientesEnCola.contains(cliente)) {
            clientesEnCola.add(cliente);
            System.out.println(this.toString());
        }
    }

    public void removeCliente(Cliente cliente) {
        if (clientesEnCola.contains(cliente)) {
            clientesEnCola.remove(cliente);
            System.out.println(this.toString());
        }
    }

    public void cobrarSiguienteCliente() throws InterruptedException {
        mutex.acquire();
        Optional<Cliente> proximoCliente = clientesEnCola.stream().findFirst();
        if (proximoCliente.isPresent()) {
            Cliente cliente = proximoCliente.get();
            cliente.pagar();
        }
        mutex.release();
    }

    @Override
    public String toString() {
        return "Caja{" +
                "id=" + id +
                ", clientesEnCola=" + this.clientesEnCola.stream().map(Cliente::toString)
                    .collect(Collectors.joining(", ")) +
                '}';
    }
}
