package clases.lenguajes;

public class Libro implements Comparable<Libro> {
    private String id;
    private String Titulo;
    private int Nprestado;
    private boolean Dispobible;

    public Libro(String id, String Titulo) {
        this.id = id;
        this.Titulo = Titulo;
        this.Nprestado = 0;
        this.Dispobible=true;
    }

    public Libro() {
        this.Nprestado = 0;
    }

    public int contarPrestamo(String Fecha1, String Fecha2) {
        if ((Fecha1 != null && !Fecha1.isEmpty()) && (Fecha2 != null && !Fecha2.isEmpty())) {
            Nprestado++;
            return Nprestado;
        }
        return Nprestado;
    }

    public boolean Esprestado(String Fecha1, String Fecha2) {
        return (Fecha1 != null && !Fecha1.isEmpty()) && (Fecha2 != null && !Fecha2.isEmpty());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }

    public int getNprestado() {
        return Nprestado;
    }

    public boolean isDispobible() {
        return Dispobible;
    }

    public void setDispobible(boolean Dispobible) {
        this.Dispobible = Dispobible;
    }
    

    @Override
    public String toString() {
        return "Libro{" + "id=" + id + ", Titulo=" + Titulo + ", Prestamos=" + Nprestado + '}';
    }

    @Override
    public int compareTo(Libro o) {
        try {
            int x = Integer.parseInt(this.id.substring(3));
            int y = Integer.parseInt(o.id.substring(3));
            return Integer.compare(x, y);
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            return this.id.compareTo(o.id);
        }
    }
}