/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
/**
 *
 * @author rodri
 */
package com.mycompany.teatromoro;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;


class Ticket {
    int ticketNumber;    
    int row;             
    int col;             
    double finalPrice;   
    String tipoEntrada;  

    public Ticket(int ticketNumber, int row, int col, double finalPrice, String tipoEntrada) {
        this.ticketNumber = ticketNumber;
        this.row = row;
        this.col = col;
        this.finalPrice = finalPrice;
        this.tipoEntrada = tipoEntrada;
    }

    public String toString() {
        return "Ticket #" + ticketNumber +
               " - Asiento: Fila " + (char) ('A' + row) +
               " Columna " + (col + 1) +
               " - Tipo: " + tipoEntrada +
               " - Precio: " + finalPrice;
    }
}

public class TeatroMoro {

    
    public static final String NOMBRE_TEATRO = "Teatro Moro";
    public static final int NUM_FILAS = 5;
    public static final int NUM_COLUMNAS = 10;
    public static final double PRECIO_UNITARIO = 50.0;
    public static double totalIngresos = 0.0;
    public static int totalEntradasVendidas = 0;
    public static int totalReservas = 0;
    
    
    public static final long TIEMPO_RESERVA = 60000; 

    
    public static char[][] seatingPlan = new char[NUM_FILAS][NUM_COLUMNAS];
  
    public static long[][] reservaTiempo = new long[NUM_FILAS][NUM_COLUMNAS];

    
    public static Ticket[] soldTickets = new Ticket[NUM_FILAS * NUM_COLUMNAS];
    public static int ticketIndex = 0; 

