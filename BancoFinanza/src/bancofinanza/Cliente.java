/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java 
 */
package bancario;

public class Cliente {
    private String rut;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String domicilio;
    private String comuna;
    private String telefono;
    private Cuenta cuentaCorriente;

    public Cliente(String rut, String nombre, String apellidoPaterno, String apellidoMaterno, 
                   String domicilio, String comuna, String telefono, int numeroCuenta) {
        if (!rut.matches("\\d{2}\\.\\d{3}\\.\\d{3}-\\d")) {
            throw new IllegalArgumentException("Error: El RUT debe estar en el formato XX.XXX.XXX-X.");
        }
        this.rut = rut;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.domicilio = domicilio;
        this.comuna = comuna;
        this.telefono = telefono;
        this.cuentaCorriente = new Cuenta(numeroCuenta, 0);
    }

    public String getRut() { return rut; }
    public Cuenta getCuentaCorriente() { return cuentaCorriente; }

    public void mostrarDatos() {
        System.out.println("\nDatos del Cliente:");
        System.out.println("RUT: " + rut);
        System.out.println("Nombre: " + nombre);
        System.out.println("Apellido Paterno: " + apellidoPaterno);
        System.out.println("Apellido Materno: " + apellidoMaterno);
        System.out.println("Domicilio: " + domicilio);
        System.out.println("Comuna: " + comuna);
        System.out.println("Telefono: " + telefono);
        System.out.println("Cuenta Corriente: " + cuentaCorriente.getNumeroCuenta());
        System.out.println("Saldo: " + cuentaCorriente.getSaldo() + " pesos.");
    }
}
