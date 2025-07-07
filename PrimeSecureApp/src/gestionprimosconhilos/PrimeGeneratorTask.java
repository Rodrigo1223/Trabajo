package gestionprimosconhilos;

/**
 *
 * @author rodri
 */

import java.util.logging.Level;
import java.util.logging.Logger;

public class PrimeGeneratorTask implements Runnable {
    private final PrimesList primesList;
    private final int startRange;
    private final int endRange;
    private static final Logger LOGGER = Logger.getLogger(PrimeGeneratorTask.class.getName());


    public PrimeGeneratorTask(PrimesList primesList, int startRange, int endRange) {
        this.primesList = primesList;
        this.startRange = startRange;
        this.endRange = endRange;
    }

    /*
     * Metodo run que se ejecuta cuando el hilo es iniciado.
     * Busca numeros primos en el rango especificado y los añade a la lista compartida.
     * La sincronización se maneja dentro de PrimesList.
     * 
     */
    @Override
    public void run() {
        LOGGER.log(Level.INFO, "Hilo {0} iniciado. Buscando primos entre {1} y {2}.", new Object[]{Thread.currentThread().getName(), startRange, endRange});
        for (int i = startRange; i <= endRange; i++) {
            try {
                // Se intenta añadir el numero. Si no es primo, PrimesList lanzara una excepcion.
                if (primesList.isPrime(i)) { // Primero verificamos si es primo para evitar la excepcion en el add.
                    primesList.add(i);
                }
            } catch (IllegalArgumentException e) {
                // Esto no deberia ocurrir si primesList.isPrime(i) es true.
                LOGGER.log(Level.WARNING, "Intento de agregar un numero no primo (esto no deberia suceder): {0}", i);
            }
        }
        LOGGER.log(Level.INFO, "Hilo {0} terminado. Primos encontrados en el rango.", Thread.currentThread().getName());
    }
}