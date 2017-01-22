package es.cesalberca.hilos.ej2;

/**
 * Created by César
 */
public class Cliente implements Runnable {
    private static int MAX_ITERACIONES = 1;
    private int id;

    public Cliente(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        int iteracionActual = 0;
        while (iteracionActual < MAX_ITERACIONES) {
//            System.out.println(String.format("%d comprando...", this.id));
            buscarCaja();
            iteracionActual++;
        }
    }

    public void pagar() {
        System.out.println(String.format("Cliente %d está pagando su compra", this.id));
    }

    private void buscarCaja() {
        System.out.println(String.format("Cliente %d está buscando caja", this.id));
        SuperMarket.asignarClienteACaja(this);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                '}';
    }
}
