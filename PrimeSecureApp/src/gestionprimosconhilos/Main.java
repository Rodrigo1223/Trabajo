package gestionprimosconhilos;

/**
 *
 * @author rodri
 */
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter; 

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        
        try {
            
            FileHandler fh = new FileHandler("app_primes.log", true);
            fh.setLevel(Level.INFO);
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error al configurar el logger para archivo: " + e.getMessage(), e);
        }
        
        LOGGER.log(Level.INFO, "Iniciando la aplicacion de gestion de numeros primos.");

        // Instancia de PrimesList. 
        PrimesList myPrimes = new PrimesList();

        LOGGER.log(Level.INFO, "Pruebas de la clase PrimesList:");

        // Prueba del metodo isPrime 
        LOGGER.log(Level.INFO, "Es 7 primo? {0}", myPrimes.isPrime(7));    // Debería ser true
        LOGGER.log(Level.INFO, "Es 10 primo? {0}", myPrimes.isPrime(10));  // Debería ser false

        // Prueba de adicion de numeros primos y no primos (con manejo de excepcion) 
        try {
            myPrimes.add(2);
            myPrimes.add(3);
            myPrimes.add(5);
            LOGGER.log(Level.INFO, "Numeros primos 2, 3, 5 agregados correctamente.");
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, "Error al agregar primos iniciales: {0}", e.getMessage());
        }

        try {
            myPrimes.add(4); // Esto deberia lanzar una excepcion 
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.INFO, "Correctamente atrapada la excepcion al intentar agregar un no primo: {0}", e.getMessage());
        }
        
        myPrimes.remove(Integer.valueOf(3));
        LOGGER.log(Level.INFO, "Numero 3 removido.");

         
        LOGGER.log(Level.INFO, "Cantidad actual de numeros primos en la lista: {0}", myPrimes.getPrimesCount());
        LOGGER.log(Level.INFO, "Lista actual de primos: {0}", myPrimes.toString());

        LOGGER.log(Level.INFO, "\nDemostracion de programacion multihilo:");

        // Creacion y ejecucion de hilos para generar primos concurrentemente.
        // Se usaran 3 hilos, cada uno buscando primos en un rango diferente.
        // Esto muestra como se pueden usar los hilos para 'gestionar y verificar grandes cantidades de números primos rapidamente' 
        int totalNumbersToCheck = 10000;
        int rangePerThread = totalNumbersToCheck / 3;

        Thread thread1 = new Thread(new PrimeGeneratorTask(myPrimes, 1, rangePerThread), "Hilo-Generador-1");
        Thread thread2 = new Thread(new PrimeGeneratorTask(myPrimes, rangePerThread + 1, 2 * rangePerThread), "Hilo-Generador-2");
        Thread thread3 = new Thread(new PrimeGeneratorTask(myPrimes, (2 * rangePerThread) + 1, totalNumbersToCheck), "Hilo-Generador-3");

        long startTime = System.currentTimeMillis();

        thread1.start(); // Inicia el primer hilo
        thread2.start(); // Inicia el segundo hilo
        thread3.start(); // Inicia el tercer hilo

        // Espera a que todos los hilos terminen su ejecucion antes de continuar.
        // Esto asegura que la lista este completa antes de imprimir el resultado final.
        
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, "Un hilo fue interrumpido: {0}", e.getMessage());
            Thread.currentThread().interrupt(); 
        }

        long endTime = System.currentTimeMillis();

        LOGGER.log(Level.INFO, "\n--- Resultados de la operacion multihilo ---");
        LOGGER.log(Level.INFO, "Cantidad total de numeros primos encontrados en la lista: {0}", myPrimes.getPrimesCount());
        LOGGER.log(Level.INFO, "Tiempo total de ejecución con hilos: {0} ms", (endTime - startTime));
       

        LOGGER.log(Level.INFO, "Aplicacion finalizada.");
    }
}