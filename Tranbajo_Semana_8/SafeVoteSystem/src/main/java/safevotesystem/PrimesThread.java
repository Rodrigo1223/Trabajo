package com.safevotesystem;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;


  // PrimesThread implementa Runnable y genera numeros aleatorios.
  // Si un numero es primo, lo añade a PrimesList de forma segura usando un Lock.
  // Tambien coloca el primo en una BlockingQueue para procesamiento posterior.
 
public class PrimesThread implements Runnable {
    private final PrimesList primesList;
    private final Lock primesListLock;
    private final BlockingQueue<Integer> primeProcessingQueue;
    private final Object queueMonitor; // Objeto monitor para wait/notify de la cola
    private final Random random = new Random();
    private static final int MAX_NUMBERS_TO_GENERATE = 50; // Cada hilo generara hasta 50 numeros
    private int numbersGenerated = 0;

    public PrimesThread(PrimesList primesList, Lock primesListLock, BlockingQueue<Integer> primeProcessingQueue, Object queueMonitor) {
        this.primesList = primesList;
        this.primesListLock = primesListLock;
        this.primeProcessingQueue = primeProcessingQueue;
        this.queueMonitor = queueMonitor;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " ha iniciado.");
        while (numbersGenerated < MAX_NUMBERS_TO_GENERATE && !Thread.currentThread().isInterrupted()) {
            int randomNumber = random.nextInt(1000) + 1; // Genera numeros entre 1 y 1000

            // Primero, verificar si es primo. Esta operacion es de lectura pura y no necesita lock de la lista.
            if (PrimesList.isPrime(randomNumber)) { // isPrime es synchronized static
                //Proteccin con Lock para Escritura en PrimesList 
                primesListLock.lock(); // Adquisición del bloqueo 
                try {
                    primesList.add(randomNumber); // Esto puede lanzar IllegalArgumentException si no es primo (redundante aquí)
                    System.out.println(Thread.currentThread().getName() + " anadio primo: " + randomNumber + ". Total primos: " + primesList.getPrimesCount());

                    
                    // Usamos put(), que es una operacion bloqueante y Thread-safe.
                    try {
                        primeProcessingQueue.put(randomNumber);
                        System.out.println(Thread.currentThread().getName() + " puso " + randomNumber + " en la cola. Tamano cola: " + primeProcessingQueue.size());
                        // Si la cola esta vacia y los consumidores están esperando, notificar.
                        synchronized (queueMonitor) {
                            queueMonitor.notifyAll(); // Notifica a los consumidores que hay un elemento (Paso 4 - notify)
                        }
                    } catch (InterruptedException e) {
                        System.err.println(Thread.currentThread().getName() + " fue interrumpido mientras ponia en la cola.");
                        Thread.currentThread().interrupt();
                        break; // Salir del bucle si es interrumpido
                    }

                } catch (IllegalArgumentException e) {
                    System.err.println(Thread.currentThread().getName() + " intento anadir un no primo (lanzo excepcion): " + randomNumber + " (" + e.getMessage() + ")");
                } finally {
                    
                    primesListLock.unlock(); // Liberacion del bloqueo (CRUCIAL en 'finally')
                }
            }
            numbersGenerated++;

            // Pequeña pausa para simular trabajo y permitir el cambio de contexto
            try {
                Thread.sleep(50); // Pausa de 50 milisegundos.
            } catch (InterruptedException e) {
                System.err.println(Thread.currentThread().getName() + " fue interrumpido mientras dormia.");
                Thread.currentThread().interrupt(); // Restaurar el estado de interrupcion
                break; // Salir del bucle si es interrumpido
            }
        }
        System.out.println(Thread.currentThread().getName() + " ha terminado de generar numeros (" + numbersGenerated + " intentos).");
    }
}