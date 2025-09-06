package clases.lenguajes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class Lenguajes {

    public static void main(String[] args) {
        String basePath = "C:\\Users\\walte\\Desktop\\Desktop_local\\Lenguajes automatas\\LenguajesF\\Proyecto-de-Lenguajes\\LenguajesFixed";

        Lista<Estudiante> Estudiantes = new Lista<>();
        Lista<Libro> Libros = new Lista<>();
        Lista<Prestamo> prestamos = new Lista<>();
        Lista<Libro> LPrestados = new Lista<>();

        boolean est = true;
        int opc = 0;
        Scanner Menu = new Scanner(System.in);

        do {
            System.out.println("\n=== SISTEMA DE BIBLIOTECA ===");
            System.out.println("1. Cargar Usuarios.");
            System.out.println("2. Cargar Libros.");
            System.out.println("3. Cargar registro de préstamo desde archivo.");
            System.out.println("4. Mostrar historial de préstamos.");
            System.out.println("5. Mostrar listado de usuarios únicos.");
            System.out.println("6. Mostrar listado de libros prestados.");
            System.out.println("7. Mostrar estadísticas de préstamos.");
            System.out.println("8. Mostrar préstamos vencidos.");
            System.out.println("9. Exportar todos los reportes a HTML.");
            System.out.println("10. Salir.");
            System.out.print("Seleccione una opción: ");

            try {
                opc = Menu.nextInt();
                Menu.nextLine();
            } catch (Exception e) {
                System.out.println("Por favor, ingrese un número válido.");
                Menu.nextLine();
                continue;
            }

            switch (opc) {
                case 1 ->
                    cargarUsuarios(Estudiantes);
                case 2 ->
                    cargarLibros(Libros);
                case 3 ->
                    cargarPrestamos(Estudiantes, Libros, prestamos, LPrestados);
                case 4 ->
                    mostrarPrestamos(prestamos);
                case 5 ->
                    mostrarEstudiantes(Estudiantes);
                case 6 ->
                    mostrarLibrosPrestados(Libros);
                case 7 ->
                    mostrarEstadisticas(prestamos, Libros);
                case 8 ->
                    mostrarPrestamosVencidos(prestamos);
                case 9 ->
                    exportarReportesHTML(basePath, Estudiantes, Libros, prestamos);
                case 10 -> {
                    est = false;
                    System.out.println("Saliendo del sistema...");
                }
                default ->
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (est);
    }

    private static void cargarUsuarios(Lista<Estudiante> Estudiantes) {
        int estudiantesCargados = 0;
        int estudiantesDuplicados = 0;
        int lineasProcesadas = 0;
        //Cargar Usuarios
        boolean ids = true;
        boolean nombres = true;
        String ver = "";
        String tipo = "";
        try {
            String dir = "";
            String elemento = "";
            System.out.println("Ingrese la direccion del archivo");
            Scanner direccion = new Scanner(System.in);
            dir = direccion.nextLine();
            File Nuevo = new File(dir);
            BufferedReader leer = new BufferedReader(new FileReader(Nuevo));
            boolean dato1 = true;
            char X;
            int Linea = 1;
            int character = 0;
            leer.readLine();
            while ((elemento = leer.readLine()) != null) {
                ver = elemento;

                X = elemento.charAt(character);
                tipo = "id";
                while (dato1) {
                    switch (tipo) {
                        //Caso en donde el tipo es id del usuario
                        case "id" -> {
                            //Si no es un numero o es la cadena es menor que cuatro
                            if (!(Character.isDigit(X) || character <= 4)) {
                                System.out.println("Formato con reconocido en linea " + Linea + " posicion " + character);
                                dato1 = false;
                                ids = false;
                            }

                            character++;//variable usada para contar los caracterres
                            X = elemento.charAt(character);
                        }
                        //Caso en donde el tipo es nombre del usuario
                        case "nombres" -> {
                            if (!(Character.isLetter(X) || X == ' ')) {
                                System.out.println("Caracter no disponible en linea " + Linea + " posicion " + character);
                                dato1 = false;
                                ids = false;
                            }
                            //Si es la cantidad de caracteres es igual a largo de la cadena 
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
                    //Si cuando es de tipo id del usuario y la cade na es 4, aceptada
                    if (character == 4) {
                        character++;
                        X = elemento.charAt(character);
                        tipo = "nombres";
                        //Si  hay mas de cuatro numeros, entinces no lo agrega
                    } else if (character >= 4 && tipo.equals("id")) {
                        System.out.println("El id del estudiante en linea " + Linea + " no es el correcto.");
                        dato1 = false;
                        ids = false;
                    }
                }
                //Si los dos valores son correctos, se agrega a una lista
                if (ids && nombres) {
                    Estudiante nuevo = new Estudiante();
                    String[] convertir;
                    convertir = ver.split(",");
                    nuevo.setId(Integer.parseInt(convertir[0]));
                    nuevo.setNombre(convertir[1]);

                    if (Estudiantes.Search(nuevo) == null) {
                        Estudiantes.add(nuevo);
                        estudiantesCargados++;
                    } else {
                        estudiantesDuplicados++;
                    }

                } else {
                    System.out.println("No se ha podido agregar el estudiante en linea: "
                            + Linea + " debido a falta de datos.");
                }

                Linea++;//Variable para contar lineas del archivo
                dato1 = true;//Para elc ciclo de busqueda

                //Variables que se usa para poder verificar la cadena
                character = 0;
                tipo = "id";
                lineasProcesadas++;
            }

            System.out.println("Los estudiantes del documento fueron agregados exitosamente.");
            System.out.println("Estudiantes cargados " + estudiantesCargados);
            System.out.println("Estudiantes duplicados " + estudiantesDuplicados);
        } catch (IOException e) {
            System.out.println("No se ha encontrado el documento que contiene los datos de los estudiantes");
        }

    }

    private static void cargarLibros(Lista<Libro> Libros) {
        int librosCargados = 0;
        int librosDuplicados = 0;
        int lineasProcesadas = 0;
        boolean ids = true;
        boolean nombres = true;
        String ver = "";
        String tipo = "";

        try {
            //parametros para poder leer un archivo .txt.
            String dirr = "";
            String elemento = "";
            System.out.println("Ingrese la direccion del archivo");
            Scanner direccion = new Scanner(System.in);
            dirr = direccion.nextLine();
            File Nuevo = new File(dirr);
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
                        //Caso en donde se verifica el id del libro
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
                                        //Casos en donde no cumple el formato del id del libro
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
                        //Caso en donde se verifica el nombre del libro
                        case "nombres" -> {
                            //Se acepta un libro endonde no tengo simbolos raros
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
                    //Si la cadena del id del libro es la correcta se acepta.
                    if (character == 6) {
                        character++;
                        X = elemento.charAt(character);
                        tipo = "nombres";
                        //Si es mas largo, no se acepta.
                    } else if (character >= 6 && tipo.equals("id")) {
                        System.out.println("El id del libro en linea " + Linea + " no es el correcto.");
                        dato1 = false;
                        ids = false;
                    }
                }

                //Si ambos casos son correctos, se agrega a la lista de libros existentes
                if (ids && nombres) {
                    Libro libro = new Libro();
                    String[] convertir;
                    convertir = ver.split(",");
                    libro.setId(convertir[0]);
                    libro.setTitulo(convertir[1]);

                    if (Libros.Search(libro) == null) {
                        Libros.add(libro);
                        librosCargados++;
                    } else {
                        librosDuplicados++;
                    }

                }
                Linea++;
                lineasProcesadas++;
            }

            System.out.println("Los libros del documento fueron agregados exitosamente.");
            System.out.println("Libros agregados " + librosCargados);
            System.out.println("Libros Duplicados " + librosDuplicados);
            System.out.println("");
        } catch (IOException e) {
            System.out.println("Documento de los libros no fue encontrado.");
        }

    }

    private static void cargarPrestamos(Lista<Estudiante> Estudiantes, Lista<Libro> Libros, Lista<Prestamo> prestamos, Lista<Libro> LPrestados) {
        int prestamosCargados = 0;
        int prestamosInvalidos = 0;
        int lineasProcesadas = 0;

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
            String dir = "";
            String elemento = "";
            System.out.println("Ingrese la direccion del archivo");
            Scanner direccion = new Scanner(System.in);
            dir = direccion.nextLine();
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
                // System.out.println(ver.length());
                ver2 = elemento.split(",");

                X = elemento.charAt(character);
                tipo = "id";
                while (dato1) {
                    switch (tipo) {
                        //Caso en donde se verifica el id del usuario
                        case "id" -> {
                            if (!(Character.isDigit(X) || character <= 4)) {
                                System.out.println("Formato con reconocido en linea " + Linea + " posicion " + character);
                                dato1 = false;
                                ids = false;
                            }
                            character++;
                            X = elemento.charAt(character);
                        }
                        //Caso en donde se verifica el nombre del usuario.
                        case "nombres" -> {
                            //Si el anterior espacio es una coma, se sigue analizado el nombre del usuario
                            if (X == ',') {
                                tipo = "idL";
                                character++;
                                X = elemento.charAt(character);

                            } else {
                                //Si es un numero no se acepta
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
                        //Caso en donde se verifica el id del libro
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
                                            //Si es letra o simbolo extraño, no se acepta
                                            if (!(Character.isDigit(X))) {
                                                dato1 = false;
                                                titulosLibro = false;
                                                break;
                                            }
                                            character++;
                                            characteridL++;
                                            X = elemento.charAt(character);
                                        }
                                        //Casos en donde ningunos de los if cumpla.
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
                        //Caso en donde se verifica el nombre del libro
                        case "nombresL" -> {

                            if (X == ',') {
                                tipo = "fecha-prestamo";
                                character++;
                                X = elemento.charAt(character);
                            } else {
                                //Si el nombre del libro tiene un caracter extraño, no se acepta
                                if (!((Character.isLetter(X) || X == ' ') || Character.isDigit(X))) {
                                    dato1 = false;
                                    titulosLibro = false;
                                } else {
                                    character++;
                                    X = elemento.charAt(character);
                                }
                            }
                        }
                        //Caso en donde se verifica la fecha de prestamo
                        case "fecha-prestamo" -> {
                            //Si la cadena es un numero o es "-"
                            while (Character.isDigit(X) || X == '-') {
                                character++;
                                X = elemento.charAt(character);
                                anio1++;//Varible para verica si la primera parte de la cadena contiene el formato correcto del año
                                if (X == '-') {//Si la cadena contiene un guion, se sigue verificado.
                                    character++;
                                    X = elemento.charAt(character);
                                    //Si el año tiene el formato correcto, se sigue verificando
                                    if (anio1 == 4) {
                                        if (Character.isDigit(X)) {
                                            character++;
                                            mes1++;//Variable para verficiar la fecha como el dia de la cadena.
                                            X = elemento.charAt(character);
                                            //Si es numero se acepta
                                            if (Character.isDigit(X)) {
                                                // character++;
                                                mes1++;
                                                anio1--;
                                            } else {
                                                //Agregar texto de salida
                                                System.out.println("Formato con reconocido en liena " + Linea + " posicion " + character);
                                                fechaPrestamo = false;
                                                break;
                                            }
                                        } else {
                                            //Agregar texto de salida
                                            System.out.println("Formato con reconocido en liena " + Linea + " posicion " + character);
                                            fechaPrestamo = false;
                                            break;
                                        }

                                    } else {
                                        //Agregar texto de salida
                                        System.out.println("Formato con reconocido en linea " + Linea + " posicion " + character);
                                        fechaPrestamo = false;
                                        break;
                                    }

                                } else if (X == ',') {
                                    //Si la cadena tiene el formato correcto se acepta.
                                    if (character == (elemento.length() - 1)) {

                                        tipo = " ";
                                        anio1 = 0;
                                        mes1 = 0;
                                        character = 0;
                                        dato1 = false;
                                        break;

                                    } else if ((anio1 == 4) && (mes1 == 4)) {

                                        tipo = "fecha-devolucion";

                                        character++;
                                        X = elemento.charAt(character);
                                        anio1 = 0;
                                        mes1 = 0;
                                        break;
                                    } else {
                                        System.out.println("Formato con reconocido en linea " + Linea + " posicion " + character);
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
                        //Caso en donde se verifica la fecha de devolucion
                        case "fecha-devolucion" -> {
                            if ((character == elemento.length() - 1)) {
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

                                                    mes1++;
                                                    anio1--;

                                                    //Si el character llega al final de la cadena y la fecha de devolucion es correcta,
                                                    //es acepta y sale de la verificacion de la cadena
                                                    if (character == elemento.length() - 1) {
                                                        if ((mes1 == 4) && (anio1 == 3)) {
                                                            tipo = " ";
                                                            dato1 = false;
                                                            mes1 = 0;
                                                            anio1 = 0;
                                                            character = 0;
                                                            characteridL = 0;
                                                            break;
                                                        } else {
                                                            System.out.println("Formato con reconocido en linea " + Linea + " posicion " + character);
                                                            fechaDevolucion = false;
                                                            tipo = " ";
                                                            dato1 = false;
                                                            mes1 = 0;
                                                            anio1 = 0;
                                                            character = 0;
                                                            characteridL = 0;
                                                            break;
                                                        }
                                                    }
                                                } else {
                                                    System.out.println("Formato con reconocido en linea " + Linea + " posicion " + character);
                                                    fechaDevolucion = false;
                                                    tipo = " ";
                                                    dato1 = false;
                                                    mes1 = 0;
                                                    anio1 = 0;
                                                    character = 0;
                                                    characteridL = 0;
                                                    break;
                                                }
                                            } else {
                                                //Agregar texto de salida
                                                System.out.println("Formato con reconocido en linea " + Linea + " posicion " + character);
                                                fechaDevolucion = false;
                                                tipo = " ";
                                                dato1 = false;
                                                mes1 = 0;
                                                anio1 = 0;
                                                character = 0;
                                                characteridL = 0;
                                                break;
                                            }

                                        } else {
                                            //Agregar texto de salida
                                            System.out.println("Formato con reconocido en linea " + Linea + " posicion " + character);
                                            fechaDevolucion = false;
                                            tipo = " ";
                                            dato1 = false;
                                            mes1 = 0;
                                            anio1 = 0;
                                            character = 0;
                                            characteridL = 0;
                                            break;
                                        }

                                    }

                                }
                            }
                        }
                        default -> {
                            System.out.println("--------");
                        }
                    }
                    //Si el id del usuario es correcto, se sigue verificando
                    if (character == 4) {
                        character++;
                        X = elemento.charAt(character);
                        tipo = "nombres";
                        //Si es mayor se sale.
                    } else if (character >= 4 && tipo.equals("id")) {
                        System.out.println("El id del estudiante en linea " + Linea + " no es el correcto.");
                        dato1 = false;
                        ids = false;
                    }
                    //Si el id del libro es el correcto se sigue verificando.
                    if (characteridL == 6 && tipo.equals("idL")) {
                        character++;
                        X = elemento.charAt(character);
                        tipo = "nombresL";

                        //Si no lo es se sale
                    } else if (characteridL >= 6 && tipo.equals("idL")) {
                        System.out.println("El id del libro en linea " + Linea + " no es el correcto.");
                        dato1 = false;
                        ids = false;
                    }
                }
                //Si todos los valores son correctos, se procede a hacer el prestamo
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

                    //Si el estudiante existe, se procede a agregarlo en una lista de prestamos
                    if (Estudiantes.Search(verificar) != null) {
                        prestamo_aceptado.setEstudiante(Estudiantes.Search(verificar));
                        //Si el libro existe, se procede a agregarlo en una lista de prestamos

                        if (Libros.Search(existente) != null) {
                            prestamo_aceptado.setLibro(Libros.Search(existente));
                            prestamo_aceptado.setFecha_prestamo(convertir[4]);

                            if (convertir.length == 6) {

                                prestamo_aceptado.setFecha_devolucion(convertir[5]);
                                existente.setDispobible(true);
                            } else {
                                existente.setDispobible(false);
                                prestamo_aceptado.setFecha_devolucion(" ");
                            }
                            prestamosCargados++;
                        } else {
                            System.out.println("El libro no esta en la lista  o ha sido prestado en linea:" + Linea);
                            prestamosInvalidos++;
                        }
                    } else {

                        System.out.println("El estudiante no esta en la lista en la linea: " + Linea);
                        prestamosInvalidos++;
                    }

                } else {
                    System.out.println("No se ha podido agregar el estudiante en linea: "
                            + Linea + " debido a falta de datos.");
                    prestamosInvalidos++;
                }
                Linea++;
                dato1 = true;
                lineasProcesadas++;

            }

            System.out.println("Los Prestamos del documento fueron agregados exitosamente.");
            System.out.println("Lineas procesadas: " + lineasProcesadas);
            System.out.println("Prestamos cargados: " + prestamosCargados);
            System.out.println("Prestamos invalidos: " + prestamosInvalidos);
            System.out.println("");

        } catch (IOException e) {
            System.out.println("No se ha encontrado el documento que contiene los datos de los estudiantes");
        }
    }

    private static void mostrarPrestamos(Lista<Prestamo> prestamos) {
        System.out.println("\n=== HISTORIAL DE PRÉSTAMOS ===");
        if (prestamos.IsEmpty()) {
            System.out.println("No hay préstamos registrados.");
        } else {
            prestamos.print();
        }
    }

    private static void mostrarEstudiantes(Lista<Estudiante> Estudiantes) {
        System.out.println("\n=== LISTADO DE USUARIOS ÚNICOS ===");
        if (Estudiantes.IsEmpty()) {
            System.out.println("No hay estudiantes registrados.");
        } else {
            Estudiantes.print();
        }
    }

    private static void mostrarLibrosPrestados(Lista<Libro> Libros) {
        System.out.println("\n=== LISTADO DE LIBROS PRESTADOS ===");
        if (Libros.IsEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        final int[] contador = {0};
        Libros.forEach(libro -> {
            if (libro.getNprestado() > 0) {
                System.out.println(libro);
                contador[0]++;
            }
        });

        if (contador[0] == 0) {
            System.out.println("No hay libros prestados.");
        }
    }

    private static void mostrarEstadisticas(Lista<Prestamo> prestamos, Lista<Libro> Libros) {
        System.out.println("\n=== ESTADÍSTICAS DE PRÉSTAMOS ===");

        // Estadísticas de préstamos
        final int[] totalPrestamos = {0};
        final int[] prestamosVencidos = {0};
        final int[] prestamosVigentes = {0};

        prestamos.forEach(prestamo -> {
            totalPrestamos[0]++;
            if (prestamo.estaVencido()) {
                prestamosVencidos[0]++;
            } else {
                prestamosVigentes[0]++;
            }
        });

        System.out.println("Total de préstamos: " + totalPrestamos[0]);
        System.out.println("Préstamos vencidos: " + prestamosVencidos[0]);
        System.out.println("Préstamos vigentes: " + prestamosVigentes[0]);

        // Estadísticas de libros
        final int[] totalLibros = {0};
        final int[] librosPrestados = {0};
        final int[] librosDisponibles = {0};
        final Libro[] libroMasPrestado = {null};

        Libros.forEach(libro -> {
            totalLibros[0]++;
            if (libro.getNprestado() > 0) {
                librosPrestados[0]++;

                // Encontrar el libro más prestado
                if (libroMasPrestado[0] == null || libro.getNprestado() > libroMasPrestado[0].getNprestado()) {
                    libroMasPrestado[0] = libro;
                }
            } else {
                librosDisponibles[0]++;
            }
        });

        System.out.println("\nTotal de libros: " + totalLibros[0]);
        System.out.println("Libros prestados: " + librosPrestados[0]);
        System.out.println("Libros disponibles: " + librosDisponibles[0]);

        if (libroMasPrestado[0] != null) {
            System.out.println("Libro más prestado: " + libroMasPrestado[0].getTitulo()
                    + " (" + libroMasPrestado[0].getNprestado() + " préstamos)");
        }
    }

    private static void mostrarPrestamosVencidos(Lista<Prestamo> prestamos) {
        System.out.println("\n=== PRÉSTAMOS VENCIDOS ===");
        if (prestamos.IsEmpty()) {
            System.out.println("No hay préstamos registrados.");
            return;
        }

        final int[] contador = {0};
        prestamos.forEach(prestamo -> {
            if (prestamo.estaVencido()) {
                System.out.println(prestamo);
                contador[0]++;
            }
        });

        if (contador[0] == 0) {
            System.out.println("No hay préstamos vencidos.");
        } else {
            System.out.println("Total de préstamos vencidos: " + contador[0]);
        }
    }

    private static void exportarReportesHTML(String basePath, Lista<Estudiante> Estudiantes,
            Lista<Libro> Libros, Lista<Prestamo> prestamos) {
        try {
            String filePath = basePath + "reportes.html";
            FileWriter writer = new FileWriter(filePath);

            // Encabezado básico HTML
            writer.write("<html><head><title>Reportes Biblioteca</title></head><body>");
            writer.write("<h1>Reportes del Sistema de Biblioteca</h1>");
            writer.write("<p>Generado el " + LocalDate.now() + "</p><hr>");

            // Estadísticas básicas
            writer.write("<h2>Estadísticas</h2>");
            writer.write("<p>Total de estudiantes: " + contarElementos(Estudiantes) + "</p>");
            writer.write("<p>Total de libros: " + contarElementos(Libros) + "</p>");
            writer.write("<p>Total de préstamos: " + contarElementos(prestamos) + "</p>");
            writer.write("<p>Préstamos vencidos: " + contarPrestamosVencidos(prestamos) + "</p>");
            writer.write("<hr>");

            // Lista de estudiantes
            writer.write("<h2>Estudiantes</h2>");
            Nodo<Estudiante> actualEst = Estudiantes.Head;
            while (actualEst != null) {
                writer.write("<p>" + actualEst.getO().getId() + " - "
                        + actualEst.getO().getNombre() + "</p>");
                actualEst = actualEst.next;
            }
            writer.write("<hr>");

            // Lista de libros
            writer.write("<h2>Libros</h2>");
            Nodo<Libro> actualLib = Libros.Head;
            while (actualLib != null) {
                writer.write("<p>" + actualLib.getO().getId() + " - "
                        + actualLib.getO().getTitulo() + " (Prestado "
                        + actualLib.getO().getNprestado() + " veces)</p>");
                actualLib = actualLib.next;
            }
            writer.write("<hr>");

            // Lista de préstamos
            writer.write("<h2>Préstamos</h2>");
            Nodo<Prestamo> actualPres = prestamos.Head;
            while (actualPres != null) {
                Prestamo p = actualPres.getO();
                String estado = p.estaVencido() ? "VENCIDO (" + p.diasRetraso() + " días de retraso)" : "VIGENTE";
                writer.write("<p>" + p.getEstudiante().getNombre() + " - "
                        + p.getLibro().getTitulo() + " - "
                        + p.getFecha_prestamo() + " a "
                        + p.getFecha_devolucion() + " - "
                        + estado + "</p>");
                actualPres = actualPres.next;
            }

            // Cierre del HTML
            writer.write("</body></html>");
            writer.close();

            System.out.println("Reportes exportados a: " + filePath);

        } catch (IOException e) {
            System.out.println("Error al exportar: " + e.getMessage());
        }
    }

// Métodos auxiliares simples (se mantienen igual)
    private static int contarElementos(Lista<?> lista) {
        int count = 0;
        if (lista.Head != null) {
            Nodo<?> actual = lista.Head;
            while (actual != null) {
                count++;
                actual = actual.next;
            }
        }
        return count;
    }

    private static int contarPrestamosVencidos(Lista<Prestamo> prestamos) {
        int count = 0;
        if (prestamos.Head != null) {
            Nodo<Prestamo> actual = prestamos.Head;
            while (actual != null) {
                if (actual.getO().estaVencido()) {
                    count++;
                }
                actual = actual.next;
            }
        }
        return count;
    }
}
