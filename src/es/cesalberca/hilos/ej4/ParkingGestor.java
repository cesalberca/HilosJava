package es.cesalberca.hilos.ej4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Created by Cesar
 */
public class ParkingGestor {
    private static int maxPlazas;
    private Semaphore semaforo;
    private static Plaza[] plazas;

    public ParkingGestor(int maxPlazas, int maxCoches) {
        ParkingGestor.maxPlazas = maxPlazas;

        semaforo = new Semaphore(maxPlazas);

        ArrayList<Coche> coches = new ArrayList<>();
        plazas = new Plaza[maxPlazas];

        // Añadimos a nuestro arraylist todos los coches, le pasamos por referencia el semáforo
        for (int i = 0; i < maxCoches; i++) {
            coches.add(new Coche(String.valueOf(i), semaforo));
        }

        for (int i = 0; i < plazas.length; i++) {
            plazas[i] = new Plaza(i, false);
        }

        System.out.println("Comenzando la ejecución del programa");

        // Iniciamos la ejecución de los hilos
        coches.forEach(Thread::start);

        // Esperamos a la ejecución los hilos
        coches.forEach((coche -> {
            try {
                coche.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        System.out.println("Fin de la ejecución del progama");
        List<Coche> todosCoches = Coche.getCoches();

        System.out.println("Todos los coches:");
        todosCoches.forEach(System.out::println);

        List<Coche> cochesAparcados = todosCoches
                .stream()
                .filter(Coche::isAparcado)
                .collect(Collectors.toList());

        System.out.println("Coche aparcados:");
        cochesAparcados.forEach(System.out::println);

        List<Coche> cochesNoAparcados = todosCoches
                .stream()
                .filter(coche -> !coche.isAparcado())
                .collect(Collectors.toList());

        System.out.println("Coches no aparcados:");
        cochesNoAparcados.forEach(System.out::println);

        System.out.println(String.format("Total coches: %d", Coche.getCoches().size()));
        System.out.println(String.format("Plazas libres: %d", semaforo.availablePermits()));
    }

    public static synchronized void asignarCocheAPlaza(Coche coche) {
        int plazaAleatoria = ThreadLocalRandom.current().nextInt(maxPlazas);
        Plaza plazaElegida = plazas[plazaAleatoria];
        plazaElegida.setOcupadaPor(coche);
        System.out.println(String.format("Coche %d aparcado en plaza %d", coche.getId(), plazaElegida.getId()));
    }

    public static synchronized void liberarPlaza(int idPlaza) {
        Plaza plazaALiberar = plazas[idPlaza];
        plazaALiberar.liberarPlaza();
        System.out.println(String.format("Coche %d dejando plaza %d", plazaALiberar.getCoche().getId(), plazaALiberar.getId()));
    }
}
