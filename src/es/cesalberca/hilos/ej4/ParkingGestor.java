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

    public Semaphore semaforo;

    public ParkingGestor(int maxPlazas, int maxCoches) {
        this.maxPlazas = maxPlazas;
        this.maxCoches = maxCoches;

        ParkingGestor.plazasLibres = maxPlazas;

        semaforo = new Semaphore(this.maxPlazas);

        ArrayList<Coche> coches = new ArrayList<>();

        // Añadimos a nuestro arraylist todos los coches, le pasamos por referencia el semáforo
        for (int i = 0; i < maxCoches; i++) {
            coches.add(new Coche(String.valueOf(i), semaforo));
        }

        System.out.println("Comenzando la ejecución del programa");

        // Iniciamos la ejecución de los hilos
        coches.forEach(Thread::start);

        // Esperamos a la ejecución los hilos
        coches.forEach((coche -> {
            try {
//                coche.start();
                coche.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        System.out.println("Fin de la ejecución del progama");

        System.out.println("Coches aparcados:");
        for (Coche coche : Coche.getCochesAparcados()) {
            System.out.println(coche);
        }

        System.out.println("Coches no aparcados:");
        for (Coche coche : Coche.getCochesNoAparcados()) {
            System.out.println(coche);
        }

        System.out.println(String.format("Total coches: %d", Coche.getCochesAparcados().size() + Coche.getCochesNoAparcados().size()));

    }
}
