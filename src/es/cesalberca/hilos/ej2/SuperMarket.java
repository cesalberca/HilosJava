package es.cesalberca.hilos.ej2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cesar
 */
public class SuperMarket {
    private Cliente[] clientes;
    private Caja[] cajas;

    private int numeroCajas;
    private int numeroClientes;

    public SuperMarket(int numeroCajas, int numeroClientes) {
        this.numeroCajas = numeroCajas;
        this.numeroClientes = numeroClientes;

        clientes = new Cliente[numeroClientes];
        cajas = new Caja[numeroCajas];

        iniciarCajas();
        iniciarClientes();
    }

    private void iniciarCajas() {
        for (int i = 0; i < cajas.length; i++) {
            cajas[i] = new Caja(5);
        }
    }

    private void iniciarClientes() {
        System.out.println("Iniciando clientes...");
        Cliente cliente;
        Thread t;
        for (int i = 0; i < clientes.length; i++) {
            cliente = new Cliente(i);
            t = new Thread(cliente);
            clientes[i] = cliente;
            t.start();
        }
    }
}
