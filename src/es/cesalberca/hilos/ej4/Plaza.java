package es.cesalberca.hilos.ej4;

/**
 * Created by Cesar
 */
public class Plaza {
    private boolean libre;

    public Plaza() {
        this.libre = true;
    }

    public boolean isLibre() {
        return libre;
    }

    public void toggleLibre() {
        this.libre = !libre;
    }
}
