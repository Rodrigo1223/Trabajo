/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.gestorentradasteatromoro;

/**
 *
 * @author rodri
 */
import java.util.Scanner;

class Entrada {
    int numero;
    String ubicacion;
    double precio;

    public Entrada(int numero, String ubicacion) {
        this.numero = numero;
        this.ubicacion = ubicacion;
        this.precio = asignarPrecio(ubicacion);
        this.precio = aplicarDescuentos(precio);
    }

    private double asignarPrecio(String ubicacion) {
        switch (ubicacion.toLowerCase()) {
            case "vip":
                return 100.0;
            case "platea":
                return 75.0;
            case "general":
                return 50.0;
            default:
                System.out.println("Ubicacion no valida, asignando precio general.");
                return 50.0;
        }
    }

    private double aplicarDescuentos(double precio) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Es estudiante? (S/N)");
        char estudiante = scanner.next().charAt(0);
        System.out.println("Es tercera edad? (S/N)");
        char terceraEdad = scanner.next().charAt(0);

        if (estudiante == 'S' || estudiante == 's') {
            precio *= 0.9;
        }
        if (terceraEdad == 'S' || terceraEdad == 's') {
            precio *= 0.85;
        }

        return precio;
    }
}

public class GestorEntradasTeatroMoro {
    static String teatro[][] = new String[5][5];
    static Entrada[] listaEntradas = new Entrada[25];
    static double ingresosTotales = 0;
    static int entradasVendidas = 0;

    public static void inicializarTeatro() {
        for (int i = 0; i < teatro.length; i++) {
            for (int j = 0; j < teatro[i].length; j++) {
                if (i < 2) {
                    teatro[i][j] = "[VIP]";
                } else if (i < 4) {
                    teatro[i][j] = "[PL]";
                } else {
                    teatro[i][j] = "[G]";
                }
            }
        }
    }

    public static void mostrarPlanoTeatro() {
        System.out.println("\nPlano del teatro:");
        System.out.print("  ");
        for (int j = 0; j < teatro[0].length; j++) {
            System.out.print(j + "  ");
        }
        System.out.println();

        for (int i = 0; i < teatro.length; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < teatro[i].length; j++) {
                System.out.print(teatro[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void mostrarEstadisticas() {
        System.out.println("\nEstadisticas del teatro:");
        System.out.println("Total de entradas vendidas: " + entradasVendidas);
        System.out.println("Ingresos totales generados: $" + ingresosTotales);
    }

    public static void ventaDeEntradas(Scanner scanner) {
        System.out.println("Ingrese el numero de la entrada:");
        int numero = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Ingrese la ubicacion (VIP, Platea, General):");
        String ubicacion = scanner.nextLine();

        Entrada nuevaEntrada = new Entrada(numero, ubicacion);
        for (int i = 0; i < listaEntradas.length; i++) {
            if (listaEntradas[i] == null) {
                listaEntradas[i] = nuevaEntrada;
                ingresosTotales += nuevaEntrada.precio;
                entradasVendidas++;
                System.out.println("Venta realizada con exito. Precio: $" + nuevaEntrada.precio);
                return;
            }
        }

        System.out.println("No hay espacio disponible para mas entradas.");
    }

    public static void buscarEntrada(Scanner scanner) {
        System.out.println("Ingrese el numero de entrada a buscar:");
        int numero = scanner.nextInt();

        for (Entrada entrada : listaEntradas) {
            if (entrada != null && entrada.numero == numero) {
                System.out.println("Entrada encontrada: Ubicacion: " + entrada.ubicacion + ", Precio: $" + entrada.precio);
                return;
            }
        }
        System.out.println("Entrada no encontrada.");
    }

    public static void eliminarEntrada(Scanner scanner) {
        System.out.println("Ingrese el numero de entrada a eliminar:");
        int numero = scanner.nextInt();

        for (int i = 0; i < listaEntradas.length; i++) {
            if (listaEntradas[i] != null && listaEntradas[i].numero == numero) {
                listaEntradas[i] = null;
                System.out.println("Entrada eliminada correctamente.");
                return;
            }
        }
        System.out.println("Entrada no encontrada.");
    }

    public static void mostrarPromociones() {
        System.out.println("Promociones disponibles:");
        System.out.println("- 10% de descuento para estudiantes.");
        System.out.println("- 15% de descuento para tercera edad.");
        System.out.println("- 5% de descuento si compras mas de 3 entradas.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        inicializarTeatro();
        System.out.println("=====================================");
        System.out.println("Bienvenido a GestorEntradasTeatroMoro");
        System.out.println("=====================================");
        mostrarPlanoTeatro();

        boolean salir = false;
        while (!salir) {
            System.out.println("\nMenu de opciones:");
            System.out.println("1. Venta de entradas");
            System.out.println("2. Buscar entrada");
            System.out.println("3. Eliminar entrada");
            System.out.println("4. Mostrar promociones");
            System.out.println("5. Mostrar estadisticas de ventas");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opcion: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    ventaDeEntradas(scanner);
                    break;
                case 2:
                    buscarEntrada(scanner);
                    break;
                case 3:
                    eliminarEntrada(scanner);
                    break;
                case 4:
                    mostrarPromociones();
                    break;
                case 5:
                    mostrarEstadisticas();
                    break;
                case 6:
                    salir = true;
                    System.out.println("===========================================================");
                    System.out.println("Gracias por usar GestorEntradasTeatroMoro. Hasta la proxima");
                    System.out.println("===========================================================");
                    break;
                default:
                    System.out.println("Opcion invalida. Intente nuevamente.");
            }
        }

        scanner.close();
    }
}