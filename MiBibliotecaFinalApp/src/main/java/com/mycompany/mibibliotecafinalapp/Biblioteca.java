/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author rodri
 */
package com.mycompany.mibibliotecafinalapp; 

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Biblioteca {
    private ArrayList<Libro> libros;
    private HashMap<String, Usuario> usuarios;
    private static final String LIBROS_CSV = "libros.csv";
    private static final String USUARIOS_CSV = "usuarios.csv";
    private static final String INFORMES_TXT = "informes.txt";

    public Biblioteca() {
        libros = new ArrayList<>();
        usuarios = new HashMap<>();
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        cargarLibrosDesdeCSV(LIBROS_CSV);
        cargarUsuariosDesdeCSV(USUARIOS_CSV);
    }

    private void cargarLibrosDesdeCSV(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 3) {
                    String titulo = datos[0];
                    String autor = datos[1];
                    boolean prestado = Boolean.parseBoolean(datos[2]);
                    Libro libro = new Libro(titulo, autor);
                    libro.setPrestado(prestado);
                    libros.add(libro);
                }
            }
            System.out.println("Libros cargados desde " + archivo);
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de libros no encontrado: " + archivo + ". Se creara uno nuevo al guardar.");
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de libros: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ocurrio un error inesperado al cargar libros: " + e.getMessage());
        }
    }

    private void guardarLibrosEnCSV(String archivo) {
        try (FileWriter fw = new FileWriter(archivo)) {
            for (Libro libro : libros) {
                fw.append(libro.getTitulo()).append(",")
                  .append(libro.getAutor()).append(",")
                  .append(String.valueOf(libro.isPrestado())).append("\n");
            }
            System.out.println("Libros guardados en " + archivo);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de libros: " + e.getMessage());
        }
    }

    private void cargarUsuariosDesdeCSV(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 2) {
                    String id = datos[0];
                    String nombre = datos[1];
                    usuarios.put(id, new Usuario(id, nombre));
                }
            }
            System.out.println("Usuarios cargados desde " + archivo);
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de usuarios no encontrado: " + archivo + ". Se creara uno nuevo al guardar.");
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de usuarios: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ocurrio un error inesperado al cargar usuarios: " + e.getMessage());
        }
    }

    private void guardarUsuariosEnCSV(String archivo) {
        try (FileWriter fw = new FileWriter(archivo)) {
            for (Usuario usuario : usuarios.values()) {
                fw.append(usuario.getIdUsuario()).append(",")
                  .append(usuario.getNombre()).append("\n");
            }
            System.out.println("Usuarios guardados en " + archivo);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de usuarios: " + e.getMessage());
        }
    }

    public Libro buscarLibro(String titulo) throws LibroNoEncontradoException {
        for (Libro libro : libros) {
            if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                return libro;
            }
        }
        throw new LibroNoEncontradoException("El libro con el titulo '" + titulo + "' no fue encontrado.");
    }

    public void prestarLibro(String tituloLibro, String idUsuario) throws LibroNoEncontradoException, LibroYaPrestadoException {
        Libro libro = buscarLibro(tituloLibro);
        if (libro.isPrestado()) {
            throw new LibroYaPrestadoException("El libro '" + tituloLibro + "' ya se encuentra prestado.");
        }
        if (!usuarios.containsKey(idUsuario)) {
            System.out.println("Advertencia: El usuario con ID '" + idUsuario + "' no existe. Considera agregarlo antes.");
        }
        libro.setPrestado(true);
        System.out.println("Libro '" + tituloLibro + "' prestado a '" + idUsuario + "'.");
        guardarLibrosEnCSV(LIBROS_CSV);
    }

    public void devolverLibro(String tituloLibro) throws LibroNoEncontradoException {
        Libro libro = buscarLibro(tituloLibro);
        if (!libro.isPrestado()) {
            System.out.println("El libro '" + tituloLibro + "' no estaba prestado, no se puede devolver.");
        } else {
            libro.setPrestado(false);
            System.out.println("Libro '" + tituloLibro + "' devuelto correctamente.");
            guardarLibrosEnCSV(LIBROS_CSV);
        }
    }

    public void agregarLibro(Libro libro) {
        libros.add(libro);
        System.out.println("Libro '" + libro.getTitulo() + "' agregado.");
        guardarLibrosEnCSV(LIBROS_CSV);
    }

    public void agregarUsuario(Usuario usuario) {
        if (usuarios.containsKey(usuario.getIdUsuario())) {
            System.out.println("Error: El usuario con ID '" + usuario.getIdUsuario() + "' ya existe.");
        } else {
            usuarios.put(usuario.getIdUsuario(), usuario);
            System.out.println("Usuario '" + usuario.getNombre() + "' agregado.");
            guardarUsuariosEnCSV(USUARIOS_CSV);
        }
    }

    public void generarInformeLibros() {
        try (FileWriter fw = new FileWriter(INFORMES_TXT)) {
            fw.append("--- Informe de Libros ---\n");
            for (Libro libro : libros) {
                fw.append(libro.toString()).append("\n");
            }
            System.out.println("Informe de libros generado en " + INFORMES_TXT);
        } catch (IOException e) {
            System.err.println("Error al generar el informe de libros: " + e.getMessage());
        }
    }

    public void generarInformeUsuarios() {
        try (FileWriter fw = new FileWriter(INFORMES_TXT, true)) {
            fw.append("\n--- Informe de Usuarios ---\n");
            for (Usuario usuario : usuarios.values()) {
                fw.append(usuario.toString()).append("\n");
            }
            System.out.println("Informe de usuarios adjuntado en " + INFORMES_TXT);
        } catch (IOException e) {
            System.err.println("Error al generar el informe de usuarios: " + e.getMessage());
        }
    }

    public static int obtenerOpcionMenu(Scanner scanner) {
        int opcion = -1;
        while (true) {
            try {
                System.out.print("Ingrese una opcion: ");
                opcion = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida. Por favor, ingrese un numero.");
                scanner.nextLine();
            }
        }
        return opcion;
    }

    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        Scanner scanner = new Scanner(System.in);

        biblioteca.agregarLibro(new Libro("Cien Anos de Soledad", "Gabriel Garcia Marquez"));
        biblioteca.agregarLibro(new Libro("Don Quijote de la Mancha", "Miguel de Cervantes"));
        biblioteca.agregarLibro(new Libro("Los reemplazantes", "Renato Garin Gonzalez"));
        biblioteca.agregarUsuario(new Usuario("U001", "Ana Perez"));
        biblioteca.agregarUsuario(new Usuario("U002", "Juan Gomez"));

        int opcion;
        do {
            System.out.println("\n--- Menu de la Biblioteca DUOC UC ---");
            System.out.println("1. Buscar libro");
            System.out.println("2. Prestar libro");
            System.out.println("3. Devolver libro");
            System.out.println("4. Agregar libro");
            System.out.println("5. Agregar usuario");
            System.out.println("6. Generar informe de libros");
            System.out.println("7. Generar informe de usuarios");
            System.out.println("0. Salir");

            opcion = obtenerOpcionMenu(scanner);

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el titulo del libro a buscar: ");
                    String tituloBuscar = scanner.nextLine();
                    try {
                        Libro libroEncontrado = biblioteca.buscarLibro(tituloBuscar);
                        System.out.println("Libro encontrado: " + libroEncontrado);
                    } catch (LibroNoEncontradoException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 2:
                    System.out.print("Ingrese el titulo del libro a prestar: ");
                    String tituloPrestar = scanner.nextLine();
                    System.out.print("Ingrese el ID del usuario: ");
                    String idUsuarioPrestar = scanner.nextLine();
                    try {
                        biblioteca.prestarLibro(tituloPrestar, idUsuarioPrestar);
                    } catch (LibroNoEncontradoException e) {
                        System.err.println(e.getMessage());
                    } catch (LibroYaPrestadoException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.print("Ingrese el titulo del libro a devolver: ");
                    String tituloDevolver = scanner.nextLine();
                    try {
                        biblioteca.devolverLibro(tituloDevolver);
                    } catch (LibroNoEncontradoException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 4:
                    System.out.print("Ingrese el titulo del nuevo libro: ");
                    String nuevoTitulo = scanner.nextLine();
                    System.out.print("Ingrese el autor del nuevo libro: ");
                    String nuevoAutor = scanner.nextLine();
                    biblioteca.agregarLibro(new Libro(nuevoTitulo, nuevoAutor));
                    break;
                case 5:
                    System.out.print("Ingrese el ID del nuevo usuario: ");
                    String nuevoIdUsuario = scanner.nextLine();
                    System.out.print("Ingrese el nombre del nuevo usuario: ");
                    String nuevoNombreUsuario = scanner.nextLine();
                    biblioteca.agregarUsuario(new Usuario(nuevoIdUsuario, nuevoNombreUsuario));
                    break;
                case 6:
                    biblioteca.generarInformeLibros();
                    break;
                case 7:
                    biblioteca.generarInformeUsuarios();
                    break;
                case 0:
                    System.out.println("Saliendo del programa. Hasta luego!");
                    break;
                default:
                    System.out.println("Opcion no valida. Por favor, intente de nuevo.");
            }
        } while (opcion != 0);

        scanner.close();
    }
}