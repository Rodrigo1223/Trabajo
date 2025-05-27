/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java 
 */
package bancario;
import java.util.Scanner;

public class Main {
    public static int leerOpcionMenu(Scanner scanner) {
        while (true) {
            System.out.print("Seleccione una opcion (1-6): ");
            if (scanner.hasNextInt()) {
                int opcion = scanner.nextInt();
                scanner.nextLine(); 

                if (opcion >= 1 && opcion <= 6) {
                    return opcion;
                } else {
                    System.out.println("Error: Opcion invalida. Debe ingresar un numero entre 1 y 6.");
                }
            } else {
                System.out.println("Error: Entrada invalida. Debe ingresar un numero entero.");
                scanner.nextLine(); 
            }
        }
    }

    public static int validarNumeroCuenta(Scanner scanner) {
        while (true) {
            System.out.print("Ingrese Numero de Cuenta (9 digitos): ");
            String entrada = scanner.nextLine();

            if (entrada.matches("\\d{9}")) {
                return Integer.parseInt(entrada);
            } else {
                System.out.println("Error: El numero de cuenta debe tener exactamente 9 digitos.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BancoBoston banco = new BancoBoston();

        int opcion;
        do {
            System.out.println("\nMenu de Operaciones:");
            System.out.println("1. Registrar Cliente");
            System.out.println("2. Ver Datos de Clientes");
            System.out.println("3. Depositar");
            System.out.println("4. Girar");
            System.out.println("5. Consultar Saldo");
            System.out.println("6. Salir");

            opcion = leerOpcionMenu(scanner);

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese RUT (Formato: XX.XXX.XXX-X): ");
                    String rut = scanner.nextLine();
                    System.out.print("Ingrese Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese Apellido Paterno: ");
                    String apellidoPaterno = scanner.nextLine();
                    System.out.print("Ingrese Apellido Materno: ");
                    String apellidoMaterno = scanner.nextLine();
                    System.out.print("Ingrese Domicilio: ");
                    String domicilio = scanner.nextLine();
                    System.out.print("Ingrese Comuna: ");
                    String comuna = scanner.nextLine();
                    System.out.print("Ingrese Telefono: ");
                    String telefono = scanner.nextLine();
                    int numeroCuenta = validarNumeroCuenta(scanner);

                    banco.registrarCliente(rut, nombre, apellidoPaterno, apellidoMaterno, domicilio, comuna, telefono, numeroCuenta);
                    break;

                case 2:
                    banco.mostrarClientes();
                    break;

                case 3:
                    System.out.print("Ingrese numero de cuenta: ");
                    int cuentaDeposito = validarNumeroCuenta(scanner);
                    Cliente clienteDeposito = banco.buscarClientePorCuenta(cuentaDeposito);
                    if (clienteDeposito != null) {
                        System.out.print("Ingrese monto a depositar: ");
                        int montoDeposito = scanner.nextInt();
                        scanner.nextLine();
                        clienteDeposito.getCuentaCorriente().depositar(montoDeposito);
                    } else {
                        System.out.println("Error: Cuenta no encontrada.");
                    }
                    break;

                case 4:
                    System.out.print("Ingrese numero de cuenta: ");
                    int cuentaGiro = validarNumeroCuenta(scanner);
                    Cliente clienteGiro = banco.buscarClientePorCuenta(cuentaGiro);
                    if (clienteGiro != null) {
                        System.out.print("Ingrese monto a girar: ");
                        int montoGiro = scanner.nextInt();
                        scanner.nextLine();
                        clienteGiro.getCuentaCorriente().girar(montoGiro);
                    } else {
                        System.out.println("Error: Cuenta no encontrada.");
                    }
                    break;

                case 5:
                    System.out.print("Ingrese numero de cuenta: ");
                    int cuentaSaldo = validarNumeroCuenta(scanner);
                    Cliente clienteSaldo = banco.buscarClientePorCuenta(cuentaSaldo);
                    if (clienteSaldo != null) {
                        clienteSaldo.getCuentaCorriente().consultarSaldo();
                    } else {
                        System.out.println("Error: Cuenta no encontrada.");
                    }
                    break;

                case 6:
                    System.out.println("Saliendo...");
                    break;
            }
        } while (opcion != 6);

        scanner.close();
    }
}
