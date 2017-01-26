package es.cesalberca.hilos.ej4;

public class Plaza {
    private int id;
    private boolean ocupada;

    public Plaza(int id, boolean ocupada) {
        this.id = id;
        this.ocupada = ocupada;
    }

    public int getId() {
        return id;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada() {
        this.ocupada = !this.ocupada;
    }

    @Override
    public String toString() {
        return "Plaza{" +
                "id=" + id +
                ", ocupada=" + ocupada +
                '}';
    }
}
