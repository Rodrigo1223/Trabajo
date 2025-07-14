package com.safevotesystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.io.IOException;
import java.util.Random;


 // La clase SafeVoteSystem es el punto de entrada principal de nuestra aplicacion.
 // Ahora demuestra un sistema mas completo de concurrencia, incluyendo productores,
 // consumidores, gestion de hilos y colecciones sincronizadas.

public class SafeVoteSystem {

    // Objeto para sincronizar el acceso a la cola de procesamiento y la señalizacion de wait/notify
    private static final Object QUEUE_MONITOR = new Object();
    private static volatile boolean producersFinished = false; // Bandera para indicar que los productores han terminado

    public static void main(String[] args) {
        System.out.println("Iniciando SafeVoteSystem: Sistema de Votacion Segura (Modo Concurrente Mejorado).");

        
        PrimesList primeNumbers = new PrimesList();
        Lock primesListLock = new ReentrantLock(); // Candado para proteger la lista de primos.

        // Cola bloqueante para distribuir primos entre hilos (simulando Queue y Topic)
        // Capacidad aumentada para simular un buffer.
        BlockingQueue<Integer> primeProcessingQueue = new ArrayBlockingQueue<>(100);

        FileManager fileManager = new FileManager(primeNumbers, primeProcessingQueue, primesListLock);

        
        // Aunque no es un framework de pruebas formal, esto demuestra la validacion basica.
        System.out.println("\n--- Ejecutando Pruebas de Clase PrimesList ---");
        try {
            PrimesList testList = new PrimesList();
            testList.add(2); // Deberia añadir 2
            System.out.println("TEST: Anadiendo 2 a la lista. OK. Primos: " + testList.getPrimesCount());
            testList.add(7); // Deberia añadir 7
            System.out.println("TEST: Anadiendo 7 a la lista. OK. Primos: " + testList.getPrimesCount());

            try {
                testList.add(4); // Deberia fallar (no primo)
                System.out.println("TEST FALLIDO: Anadio 4 (no primo) a la lista.");
            } catch (IllegalArgumentException e) {
                System.out.println("TEST: Intentando anadir 4 (no primo). Excepcion esperada: " + e.getMessage() + ". OK.");
            }

            testList.remove(Integer.valueOf(7)); // Debería remover 
            System.out.println("TEST: Removiendo 7. OK. Primos: " + testList.getPrimesCount());

            System.out.println("--- Pruebas de PrimesList Concluidas ---\n");

        } catch (Exception e) {
            System.err.println("ERROR DURANTE PRUEBAS DE PrimesList: " + e.getMessage());
        }

        //Configuración de Hilos Productores y Consumidores 
        int numberOfProducers = 3; // Hilos que generan y añaden primos
        int numberOfConsumers = 2; // Hilos que procesan primos de la cola

        List<Thread> producerThreads = new ArrayList<>();
        List<Thread> consumerThreads = new ArrayList<>();

        System.out.println("Creando " + numberOfProducers + " hilos productores y " + numberOfConsumers + " hilos consumidores...");

        // Crear y añadir hilos productores
        for (int i = 0; i < numberOfProducers; i++) {
            PrimesThread producer = new PrimesThread(primeNumbers, primesListLock, primeProcessingQueue, QUEUE_MONITOR);
            Thread producerThread = new Thread(producer, "ProducerThread-" + (i + 1));
            producerThreads.add(producerThread);
            producerThread.start(); // Iniciar los hilos productores 
        }

        // Crear y añadir hilos consumidores
        for (int i = 0; i < numberOfConsumers; i++) {
            PrimeConsumerThread consumer = new PrimeConsumerThread(primeProcessingQueue, QUEUE_MONITOR);
            Thread consumerThread = new Thread(consumer, "ConsumerThread-" + (i + 1));
            consumerThreads.add(consumerThread);
            consumerThread.start(); // Inicia los hilos consumidores 
        }

        //Opciones de Carga de Archivos 
        Scanner scanner = new Scanner(System.in);
        System.out.println("Desea cargar numeros primos desde un archivo CSV? (s/n)");
        String response = scanner.nextLine();
        if (response.equalsIgnoreCase("s")) {
            System.out.println("Cargando primos desde el archivo 'primes.csv'...");
            try {
                
                // fileManager.createDummyPrimesCsv("primes.csv");
                fileManager.loadPrimesFromCsv("primes.csv");
                System.out.println("Carga desde CSV finalizada.");
            } catch (IOException e) {
                System.err.println("Error al cargar primos desde CSV: " + e.getMessage());
            } catch (InterruptedException e) {
                System.err.println("Carga desde CSV interrumpida: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

        // Esperar a que los hilos productores terminen 
        System.out.println("\nEsperando a que los hilos productores terminen su trabajo...");
        for (Thread producerThread : producerThreads) {
            try {
                producerThread.join(); // Esperar la conclusion de los productores
            } catch (InterruptedException e) {
                System.err.println("El hilo principal fue interrumpido mientras esperaba a los productores.");
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Todos los hilos productores han terminado.");

        // Notificar a los consumidores que no habra mas elementos en la cola una vez que se procese lo existente.
        // Esto es crucial para que los consumidores salgan de su bucle de 'take()'.
        synchronized (QUEUE_MONITOR) {
            producersFinished = true; // Establecer la bandera para que los consumidores lo sepan
            QUEUE_MONITOR.notifyAll(); // Notificar a todos los consumidores que revisen la bandera
        }
        System.out.println("Bandera de productores finalizados establecida y consumidores notificados.");

        // Esperar a que los hilos consumidores terminen de procesar la cola
        System.out.println("Esperando a que los hilos consumidores terminen su trabajo...");
        for (Thread consumerThread : consumerThreads) {
            try {
                consumerThread.join(); // Esperar la conclusión de los consumidores
            } catch (InterruptedException e) {
                System.err.println("El hilo principal fue interrumpido mientras esperaba a los consumidores.");
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Todos los hilos consumidores han terminado.");


        
        int choice;
        do {
            System.out.println("\n--- Menu Principal ---");
            System.out.println("1. Anadir un numero primo manualmente");
            System.out.println("2. Verificar cantidad de primos");
            System.out.println("3. Escribir mensaje encriptado");
            System.out.println("4. Salir");
            System.out.print("Ingrese su opcion: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Ingrese el numero a anadir: ");
                    int num = scanner.nextInt();
                    primesListLock.lock(); // Proteger con Lock incluso en adiciones manuales
                    try {
                        primeNumbers.add(num);
                        System.out.println("Numero anadido (si es primo).");
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error: " + e.getMessage());
                    } finally {
                        primesListLock.unlock();
                    }
                    break;
                case 2:
                    System.out.println("Actualmente hay " + primeNumbers.getPrimesCount() + " numeros primos en la lista.");
                    break;
                case 3:
                    scanner.nextLine(); // Consumir nueva linea
                    System.out.print("Ingrese el mensaje a encriptar: ");
                    String message = scanner.nextLine();
                    Random rand = new Random();
                    int primeCode = primeNumbers.getPrimesCount() > 0 ? primeNumbers.get(rand.nextInt(primeNumbers.size())) : 0; // Obtener un primo aleatorio si hay
                    try {
                        fileManager.writeEncryptedMessage("encrypted_messages.txt", message, primeCode);
                        System.out.println("Mensaje encriptado y guardado.");
                    } catch (IOException e) {
                        System.err.println("Error al escribir mensaje encriptado: " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Saliendo de SafeVoteSystem. Hasta luego!");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo.");
            }
        } while (choice != 4);

        scanner.close();
    }

    // Metodo para que los consumidores verifiquen si los productores han terminado
    public static boolean areProducersFinished() {
        return producersFinished;
    }
}