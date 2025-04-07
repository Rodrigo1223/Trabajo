package com.mycompany.teatromoro;

import java.util.Scanner;

public class TeatroMoro { 
    private static final int NUM_FILAS = 5;
    private static final int ASIENTOS_POR_FILA = 10;
    private static final boolean[][] asientosOcupados = new boolean[NUM_FILAS][ASIENTOS_POR_FILA];
    private static double[] preciosPorFila = {60.0, 55.0, 50.0, 45.0, 40.0};
    private static final double DESCUENTO_ESTUDIANTE = 0.10;
    private static final double DESCUENTO_TERCERA_EDAD = 0.15;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuarCompra = true;

        while (continuarCompra) {
            mostrarMenuPrincipal();
            int opcion = obtenerOpcion(scanner);

            switch (opcion) {
                case 1:
                    comprarEntrada(scanner);
                    break;
                case 2:
                    System.out.println("Gracias por su visita. Vuelva pronto!");
                    continuarCompra = false;
                    break;
                default:
                    System.out.println("Opcion invalida. Intente nuevamente.");
            }

            if (continuarCompra && opcion != 2) {
                System.out.print("\nDesea realizar otra compra? (s/n): ");
                String respuesta = scanner.nextLine().toLowerCase();
                if (!respuesta.equals("s")) {
                    continuarCompra = false;
                    System.out.println("Gracias por su visita. Vuelva pronto!");
                }
            }
        }
        scanner.close();
    }

    public static void mostrarMenuPrincipal() {
        System.out.println("\n==============BIENVENIDO===============");
        System.out.println("=========== Teatro Moro ===========");
        for (int i = 1; i <= 2; i++) {
            if (i == 1) {
                System.out.println(i + ". Comprar entrada.");
            } else if (i == 2) {
                System.out.println(i + ". Salir.");
            }
        }
        System.out.println("=====================================");
        System.out.print("Seleccione una opcion: ");
    }

    public static int obtenerOpcion(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Ingrese un numero.");
                System.out.print("Seleccione una opcion: ");
            }
        }
    }

    public static void mostrarPlanoTeatro() {
        System.out.println("\nPlano del Teatro Moro:");
        for (int i = 0; i < NUM_FILAS; i++) {
            System.out.print("Fila " + (i + 1) + ": ");
            for (int j = 0; j < ASIENTOS_POR_FILA; j++) {
                System.out.print((j + 1) + (asientosOcupados[i][j] ? "(O) " : "(L) "));
            }
            System.out.println();
        }
        System.out.println("Leyenda: (L) Libre, (O) Ocupado");
    }

    public static void comprarEntrada(Scanner scanner) {
        mostrarPlanoTeatro();
        int fila = obtenerFila(scanner);
        int asiento = obtenerAsiento(scanner, fila);

        if (!asientosOcupados[fila][asiento]) {
            asientosOcupados[fila][asiento] = true;
            int edad = obtenerEdad(scanner);
            double precioBase = preciosPorFila[fila];
            double descuentoAplicado = calcularDescuento(edad);
            double precioFinal = calcularPrecioFinal(precioBase, descuentoAplicado);
            mostrarResumenCompra(fila, asiento, precioBase, descuentoAplicado, precioFinal);
        } else {
            System.out.println("El asiento seleccionado ya esta ocupado. Por favor, intente nuevamente.");
        }
    }

    public static int obtenerFila(Scanner scanner) {
        int fila;
        while (true) {
            System.out.print("Ingrese el numero de fila (1-" + NUM_FILAS + "): ");
            try {
                fila = Integer.parseInt(scanner.nextLine());
                if (fila >= 1 && fila <= NUM_FILAS) {
                    return fila - 1;
                } else {
                    System.out.println("Número de fila invalido. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Ingrese un numero.");
            }
        }
    }

    public static int obtenerAsiento(Scanner scanner, int fila) {
        int asiento;
        while (true) {
            System.out.print("Ingrese el numero de asiento en la fila " + (fila + 1) + " (1-" + ASIENTOS_POR_FILA + "): ");
            try {
                asiento = Integer.parseInt(scanner.nextLine());
                if (asiento >= 1 && asiento <= ASIENTOS_POR_FILA) {
                    return asiento - 1;
                } else {
                    System.out.println("Numero de asiento inválido para esta fila. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Ingrese un numero.");
            }
        }
    }

    public static int obtenerEdad(Scanner scanner) {
        int edad;
        while (true) {
            System.out.print("Ingrese su edad: ");
            try {
                edad = Integer.parseInt(scanner.nextLine());
                if (edad >= 0) {
                    return edad;
                } else {
                    System.out.println("Edad invalida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Ingrese un numero.");
            }
        }
    }

    public static double calcularDescuento(int edad) {
        System.out.print("Es estudiante? (s/n): ");
        Scanner scanner = new Scanner(System.in);
        String esEstudiante = scanner.nextLine().toLowerCase();

        if (esEstudiante.equals("s")) {
            return DESCUENTO_ESTUDIANTE;
        } else if (edad >= 65) {
            return DESCUENTO_TERCERA_EDAD;
        }
        return 0;
    }

    public static double calcularPrecioFinal(double precioBase, double descuento) {
        double precioFinalCalculado = precioBase;
        int intentos = 0;
        while (intentos < 3) {
            precioFinalCalculado = precioBase * (1 - descuento);
            if (precioFinalCalculado >= 0) {
                break;
            }
            intentos++;
        }
        return precioFinalCalculado;
    }

    public static void mostrarResumenCompra(int fila, int asiento, double precioBase, double descuento, double precioFinal) {
        System.out.println("\n==============Resumen de la Compra==============");
        System.out.println("Ubicacion del asiento: Fila " + (fila + 1) + ", Asiento " + (asiento + 1));
        System.out.println("Precio base de la entrada: $" + String.format("%.2f", precioBase));
        System.out.println("Descuento aplicado: " + String.format("%.2f", descuento * 100) + "%");
        System.out.println("Precio final a pagar: $" + String.format("%.2f", precioFinal));
        System.out.println("================================================");
    }
}