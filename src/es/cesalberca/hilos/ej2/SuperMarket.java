package es.cesalberca.hilos.ej2;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Cesar
 */
public class SuperMarket {
    private static Cliente[] clientes;
    private static Caja[] cajas;

    private static int numeroCajas;
    private static int numeroClientes;

    public SuperMarket(int numeroCajas, int numeroClientes) {
        SuperMarket.numeroCajas = numeroCajas;
        SuperMarket.numeroClientes = numeroClientes;

        clientes = new Cliente[SuperMarket.numeroClientes];
        cajas = new Caja[SuperMarket.numeroCajas];

        iniciarCajas();
        iniciarClientes();
    }

    private void iniciarCajas() {
        for (int i = 0; i < cajas.length; i++) {
            cajas[i] = new Caja(i);
        }
    }

    private void iniciarClientes() {
        System.out.println("Iniciando clientes...");
        Cliente cliente;
        Thread t;
        for (int i = 0; i < SuperMarket.numeroClientes; i++) {
            cliente = new Cliente(i);
            t = new Thread(cliente);
            clientes[i] = cliente;
            t.start();
        }
    }

    public static void asignarClienteACaja(Cliente cliente) {
        try {
            Caja cajaElegida = cajas[ThreadLocalRandom.current().nextInt(numeroCajas)];
            System.out.println("Cliente " + cliente.getId() + " asignado a caja " + cajaElegida.getId());
            cajaElegida.addClienteACola(cliente);
            cajaElegida.cobrarSiguienteCliente();
            cajaElegida.removeCliente(cliente);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
