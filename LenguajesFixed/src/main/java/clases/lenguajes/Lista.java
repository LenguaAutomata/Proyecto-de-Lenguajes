/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases.lenguajes;

import java.util.function.Consumer;
/**
 *
 * @author walter
 * @param <T>
 * Lista generica con la capacidad de usar objetos genericos
 */


public class Lista<T extends Comparable<T>> {
    public Nodo<T> Head;  // Cambiado de private a public
    private int size;

    public Lista() {
        this.Head = null;
        this.size = 0;
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
        if (IsEmpty()) {
            return null;
        }
        
        if (Head.getO().compareTo(eliminar) == 0) {
            T resultado = Head.getO();
            Head = Head.next;
            size--;
            return resultado;
        } else {
            Nodo<T> actual = Head.next;
            Nodo<T> anterior = Head;

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
    
    public void forEach(Consumer<T> action) {
        Nodo<T> actual = Head;
        while (actual != null) {
            action.accept(actual.getO());
            actual = actual.next;
        }
    }
}