package clases.lenguajes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Lenguajes {

    private static boolean validarFormatoFecha(String fecha) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(fecha, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    public static void main(String[] args) {
        String basePath = "C:\\Users\\josem\\Documents\\6to Semestre\\LF&A\\Proyecto-de-Lenguajes\\LenguajesFixed\\";
        
        Lista<Estudiante> Estudiantes = new Lista<>();
        Lista<Libro> Libros = new Lista<>();
        Lista<Prestamo> prestamos = new Lista<>();

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
                case 1 -> cargarUsuarios(basePath, Estudiantes);
                case 2 -> cargarLibros(basePath, Libros);
                case 3 -> cargarPrestamos(basePath, Estudiantes, Libros, prestamos);
                case 4 -> mostrarPrestamos(prestamos);
                case 5 -> mostrarEstudiantes(Estudiantes);
                case 6 -> mostrarLibrosPrestados(Libros);
                case 7 -> mostrarEstadisticas(prestamos, Libros);
                case 8 -> mostrarPrestamosVencidos(prestamos);
                case 9 -> exportarReportesHTML(basePath, Estudiantes, Libros, prestamos);
                case 10 -> {
                    est = false;
                    System.out.println("Saliendo del sistema...");
                }
                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (est);
    }
    
    private static void cargarUsuarios(String basePath, Lista<Estudiante> Estudiantes) {
        int estudiantesCargados = 0;
        int estudiantesDuplicados = 0;
        int lineasProcesadas = 0;
        
        try {
            String dir = basePath + "Usuarios.txt";
            File archivo = new File(dir);
            BufferedReader lector = new BufferedReader(new FileReader(archivo));
            
            String encabezado = lector.readLine();
            if (encabezado == null || !encabezado.equals("id_usuario,nombre_usuario")) {
                System.out.println("Formato de encabezado incorrecto o archivo vacío.");
                return;
            }
            
            String linea;
            while ((linea = lector.readLine()) != null) {
                lineasProcesadas++;
                if (linea.trim().isEmpty()) continue;
                
                String[] campos = linea.split(",", 2);
                if (campos.length < 2) {
                    System.out.println("Línea incompleta en línea " + lineasProcesadas);
                    continue;
                }
                
                String idStr = campos[0].trim();
                String nombre = campos[1].trim();
                
                try {
                    int id = Integer.parseInt(idStr);
                    
                    Estudiante nuevo = new Estudiante();
                    nuevo.setId(id);
                    nuevo.setNombre(nombre);
                    
                    if (Estudiantes.Search(nuevo) == null) {
                        Estudiantes.add(nuevo);
                        estudiantesCargados++;
                    } else {
                        estudiantesDuplicados++;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID inválido en línea " + lineasProcesadas + ": " + idStr);
                }
            }
            
            System.out.println("Estudiantes cargados: " + estudiantesCargados);
            System.out.println("Estudiantes duplicados omitidos: " + estudiantesDuplicados);
            
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de usuarios: " + e.getMessage());
        }
    }
    
    private static void cargarLibros(String basePath, Lista<Libro> Libros) {
        int librosCargados = 0;
        int librosDuplicados = 0;
        int lineasProcesadas = 0;
        
        try {
            String dir = basePath + "Libros.txt";
            File archivo = new File(dir);
            BufferedReader lector = new BufferedReader(new FileReader(archivo));
            
            String encabezado = lector.readLine();
            if (encabezado == null || !encabezado.equals("id_libro,titulo_libro")) {
                System.out.println("Formato de encabezado incorrecto o archivo vacío.");
                return;
            }
            
            String linea;
            while ((linea = lector.readLine()) != null) {
                lineasProcesadas++;
                if (linea.trim().isEmpty()) continue;
                
                String[] campos = linea.split(",", 2);
                if (campos.length < 2) {
                    System.out.println("Línea incompleta en línea " + lineasProcesadas);
                    continue;
                }
                
                String id = campos[0].trim();
                String titulo = campos[1].trim();
                
                Libro nuevo = new Libro();
                nuevo.setId(id);
                nuevo.setTitulo(titulo);
                
                if (Libros.Search(nuevo) == null) {
                    Libros.add(nuevo);
                    librosCargados++;
                } else {
                    librosDuplicados++;
                }
            }
            
            System.out.println("Libros cargados: " + librosCargados);
            System.out.println("Libros duplicados omitidos: " + librosDuplicados);
            
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de libros: " + e.getMessage());
        }
    }
    
    private static void cargarPrestamos(String basePath, Lista<Estudiante> Estudiantes, Lista<Libro> Libros, Lista<Prestamo> prestamos) {
        int prestamosCargados = 0;
        int prestamosInvalidos = 0;
        int lineasProcesadas = 0;
        
        try {
            String dir = basePath + "Biblioteca.txt";
            File archivo = new File(dir);
            BufferedReader lector = new BufferedReader(new FileReader(archivo));
            
            String encabezado = lector.readLine();
            if (encabezado == null || !encabezado.equals("id_usuario,nombre_usuario,id_libro,titulo_libro,fecha_prestamo,fecha_devolucion")) {
                System.out.println("Formato de encabezado incorrecto o archivo vacío.");
                return;
            }
            
            String linea;
            while ((linea = lector.readLine()) != null) {
                lineasProcesadas++;
                if (linea.trim().isEmpty()) continue;
                
                String[] campos = linea.split(",", 6);
                if (campos.length < 6) {
                    System.out.println("Línea incompleta en línea " + lineasProcesadas);
                    prestamosInvalidos++;
                    continue;
                }
                
                String idUsuario = campos[0].trim();
                String nombreUsuario = campos[1].trim();
                String idLibro = campos[2].trim();
                String tituloLibro = campos[3].trim();
                String fechaPrestamo = campos[4].trim();
                String fechaDevolucion = campos[5].trim();
                
                if (fechaPrestamo.isEmpty() || fechaDevolucion.isEmpty()) {
                    System.out.println("Fecha de préstamo o devolución vacía en línea " + lineasProcesadas);
                    prestamosInvalidos++;
                    continue;
                }
                
                if (!validarFormatoFecha(fechaPrestamo) || !validarFormatoFecha(fechaDevolucion)) {
                    System.out.println("Formato de fecha inválido en línea " + lineasProcesadas);
                    prestamosInvalidos++;
                    continue;
                }
                
                Estudiante estudianteBusqueda = new Estudiante();
                try {
                    estudianteBusqueda.setId(Integer.parseInt(idUsuario));
                } catch (NumberFormatException e) {
                    System.out.println("ID de usuario inválido en línea " + lineasProcesadas + ": " + idUsuario);
                    prestamosInvalidos++;
                    continue;
                }
                estudianteBusqueda.setNombre(nombreUsuario);
                
                Estudiante estudiante = Estudiantes.Search(estudianteBusqueda);
                if (estudiante == null) {
                    System.out.println("Estudiante no encontrado en línea " + lineasProcesadas + ": " + idUsuario);
                    prestamosInvalidos++;
                    continue;
                }
                
                Libro libroBusqueda = new Libro();
                libroBusqueda.setId(idLibro);
                libroBusqueda.setTitulo(tituloLibro);
                
                Libro libro = Libros.Search(libroBusqueda);
                if (libro == null) {
                    System.out.println("Libro no encontrado en línea " + lineasProcesadas + ": " + idLibro);
                    prestamosInvalidos++;
                    continue;
                }
                
                Prestamo prestamo = new Prestamo(fechaPrestamo, fechaDevolucion, estudiante, libro);
                prestamos.add(prestamo);
                prestamosCargados++;
                
                libro.contarPrestamo(fechaPrestamo, fechaDevolucion);
            }
            
            System.out.println("Préstamos cargados exitosamente: " + prestamosCargados);
            System.out.println("Préstamos inválidos: " + prestamosInvalidos);
            System.out.println("Total de líneas procesadas: " + lineasProcesadas);
            
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de préstamos: " + e.getMessage());
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
            System.out.println("Libro más prestado: " + libroMasPrestado[0].getTitulo() + 
                               " (" + libroMasPrestado[0].getNprestado() + " préstamos)");
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
            writer.write("<p>" + actualEst.getO().getId() + " - " + 
                        actualEst.getO().getNombre() + "</p>");
            actualEst = actualEst.next;
        }
        writer.write("<hr>");
        
        // Lista de libros
        writer.write("<h2>Libros</h2>");
        Nodo<Libro> actualLib = Libros.Head;
        while (actualLib != null) {
            writer.write("<p>" + actualLib.getO().getId() + " - " + 
                        actualLib.getO().getTitulo() + " (Prestado " + 
                        actualLib.getO().getNprestado() + " veces)</p>");
            actualLib = actualLib.next;
        }
        writer.write("<hr>");
        
        // Lista de préstamos
        writer.write("<h2>Préstamos</h2>");
        Nodo<Prestamo> actualPres = prestamos.Head;
        while (actualPres != null) {
            Prestamo p = actualPres.getO();
            String estado = p.estaVencido() ? "VENCIDO (" + p.diasRetraso() + " días de retraso)" : "VIGENTE";
            writer.write("<p>" + p.getEstudiante().getNombre() + " - " + 
                        p.getLibro().getTitulo() + " - " + 
                        p.getFecha_prestamo() + " a " + 
                        p.getFecha_devolucion() + " - " + 
                        estado + "</p>");
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