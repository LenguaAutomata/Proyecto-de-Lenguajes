/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases.lenguajes;

/**
 *
 * @author walter
 * clase del libro con metodo para comparse entre si
 * 
 */
public class Libro implements Comparable<Libro> {

    private String id;
    private String Titulo;
    private int Nprestado;

    public Libro(String id, String Titulo) {
        this.id = id;
        this.Titulo = Titulo;
    }

    public Libro() {

    }

    public int contarPrestamo(String Fecha1, String Fecha2) {

        if ((!Fecha1.isEmpty()) && (!Fecha2.isEmpty())) {
            return Nprestado++;
        }
        return 0;
    }

    public boolean Esprestado(String Fecha1, String Fecha2) {
        return (!Fecha1.isEmpty()) && (!Fecha2.isEmpty());
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

    @Override
    public int compareTo(Libro o) {
        int x = Integer.parseInt(this.id.substring(3, 6));
        int y = Integer.parseInt(o.id.substring(3, 6));
        return Integer.compare(x, y);
    }

}
