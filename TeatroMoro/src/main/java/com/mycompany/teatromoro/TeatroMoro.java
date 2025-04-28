/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt 
 */

package com.mycompany.teatromoro;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 *
 * @author rodri
 */

public class TeatroMoro {

   
    static int totalEntradasVendidas = 0;     
    static double totalIngresos = 0.0;          
    static int contadorVentas = 0;           

  
    static String[] ubicaciones = {"VIP", "Platea", "Balcon"};
    static double[] preciosBase = {150.0, 100.0, 70.0};

    
    static class Venta {
        int id;                   
        String ubicacion;         
        double costoBase;         
        double descuentoAplicado;
        double costoFinal;        

        
        public Venta(int id, String ubicacion, double costoBase, double descuentoAplicado, double costoFinal) {
            this.id = id;
            this.ubicacion = ubicacion;
            this.costoBase = costoBase;
            this.descuentoAplicado = descuentoAplicado;
            this.costoFinal = costoFinal;
        }

        
        public void imprimirBoleta() {
            System.out.println("\n========== BOLETA ==========");
            System.out.println("Numero de Venta: " + id);
            System.out.println("Ubicacion: " + ubicacion);
            System.out.println("Costo Base: $" + costoBase);
            System.out.println("Descuento Aplicado: $" + descuentoAplicado);
            System.out.println("Costo Final: $" + costoFinal);
            System.out.println("Gracias por su compra");
            System.out.println("============================\n");
        }

        
        public String resumen() {
            return String.format("Venta %d - Ubicacion: %s | Costo Final: $%.2f | Descuento: $%.2f",
                    id, ubicacion, costoFinal, descuentoAplicado);
        }
    }

    public static void main(String[] args) {
       
        List<Venta> ventas = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

       
        int opcion;

        
        do {
            System.out.println("===========Menu===========");
            System.out.println("======= Entradas =======");
            System.out.println("=== Teatro Moro ===");
            System.out.println("1. Venta de entradas");
            System.out.println("2. Visualizar resumen de ventas");
            System.out.println("3. Generar boleta (por venta)");
            System.out.println("4. Calcular ingresos totales");
            System.out.println("5. Salir del programa");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                   
                    System.out.println("\n--- Venta de Entradas ---");
                    System.out.println("Seleccione la ubicacion deseada: ");
                    
                    for (int i = 0; i < ubicaciones.length; i++) {
                        System.out.println((i + 1) + ". " + ubicaciones[i] + " (Precio: $" + preciosBase[i] + ")");
                    }
                    int opcionUbicacion = scanner.nextInt();

                    
                    if (opcionUbicacion < 1 || opcionUbicacion > ubicaciones.length) {
                        System.out.println("Ubicacion invalida. Intente nuevamente.\n");
                        break;
                    }

                    
                    String ubicacionSeleccionada = ubicaciones[opcionUbicacion - 1];
                    double costoBase = preciosBase[opcionUbicacion - 1];

                    
                    System.out.println("El comprador es:");
                    System.out.println("1. Estudiante (10% de descuento)");
                    System.out.println("2. Tercera edad (15% de descuento)");
                    System.out.println("3. Ninguno");
                    int opcionDescuento = scanner.nextInt();

                    
                    double descuentoPorcentaje = 0.0;
                    if (opcionDescuento == 1) {
                        descuentoPorcentaje = 0.10;
                    } else if (opcionDescuento == 2) {
                        descuentoPorcentaje = 0.15;
                    }
                    double descuentoAplicado = costoBase * descuentoPorcentaje;
                    double costoFinal = costoBase - descuentoAplicado;

                    
                    contadorVentas++;
                    Venta venta = new Venta(contadorVentas, ubicacionSeleccionada, costoBase, descuentoAplicado, costoFinal);
                    ventas.add(venta);
                    totalEntradasVendidas++;
                    totalIngresos += costoFinal;

                    
                    venta.imprimirBoleta();
                    break;

                case 2:
                    
                    System.out.println("\n--- Resumen de Ventas ---");
                    if (ventas.isEmpty()) {
                        System.out.println("No hay ventas registradas.\n");
                    } else {
                        for (Venta v : ventas) {
                            System.out.println(v.resumen());
                        }
                        System.out.println();
                    }
                    break;

                case 3:
                    
                    System.out.println("\n--- Generar Boleta ---");
                    System.out.print("Ingrese el numero de venta para generar la boleta: ");
                    int numVenta = scanner.nextInt();

                    boolean encontrada = false;
                    for (Venta v : ventas) {
                        if (v.id == numVenta) {
                            v.imprimirBoleta();
                            encontrada = true;
                        }
                    }
                    if (!encontrada) {
                        System.out.println("Venta no encontrada.\n");
                    }
                    break;

                case 4:
                    
                    System.out.println("\n--- Ingresos Totales ---");
                    System.out.println("Total ingresos acumulados: $" + totalIngresos + "\n");
                    break;

                case 5:
                    
                    System.out.println("\nGracias por su compra. Hasta pronto!");
                    System.out.println("=== Teatro Moro ===");
                    break;

                default:
                    System.out.println("\nOpcion invalida. Por favor intente nuevamente.\n");
                    break;
            }
        } while (opcion != 5);

        scanner.close();
    }
}