    public static void main(String[] args) {
        
        for (int i = 0; i < NUM_FILAS; i++) {
            for (int j = 0; j < NUM_COLUMNAS; j++) {
                seatingPlan[i][j] = 'D';
                reservaTiempo[i][j] = 0;
            }
        }
        
        Timer timer = new Timer(true); 
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                actualizarReservas();
            }
        }, 0, 5000); 

        System.out.println("Bienvenido al sistema de reserva y venta de entradas del " + NOMBRE_TEATRO);
        Scanner sc = new Scanner(System.in);
        int option = 0;

        
        do {
            mostrarMenu();
            option = sc.nextInt();
            sc.nextLine(); 

            switch (option) {
                case 1:
                    reservarEntradas(sc);
                    break;
                case 2:
                    comprarEntradas(sc);
                    break;
                case 3:
                    modificarVenta(sc);
                    break;
                case 4:
                    imprimirBoleta(sc);
                    break;
                case 5:
                    System.out.println("Gracias por utilizar el sistema. Hasta pronto!");
                    break;
                default:
                    System.out.println("Opcion invalida. Intente de nuevo.");
                    
            }
        } while (option != 5);

        sc.close();
    }

    
    public static void actualizarReservas() {
        long tiempoActual = System.currentTimeMillis();
        for (int i = 0; i < NUM_FILAS; i++) {
            for (int j = 0; j < NUM_COLUMNAS; j++) {
                if (seatingPlan[i][j] == 'R' && reservaTiempo[i][j] != 0 && tiempoActual > reservaTiempo[i][j]) {
                    seatingPlan[i][j] = 'D'; 
                    reservaTiempo[i][j] = 0;
                    totalReservas--;
                    System.out.println("DEBUG: La reserva en Fila " + (char) ('A' + i) +
                            " Columna " + (j + 1) + " ha expirado y se reinicia.");
                }
            }
        }
    }

    public static void mostrarMenu() {
        System.out.println("\nMenu de Opciones:");
        System.out.println("1. Reservar entradas");
        System.out.println("2. Comprar entradas");
        System.out.println("3. Modificar venta existente");
        System.out.println("4. Imprimir boleta");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    
    public static void mostrarPlano() {
        System.out.println("\nPlano del Teatro:");
        System.out.print("    ");
        for (int col = 0; col < NUM_COLUMNAS; col++) {
            System.out.printf("%3d", (col + 1));
        }
        System.out.println();
        for (int i = 0; i < NUM_FILAS; i++) {
            System.out.printf("Fila %c", (char) ('A' + i));
            for (int j = 0; j < NUM_COLUMNAS; j++) {
                System.out.printf("%3c", seatingPlan[i][j]);
            }
            System.out.println();
        }
    }


    public static int convertirFila(char fila) {
        return fila - 'A';
    }

    
    public static void reservarEntradas(Scanner sc) {
        System.out.println("\n--- Reservar Entradas ---");
        mostrarPlano();
        System.out.print("Ingrese la cantidad de entradas a reservar: ");
        int cantidad = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < cantidad; i++) {
            System.out.println("\nReserva " + (i + 1) + ":");
            System.out.print("Ingrese la fila (A-" + (char) ('A' + NUM_FILAS - 1) + "): ");
            String filaInput = sc.nextLine().toUpperCase();
            if (filaInput.isEmpty()) {
                System.out.println("Entrada vacia. Intente de nuevo.");
                i--;
                continue;
            }
            char filaChar = filaInput.charAt(0);
            int fila = convertirFila(filaChar);
            System.out.print("Ingrese la columna (1-" + NUM_COLUMNAS + "): ");
            int columna = sc.nextInt();
            sc.nextLine();
            int col = columna - 1;

           
             if (fila < 0 || fila >= NUM_FILAS || col < 0 || col >= NUM_COLUMNAS) {
                System.out.println("DEBUG: Asiento fuera de rango.");
                System.out.println("Asiento invalido. Intente de nuevo.");
                i--;
                continue;
            }

            
            System.out.println("DEBUG: Verificando disponibilidad del asiento " + filaChar + columna);
            if (seatingPlan[fila][col] == 'D') {
                seatingPlan[fila][col] = 'R';
                
                reservaTiempo[fila][col] = System.currentTimeMillis() + TIEMPO_RESERVA;
                totalReservas++;
                System.out.println("Asiento " + filaChar + columna + " reservado exitosamente. Tienes " +
                        (TIEMPO_RESERVA / 1000) + " segundos para comprarlo.");
            } else {
                System.out.println("El asiento no esta disponible. Intente con otro asiento.");
                i--;
            }
        }
        System.out.println("DEBUG: Finalizacion del proceso de reserva.\n");
    }

   
    public static void comprarEntradas(Scanner sc) {
        System.out.println("\n--- Comprar Entradas ---");
        mostrarPlano();
        System.out.print("Ingrese la cantidad de entradas a comprar: ");
        int cantidad = sc.nextInt();
        sc.nextLine();

        double descuentoTemporal = 0.0;
        String tipoEntradaTemporal = "Normal";
        double precioTotalTransaccion = 0.0;
        int conversion;

        for (int i = 0; i < cantidad; i++) {
            System.out.println("\nCompra " + (i + 1) + ":");
            System.out.print("Ingrese la fila (A-" + (char) ('A' + NUM_FILAS - 1) + "): ");
            String filaInput = sc.nextLine().toUpperCase();
            if (filaInput.isEmpty()) {
                System.out.println("Entrada vacia. Intente de nuevo.");
                i--;
                continue;
            }
            char filaChar = filaInput.charAt(0);
            int fila = convertirFila(filaChar);
            System.out.print("Ingrese la columna (1-" + NUM_COLUMNAS + "): ");
            int columna = sc.nextInt();
            sc.nextLine();
            int col = columna - 1;

            if (fila < 0 || fila >= NUM_FILAS || col < 0 || col >= NUM_COLUMNAS) {
                System.out.println("DEBUG: Asiento fuera de rango.");
                System.out.println("Asiento invalido. Intente de nuevo.");
                i--;
                continue;
            }

            char estado = seatingPlan[fila][col];
            if (estado == 'D') {
                seatingPlan[fila][col] = 'V';
                System.out.println("DEBUG: Asiento " + filaChar + columna + " validado para compra.");
            } else if (estado == 'R') {
                System.out.print("El asiento esta reservado. Desea convertir la reserva en compra? (1 = Si, 0 = No): ");
                conversion = sc.nextInt();
                sc.nextLine();
                if (conversion == 1) {
                    seatingPlan[fila][col] = 'V';
                    reservaTiempo[fila][col] = 0;
                    totalReservas--;
                    System.out.println("DEBUG: Reserva convertida a compra para el asiento " + filaChar + columna);
                } else {
                    System.out.println("No se realizo la compra para este asiento.");
                    i--;
                    continue;
                }
            } else if (estado == 'V') {
                System.out.println("El asiento ya esta vendido. Seleccione otro.");
                i--;
                continue;
            }

            System.out.print("Tiene descuento? (1 = Si, 0 = No): ");
            int tieneDescuento = sc.nextInt();
            sc.nextLine();
            if (tieneDescuento == 1) {
                System.out.print("Ingrese el porcentaje de descuento (ej. 10 para 10%): ");
                descuentoTemporal = sc.nextDouble();
                sc.nextLine();
                descuentoTemporal = descuentoTemporal / 100.0;
                tipoEntradaTemporal = "Con Descuento";
            } else {
                descuentoTemporal = 0.0;
                tipoEntradaTemporal = "Normal";
            }

            double precioFinal = PRECIO_UNITARIO * (1 - descuentoTemporal);
            System.out.println("DEBUG: Precio final calculado para el asiento " + filaChar + columna + " es " + precioFinal);
            precioTotalTransaccion += precioFinal;

            Ticket ticket = new Ticket(ticketIndex + 1, fila, col, precioFinal, tipoEntradaTemporal);
            soldTickets[ticketIndex] = ticket;
            ticketIndex++;
            totalEntradasVendidas++;
            totalIngresos += precioFinal;
        }
        System.out.println("\nCompra completada. Total a pagar: " + precioTotalTransaccion);
    }

    
    public static void modificarVenta(Scanner sc) {
        System.out.println("\n--- Modificar Venta Existente ---");
        System.out.print("Ingrese el numero de ticket a modificar: ");
        int numeroTicket = sc.nextInt();
        sc.nextLine();
        boolean encontrado = false;

        for (int i = 0; i < ticketIndex; i++) {
            if (soldTickets[i].ticketNumber == numeroTicket) {
                encontrado = true;
                Ticket ticket = soldTickets[i];
                System.out.println("Ticket encontrado: " + ticket);
                System.out.print("Desea cambiar de asiento? (1 = Si, 0 = No): ");
                int cambio = sc.nextInt();
                sc.nextLine();

                if (cambio == 1) {
                    
                    seatingPlan[ticket.row][ticket.col] = 'D';
                    System.out.print("Ingrese la nueva fila (A-" + (char) ('A' + NUM_FILAS - 1) + "): ");
                    String nuevaFilaInput = sc.nextLine().toUpperCase();
                    if (nuevaFilaInput.isEmpty()) {
                        System.out.println("Entrada vacia. Modificacion cancelada.");
                        return;
                    }
                    char nuevaFilaChar = nuevaFilaInput.charAt(0);
                    int nuevaFila = convertirFila(nuevaFilaChar);
                    System.out.print("Ingrese la nueva columna (1-" + NUM_COLUMNAS + "): ");
                    int nuevaColumna = sc.nextInt();
                    sc.nextLine();
                    int nuevaCol = nuevaColumna - 1;

                    if (nuevaFila < 0 || nuevaFila >= NUM_FILAS || nuevaCol < 0 || nuevaCol >= NUM_COLUMNAS) {
                        System.out.println("DEBUG: Nuevo asiento fuera de rango.");
                        System.out.println("Asiento invalido. Modificación cancelada.");
                        return;
                    }

                    if (seatingPlan[nuevaFila][nuevaCol] == 'D') {
                        seatingPlan[nuevaFila][nuevaCol] = 'V';
                        totalIngresos -= ticket.finalPrice;

                        double descuentoMod = 0.0;
                        System.out.print("Aplica descuento para el nuevo asiento? (1 = Si, 0 = No): ");
                        int tieneDesc = sc.nextInt();
                        sc.nextLine();

                        String tipoN = "Normal";
                        if (tieneDesc == 1) {
                            System.out.print("Ingrese el porcentaje de descuento: ");
                            descuentoMod = sc.nextDouble();
                            sc.nextLine();
                            descuentoMod = descuentoMod / 100.0;
                            tipoN = "Con Descuento";
                        }
                        double nuevoPrecio = PRECIO_UNITARIO * (1 - descuentoMod);
                        ticket.finalPrice = nuevoPrecio;
                        ticket.row = nuevaFila;
                        ticket.col = nuevaCol;
                        ticket.tipoEntrada = tipoN;
                        totalIngresos += nuevoPrecio;

                        System.out.println("DEBUG: Modificacion exitosa. Nuevo ticket: " + ticket);
                    } else {
                        System.out.println("El asiento seleccionado no esta disponible. Modificacion cancelada.");
                    }
                }
                break;
            }
        }
        if (!encontrado) {
            System.out.println("Ticket no encontrado.");
        }
    }

    
    public static void imprimirBoleta(Scanner sc) {
        System.out.println("\n--- Imprimir Boleta ---");
        System.out.print("Ingrese el numero de ticket: ");
        int numeroTicket = sc.nextInt();
        sc.nextLine();
        boolean encontrado = false;

        for (int i = 0; i < ticketIndex; i++) {
            if (soldTickets[i].ticketNumber == numeroTicket) {
                encontrado = true;
                Ticket ticket = soldTickets[i];

                System.out.println("DEBUG: Iniciando proceso de generacion de boleta para ticket " + numeroTicket);
                String boleta = "\n===== BOLETA DE COMPRA =====\n";
                boleta += "Teatro: " + NOMBRE_TEATRO + "\n";
                boleta += "Ticket: " + ticket.ticketNumber + "\n";
                boleta += "Asiento: Fila " + (char) ('A' + ticket.row) + " - Columna " + (ticket.col + 1) + "\n";
                boleta += "Tipo de Entrada: " + ticket.tipoEntrada + "\n";
                boleta += "Precio: " + ticket.finalPrice + "\n";
                boleta += "===========================\n";

                System.out.println("DEBUG: Detalles de la boleta compilados.");
                System.out.println(boleta);
                System.out.println("DEBUG: Impresion de boleta finalizada.");
                break;
            }
        }
        if (!encontrado) {
            System.out.println("Ticket no encontrado.");
        }
    }
}

