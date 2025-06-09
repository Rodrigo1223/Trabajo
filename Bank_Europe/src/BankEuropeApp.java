/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rodri
 */
package com.bankeurope; 

import com.bankeurope.client.Cliente;
import com.bankeurope.model.CuentaBancaria;
import com.bankeurope.model.accounts.CuentaAhorros;
import com.bankeurope.model.accounts.CuentaCorriente;
import com.bankeurope.model.accounts.CuentaDigital;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankEuropeApp {
    private static List<Cliente> clientes = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static Cliente clienteActual = null;
    private static final String NOMBRE_ARCHIVO_CLIENTES = "clientes_bankeurope.dat";

    public static void main(String[] args) {
        cargarClientes();

        int opcion;
        do {
            mostrarMenu();
            System.out.print("Seleccione una opcion: ");
            opcion = obtenerEnteroValido();

            switch (opcion) {
                case 1:
                    registrarCliente();
                    break;
                case 2:
                    verDatosDeCliente();
                    break;
                case 3:
                    seleccionarCliente();
                    break;
                case 4:
                    depositar();
                    break;
                case 5:
                    girar();
                    break;
                case 6:
                    consultarSaldo();
                    break;
                case 7:
                    calcularIntereses();
                    break;
                case 8:
                    System.out.println("Saliendo de la aplicacion. Gracias por preferir Bank Europe!");
                    guardarClientes();
                    break;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo.");
            }
            System.out.println("---");
        } while (opcion != 8);

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Menu Principal Bank Europe ---");
        System.out.println("1. Registrar cliente");
        System.out.println("2. Ver datos de cliente");
        System.out.println("3. Seleccionar Cliente");
        System.out.println("4. Depositar");
        System.out.println("5. Girar");
        System.out.println("6. Consultar saldo");
        System.out.println("7. Calcular Intereses (para cliente actual)");
        System.out.println("8. Salir");
    }

    private static int obtenerEnteroValido() {
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada invalida. Por favor, ingrese un numero.");
            scanner.next();
            System.out.print("Seleccione una opcion: ");
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); 
        return valor;
    }

    private static void registrarCliente() {
        System.out.println("\n--- Registro de Cliente ---");
        String rut;
        do {
            System.out.print("Ingrese Rut (ej: 20.345.678-0): ");
            rut = scanner.nextLine();
            if (rut.length() < 11 || rut.length() > 12) {
                System.out.println("Rut invalido. Debe tener entre 11 y 12 caracteres (incluyendo puntos y guion).");
            }
        } while (rut.length() < 11 || rut.length() > 12);

        for (Cliente c : clientes) {
            if (c.getRut().equalsIgnoreCase(rut)) {
                System.out.println("Ya existe un cliente registrado con este RUT.");
                return;
            }
        }

        System.out.print("Ingrese nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese apellido paterno: ");
        String apellidoPaterno = scanner.nextLine();
        System.out.print("Ingrese apellido materno: ");
        String apellidoMaterno = scanner.nextLine();
        System.out.print("Ingrese domicilio: ");
        String domicilio = scanner.nextLine();
        System.out.print("Ingrese comuna: ");
        String comuna = scanner.nextLine();
        System.out.print("Ingrese telefono: ");
        String telefono = scanner.nextLine();

        String numeroCuenta;
        do {
            System.out.print("Ingrese numero de cuenta (9 digitos): ");
            numeroCuenta = scanner.nextLine();
            if (numeroCuenta.length() != 9 || !numeroCuenta.matches("\\d+")) {
                System.out.println("Numero de cuenta invalido. Debe ser un numero de 9 digitos.");
            }
        } while (numeroCuenta.length() != 9 || !numeroCuenta.matches("\\d+"));

        System.out.println("Seleccione tipo de cuenta:");
        System.out.println("1. Cuenta Corriente");
        System.out.println("2. Cuenta de Ahorro");
        System.out.println("3. Cuenta Digital");
        System.out.print("Opcion de cuenta: ");
        int tipoCuentaOpcion = obtenerEnteroValido();

        CuentaBancaria nuevaCuenta = null;
        try {
            switch (tipoCuentaOpcion) {
                case 1:
                    nuevaCuenta = new CuentaCorriente(numeroCuenta);
                    break;
                case 2:
                    nuevaCuenta = new CuentaAhorros(numeroCuenta);
                    break;
                case 3:
                    nuevaCuenta = new CuentaDigital(numeroCuenta);
                    break;
                default:
                    System.out.println("Tipo de cuenta no valido. Se asignara una Cuenta Corriente por defecto.");
                    nuevaCuenta = new CuentaCorriente(numeroCuenta);
                    break;
            }

            Cliente nuevoCliente = new Cliente(rut, nombre, apellidoPaterno, apellidoMaterno,
                                                domicilio, comuna, telefono, nuevaCuenta);
            clientes.add(nuevoCliente);
            clienteActual = nuevoCliente;
            System.out.println("Cliente registrado exitosamente con su " + nuevaCuenta.getClass().getSimpleName() + "!");
            System.out.println("Cliente actual establecido a: " + clienteActual.getNombre() + " " + clienteActual.getApellidoPaterno());

        } catch (IllegalArgumentException e) {
            System.out.println("Error al registrar cliente: " + e.getMessage());
        }
    }

    private static void verDatosDeCliente() {
        if (clienteActual == null) {
            System.out.println("No hay un cliente seleccionado. Por favor, registre un cliente o seleccione uno.");
            return;
        }
        System.out.println("\n--- Datos del Cliente Actual ---");
        clienteActual.mostrarInformacionCliente();
    }

    private static void seleccionarCliente() {
        System.out.println("\n--- Seleccionar Cliente ---");
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados aun.");
            return;
        }
        System.out.print("Ingrese el RUT del cliente a seleccionar: ");
        String rutBuscado = scanner.nextLine();

        Cliente encontrado = null;
        for (Cliente c : clientes) {
            if (c.getRut().equalsIgnoreCase(rutBuscado)) {
                encontrado = c;
                break;
            }
        }

        if (encontrado != null) {
            clienteActual = encontrado;
            System.out.println("Cliente '" + clienteActual.getNombre() + " " + clienteActual.getApellidoPaterno() + "' seleccionado exitosamente.");
            System.out.println("Ahora puede realizar operaciones con este cliente.");
        } else {
            System.out.println("Cliente con RUT '" + rutBuscado + "' no encontrado.");
        }
    }

    private static void depositar() {
        if (clienteActual == null) {
            System.out.println("No hay un cliente seleccionado para realizar un deposito.");
            return;
        }
        System.out.println("\n--- Realizar Deposito ---");
        System.out.print("Ingrese un monto para depositar: ");
        double monto = obtenerDoubleValido();

        clienteActual.getCuenta().depositar(monto);
    }

    private static void girar() {
        if (clienteActual == null) {
            System.out.println("No hay un cliente seleccionado para realizar un giro.");
            return;
        }
        System.out.println("\n--- Realizar Giro ---");
        System.out.print("Ingrese un monto para girar: ");
        double monto = obtenerDoubleValido();

        clienteActual.getCuenta().girar(monto);
    }

    private static void consultarSaldo() {
        if (clienteActual == null) {
            System.out.println("No hay un cliente seleccionado para consultar saldo.");
            return;
        }
        System.out.println("\n--- Consultar Saldo ---");
        clienteActual.getCuenta().consultarSaldo();
    }

    private static void calcularIntereses() {
        if (clienteActual == null) {
            System.out.println("No hay un cliente seleccionado para calcular intereses.");
            return;
        }
        System.out.println("\n--- Calculando Intereses ---");
        clienteActual.getCuenta().calcularInteres();
    }

    private static double obtenerDoubleValido() {
        while (!scanner.hasNextDouble()) {
            System.out.println("Entrada invalida. Por favor, ingrese un numero decimal.");
            scanner.next();
            System.out.print("Ingrese un monto: ");
        }
        double valor = scanner.nextDouble();
        scanner.nextLine(); // Consume la nueva linea
        return valor;
    }

    private static void guardarClientes() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(NOMBRE_ARCHIVO_CLIENTES))) {
            oos.writeObject(clientes);
            System.out.println("Datos de clientes guardados exitosamente en " + NOMBRE_ARCHIVO_CLIENTES);
        } catch (IOException e) {
            System.err.println("Error al guardar los datos de los clientes: " + e.getMessage());
        }
    }

    private static void cargarClientes() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(NOMBRE_ARCHIVO_CLIENTES))) {
            clientes = (List<Cliente>) ois.readObject();
            System.out.println("Datos de clientes cargados exitosamente desde " + NOMBRE_ARCHIVO_CLIENTES);

            
            if (!clientes.isEmpty()) {
                clienteActual = clientes.get(0);
                System.out.println("Cliente actual establecido a: " + clienteActual.getNombre() + " " + clienteActual.getApellidoPaterno());
            }
               // Opcionalmente, crear un cliente de demostracion si no existe el archivo
        } catch (FileNotFoundException e) {
            System.out.println("No se encontraron datos de clientes anteriores. Se iniciara con una lista vacia.");
            
            try {
                CuentaCorriente cuentaDemo = new CuentaCorriente("123456789", 500000);
                Cliente clienteDemo = new Cliente("19.767.453-0", "Ana", "Gomez", "Perez",
                                                  "Calle Falsa #123", "Santiago", "987654321", cuentaDemo);
                clientes.add(clienteDemo);
                clienteActual = clienteDemo;
                System.out.println("Cliente de demostracion cargado!");
                System.out.println("Cliente actual establecido a: " + clienteActual.getNombre() + " " + clienteActual.getApellidoPaterno());
            } catch (IllegalArgumentException ex) {
                System.out.println("Error al inicializar cliente de demostracion: " + ex.getMessage());
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar los datos de los clientes: " + e.getMessage());
        }
    }
}