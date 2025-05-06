/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.teatromorogestion;

/**
 *
 * @author rodri
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Cliente {
    int id;
    String nombre;
    int edad;
    boolean estudiante;

    public Cliente(int id, String nombre, int edad, boolean estudiante) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.estudiante = estudiante;
    }
}

class Entrada {
    int idVenta;
    String asiento;
    Cliente cliente;
    double descuentoAplicado;

    public Entrada(int idVenta, String asiento, Cliente cliente, double descuentoAplicado) {
        this.idVenta = idVenta;
        this.asiento = asiento;
        this.cliente = cliente;
        this.descuentoAplicado = descuentoAplicado;
    }

    @Override
    public String toString() {
        return "Venta ID: " + idVenta + ", Asiento: " + asiento + ", Cliente: " + cliente.nombre + ", Descuento: " + descuentoAplicado + "%";
    }
}

class Reserva {
    int idReserva;
    Cliente cliente;

    public Reserva(int idReserva, Cliente cliente) {
        this.idReserva = idReserva;
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Reserva ID: " + idReserva + ", Cliente: " + cliente.nombre;
    }
}

public class TeatroMoroGestion {
    static Entrada[] ventas = new Entrada[100];
    static String[] asientos = new String[100];
    static Cliente[] clientes = new Cliente[100];
    static List<Reserva> reservas = new ArrayList<>();
    static int contadorVentas = 0;
    static Scanner scanner = new Scanner(System.in);

    public static void mostrarBienvenida() {
        System.out.println(" Bienvenido al sistema de gestion del Teatro Moro! ");
        System.out.println("Aqui podras vender entradas, aplicar descuentos y administrar reservas.");
        System.out.println("Por favor, selecciona una opcion del menu para comenzar.");
    }

    public static void venderEntrada() {
        System.out.print("Ingrese nombre del cliente: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese edad del cliente: ");
        int edad = scanner.nextInt();
        System.out.print("Es estudiante? (true/false): ");
        boolean estudiante = scanner.nextBoolean();
        scanner.nextLine();
        System.out.print("Ingrese numero de asiento: ");
        String asiento = scanner.nextLine();

        if (contadorVentas >= ventas.length) {
            System.out.println("No hay mas entradas disponibles.");
            return;
        }

        Cliente cliente = new Cliente(contadorVentas + 1, nombre, edad, estudiante);
        double descuento = calcularDescuento(cliente);
        ventas[contadorVentas] = new Entrada(contadorVentas + 1, asiento, cliente, descuento);
        clientes[contadorVentas] = cliente;
        asientos[contadorVentas] = asiento;

        System.out.println(" Entrada vendida a " + nombre + " con descuento: " + descuento + "%");
        contadorVentas++;
    }

    public static double calcularDescuento(Cliente cliente) {
        if (cliente.estudiante) return 10.0;
        if (cliente.edad >= 65) return 15.0;
        return 0.0;
    }

    public static void agregarReserva() {
        System.out.print("Ingrese nombre del cliente que hace la reserva: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese ID de la reserva: ");
        int idReserva = scanner.nextInt();
        scanner.nextLine();

        Cliente cliente = new Cliente(idReserva, nombre, 0, false);
        reservas.add(new Reserva(idReserva, cliente));

        System.out.println(" Reserva realizada por " + nombre);
    }

    public static void mostrarVentas() {
        if (contadorVentas == 0) {
            System.out.println(" No hay ventas registradas.");
            return;
        }
        System.out.println("\nEntradas vendidas:");
        for (int i = 0; i < contadorVentas; i++) {
            System.out.println(ventas[i]);
        }
    }

    public static void mostrarReservas() {
        if (reservas.isEmpty()) {
            System.out.println(" No hay reservas registradas.");
            return;
        }
        System.out.println("\n Reservas realizadas:");
        for (Reserva reserva : reservas) {
            System.out.println(reserva);
        }
    }

    public static void validarDatos() {
        for (int i = 0; i < contadorVentas; i++) {
            if (ventas[i] == null || asientos[i] == null || clientes[i] == null) {
                System.out.println(" Error en los datos de la venta " + (i + 1));
            }
        }
        System.out.println(" Validación de datos completada.");
    }

    public static void main(String[] args) {
        mostrarBienvenida();
        while (true) {
            System.out.println("\n Menu:");
            System.out.println("1 Vender entrada");
            System.out.println("2 Agregar reserva");
            System.out.println("3 Mostrar ventas");
            System.out.println("4 Mostrar reservas");
            System.out.println("5 Validar datos");
            System.out.println("6 Salir");
            System.out.print(" Seleccione una opcion: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> venderEntrada();
                case 2 -> agregarReserva();
                case 3 -> mostrarVentas();
                case 4 -> mostrarReservas();
                case 5 -> validarDatos();
                case 6 -> {
                    System.out.println(" Saliendo del sistema...");
                    scanner.close();
                    return;
                }
                default -> System.out.println(" Opcion invalida. Intente de nuevo.");
            }
        }
    }
}
