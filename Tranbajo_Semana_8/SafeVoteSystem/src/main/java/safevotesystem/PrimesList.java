package com.safevotesystem;

import java.util.ArrayList;


  // PrimesList extiende ArrayList para almacenar solo numeros primos.
  // Incorpora metodos para verificar la primalidad y sobrescribe 'add' y 'remove'
  // para asegurar la integridad de la lista.
  // Se han añadido bloques synchronized para demostrar su uso ademas de Locks.
 
public class PrimesList extends ArrayList<Integer> {

    
     // Verifica si un numero es primo.
     // Implementacion optimizada usando la raiz cuadrada.
     // Se ha marcado como 'synchronized' solo con fines demostrativos del concepto,
     // ya que al ser un metodo estatico y sin estado, no requiere sincronizacion para su correccion.
     
    public synchronized static boolean isPrime(int number) {
        if (number <= 1) return false;
        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) return false;
        }
        return true;
    }

    @Override
    public boolean add(Integer element) {
        if (element == null) {
            throw new IllegalArgumentException("No se permite anadir elementos nulos.");
        }
        if (!isPrime(element)) {
            throw new IllegalArgumentException("El numero " + element + " no es primo y no puede ser anadido a la lista.");
        }
        // La sincronizacion para la adición a la lista subyacente se maneja externamente con un Lock
        // en SafeVoteSystem o PrimesThread. Esto es una demostracion de colecciones
        // "sincronizadas por su acceso", no una coleccion intrinsecamente sincronizada.
        return super.add(element);
    }

    @Override
    public boolean remove(Object o) {
        // No se requiere validacion especial para la eliminacion de no primos,
        // ya que la lista solo contendrá primos debido a la validación en 'add'.
        // La sincronizacion para la eliminacion se maneja externamente con un Lock.
        return super.remove(o);
    }

    
      // Retorna la cantidad de numeros primos en la lista.
      // Se usa 'synchronized' para demostrar la protección de lectura del estado de la lista,
      // complementando la proteccion de escritura con Locks en otros lugares.
    
    @Override
    public synchronized int size() {
        return super.size();
    }

    public int getPrimesCount() {
        return size(); // Reutiliza el metodo size() que ahora es synchronized
    }

    // Un metodo de ejemplo para demostrar 'wait'/'notify' si fuera necesario para la PrimesList
    // (no se usa directamente en este flujo, pero muestra el concepto)
    public synchronized void waitForMinimumPrimes(int minCount) throws InterruptedException {
        while (size() < minCount) {
            System.out.println("Esperando alcanzar " + minCount + " primos. Actual: " + size());
            wait(); // Espera hasta que sea notificado
        }
        System.out.println("Mínimo de " + minCount + " primos alcanzado.");
    }

    public synchronized void notifyAboutPrimes() {
        notifyAll(); // Notifica a todos los hilos que puedan estar esperando
    }
}