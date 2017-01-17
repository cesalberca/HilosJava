package es.cesalberca.hilos.ej1;

import java.util.Date;

public class Relevo implements Runnable {
    @Override
    public void run() {
        Thread hilo = Thread.currentThread();
        System.out.println("Relevo llevado por hilo: " + hilo.getName() + " | " + new Date());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}