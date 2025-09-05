package clases.lenguajes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Prestamo implements Comparable<Prestamo> {
    private String Fecha_prestamo;
    private String Fecha_devolucion;
    private Estudiante Estudiante;
    private Libro Libro;

    public Prestamo(String Fecha_prestamo, String Fecha_devolucion, Estudiante Estudiante, Libro Libro) {
        this.Fecha_prestamo = Fecha_prestamo;
        this.Fecha_devolucion = Fecha_devolucion;
        this.Estudiante = Estudiante;
        this.Libro = Libro;
    }
    
    public Prestamo() {
    }

    public String getFecha_prestamo() {
        return Fecha_prestamo;
    }

    public void setFecha_prestamo(String Fecha_prestamo) {
        this.Fecha_prestamo = Fecha_prestamo;
    }

    public String getFecha_devolucion() {
        return Fecha_devolucion;
    }

    public void setFecha_devolucion(String Fecha_devolucion) {
        this.Fecha_devolucion = Fecha_devolucion;
    }

    public Estudiante getEstudiante() {
        return Estudiante;
    }

    public void setEstudiante(Estudiante Estudiante) {
        this.Estudiante = Estudiante;
    }

    public Libro getLibro() {
        return Libro;
    }

    public void setLibro(Libro Libro) {
        this.Libro = Libro;
    }
    
    public boolean estaVencido() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fechaDevolucion = LocalDate.parse(this.Fecha_devolucion, formatter);
            LocalDate hoy = LocalDate.now();
            return hoy.isAfter(fechaDevolucion);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    public int diasRetraso() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fechaDevolucion = LocalDate.parse(this.Fecha_devolucion, formatter);
            LocalDate hoy = LocalDate.now();
            
            if (hoy.isAfter(fechaDevolucion)) {
                return (int) java.time.temporal.ChronoUnit.DAYS.between(fechaDevolucion, hoy);
            }
            return 0;
        } catch (DateTimeParseException e) {
            return 0;
        }
    }
    
    @Override
    public String toString() {
        String estado = estaVencido() ? "VENCIDO (" + diasRetraso() + " días de retraso)" : "VIGENTE";
        return "Prestamo{" + 
               "Estudiante=" + Estudiante.getNombre() + 
               ", Libro=" + Libro.getTitulo() + 
               ", Fecha_prestamo=" + Fecha_prestamo + 
               ", Fecha_devolucion=" + Fecha_devolucion +
               ", Estado=" + estado + '}';
    }

    @Override
    public int compareTo(Prestamo otro) {
        return this.Fecha_prestamo.compareTo(otro.Fecha_prestamo);
    }
}