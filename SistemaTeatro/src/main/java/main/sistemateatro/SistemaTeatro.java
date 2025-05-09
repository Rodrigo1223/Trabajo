/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package main.sistemateatro;

/**
 *
 * @author rodri
 */
import java.util.ArrayList;
import java.util.Scanner;

class Entrada {
    String comprador;
    int edad;
    String genero;
    boolean estudiante;
    String ubicacion;
    int asiento;
    double precioOriginal;
    double precioFinal;
    double descuentoAplicado;

    public Entrada(String comprador, int edad, String genero, boolean estudiante, String ubicacion, int asiento, double precioOriginal) {
        this.comprador = comprador;
        this.edad = edad;
        this.genero = genero;
        this.estudiante = estudiante;
        this.ubicacion = ubicacion;
        this.asiento = asiento;
        this.precioOriginal = precioOriginal;
        this.precioFinal = calcularDescuento(precioOriginal, edad, genero, estudiante);
        this.descuentoAplicado = precioOriginal - precioFinal;
    }

    private double calcularDescuento(double precioBase, int edad, String genero, boolean estudiante) {
        double descuento = 0.0;

        if (edad < 12) descuento = 0.10;  
        if (genero.equalsIgnoreCase("Mujer")) descuento = 0.20; 
        if (estudiante) descuento = 0.15;  
        if (edad >= 60) descuento = 0.25;  

        return precioBase * (1 - descuento);
    }

    @Override
    public String toString() {
        return "Asiento No. " + asiento + " - Comprador: " + comprador;
    }
}

class Teatro {
    ArrayList<Entrada> entradasVendidas = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    public void venderEntrada() {
        System.out.print("Nombre del comprador: ");
        String comprador = scanner.nextLine();

        int edad = obtenerEdadValida();
        System.out.print("Genero (Hombre/Mujer): ");
        String genero = scanner.nextLine();
        System.out.print("Es estudiante? (Si/No): ");
        boolean estudiante = scanner.nextLine().equalsIgnoreCase("Sí");

        System.out.println("Seleccione ubicacion:");
        System.out.println("1. VIP ($30,000)");
        System.out.println("2. Palco ($25,000)");
        System.out.println("3. Platea Baja ($20,000)");
        System.out.println("4. Platea Alta ($15,000)");
        System.out.println("5. Galeria ($10,000)");
        int opcionUbicacion = obtenerNumeroValido(1, 5);

        String ubicacion = obtenerUbicacion(opcionUbicacion);
        double precioBase = obtenerPrecioBase(opcionUbicacion);
        int asiento = obtenerNumeroValido(1, 50); 

        if (asientoOcupado(asiento)) {
            System.out.println("El asiento ya esta ocupado. Por favor, elige otro.");
            return;
        }

        Entrada entrada = new Entrada(comprador, edad, genero, estudiante, ubicacion, asiento, precioBase);
        entradasVendidas.add(entrada);
        imprimirBoleta(entrada);
    }

    private int obtenerEdadValida() {
        int edad;
        while (true) {
            System.out.print("Edad del comprador: ");
            if (scanner.hasNextInt()) {
                edad = scanner.nextInt();
                scanner.nextLine();
                if (edad > 0) return edad;
                else System.out.println("Ingrese una edad valida.");
            } else {
                System.out.println("Entrada invalida. Por favor, ingrese un numero.");
                scanner.next();
            }
        }
    }

    private int obtenerNumeroValido(int min, int max) {
        int numero;
        while (true) {
            System.out.print("Ingrese un numero (" + min + "-" + max + "): ");
            if (scanner.hasNextInt()) {
                numero = scanner.nextInt();
                scanner.nextLine();
                if (numero >= min && numero <= max) return numero;
                else System.out.println("Numero fuera de rango. Debe estar entre " + min + " y " + max + ".");
            } else {
                System.out.println("Entrada invalida. Por favor, ingrese un numero.");
                scanner.next();
            }
        }
    }

    private String obtenerUbicacion(int opcion) {
        switch (opcion) {
            case 1: return "VIP";
            case 2: return "Palco";
            case 3: return "Platea Baja";
            case 4: return "Platea Alta";
            case 5: return "Galeria";
            default: return "Desconocido";
        }
    }

    private double obtenerPrecioBase(int opcion) {
        switch (opcion) {
            case 1: return 30000;
            case 2: return 25000;
            case 3: return 20000;
            case 4: return 15000;
            case 5: return 10000;
            default: return 0;
        }
    }

    private boolean asientoOcupado(int asiento) {
        for (Entrada entrada : entradasVendidas) {
            if (entrada.asiento == asiento) {
                return true;
            }
        }
        return false;
    }

    public void imprimirBoleta(Entrada entrada) {
        System.out.println("\n--- BOLETA ---");
        System.out.println("Comprador: " + entrada.comprador);
        System.out.println("Edad: " + entrada.edad);
        System.out.println("Genero: " + entrada.genero);
        System.out.println("Estudiante: " + (entrada.estudiante ? "Sí" : "No"));
        System.out.println("Ubicacion: " + entrada.ubicacion);
        System.out.println("Asiento No.: " + entrada.asiento);
        System.out.println("Precio original: $" + entrada.precioOriginal);
        System.out.println("Descuento aplicado: $" + entrada.descuentoAplicado);
        System.out.println("Precio final: $" + entrada.precioFinal);
        System.out.println("----------------\n");
    }

    public void mostrarEstadoTeatro() {
        System.out.println("\nEstado de los asientos:");
        for (int i = 1; i <= 50; i++) {
            String comprador = "Libre";
            for (Entrada entrada : entradasVendidas) {
                if (entrada.asiento == i) {
                    comprador = entrada.comprador;
                    break;
                }
            }
            System.out.println("Asiento " + i + ": " + comprador);
        }
    }
}

public class SistemaTeatro {
    public static void main(String[] args) {
        Teatro teatro = new Teatro();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("1. Comprar entrada");
            System.out.println("2. Mostrar estado del teatro");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opcion: ");
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();
                switch (opcion) {
                    case 1:
                        teatro.venderEntrada();
                        break;
                    case 2:
                        teatro.mostrarEstadoTeatro();
                        break;
                    case 3:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                }
            } else {
                System.out.println("Entrada invalida. Por favor, ingrese un numero.");
                scanner.next();
                opcion = 0;
            }
        } while (opcion != 3);
    }
}
