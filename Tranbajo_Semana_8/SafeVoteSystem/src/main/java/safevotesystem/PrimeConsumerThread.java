package com.safevotesystem;

import java.util.concurrent.BlockingQueue;


 // PrimeConsumerThread implementa Runnable y consume numeros primos de una BlockingQueue.
 // Demuestra el patron productor-consumidor y el uso de Queue para la distribucion de tareas.
 
public class PrimeConsumerThread implements Runnable {
    private final BlockingQueue<Integer> primeProcessingQueue;
    private final Object queueMonitor; // Objeto monitor para wait/notify

    public PrimeConsumerThread(BlockingQueue<Integer> primeProcessingQueue, Object queueMonitor) {
        this.primeProcessingQueue = primeProcessingQueue;
        this.queueMonitor = queueMonitor;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " ha iniciado.");
        try {
            while (true) {
                Integer prime = null;

                // Bloque sincronizado para wait/notify (Paso 4 - wait/notify)
                synchronized (queueMonitor) {
                    // Esperar mientras la cola esta vacia y los productores no han terminado
                    while (primeProcessingQueue.isEmpty() && !SafeVoteSystem.areProducersFinished()) {
                        System.out.println(Thread.currentThread().getName() + " esta esperando por elementos en la cola...");
                        queueMonitor.wait(); // Esperar a ser notificado
                    }

                    // Si la cola esta vacia Y los productores han terminado, salir del bucle
                    if (primeProcessingQueue.isEmpty() && SafeVoteSystem.areProducersFinished()) {
                        System.out.println(Thread.currentThread().getName() + " detecto que la cola esta vacia y los productores terminaron. Saliendo.");
                        break; // Salir del bucle while(true)
                    }

                    // Tomar un elemento de la cola si no esta vacia
                    if (!primeProcessingQueue.isEmpty()) {
                        prime = primeProcessingQueue.take(); // take() es bloqueante y Thread-safe
                    }
                }

                if (prime != null) {
                    System.out.println(Thread.currentThread().getName() + " consumio primo: " + prime + ". Quedan en cola: " + primeProcessingQueue.size());
                    // Simular procesamiento del primo
                    Thread.sleep(70); // Pequeña pausa para simular trabajo de procesamiento
                } else {
                    // Este 'else' es un fallback, el 'break' dentro del synchronized block es el principal.
                    if (SafeVoteSystem.areProducersFinished() && primeProcessingQueue.isEmpty()) {
                        System.out.println(Thread.currentThread().getName() + " no encontro mas primos y los productores terminaron. Saliendo.");
                        break;
                    }
                }
            }
        } catch (InterruptedException e) {
            System.err.println(Thread.currentThread().getName() + " fue interrumpido mientras consumia.");
            Thread.currentThread().interrupt();
        }
        System.out.println(Thread.currentThread().getName() + " ha terminado su ejecucion.");
    }
}