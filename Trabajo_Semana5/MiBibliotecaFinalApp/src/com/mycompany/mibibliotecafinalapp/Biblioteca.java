/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.mibibliotecafinalapp;

/**
 *
 * @author rodri
 */

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Biblioteca {
    
    // Opte por un ArrayList para almacenar los objetos Libro. Esto me permite mantener el orden
    // en que los libros fueron agregados y facilita la iteración secuencial para listarlos todos.
    // Aunque un ArrayList permite duplicados, la lógica de 'librosUnicos' previene la adicion
    // de libros con el mismo ID.
    private ArrayList<Libro> libros;

    // Para la gestion de usuarios, un HashMap resulto ser la opcion mas adecuada. Al usar el 'idUsuario'
    // como clave, consigo busquedas, inserciones y eliminaciones de usuarios de forma extremadamente rapida
    // (en tiempo constante, O(1) en promedio). Esto es vital para operaciones frecuentes como
    // buscar o eliminar un usuario por su identificador unico.
    private HashMap<String, Usuario> usuarios;

    // Implemente un HashSet complementario a mi ArrayList de libros. Su proposito principal es
    // garantizar la unicidad de cada libro basandose en su ID. Usar un HashSet me proporciona
    // una forma muy eficiente de verificar si un libro ya existe antes de permitir su adicion,
    // lo cual es crucial para la integridad de los datos. Esto requiere que la clase Libro
    // tenga los metodos equals() y hashCode() correctamente implementados para definir la unicidad por ID.
    private HashSet<Libro> librosUnicos;

    // Elegi un TreeSet para mantener una coleccion de usuarios que se mantenga automaticamente
    // ordenada por su nombre. Esto elimina la necesidad de ordenar la lista cada vez que
    // quiero mostrar los usuarios alfabeticamente. Para que esto funcione, la clase Usuario
    // debe implementar la interfaz Comparable<Usuario> y definir la comparacion por el atributo 'nombre'.
    private TreeSet<Usuario> usuariosOrdenadosPorNombre;

   
    // Constantes de Archivos: Organizando la Persistencia 
    
    // Defino constantes para los nombres de los archivos CSV. Esto mejora la legibilidad del codigo
    // y hace que sea sencillo cambiar los nombres de los archivos en un solo lugar si fuera necesario.
    private static final String LIBROS_CSV = "libros.csv";
    private static final String USUARIOS_CSV = "usuarios.csv";
    // Aunque 'informes.txt' no se utiliza en este ejemplo basico, lo inclui como una prevision
    // para futuras funcionalidades de generacion de informes.
    private static final String INFORMES_TXT = "informes.txt";

    
    // Constructor de la clase Biblioteca.
    // Al inicializar la biblioteca, mi primer paso es cargar los datos existentes desde los archivos
    // para recuperar el estado previo del sistema. Luego, es fundamental sincronizar las colecciones
    // auxiliares (HashSet y TreeSet) para que esten al dia con los datos recien cargados,
    // manteniendo asi sus propiedades de unicidad y ordenamiento.
     
    public Biblioteca() {
        libros = new ArrayList<>();
        usuarios = new HashMap<>();
        librosUnicos = new HashSet<>();
        usuariosOrdenadosPorNombre = new TreeSet<>();

        cargarLibros(); // Carga los libros al iniciar.
        cargarUsuarios(); // Carga los usuarios al iniciar.
        // La sincronizacion es crucial para asegurar que librosUnicos y usuariosOrdenadosPorNombre
        // contengan todos los datos cargados y mantengan sus propiedades de colección.
        sincronizarColeccionesAuxiliares();
    }

    // Agrega un nuevo libro a la biblioteca.
    // Antes de añadir un libro, realizo una verificacion de unicidad usando el HashSet 'librosUnicos'.
    // Esto me permite detectar y prevenir eficientemente (en tiempo casi constante) la adicion
    // de libros con IDs duplicados, manteniendo la integridad de mi catalogo.
    // @param libro El objeto Libro a añadir.
    // @return true si el libro se añadio exitosamente, false si ya existe un libro con el mismo ID.
     
    public boolean agregarLibro(Libro libro) {
        if (librosUnicos.contains(libro)) {
            System.out.println("Error: Ya existe un libro con el ID " + libro.getIdLibro() + ".");
            return false;
        }
        libros.add(libro); // Lo añado al ArrayList para la lista general.
        librosUnicos.add(libro); // Tambien lo agrego al HashSet para mantener el control de unicidad.
        System.out.println("Libro '" + libro.getTitulo() + "' agregado exitosamente.");
        return true;
    }

    // Busca un libro por su ID.
    // Esta implementación realiza una busqueda lineal (O(n)) a traves del ArrayList de libros.
    // Si bien es funcional para colecciones pequeñas a medianas, reconozco que para bibliotecas
    // con un volumen de libros muy grande y busquedas constantes, una optimizacion podria ser
    // introducir un HashMap adicional para libros (similar a como manejo los usuarios), lo que permitiria
    // busquedas en tiempo constante. Sin embargo, para este alcance, el ArrayList es suficiente.
    // @param idLibro El ID del libro a buscar.
    // @return El objeto Libro si se encuentra, o null si no existe.
     
    public Libro buscarLibroPorId(String idLibro) {
        for (Libro libro : libros) {
            if (libro.getIdLibro().equals(idLibro)) {
                return libro;
            }
        }
        return null;
    }

    
    // Elimina un libro de la biblioteca por su ID.
    // Primero, busco el libro en el ArrayList. Si lo encuentro, lo remuevo de ambas colecciones:
     // del ArrayList principal y del HashSet de libros unicos. Esto asegura la coherencia
     // en el estado de todos mis contenedores de datos.
     // @param idLibro El ID del libro a eliminar.
     // @return true si el libro fue eliminado, false si no se encontro.
     
    public boolean eliminarLibro(String idLibro) {
        Libro libroAEliminar = null;
        for (Libro libro : libros) {
            if (libro.getIdLibro().equals(idLibro)) {
                libroAEliminar = libro;
                break;
            }
        }
        if (libroAEliminar != null) {
            libros.remove(libroAEliminar);
            librosUnicos.remove(libroAEliminar); // Es crucial eliminarlo tambien del HashSet.
            System.out.println("Libro con ID " + idLibro + " eliminado exitosamente.");
            return true;
        }
        System.out.println("Error: Libro con ID " + idLibro + " no encontrado.");
        return false;
    }

    
     // Muestra todos los libros actualmente registrados en la biblioteca.
     // Simplemente itero sobre el ArrayList 'libros', lo que presenta los libros
     // en el orden en que fueron agregados o cargados.
     
    public void listarLibros() {
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la biblioteca.");
            return;
        }
        System.out.println("\n--- Lista de Libros ---");
        for (Libro libro : libros) {
         // Asumo que la clase Libro tiene un metodo toString() bien definido para una salida legible.
            System.out.println(libro);
        }
    }

     // Registra un nuevo usuario en la biblioteca.
     // Utilizo el HashMap para verificar eficientemente si ya existe un usuario con el mismo ID.
     // Si el ID es unico, el usuario se añade tanto al HashMap (para busquedas rapidas por ID)
     // como al TreeSet (para mantener una lista de usuarios siempre ordenada por nombre).
     // @param usuario El objeto Usuario a registrar.
     // @return true si el usuario se registro exitosamente, false si ya existe un usuario con el mismo ID.
     
    public boolean registrarUsuario(Usuario usuario) {
        if (usuarios.containsKey(usuario.getIdUsuario())) { // Verificación O(1) con HashMap.
            System.out.println("Error: Ya existe un usuario con el ID " + usuario.getIdUsuario() + ".");
            return false;
        }
        usuarios.put(usuario.getIdUsuario(), usuario); // Lo agrego al HashMap.
        usuariosOrdenadosPorNombre.add(usuario); // Lo añado al TreeSet para el ordenamiento automatico.
        System.out.println("Usuario '" + usuario.getNombre() + "' registrado exitosamente.");
        return true;
    }

    
     // Busca un usuario por su ID.
     // Gracias al uso del HashMap, esta es una operacion muy eficiente, con un tiempo
     // de ejecucion practicamente constante (O(1)).
     // @param idUsuario El ID del usuario a buscar.
     // @return El objeto Usuario si se encuentra, o null si no existe.
     
    public Usuario buscarUsuarioPorId(String idUsuario) {
        return usuarios.get(idUsuario); // Acceso directo por clave en el HashMap.
    }

    
     // Elimina un usuario de la biblioteca por su ID.
     // La eliminacion del HashMap es muy rapida. Es crucial tambien remover el usuario
     // del TreeSet para asegurar que todas mis estructuras de datos se mantengan coherentes.
     // @param idUsuario El ID del usuario a eliminar.
     // @return true si el usuario fue eliminado, false si no se encontro.
     
    public boolean eliminarUsuario(String idUsuario) {
        Usuario usuarioAEliminar = usuarios.remove(idUsuario); // Eliminacion eficiente con HashMap.
        if (usuarioAEliminar != null) {
            usuariosOrdenadosPorNombre.remove(usuarioAEliminar); // Tambien lo remuevo del TreeSet.
            System.out.println("Usuario con ID " + idUsuario + " eliminado exitosamente.");
            return true;
        }
        System.out.println("Error: Usuario con ID " + idUsuario + " no encontrado.");
        return false;
    }

    
     // Muestra todos los usuarios en la biblioteca, presentados en orden alfabetico por su nombre.
     // Esto se logra simplemente iterando sobre el TreeSet 'usuariosOrdenadosPorNombre',
     // el cual mantiene los elementos ordenados de forma inherente.
     
    public void listarUsuariosOrdenados() {
        if (usuariosOrdenadosPorNombre.isEmpty()) {
            System.out.println("No hay usuarios registrados en la biblioteca.");
            return;
        }
        System.out.println("\n--- Lista de Usuarios (Ordenados por Nombre) ---");
        for (Usuario usuario : usuariosOrdenadosPorNombre) {
            // Asumo que la clase Usuario tiene un metodo toString() bien definido.
            System.out.println(usuario);
        }
    }

     // Carga los datos de los libros desde el archivo CSV.
     // Este metodo se ejecuta al inicio para restaurar el estado de los libros
     // de la ultima sesion. Incorpora un control para verificar si el archivo existe
     // y maneja posibles errores de lectura para una carga robusta de los datos.
     // Cada línea se parsea para reconstruir un objeto Libro.
     
    private void cargarLibros() {
        File file = new File(LIBROS_CSV);
        if (!file.exists()) {
            System.out.println("Archivo de libros no encontrado: " + LIBROS_CSV + ". Se creara uno nuevo al guardar.");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) { // Espero el formato: ID, Titulo, Autor, Disponible
                    String id = data[0];
                    String titulo = data[1];
                    String autor = data[2];
                    boolean disponible = Boolean.parseBoolean(data[3]);
                    Libro libro = new Libro(id, titulo, autor);
                    libro.setDisponible(disponible);
                    libros.add(libro); // Solo lo añado al ArrayList; la sincronizacion posterior se encargara del HashSet.
                }
            }
            System.out.println("Libros cargados exitosamente desde " + LIBROS_CSV);
        } catch (IOException e) {
            System.err.println("Error al cargar libros: " + e.getMessage());
        }
    }

    
     // Guarda el estado actual de los libros en el archivo CSV.
     // Esta funcion es esencial para la persistencia. Recorro mi coleccion de libros
     // y escribo cada uno en una nueva línea del archivo CSV, asegurando que todos
     // los cambios realizados durante la ejecucion se guarden para la proxima sesion.
     
    public void guardarLibros() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(LIBROS_CSV))) {
            for (Libro libro : libros) {
                bw.write(libro.getIdLibro() + "," + libro.getTitulo() + "," + libro.getAutor() + "," + libro.isDisponible());
                bw.newLine(); // Cada libro en una nueva línea para facilitar el parseo.
            }
            System.out.println("Libros guardados exitosamente en " + LIBROS_CSV);
        } catch (IOException e) {
            System.err.println("Error al guardar libros: " + e.getMessage());
        }
    }

    
     // Carga los datos de los usuarios desde el archivo CSV.
     // Similar al proceso de carga de libros, este metodo recupera la informacion de los usuarios
     // al inicio de la aplicacion. Maneja posibles errores de archivo y parsea cada linea
     // para reconstruir los objetos Usuario y añadirlos al HashMap.
     
    private void cargarUsuarios() {
        File file = new File(USUARIOS_CSV);
        if (!file.exists()) {
            System.out.println("Archivo de usuarios no encontrado: " + USUARIOS_CSV + ". Se creara uno nuevo al guardar.");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) { // Espero el formato: ID, Nombre
                    String id = data[0];
                    String nombre = data[1];
                    Usuario usuario = new Usuario(id, nombre);
                    usuarios.put(id, usuario); // Solo lo añado al HashMap; el TreeSet se sincronizara despues.
                }
            }
            System.out.println("Usuarios cargados exitosamente desde " + USUARIOS_CSV);
        } catch (IOException e) {
            System.err.println("Error al cargar usuarios: " + e.getMessage());
        }
    }
    
     // Guarda el estado actual de los usuarios en el archivo CSV.
     // Este metodo asegura que los datos de los usuarios persistan.
     // Recorro los valores (los objetos Usuario) de mi HashMap y los escribo
     // en el archivo CSV, manteniendo el registro de todos los usuarios.
     
    public void guardarUsuarios() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USUARIOS_CSV))) {
            for (Usuario usuario : usuarios.values()) { // Obtengo los objetos Usuario directamente del HashMap.
                bw.write(usuario.getIdUsuario() + "," + usuario.getNombre());
                bw.newLine();
            }
            System.out.println("Usuarios guardados exitosamente en " + USUARIOS_CSV);
        } catch (IOException e) {
            System.err.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    
     // Sincroniza las colecciones auxiliares ('librosUnicos' y 'usuariosOrdenadosPorNombre')
     // despues de haber cargado los datos principales desde los archivos.
     // Esto es una etapa crucial en el inicio de la aplicacion, ya que 'cargarLibros()'
     // y 'cargarUsuarios()' solo llenan el ArrayList principal y el HashMap.
     // Este metodo asegura que las estructuras de datos auxiliares reflejen fielmente
     // los datos cargados y mantengan sus propiedades de unicidad y ordenamiento.
     
    private void sincronizarColeccionesAuxiliares() {
        // Sincronizo 'librosUnicos': primero lo limpio y luego añado todos los libros del ArrayList.
        librosUnicos.clear();
        librosUnicos.addAll(libros);

        // Sincronizo 'usuariosOrdenadosPorNombre': lo limpio y luego añado todos los usuarios del HashMap.
        usuariosOrdenadosPorNombre.clear();
        usuariosOrdenadosPorNombre.addAll(usuarios.values());
    }

    // Entrada de la Aplicación: El menu interactivo

    
     // Metodo principal (main) para iniciar y gestionar la aplicacion de la biblioteca.
     // Este metodo configura la interfaz de usuario basada en consola, permitiendo al usuario
     // interactuar con el sistema para realizar diversas operaciones de gestion.
     // Incluyo manejo de excepciones para la entrada del usuario (`InputMismatchException`)
     // y otros errores generales, haciendo la aplicacion mas robusta y amigable.
     
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Biblioteca biblioteca = new Biblioteca(); // Al instanciar, se cargan los datos automaticamente.

        int opcion;
        do {
            System.out.println("\n--- Menu de la Biblioteca ---");
            System.out.println("1. Agregar Libro");
            System.out.println("2. Eliminar Libro");
            System.out.println("3. Buscar Libro por ID");
            System.out.println("4. Listar Libros");
            System.out.println("5. Registrar Usuario");
            System.out.println("6. Eliminar Usuario");
            System.out.println("7. Buscar Usuario por ID");
            System.out.println("8. Listar Usuarios Ordenados por Nombre");
            System.out.println("9. Guardar Datos y Salir");
            System.out.print("Ingrese su opcion: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consumo el salto de linea pendiente despues de leer el numero.

                switch (opcion) {
                    case 1:
                        System.out.print("ID del libro: ");
                        String idLibro = scanner.nextLine();
                        System.out.print("Titulo del libro: ");
                        String titulo = scanner.nextLine();
                        System.out.print("Autor del libro: ");
                        String autor = scanner.nextLine();
                        biblioteca.agregarLibro(new Libro(idLibro, titulo, autor));
                        break;
                    case 2:
                        System.out.print("ID del libro a eliminar: ");
                        String idEliminarLibro = scanner.nextLine();
                        biblioteca.eliminarLibro(idEliminarLibro);
                        break;
                    case 3:
                        System.out.print("ID del libro a buscar: ");
                        String idBuscarLibro = scanner.nextLine();
                        Libro libroEncontrado = biblioteca.buscarLibroPorId(idBuscarLibro);
                        if (libroEncontrado != null) {
                            System.out.println("Libro encontrado: " + libroEncontrado);
                        } else {
                            System.out.println("Libro no encontrado.");
                        }
                        break;
                    case 4:
                        biblioteca.listarLibros();
                        break;
                    case 5:
                        System.out.print("ID del usuario: ");
                        String idUsuario = scanner.nextLine();
                        System.out.print("Nombre del usuario: ");
                        String nombreUsuario = scanner.nextLine();
                        biblioteca.registrarUsuario(new Usuario(idUsuario, nombreUsuario));
                        break;
                    case 6:
                        System.out.print("ID del usuario a eliminar: ");
                        String idEliminarUsuario = scanner.nextLine();
                        biblioteca.eliminarUsuario(idEliminarUsuario);
                        break;
                    case 7:
                        System.out.print("ID del usuario a buscar: ");
                        String idBuscarUsuario = scanner.nextLine();
                        Usuario usuarioEncontrado = biblioteca.buscarUsuarioPorId(idBuscarUsuario);
                        if (usuarioEncontrado != null) {
                            System.out.println("Usuario encontrado: " + usuarioEncontrado);
                        } else {
                            System.out.println("Usuario no encontrado.");
                        }
                        break;
                    case 8:
                        biblioteca.listarUsuariosOrdenados();
                        break;
                    case 9:
                        // Antes de finalizar el programa, me aseguro de guardar todos los datos
                        // para que los cambios no se pierdan.
                        biblioteca.guardarLibros();
                        biblioteca.guardarUsuarios();
                        System.out.println("Datos guardados. Saliendo de la aplicacion...");
                        break;
                    default:
                        System.out.println("Opcion no valida. Por favor, intente de nuevo.");
                }
            } catch (InputMismatchException e) {
                // Capturo esta excepcion si el usuario ingresa texto en lugar de un numero para la opcion.
                System.out.println("Entrada invalida. Por favor, ingrese un numero.");
                scanner.nextLine(); // Es crucial consumir la linea incorrecta para evitar un bucle infinito.
                opcion = 0; // Reinicio la opcion para que el menu se muestre de nuevo.
            } catch (Exception e) {
                // Captura cualquier otro error inesperado que pueda ocurrir, para evitar que la aplicacion se cierre bruscamente.
                System.err.println("Ocurrio un error inesperado: " + e.getMessage());
                opcion = 0; // Igualmente, reinicio la opcion.
            }

        } while (opcion != 9); // El bucle continua hasta que el usuario elige salir (opcion 9).

        scanner.close(); // Siempre cierro el Scanner para liberar los recursos del sistema.
    }
}