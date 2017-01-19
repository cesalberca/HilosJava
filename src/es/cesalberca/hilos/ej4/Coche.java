package es.cesalberca.hilos.ej4;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Cesar
 */
public class Coche extends Thread {

    private String id;
    private boolean aparcado;

    private static ArrayList<Coche> coches;

    public Coche(String id) {
        this.id = id;
        this.aparcado = false;


        if (coches == null)
            coches = new ArrayList<>();
    }

    @Override
    public void run() {
        boolean plazaConseguida;
        int maxIteraciones = 100;
        int iteracionActual = 0;

        // El coche debe buscar plaza hasta que o esté aparcado o no queden plazas en el aparcamiento
        while (ParkingGestor.semaforo.availablePermits() != 0 && !this.aparcado && iteracionActual < maxIteraciones) {
            try {
                // Tenemos que comprobar que este coche no está aparcado ya. Un coche no se puede aparcar si ya está aparcado
                if (!this.aparcado) {
                    System.out.println(String.format("Coche %s buscando plaza de las %s plazas libres", this.id, ParkingGestor.semaforo.availablePermits()));
                    // Hay un 50% de posibilidades de conseguir plaza
                    plazaConseguida = ThreadLocalRandom.current().nextDouble(1) >= 0.5;
                    if (plazaConseguida && ParkingGestor.semaforo.availablePermits() != 0) {
                        ParkingGestor.semaforo.acquire();
                        this.aparcado = true;
                        Coche.coches.add(this);
                        System.out.println(String.format("Coche %s conseguida una plaza de las %d plazas disponibles", this.id, ParkingGestor.semaforo.availablePermits()));
                        // Aquí este coche debería parar su hilo
                    } else {
                        System.out.println(String.format("Plaza no conseguida en esta iteración para el coche %s", this.id));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                iteracionActual++;
            }
        }

        if (!aparcado) {
            System.out.println(String.format("No hay plazas libres para coche %s, saliendo del aparcamiento...", this.id));
            coches.add(this);
        }
    }

    public static ArrayList<Coche> getCoches() {
        return coches;
    }

    @Override
    public String toString() {
        return "Coche{" +
                "id='" + id + '\'' +
                ", aparcado=" + aparcado +
                '}';
    }
}
