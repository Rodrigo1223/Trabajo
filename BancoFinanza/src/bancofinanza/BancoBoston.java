/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java 
 */

package bancario;
import java.util.ArrayList;

public class BancoBoston {
    private ArrayList<Cliente> clientes = new ArrayList<>();

    private boolean clienteExiste(String rut, int numeroCuenta) {
        for (Cliente cliente : clientes) {
            if (cliente.getRut().equals(rut) || cliente.getCuentaCorriente().getNumeroCuenta() == numeroCuenta) {
                return true;
            }
        }
        return false;
    }

    public void registrarCliente(String rut, String nombre, String apellidoPaterno, String apellidoMaterno,
                                 String domicilio, String comuna, String telefono, int numeroCuenta) {
        if (clienteExiste(rut, numeroCuenta)) {
            System.out.println("Error: El cliente con RUT " + rut + " o cuenta " + numeroCuenta + " ya esta registrado.");
            return;
        }

        Cliente nuevoCliente = new Cliente(rut, nombre, apellidoPaterno, apellidoMaterno, domicilio, comuna, telefono, numeroCuenta);
        clientes.add(nuevoCliente);
        System.out.println("Cliente registrado exitosamente.");
    }

    public Cliente buscarClientePorCuenta(int numeroCuenta) {
        for (Cliente cliente : clientes) {
            if (cliente.getCuentaCorriente().getNumeroCuenta() == numeroCuenta) {
                return cliente;
            }
        }
        return null;
    }

    public void mostrarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            for (Cliente cliente : clientes) {
                cliente.mostrarDatos();
            }
        }
    }
}
