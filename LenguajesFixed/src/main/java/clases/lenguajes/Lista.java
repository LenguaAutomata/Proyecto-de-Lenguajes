/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases.lenguajes;

/**
 *
 * @author walter
 * @param <T>
 * Lista generica con la capacidad de usar objetos genericos
 */
public class Lista<T extends Comparable<T>> {

    private Nodo<T> Head;
    private int size;

    public Lista() {
        this.Head = null;
    }

    public void add(T objeto) {
        Nodo<T> nuevo = new Nodo<>(objeto);
        if (IsEmpty()) {
            Head = nuevo;
            size++;
        } else {
            Nodo<T> actual = Head;
            while (actual.next != null) {
                actual = actual.next;
            }
            actual.next = nuevo;
            size++;
        }

    }

    public boolean IsEmpty() {

        return Head == null;
    }

    public int Size() {
        return size;

    }

    public void print() {
        Nodo<T> contar = Head;
        while (contar != null) {
            System.out.println(contar);
            contar = contar.next;
        }
    }

    public T Search(T elemento) {
        Nodo<T> buscar = Head;
        if (IsEmpty()) {
            return null;
        } else {
            while (buscar != null) {
                if (buscar.getO().compareTo(elemento) == 0) {
                    return buscar.getO();
                }
                buscar = buscar.next;
            }
        }
        return null;
    }

    public T borrar(T eliminar) {
        Nodo<T> actual = Head;
        if (eliminar == Head.getO()) {
            Nodo<T> resultado = Head;
            Head = Head.next;
            Head = null;
            size--;
            return resultado.getO();
        } else {
            Nodo<T> anterior = new Nodo();

            while (actual != null) {

                if (actual.getO().compareTo(eliminar) == 0) {
                    T valor = actual.getO();
                    anterior.next = actual.next;
                    size--;
                    return valor;
                }
                anterior = actual;
                actual = actual.next;

            }

        }
        return null;
    }

}
