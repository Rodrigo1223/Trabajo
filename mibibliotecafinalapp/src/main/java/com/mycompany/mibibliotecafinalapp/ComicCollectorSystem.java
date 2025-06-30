/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.mibibliotecafinalapp;
/**
 *
 * @author rodri
 */
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Gestiona la coleccion de comics y la informacion de usuarios en el sistema
 * Sirve como el punto central para todas las operaciones de la tienda,
 * desde la gestion de inventario hasta el registro de usuarios y la generacion de informes
 */
public class ComicCollectorSystem {
    // Almacena todos los comics disponibles en la coleccion.
    // Se usa ArrayList,lo que implica gestion manual de duplicados
    // de duplicados debe hacerse manualmente al añadir nuevos comics.
    private ArrayList<Comic> comics;
    // Almacena los usuarios registrados en el sistema.
    // Usa HashMap para permitir busquedas rapidas por el ID del usuario,
    // y para asegurar que cada ID de usuario sea unico.
    private HashMap<String, Usuario> usuarios;
    
    // Constantes para los nombres de los archivos de persistencia.
    // Esto centraliza las rutas y facilita cualquier cambio futuro.
    private static final String COMICS_CSV = "comics.csv"; 
    private static final String USUARIOS_CSV = "usuarios.csv";
    private static final String INFORMES_TXT = "informes.txt";
    
    /**
     * Constructor de la clase ComicCollectorSystem.
     * Inicializa las colecciones de cómics y usuarios, y luego carga los datos
     * persistidos desde los archivos CSV al iniciar la aplicacion.
     */
    public ComicCollectorSystem() {
        comics = new ArrayList<>();
        usuarios = new HashMap<>();
        cargarDatosIniciales();// Llama a la funcion para precargar la informacion
    }

    private void cargarDatosIniciales() {
        cargarComicsDesdeCSV(COMICS_CSV);
        cargarUsuariosDesdeCSV(USUARIOS_CSV);
    }

