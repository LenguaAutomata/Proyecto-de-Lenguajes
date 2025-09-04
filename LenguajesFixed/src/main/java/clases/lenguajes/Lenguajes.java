/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package clases.lenguajes;
//Librerias que si podemos usar

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author walter
 */
public class Lenguajes {

    public static void main(String[] args) {

        
        Lista Estudiantes;
        Estudiantes = new Lista();
        Lista Libros = new Lista();

        Lista prestamos = new Lista();

        //Inicio del menu
        boolean est = true;
        int opc = 0;
        Scanner Menu = new Scanner(System.in);
        do {
            System.out.println("1.  Cargar Usuarios.");
            System.out.println("2.  Cargar Libros.");
            System.out.println("3.  Cargar resgitro de prestamo desde archivo.");
            System.out.println("4.  Mostrar historial de prestamos.");
            opc = Menu.nextInt();
            switch (opc) {
                case 1 -> {
                    //Cargar Usuarios
                    boolean ids = true;
                    boolean nombres = true;
                    String ver = "";
                    String tipo = "";
                    try {
                        String dir = "C:\\Users\\walte\\Desktop\\Usuarios.txt";
                        String elemento = "";
                        //System.out.println("Ingrese la direccion del archivo");
                        //Scanner direccion = new Scanner(System.in);
                        //dir = direccion.nextLine();
                        File Nuevo = new File(dir);
                        BufferedReader leer = new BufferedReader(new FileReader(Nuevo));
                        boolean dato1 = true;
                        char X;
                        int Linea = 1;
                        int character = 0;
                        leer.readLine();
                        while ((elemento = leer.readLine()) != null) {
                            ver = elemento;
                            //Metodo para poder saber si el ID es contiene un caracter que no es un numero.
                            X = elemento.charAt(character);
                            tipo = "id";
                            while (dato1) {
                                switch (tipo) {
                                    case "id" -> {
                                        if (!(Character.isDigit(X) || character <= 4)) {
                                            System.out.println("Formato con reconocido en linea " + Linea + " posicion " + character);
                                            dato1 = false;
                                            ids = false;
                                        }
                                        character++;
                                        X = elemento.charAt(character);
                                    }
                                    case "nombres" -> {
                                        if (!(Character.isLetter(X) || X == ' ')) {
                                            System.out.println("Caracter no disponible en linea " + Linea + " posicion " + character);
                                            dato1 = false;
                                            ids = false;
                                        }
                                        if ((character) == (elemento.length() - 1)) {
                                            dato1 = false;
                                        } else {
                                            character++;
                                            X = elemento.charAt(character);
                                        }

                                    }
                                    default -> {
                                        System.out.println("Funcion no conocida.");
                                    }
                                }
                                if (character == 4) {
                                    character++;
                                    X = elemento.charAt(character);
                                    tipo = "nombres";
                                } else if (character >= 4 && tipo.equals("id")) {
                                    System.out.println("El id del estudiante en linea " + Linea + " no es el correcto.");
                                    dato1 = false;
                                    ids = false;
                                }
                            }

                            if (ids && nombres) {
                                Estudiante nuevo = new Estudiante();
                                String[] convertir;
                                convertir = ver.split(",");
                                nuevo.setId(Integer.parseInt(convertir[0]));
                                nuevo.setNombre(convertir[1]);
                                Estudiantes.add(nuevo);
                            } else {
                                System.out.println("No se ha podido agregar el estudiante en linea: "
                                        + Linea + " debido a falta de datos.");
                            }
                            Linea++;
                            dato1 = true;
                            character = 0;
                            tipo = "id";
                        }
                        

                        System.out.println("Los estudiantes del documento fueron agregados exitosamente.");
                    } catch (IOException e) {
                        System.out.println("No se ha encontrado el documento que contiene los datos de los estudiantes");
                    }
                }

                case 2 -> {
                    boolean ids = true;
                    boolean nombres = true;
                    String ver = "";
                    String tipo = "";
                    try {
                        //parametros para poder leer un archivo .txt.
                        String dir = "C:\\Users\\walte\\Desktop\\Libros.txt";
                        String elemento = "";
                        //System.out.println("Ingrese la direccion del archivo");
                        //Scanner direccion = new Scanner(System.in);
                        //dir = direccion.nextLine();
                        File Nuevo = new File(dir);
                        BufferedReader leer = new BufferedReader(new FileReader(Nuevo));
                        String[] cadena;
                        boolean dato1 = true;
                        char X;
                        int Linea = 1;
                        int character = 0;
                        int LIB = 0;
                        leer.readLine();
                        //Inicio de ciclo para poder leer el documento
                        while ((elemento = leer.readLine()) != null) {
                            ver = elemento;
                            //Metodo para poder saber si el ID es contiene un caracter que no es un numero.
                            X = elemento.charAt(character);
                            tipo = "id";
                            while (dato1) {
                                switch (tipo) {
                                    case "id" -> {
                                        if (X == 'L') {
                                            character++;
                                            X = elemento.charAt(character);
                                            if (X == 'I') {
                                                character++;
                                                X = elemento.charAt(character);
                                                if (X == 'B') {
                                                    character++;
                                                    X = elemento.charAt(character);
                                                    while (X != ',') {
                                                        if (!(Character.isDigit(X))) {
                                                            dato1 = false;
                                                            ids = false;
                                                            break;
                                                        }
                                                        character++;
                                                        X = elemento.charAt(character);
                                                    }
                                                } else {
                                                    System.out.println("Formato con reconocido en liena " + Linea + " posicion " + character);
                                                    dato1 = false;
                                                    ids = false;
                                                }
                                            } else {
                                                System.out.println("Formato con reconocido en linea " + Linea + " posicion " + character);
                                                dato1 = false;
                                                ids = false;
                                            }
                                        } else {
                                            System.out.println("Formato con reconocido en linea " + Linea + " posicion " + character);
                                            dato1 = false;
                                            ids = false;
                                        }
                                    }
                                    case "nombres" -> {
                                        if (!((Character.isLetter(X) || X == ' ') || Character.isDigit(X))) {
                                            dato1 = false;
                                            ids = false;
                                        }
                                        if ((character) == (elemento.length() - 1)) {
                                            dato1 = false;
                                        } else {
                                            character++;
                                            X = elemento.charAt(character);
                                        }

                                    }
                                    default -> {
                                        System.out.println("Funcion no conocida.");
                                    }
                                }
                                if (character == 6) {
                                    character++;
                                    X = elemento.charAt(character);
                                    tipo = "nombres";
                                } else if (character >= 6 && tipo.equals("id")) {
                                    System.out.println("El id del libro en linea " + Linea + " no es el correcto.");
                                    dato1 = false;
                                    ids = false;
                                }
                            }

                            if (ids && nombres) {
                                Libro libro = new Libro();
                                String[] convertir;
                                convertir = ver.split(",");
                                libro.setId(convertir[0]);
                                libro.setTitulo(convertir[1]);

                                Libros.add(libro);
                            }
                            Linea++;
                        }

                        System.out.println("Los libros del documento fueron agregados exitosamente.");
                        System.out.println("");
                    } catch (IOException e) {
                        System.out.println("Documento de los libros no fue encontrado.");
                    }
                }
                case 3 -> {
                    //Cargar resgitro de prestamo desde archivo

                    int Linea = 1;

                    //booleanos para verifcar si las cadenas son correctas
                    boolean ids = true;
                    boolean nombres = true;
                    boolean idsLibros = true;
                    boolean titulosLibro = true;
                    boolean fechaPrestamo = true;
                    boolean fechaDevolucion = true;

                    //Objetos que se usaran para buscar el las listas de sus respectivos objetos
                    //Variables para la verificacion 
                    String ver = "";
                    String tipo = "";
                    try {
                        String dir = "C:\\Users\\walte\\Desktop\\biblioteca.txt";
                        String elemento = "";
                        //System.out.println("Ingrese la direccion del archivo");
                        //Scanner direccion = new Scanner(System.in);
                        //dir = direccion.nextLine();
                        File Nuevo = new File(dir);
                        BufferedReader leer = new BufferedReader(new FileReader(Nuevo));
                        boolean dato1 = true;
                        char X;
                        String[] ver2;
                        int character = 0;
                        int characteridL = 0;
                        int anio1 = 0, mes1 = 0, dia1 = 0;
                        leer.readLine();
                        while ((elemento = leer.readLine()) != null) {
                            ver = elemento;
                            System.out.println(ver.length());
                            ver2 = elemento.split(",");
                            //Metodo para poder saber si el ID es contiene un caracter que no es un numero.
                            X = elemento.charAt(character);
                            tipo = "id";
                            while (dato1) {
                                switch (tipo) {
                                    case "id" -> {
                                        if (!(Character.isDigit(X) || character <= 4)) {
                                            System.out.println("Formato con reconocido en linea " + Linea + " posicion " + character);
                                            dato1 = false;
                                            ids = false;
                                        }
                                        character++;
                                        X = elemento.charAt(character);
                                    }
                                    case "nombres" -> {

                                        if (X == ',') {
                                            tipo = "idL";
                                            character++;
                                            X = elemento.charAt(character);
                                        } else {
                                            if (!(Character.isLetter(X) || X == ' ')) {
                                                System.out.println("Caracter no disponible en linea " + Linea + " posicion " + character);
                                                dato1 = false;
                                                idsLibros = false;
                                            } else {
                                                character++;
                                                X = elemento.charAt(character);
                                            }
                                        }

                                    }
                                    case "idL" -> {
                                        if (X == 'L') {
                                            character++;
                                            characteridL++;
                                            X = elemento.charAt(character);
                                            if (X == 'I') {
                                                character++;
                                                characteridL++;
                                                X = elemento.charAt(character);
                                                if (X == 'B') {
                                                    character++;
                                                    characteridL++;
                                                    X = elemento.charAt(character);
                                                    while (X != ',') {
                                                        if (!(Character.isDigit(X))) {
                                                            dato1 = false;
                                                            titulosLibro = false;
                                                            break;
                                                        }
                                                        character++;
                                                        characteridL++;
                                                        X = elemento.charAt(character);
                                                    }
                                                } else {
                                                    System.out.println("Formato con reconocido en liena " + Linea + " posicion " + character);
                                                    dato1 = false;
                                                    titulosLibro = false;
                                                }
                                            } else {
                                                System.out.println("Formato con reconocido en linea " + Linea + " posicion " + character);
                                                dato1 = false;
                                                titulosLibro = false;
                                            }
                                        } else {
                                            System.out.println("Formato con reconocido en linea " + Linea + " posicion " + character);
                                            dato1 = false;
                                            titulosLibro = false;
                                        }
                                    }
                                    case "nombresL" -> {

                                        if (X == ',') {
                                            tipo = "fecha-prestamo";
                                            character++;
                                            X = elemento.charAt(character);
                                        } else {
                                            if (!((Character.isLetter(X) || X == ' ') || Character.isDigit(X))) {
                                                dato1 = false;
                                                titulosLibro = false;
                                            } else {
                                                character++;
                                                X = elemento.charAt(character);
                                            }
                                        }
                                    }
                                    case "fecha-prestamo" -> {
                                        while (Character.isDigit(X) || X == '-') {
                                            character++;
                                            X = elemento.charAt(character);
                                            anio1++;
                                            if (X == '-') {
                                                character++;
                                                X = elemento.charAt(character);
                                                if (anio1 == 4) {
                                                    if (Character.isDigit(X)) {
                                                        character++;
                                                        mes1++;
                                                        X = elemento.charAt(character);
                                                        if (Character.isDigit(X)) {
                                                            // character++;
                                                            mes1++;
                                                            anio1--;
                                                            // X = elemento.charAt(character);

//                                                            if (character == elemento.length()) {
//                                                                break;
//                                                            }
                                                        } else {
                                                            //Agregar texto de salida
                                                            fechaPrestamo = false;
                                                            break;
                                                        }
                                                    } else {
                                                        //Agregar texto de salida
                                                        fechaPrestamo = false;
                                                        break;
                                                    }

                                                } else {
                                                    //Agregar texto de salida
                                                    fechaPrestamo = false;
                                                    break;
                                                }

                                            } else if (X == ',') {
                                                if ((anio1 == 4) && (mes1 == 4)) {
                                                    tipo = "fecha-devolucion";
                                                    character++;
                                                    X = elemento.charAt(character);
                                                    anio1 = 0;
                                                    mes1 = 0;
                                                    break;
                                                } else {
                                                    //Agregar texto de salida
                                                    fechaPrestamo = false;
                                                    anio1 = 0;
                                                    mes1 = 0;
                                                    character++;
                                                    X = elemento.charAt(character);
                                                    break;
                                                }

                                            }

                                        }
                                    }
                                    case "fecha-devolucion" -> {
                                        if (X == ' ' || (character == elemento.length())) {
                                            System.out.println("Error");
                                        } else {
                                            while (Character.isDigit(X) || X == '-') {
                                                character++;
                                                X = elemento.charAt(character);
                                                anio1++;
                                                if (X == '-') {
                                                    character++;
                                                    X = elemento.charAt(character);
                                                    if (anio1 == 4) {
                                                        if (Character.isDigit(X)) {
                                                            character++;
                                                            mes1++;
                                                            X = elemento.charAt(character);
                                                            if (Character.isDigit(X)) {
                                                                // character++;
                                                                mes1++;
                                                                anio1--;
                                                                // X = elemento.charAt(character);

                                                                if (character == elemento.length() - 1) {
                                                                    if ((mes1 == 4) && (anio1 == 3)) {
                                                                        tipo = " ";
                                                                        dato1 = false;
                                                                        mes1 = 0;
                                                                        anio1 = 0;
                                                                        character=0;
                                                                        characteridL=0;
                                                                        break;
                                                                    } else {
                                                                        fechaDevolucion = false;
                                                                        tipo = " ";
                                                                        dato1 = false;
                                                                        mes1 = 0;
                                                                        anio1 = 0;
                                                                        character=0;
                                                                        characteridL=0;
                                                                        break;
                                                                    }
                                                                }
                                                            } else {
                                                                //Agregar texto de salida
                                                                fechaDevolucion = false;
                                                                tipo = " ";
                                                                dato1 = false;
                                                                mes1 = 0;
                                                                anio1 = 0;
                                                                character=0;
                                                                characteridL=0;
                                                                break;
                                                            }
                                                        } else {
                                                            //Agregar texto de salida
                                                            fechaDevolucion = false;
                                                            tipo = " ";
                                                            dato1 = false;
                                                            mes1 = 0;
                                                            anio1 = 0;
                                                            character=0;
                                                            characteridL=0;
                                                            break;
                                                        }

                                                    } else {
                                                        //Agregar texto de salida
                                                        fechaDevolucion = false;
                                                        tipo = " ";
                                                        dato1 = false;
                                                        mes1 = 0;
                                                        anio1 = 0;
                                                        character=0;
                                                        characteridL=0;
                                                        break;
                                                    }

                                                }

                                            }
                                        }
                                    }
                                    default -> {
                                        System.out.println("Funcion no disponible.");
                                    }
                                }
                                if (character == 4) {
                                    character++;
                                    X = elemento.charAt(character);
                                    tipo = "nombres";
                                } else if (character >= 4 && tipo.equals("id")) {
                                    System.out.println("El id del estudiante en linea " + Linea + " no es el correcto.");
                                    dato1 = false;
                                    ids = false;
                                }

                                if (characteridL == 6 && tipo.equals("idL")) {
                                    character++;
                                    X = elemento.charAt(character);
                                    tipo = "nombresL";
                                } else if (characteridL >= 6 && tipo.equals("idL")) {
                                    System.out.println("El id del libro en linea " + Linea + " no es el correcto.");
                                    dato1 = false;
                                    ids = false;
                                }
                            }

                            if (ids && nombres && idsLibros && titulosLibro && fechaPrestamo && fechaDevolucion) {
                                Estudiante verificar = new Estudiante();
                                Libro existente = new Libro();
                                Prestamo prestamo_aceptado = new Prestamo();
                                String[] convertir;
                                convertir = ver.split(",");
                                verificar.setId(Integer.parseInt(convertir[0]));
                                verificar.setNombre(convertir[1]);
                                //Estudiantes.add(nuevo);
                                existente.setId(convertir[2]);
                                existente.setTitulo(convertir[3]);

                                if (Estudiantes.Search(verificar) != null) {
                                    prestamo_aceptado.setEstudiante(Estudiantes.borrar(verificar));
                                    if (Libros.Search(existente) != null) {
                                        prestamo_aceptado.setLibro(Libros.borrar(existente));
                                        prestamo_aceptado.setFecha_prestamo(convertir[4]);
                                        prestamo_aceptado.setFecha_devolucion(convertir[5]);
                                    } else {
                                        System.out.println("El libro no esta en la lista");
                                    }
                                } else {

                                    System.out.println("El estudiante no esta en la lista");
                                }

                            } else {
                                System.out.println("No se ha podido agregar el estudiante en linea: "
                                        + Linea + " debido a falta de datos.");
                            }
                            Linea++;
                            dato1 = true;
                            
                        }

                        System.out.println("Los Prestamos del documento fueron agregados exitosamente.");
                    } catch (IOException e) {
                        System.out.println("No se ha encontrado el documento que contiene los datos de los estudiantes");
                    }

                }
                case 4 -> {
                    //Mostrar historial de prestamos.
                }
                default ->
                    throw new AssertionError();
            }
        } while (est);

    }
}
