package es.cesalberca.hilos.ej2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cesar
 */
public class SuperMarket {
    private List<Cliente> clientes;
    private List<Caja> cajas;

    private int numeroCajas;
    private int numeroClientes;

    public SuperMarket(int numeroCajas, int numeroClientes) {
        this.numeroCajas = numeroCajas;
        this.numeroClientes = numeroClientes;

        clientes = new ArrayList<>();
        cajas = new ArrayList<>();
    }
}