    /**
 * Carga los comics desde un archivo CSV al iniciar la aplicacion
 * Utiliza try-with-resources para asegurar que el archivo se cierre correctamente
 *
 * @param archivo La ruta del archivo CSV de comics.
 */
    private void cargarComicsDesdeCSV(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                // Validar que la linea tenga el numero correcto de campos para un Comic.
                if (datos.length == 6) {
                    // Analisis seguro de los datos de cada columna.
                    String titulo = datos[0];
                    String autor = datos[1];
                    String genero = datos[2];
                    String fechaPublicacion = datos[3];
                    double precio = Double.parseDouble(datos[4]);
                    boolean disponible = Boolean.parseBoolean(datos[5]);

                    Comic comic = new Comic(titulo, autor, genero, fechaPublicacion, precio);
                    comic.setDisponible(disponible); 
                    
                    // IMPORTANTE: Como 'comics' es un ArrayList (por requisito),
                    // debemos verificar manualmente si el cómic ya existe para evitar duplicados.
                    boolean existe = comics.stream().anyMatch(c ->
                        c.getTitulo().equalsIgnoreCase(comic.getTitulo()) &&
                        c.getAutor().equalsIgnoreCase(comic.getAutor())
                    );
                    if (!existe) {
                        comics.add(comic);
                    }
            } else {
                // Esto ayuda a depurar si hay lineas malformadas en el CSV
                System.err.println("Advertencia: Línea con formato incorrecto en " + archivo + ": " + linea);
            }
        }
          System.out.println("Comics cargados desde " + archivo);
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de comics no encontrado: " + archivo + ". Se creara uno nuevo al guardar.");
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de comics: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error de formato numerico al cargar comics: " + e.getMessage());
        } catch (Exception e) { 
             // Captura cualquier otro error inesperado durante la carga.
            System.err.println("Ocurrio un error inesperado al cargar comics: " + e.getMessage());
        }
    }

    /**
     * Guarda el estado actual de la colección de comics en un archivo CSV.
     * Cada comic se guarda en una linea separada con sus atributos delimitados por comas.
     * Utiliza try-with-resources para asegurar que el FileWriter se cierre automaticamente.
     * @param archivo El nombre del archivo CSV donde se guardaran los comics.
     */
    private void guardarComicsEnCSV(String archivo) {
        try (FileWriter fw = new FileWriter(archivo)) {
            for (Comic comic : comics) {
                // Escribe los atributos del cómic en formato CSV.
                fw.append(comic.getTitulo()).append(",")
                  .append(comic.getAutor()).append(",")
                  .append(comic.getGenero()).append(",")
                  .append(comic.getFechaPublicacion()).append(",")
                  .append(String.valueOf(comic.getPrecio())).append(",")
                  .append(String.valueOf(comic.isDisponible())).append("\n");
            }
            System.out.println("Comics guardados en " + archivo);
        } catch (IOException e) {
            // Maneja errores que puedan ocurrir durante la escritura del archivo.
            System.err.println("Error al escribir en el archivo de comics: " + e.getMessage());
        }
    }
    /**
     * Carga los usuarios desde un archivo CSV.
     * Cada linea del archivo debe contener el ID y el nombre del usuario.
     * Utiliza try-with-resources para asegurar el cierre automatico del BufferedReader.
     * @param archivo El nombre del archivo CSV de usuarios.
     */
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
            // Informa si el archivo de usuarios no existe.
            System.out.println("Archivo de usuarios no encontrado: " + archivo + ". Se creara uno nuevo al guardar.");
        } catch (IOException e) {
            // Captura errores durante la lectura del archivo.
            System.err.println("Error al leer el archivo de usuarios: " + e.getMessage());
        } catch (Exception e) { 
            // Captura cualquier otro error inesperado durante la carga de usuarios.
            System.err.println("Ocurrio un error inesperado al cargar usuarios: " + e.getMessage());
        }
    }
    /**
     * Guarda el estado actual de los usuarios en un archivo CSV.
     * Cada usuario se guarda en una linea separada con su ID y nombre.
     * Utiliza try-with-resources para asegurar el cierre automatico del FileWriter.
     * @param archivo El nombre del archivo CSV donde se guardaran los usuarios.
     */
    private void guardarUsuariosEnCSV(String archivo) {
        try (FileWriter fw = new FileWriter(archivo)) {
            for (Usuario usuario : usuarios.values()) {
                // Escribe el ID y el nombre del usuario en formato CSV.
                fw.append(usuario.getIdUsuario()).append(",")
                  .append(usuario.getNombre()).append("\n");
            }
            System.out.println("Usuarios guardados en " + archivo);
        } catch (IOException e) {
            // Maneja errores que puedan ocurrir durante la escritura del archivo.
            System.err.println("Error al escribir en el archivo de usuarios: " + e.getMessage());
        }
    }

     /**
     * Busca un cómic dentro de la colección por su título.
     * Realiza una busqueda lineal a traves del ArrayList de comics.
     * @param titulo El titulo del comic a buscar (no sensible a mayusculas/minusculas).
     * @return El objeto Comic si se encuentra.
     * @throws ComicNoEncontradoException Si no se encuentra ningun comic con el titulo especificado.
     */
    public Comic buscarComic(String titulo) throws ComicNoEncontradoException {
        for (Comic comic : comics) {
            if (comic.getTitulo().equalsIgnoreCase(titulo)) {
                return comic; // Retorna el cómic tan pronto como lo encuentra.
            }
        }
        // Si el bucle termina sin encontrar el comic, significa que no existe en la coleccion.
        throw new ComicNoEncontradoException("El comic con el titulo '" + titulo + "' no fue encontrado.");
    }
    /**
     * Marca un comic como no disponible (simulando un prestamo o venta) para un usuario.
     * Verifica la disponibilidad del comic y la existencia del usuario.
     * @param tituloComic El titulo del comic a gestionar.
     * @param idUsuario El ID del usuario que adquiere el comic.
     * @throws ComicNoEncontradoException Si el comic no existe en la coleccion.
     * @throws ComicYaPrestadoException Si el comic ya esta marcado como no disponible.
     */
    public void prestarComic(String tituloComic, String idUsuario) throws ComicNoEncontradoException, ComicYaPrestadoException {
        Comic comic = buscarComic(tituloComic);
        // Primero, intenta encontrar el cómic.
        // Si el comic existe, verifica su estado de disponibilidad.      
        if (!comic.isDisponible()) {
            throw new ComicYaPrestadoException("El comic '" + tituloComic + "' ya no esta disponible.");
        }
        
        // Opcional: Advertir si el usuario no existe. La operacion de prestamo/venta
        // puede continuar, pero se le informa al operador sobre el usuario no registrado.
        if (!usuarios.containsKey(idUsuario)) {
            System.out.println("Advertencia: El ID de usuario '" + idUsuario + "' no está registrado. Continúe, pero considere agregarlo.");
        }
        comic.setDisponible(false); // Cambia el estado del comic a no disponible.
        System.out.println("Comic '" + tituloComic + "' marcado como no disponible (prestado/vendido) a '" + idUsuario + "'.");
        guardarComicsEnCSV(COMICS_CSV);// Persiste el cambio de estado en el archivo.
    }

    /**
     * Marca un comic como disponible nuevamente (simulando una devolucion).
     * @param tituloComic El titulo del comic a devolver.
     * @throws ComicNoEncontradoException Si el comic no existe en la coleccion.
     */
    public void devolverComic(String tituloComic) throws ComicNoEncontradoException {
        Comic comic = buscarComic(tituloComic);
        // Primero, encuentra el comic.
        // Verifica si el comic ya esta disponible.
        if (comic.isDisponible()) {
            System.out.println("El comic '" + tituloComic + "' no estaba prestado/vendido, no se puede devolver.");
        } else {
            comic.setDisponible(true); // Marca el comic como disponible.
            System.out.println("Comic '" + tituloComic + "' devuelto correctamente.");
            guardarComicsEnCSV(COMICS_CSV);// Persiste el cambio de estado.
        }
    }
     /**
     * Agrega un nuevo comic a la coleccion.
     * Incluye una validacion para evitar añadir comics duplicados (basado en titulo y autor).
     * @param comic El objeto Comic a agregar.
     */
    public void agregarComic(Comic comic) {
        // Realiza una busqueda para ver si ya existe un comic con el mismo titulo y autor.
        // Esto es necesario porque 'comics' es un ArrayList y no garantiza unicidad por si mismo.
        boolean existe = comics.stream().anyMatch(c ->
            c.getTitulo().equalsIgnoreCase(comic.getTitulo()) &&
            c.getAutor().equalsIgnoreCase(comic.getAutor())
        );

        if (existe) {
            System.out.println("Error: El comic '" + comic.getTitulo() + "' de '" + comic.getAutor() + "' ya existe.");
        } else {
            comics.add(comic);  // Añade el comic si no es un duplicado.
            System.out.println("Comic '" + comic.getTitulo() + "' agregado.");
            guardarComicsEnCSV(COMICS_CSV); // Guarda la coleccion actualizada.
        }
    }
     /**
     * Agrega un nuevo usuario al sistema.
     * Verifica si ya existe un usuario con el mismo ID antes de añadirlo.
     * @param usuario El objeto Usuario a agregar.
     */
    public void agregarUsuario(Usuario usuario) {
        // HashMap ya maneja la unicidad de claves, pero esta verificacion
        // proporciona un mensaje más claro al usuario si el ID ya esta en uso.
        if (usuarios.containsKey(usuario.getIdUsuario())) {
            System.out.println("Error: El usuario con ID '" + usuario.getIdUsuario() + "' ya existe.");
        } else {
            usuarios.put(usuario.getIdUsuario(), usuario);// Añade el usuario al mapa.
            System.out.println("Usuario '" + usuario.getNombre() + "' agregado.");
            guardarUsuariosEnCSV(USUARIOS_CSV); // Guarda la lista de usuarios actualizada.
        }
    }

     /**
     * Genera un informe detallado de todos los comics en la coleccion.
     * Escribe la informacion en el archivo de informes (INFORMES_TXT).
     * Utiliza try-with-resources para asegurar el cierre automatico del FileWriter.
     */
    public void generarInformeComics() {
        try (FileWriter fw = new FileWriter(INFORMES_TXT)) {
            fw.append("--- Informe de Comics ---\n");
            // Se puede usar un HashSet temporal para eliminar duplicados en el informe,
            // aunque la coleccion principal sea ArrayList.
            // Si quieres eliminar duplicados para el informe:
            // HashSet<Comic> comicsSinDuplicados = new HashSet<>(comics);
            // for (Comic comic : comicsSinDuplicados) {
            for (Comic comic : comics) { // Itera directamente sobre ArrayList si se quiere incluir posibles duplicados en informe.
                fw.append(comic.toString()).append("\n");
            }
            System.out.println("Informe de comics generado en " + INFORMES_TXT);
        } catch (IOException e) {
            System.err.println("Error al generar el informe de comics: " + e.getMessage());
        }
    }
    /**
     * Genera un informe de todos los usuarios registrados en el sistema.
     * Adjunta este informe al archivo de informes existente (INFORMES_TXT).
     * Utiliza try-with-resources para asegurar el cierre automático del FileWriter.
     */
    public void generarInformeUsuarios() {
        // El 'true' en el constructor de FileWriter indica que se adjuntara al final del archivo.
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

    /**
     * Solicita al usuario una opción numérica del menú y valida la entrada.
     * Maneja InputMismatchException si el usuario ingresa algo que no es un numero.
     * @param scanner El objeto Scanner utilizado para leer la entrada del usuario.
     * @return La opción numérica valida seleccionada por el usuario.
     */
    public static int obtenerOpcionMenu(Scanner scanner) {
        int opcion = -1; // Valor inicial para indicar que no se ha recibido una opcion valida.
        while (true) {   // Bucle para asegurar que se obtiene una entrada valida.
            try {
                System.out.print("Ingrese una opcion: ");
                opcion = scanner.nextInt(); // Intenta leer un entero.
                scanner.nextLine(); // Consume el resto de la linea (el salto de linea) despues del numero.
                break;  // Sale del bucle si la entrada es valida.
            } catch (InputMismatchException e) {
                // Captura si el usuario no ingresa un numero.
                System.out.println("Entrada invalida. Por favor, ingrese un nmero.");
                scanner.nextLine(); // Consume la entrada incorrecta para evitar un bucle infinito.
            }
        }
        return opcion;
    }
     
    public static void main(String[] args) {
        ComicCollectorSystem comicSystem = new ComicCollectorSystem();
        Scanner scanner = new Scanner(System.in);
        // Agregando algunos comics y usuarios iniciales para probar la funcionalidad.
        // Estos datos se cargaran si los archivos CSV no existen, o se añadiran si ya existen.
        // Formato: Titulo, Autor, Genero, Fecha Publicacion (YYYY-MM-DD), Precio
        comicSystem.agregarComic(new Comic("Watchmen", "Alan Moore", "Superheroes", "1986-09-01", 19.99));
        comicSystem.agregarComic(new Comic("Maus I", "Art Spiegelman", "Biografico", "1986-01-01", 15.50));
        comicSystem.agregarComic(new Comic("Sandman Vol. 1", "Neil Gaiman", "Fantasia Oscura", "1989-01-01", 24.95));
        comicSystem.agregarUsuario(new Usuario("U001", "Ana Perez"));
        comicSystem.agregarUsuario(new Usuario("U002", "Juan Gomez"));

        int opcion;
        // Bucle principal del menú de la aplicacion.
        do {
            System.out.println("\n--- Menu de ComicCollectorSystem DUOC UC ---");
            System.out.println("1. Buscar comic");
            System.out.println("2. Prestar/Vender comic");
            System.out.println("3. Devolver comic");
            System.out.println("4. Agregar comic");
            System.out.println("5. Agregar usuario");
            System.out.println("6. Generar informe de comics");
            System.out.println("7. Generar informe de usuarios");
            System.out.println("0. Salir");

            opcion = obtenerOpcionMenu(scanner);
            // Estructura switch para manejar las diferentes opciones del menú.
            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el titulo del comic a buscar: ");
                    String tituloBuscar = scanner.nextLine();
                    try {
                        Comic comicEncontrado = comicSystem.buscarComic(tituloBuscar);
                        System.out.println("Comic encontrado: " + comicEncontrado);
                    } catch (ComicNoEncontradoException e) {
                        System.err.println(e.getMessage()); // Muestra el mensaje de la excepcion si no se encuentra.
                    }
                    break;
                case 2:
                    System.out.print("Ingrese el titulo del comic a prestar/vender: ");
                    String tituloPrestar = scanner.nextLine();
                    System.out.print("Ingrese el ID del usuario: ");
                    String idUsuarioPrestar = scanner.nextLine();
                    try {
                        comicSystem.prestarComic(tituloPrestar, idUsuarioPrestar);
                    } catch (ComicNoEncontradoException e) {
                        System.err.println(e.getMessage());
                    } catch (ComicYaPrestadoException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.print("Ingrese el titulo del comic a devolver: ");
                    String tituloDevolver = scanner.nextLine();
                    try {
                        comicSystem.devolverComic(tituloDevolver);
                    } catch (ComicNoEncontradoException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 4:
                    System.out.print("Ingrese el titulo del nuevo comic: ");
                    String nuevoTitulo = scanner.nextLine();
                    System.out.print("Ingrese el autor del nuevo comic: ");
                    String nuevoAutor = scanner.nextLine();
                    System.out.print("Ingrese el genero del nuevo comic: ");
                    String nuevoGenero = scanner.nextLine();
                    System.out.print("Ingrese la fecha de publicacion (YYYY-MM-DD): ");
                    String nuevaFechaPublicacion = scanner.nextLine();
                    System.out.print("Ingrese el precio del nuevo comic: ");
                    double nuevoPrecio = 0.0;
                    try {
                        nuevoPrecio = scanner.nextDouble();
                        scanner.nextLine(); // Consumir la nueva línea
                        comicSystem.agregarComic(new Comic(nuevoTitulo, nuevoAutor, nuevoGenero, nuevaFechaPublicacion, nuevoPrecio));
                    } catch (InputMismatchException e) {
                        System.err.println("Entrada invalida para el precio. Por favor, ingrese un numero.");
                        scanner.nextLine(); // Limpia el buffer del scanner
                    }
                    break;
                case 5:
                    System.out.print("Ingrese el ID del nuevo usuario: ");
                    String nuevoIdUsuario = scanner.nextLine();
                    System.out.print("Ingrese el nombre del nuevo usuario: ");
                    String nuevoNombreUsuario = scanner.nextLine();
                    comicSystem.agregarUsuario(new Usuario(nuevoIdUsuario, nuevoNombreUsuario));
                    break;
                case 6:
                    comicSystem.generarInformeComics();
                    break;
                case 7:
                    comicSystem.generarInformeUsuarios();
                    break;
                case 0:
                    System.out.println("Saliendo del programa. Hasta luego!");
                    break;
                default:
                    System.out.println("Opcion no valida. Por favor, intente de nuevo.");
            }
        } while (opcion != 0);  // El bucle continua hasta que el usuario elige salir (opcion 0).
        scanner.close();  // Cierra el objeto Scanner al finalizar el programa para liberar recursos.
    }
}