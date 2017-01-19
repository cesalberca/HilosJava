package es.cesalberca.hilos.ej4;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Created by Cesar
 */
public class Coche extends Thread {

    private String id;
    private boolean aparcado;
    private Semaphore semaforo;

    private static ArrayList<Coche> cochesAparcados;

    public Coche(String id, Semaphore semaforo) {
        this.id = id;
        this.aparcado = false;
        this.semaforo = semaforo;

        if (cochesAparcados == null)
            cochesAparcados = new ArrayList<>();
    }

    @Override
    public void run() {
        while (!aparcado && semaforo.availablePermits() != 0) {
            try {
                System.out.println(String.format("Coche %s buscando plaza de las %s plazas libres", this.id, semaforo.availablePermits()));

                // Tras buscar un poco encuentra plaza
                Thread.sleep(1000 );
                boolean plazaConseguida = Math.random() >= 0.5;

                if (plazaConseguida && !aparcado) {
                    semaforo.acquire();
                    this.aparcado = true;
                    System.out.println(String.format("Coche %s conseguida una plaza de las %s plazas restantes", this.id, semaforo.availablePermits()));
                    cochesAparcados.add(this);
                } else {
                    System.out.println(String.format("Plaza no conseguida en esta iteración para el coche %s", this.id));
                }

                Thread.sleep(1000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!aparcado)
            System.out.println(String.format("No hay plazas libres para coche %s, saliendo del aparcamiento...", this.id));
    }

    public static ArrayList<Coche> getCochesAparcados() {
        return cochesAparcados;
    }

    @Override
    public String toString() {
        return "Coche{" +
                "id='" + id + '\'' +
                ", aparcado=" + aparcado +
                '}';
    }
}
