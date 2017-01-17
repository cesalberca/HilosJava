package es.cesalberca.hilos.ej4;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Created by Cesar
 */
public class ParkingGestor {
    private static int plazasLibres;

    private int maxPlazas;
    private int maxCoches;
    private int cochesEnParking;

    private static Semaphore semaforo;

    public ParkingGestor(int maxPlazas, int maxCoches) {
        this.maxPlazas = maxPlazas;
        this.maxCoches = maxCoches;

        this.cochesEnParking = 0;
        ParkingGestor.plazasLibres = maxPlazas;

        semaforo = new Semaphore(this.maxPlazas);

        ArrayList<Coche> coches = new ArrayList<>();

        // Añadimos a nuestro arraylist todos los coches
        for (int i = 0; i < maxCoches; i++) {
            coches.add(new Coche(String.valueOf(i), semaforo));
        }

        // Iniciamos los hilos
        coches.forEach(Coche::start);
    }

    public static int getPlazasLibres() {
        return plazasLibres;
    }

    public static void sumarPlazasLibres() {
        ParkingGestor.plazasLibres++;
    }

    public static void restarPlazasLibres() {
        ParkingGestor.plazasLibres--;
    }
}
