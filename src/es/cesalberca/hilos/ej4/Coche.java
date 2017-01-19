package es.cesalberca.hilos.ej4;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Cesar
 */
public class Coche extends Thread {

    private String id;
    private boolean aparcado;
    private Semaphore semaforo;

    private static ArrayList<Coche> cochesAparcados = new ArrayList<>();
    private static ArrayList<Coche> cochesNoAparcados = new ArrayList<>();

    public Coche(String id, Semaphore semaforo) {
        this.id = id;
        this.semaforo = semaforo;
        this.aparcado = false;
    }

    @Override
    public void run() {
        boolean plazaConseguida;
        boolean decideAbandonarAparcamiento;
        int maxIteraciones = 10;
        int iteracionActual = 0;

        // El proceso se debe repetir n veces
        while (iteracionActual < maxIteraciones) {
            try {
                // Tenemos que comprobar que este coche no está aparcado ya. Un coche no se puede aparcar si ya está aparcado
                // También tenemos que comprobar que hay plazas disponibles.
                if (!this.aparcado && this.semaforo.availablePermits() != 0) {
                    System.out.println(String.format("Coche %s buscando plaza de las %s plazas libres", this.id, this.semaforo.availablePermits()));
                    // Hay un 50% de posibilidades de conseguir plaza
                    plazaConseguida = ThreadLocalRandom.current().nextDouble(1) >= 0.5;
                    // Debemos comprobar otra vez que hay plazas disponibles, ya que otro coche se nos puede haber adelantado. Cosas de hilos.
                    if (plazaConseguida && this.semaforo.availablePermits() != 0) {
                        this.aparcado = true;
                        Coche.cochesAparcados.add(this);
                        Coche.cochesNoAparcados.remove(this);
                        this.semaforo.acquire();
                        System.out.println(String.format("Coche %s conseguida una plaza de las %d plazas disponibles", this.id, this.semaforo.availablePermits()));
                        // Aquí este coche debería parar su hilo
                    } else {
                        System.out.println(String.format("Plaza no conseguida en esta iteración para el coche %s", this.id));
                    }
                } else {
                    // El coche a veces decide salir del aparcamiento. Hay un 25% de posibilidades de que esto ocurra.
                    decideAbandonarAparcamiento = ThreadLocalRandom.current().nextDouble(1) >= 0.25;

                    if (decideAbandonarAparcamiento) {
                        this.aparcado = false;
                        Coche.cochesAparcados.remove(this);
                        Coche.cochesNoAparcados.add(this);
                        this.semaforo.release();
                        System.out.println(String.format("Coche %s saliendo del aparcamiento...", this.id));
                    } else {
                        // Pasa un tiempo hasta que vuelve a decidir
//                        Thread.sleep(1000);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                iteracionActual++;
            }
        }
    }

    public static ArrayList<Coche> getCochesAparcados() {
        return cochesAparcados;
    }

    public static ArrayList<Coche> getCochesNoAparcados() {
        return cochesNoAparcados;
    }

    @Override
    public String toString() {
        return "Coche{" +
                "id='" + id + '\'' +
                ", aparcado=" + aparcado +
                '}';
    }
}
