package es.cesalberca.hilos.ej4;

import java.util.concurrent.Semaphore;

/**
 * Created by Cesar
 */
public class Coche extends Thread {

    private String id;
    private Semaphore semaforo;
    private boolean aparcado;

    public Coche(String id, Semaphore semaforo) {
        this.id = id;
        this.semaforo = semaforo;
        this.aparcado = false;
    }

    @Override
    public void run() {
        while (!aparcado && semaforo.availablePermits() != 0) {
            try {
                System.out.println(String.format("Coche %s buscando plaza de las %s plazas libres", this.id, semaforo.availablePermits()));

                Thread.sleep(1000 );
                // Tras buscar un poco encuentra plaza
                boolean esPlazaConseguida = Math.random() >= 0.5;
                if (esPlazaConseguida) {
                    semaforo.acquire();
                    this.aparcado = true;
                    System.out.println(String.format("Coche %s conseguido una plaza de las %s plazas restantes", this.id, semaforo.availablePermits()));
                } else {
                    System.out.println(String.format("Plaza no conseguida en esta iteración para el coche %s", this.id));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!aparcado)
            System.out.println(String.format("No hay plazas libres para coche %s, saliendo del aparcamiento...", this.id));
    }
}
