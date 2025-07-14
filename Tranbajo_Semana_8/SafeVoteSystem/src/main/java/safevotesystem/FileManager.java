package com.safevotesystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;


  // La clase FileManager maneja las operaciones de lectura y escritura de archivos.
  // Ahora interactúa con PrimesList y una BlockingQueue, usando Lock para seguridad.
 
public class FileManager {
    private final PrimesList primesList;
    private final BlockingQueue<Integer> primeProcessingQueue;
    private final Lock primesListLock; // Lock para proteger PrimesList durante la carga de archivos.

    public FileManager(PrimesList primesList, BlockingQueue<Integer> primeProcessingQueue, Lock primesListLock) {
        this.primesList = primesList;
        this.primeProcessingQueue = primeProcessingQueue;
        this.primesListLock = primesListLock;
    }

     // Carga números primos desde un archivo CSV y los añade a PrimesList
     // y a la BlockingQueue. Utiliza el Lock para proteger PrimesList.
     // @param filePath Ruta del archivo CSV.
     // @throws IOException Si ocurre un error de I/O.
     // @throws InterruptedException Si el hilo es interrumpido mientras pone en la cola.
     
    public void loadPrimesFromCsv(String filePath) throws IOException, InterruptedException {
        // Asegurarse de que el archivo existe o crearlo si es necesario (para el ejemplo)
        // fileManager.createDummyPrimesCsv(filePath); // Descomentar para generar un archivo de prueba

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    int number = Integer.parseInt(line.trim());
                    // Proteger la adición a primesList con el Lock
                    primesListLock.lock();
                    try {
                        if (PrimesList.isPrime(number)) { // isPrime es synchronized static
                            primesList.add(number);
                            System.out.println("FileManager: Anadido primo desde CSV: " + number);
                            // También añadir a la cola para que los consumidores lo procesen
                            primeProcessingQueue.put(number); // put() es Thread-safe y bloqueante
                            System.out.println("FileManager: Puesto " + number + " en cola desde CSV.");
                        } else {
                            System.out.println("FileManager: Ignorado no primo desde CSV: " + number);
                        }
                    } catch (IllegalArgumentException e) {
                        System.err.println("FileManager: Error al anadir primo desde CSV: " + number + " (" + e.getMessage() + ")");
                    } finally {
                        primesListLock.unlock(); // Liberar el Lock
                    }
                } catch (NumberFormatException e) {
                    System.err.println("FileManager: Error de formato en linea CSV: " + line + " (" + e.getMessage() + ")");
                }
            }
        }
    }

   
       //Escribe un mensaje encriptado y un codigo primo en un archivo de texto.
       //@param filePath Ruta del archivo de salida.
       //@param message Mensaje a encriptar.
       //@param primeCode Codigo primo usado para la \"encriptacion\".
       //@throws IOException Si ocurre un error de I/O.
     
    public void writeEncryptedMessage(String filePath, String message, int primeCode) throws IOException {
        // Usar 'true' para el segundo argumento de FileWriter para habilitar el modo de añadir (append).
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println("Mensaje: \"" + message + "\" | Codigo Primo: " + primeCode + " | Timestamp: " + new java.util.Date());
            System.out.println("Mensaje encriptado escrito en: " + filePath);
        }
    }

    // Metodo auxiliar para crear un archivo CSV de prueba si no existe
    public void createDummyPrimesCsv(String filePath) throws IOException {
        java.io.File file = new java.io.File(filePath);
        if (!file.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
                writer.println("2");
                writer.println("3");
                writer.println("5");
                writer.println("10"); // No primo para probar validacion
                writer.println("7");
                writer.println("11");
                System.out.println("Archivo CSV de prueba creado: " + filePath);
            }
        }
    }
}