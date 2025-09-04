/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases.lenguajes;

/**
 *
 * @author walter
 * Clase de los estudantes con la capacidad de comparar entre si
 */
public class Estudiante implements Comparable<Estudiante> {

    private int Id;
    private String Nombre;

    public Estudiante(int Id, String Nombre) {
        this.Id = Id;
        this.Nombre = Nombre;

    }

    public Estudiante() {
    }

    @Override
    public String toString() {
        return "Estudiante{" + "Id=" + Id + ", Nombre=" + Nombre + '}';
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    @Override
    public int compareTo(Estudiante o) {
       return Integer.compare(this.Id, o.Id);
    }

}
