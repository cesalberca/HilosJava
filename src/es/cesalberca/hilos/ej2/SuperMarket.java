package es.cesalberca.hilos.ej2;

/**
 * Created by Cesar
 */
public class SuperMarket {
    private static Cliente[] clientes;
    private static Caja[] cajas;

    private int numeroCajas;
    private int numeroClientes;

    public SuperMarket(int numeroCajas, int numeroClientes) {
        this.numeroCajas = numeroCajas;
        this.numeroClientes = numeroClientes;

        clientes = new Cliente[this.numeroClientes];
        cajas = new Caja[this.numeroCajas];

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
        for (int i = 0; i < 1; i++) {
            cliente = new Cliente(i);
            t = new Thread(cliente);
            clientes[i] = cliente;
            t.start();
        }
    }

    public static void asignarClienteACaja(Cliente cliente) {
        try {
            cajas[0].addClienteACola(cliente);
            cajas[0].cobrarSiguienteCliente();
            cajas[0].removeCliente(cliente);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
