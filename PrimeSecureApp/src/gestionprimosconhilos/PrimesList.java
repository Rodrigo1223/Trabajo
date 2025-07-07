package gestionprimosconhilos;

/**
 *
 * @author rodri
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrimesList extends ArrayList<Integer> {

    // Mecanismo de bloqueo para asegurar la seguridad de los hilos al modificar la lista.
    private final Lock listLock = new ReentrantLock();

    /*
     * Verifica si un numero dado es primo.
     * @param number El numero entero a verificar.
     * @return true si el numero es primo, false en caso contrario.
     */
    public boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        
        // Necesitamos verificar divisores hasta la raiz cuadrada del numero.
        
        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
    /*
     * Sobrescribe el método add para asegurar que solo se agreguen numeros primos.
     * Si se intenta agregar un numero no primo, lanza una IllegalArgumentException.
     * 
     * @param element El número a agregar a la lista.
     * @return true si el número primo se agregó correctamente.
     * @throws IllegalArgumentException si el número no es primo.
     */
    @Override
    public boolean add(Integer element) {
        if (!isPrime(element)) {
            // Lanza una excepcion si el numero no es primo.
            
            throw new IllegalArgumentException("Solo se pueden agregar numeros primos a la lista.");
        }
        
        listLock.lock(); 
        try {
            return super.add(element); // Llama al metodo add original de ArrayList.
        } finally {
            listLock.unlock(); // Desbloquea para permitir acceso a otros hilos.
        }
    }

    @Override
    public boolean addAll(Collection<? extends Integer> c) {
        // verifica que todos los elementos sean primos antes de agregar cualquiera.
        for (Integer element : c) {
            if (!isPrime(element)) {
                throw new IllegalArgumentException("Solo se pueden agregar numeros primos a la lista.");
            }
        }
        listLock.lock();
        try {
            return super.addAll(c);
        } finally {
            listLock.unlock();
        }
    }
     // Sobrescribe el metodo remove para mantener la consistencia, aunque la verificacion de primos
     // Solo aplica para la adicion.
     
    @Override
    public boolean remove(Object o) {
        listLock.lock();
        try {
            return super.remove(o); // Llama al metodo remove original de ArrayList.
        } finally {
            listLock.unlock();
        }
    }

    /*
     * Devuelve la cantidad de numeros primos en la lista.
     * Ya que solo se permiten numeros primos, esto simplemente devuelve el tamaño de la lista.
     * 
     * @return La cantidad de numeros primos en la lista.
     */
    public int getPrimesCount() {
        listLock.lock();
        try {
            return size(); // Devuelve el numero de elementos en la lista.
        } finally {
            listLock.unlock();
        }
    }
}