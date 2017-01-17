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
//        int iteracion = 0;

        while (!aparcado && semaforo.availablePermits() != 0) {
            try {
                System.out.println(String.format("Coche %s buscando plaza de las %s plazas libres", this.id, semaforo.availablePermits()));

                Thread.sleep(1000 );

                // Tras buscar un poco encuentra plaza
                this.aparcado = true;
                semaforo.acquire();

                System.out.println(String.format("Coche %s conseguido una plaza, Plazas restantes: %s", this.id, semaforo.availablePermits()));
                // Determinar qué plaza ha conseguido
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            iteracion++;
//            System.out.println(String.format("Iteración número: %s", iteracion));
        }
        System.out.println(String.format("No hay plazas libres para coche %s, saliendo del aparcamiento...", this.id));
    }
}
