package clases.lenguajes;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author walter
 * @param <T>
 *
 */
public class Nodo<T extends Comparable<T>> {

    Nodo<T> next;
    T O;

    public Nodo(T Libro) {
        this.next = null;
        this.O = Libro;
    }

    public Nodo() {
    }

    @Override
    public String toString() {
        return "{" + O + '}';
    }

    public T getO() {
        return O;
    }

    public int compareTo(T o) {
      return this.O.compareTo(o);
    }

}
