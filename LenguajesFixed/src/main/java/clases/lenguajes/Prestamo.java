/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases.lenguajes;

/**
 *
 * @author walte
 * @param <T>
 * Clase prestamo en donde se aguaradaran los prestamos hechos
 */
public class Prestamo<T> {

    private String Fecha_prestamo;
    private String Fecha_devolucion;
    private T Estudiante;
    private T Libro;

    public Prestamo(String Fecha_prestamo, String Fecha_devolucion, T Estudiante, T Libro) {
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

    public T getEstudiante() {
        return Estudiante;
    }

    public void setEstudiante(T Estudiante) {
        this.Estudiante = Estudiante;
    }

    public T getLibro() {
        return Libro;
    }

    public void setLibro(T Libro) {
        this.Libro = Libro;
    }
    
    

}
