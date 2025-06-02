/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rodri
 */
package bankbostonapp; 

import java.io.Serializable;


public class Cliente implements InformacionCliente, Serializable {
    private static final long serialVersionUID = 1L; 

    private String rut;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String domicilio;
    private String comuna;
    private String telefono;
   
    private CuentaBancaria cuentaBancaria;

   
    public Cliente(String rut, String nombre, String apellidoPaterno, String apellidoMaterno,
                   String domicilio, String comuna, String telefono, CuentaBancaria cuentaBancaria) {
        if (!validarRut(rut)) {
            throw new IllegalArgumentException("Rut invalido. Debe tener entre 11 y 12 caracteres (incluyendo puntos y guion).");
        }
        this.rut = rut;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.domicilio = domicilio;
        this.comuna = comuna;
        this.telefono = telefono;
        this.cuentaBancaria = cuentaBancaria; 
    }

    
    public String getRut() { return rut; }
    public String getNombre() { return nombre; }
    public String getApellidoPaterno() { return apellidoPaterno; }
    public String getApellidoMaterno() { return apellidoMaterno; }
    public String getDomicilio() { return domicilio; }
    public String getComuna() { return comuna; }
    public String getTelefono() { return telefono; }
   
    public CuentaBancaria getCuentaBancaria() { return cuentaBancaria; }

    
    public void setRut(String rut) {
        if (!validarRut(rut)) {
            throw new IllegalArgumentException("Rut invalido. Debe tener entre 11 y 12 caracteres (incluyendo puntos y guion).");
        }
        this.rut = rut;
    }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellidoPaterno(String apellidoPaterno) { this.apellidoPaterno = apellidoPaterno; }
    public void setApellidoMaterno(String apellidoMaterno) { this.apellidoMaterno = apellidoMaterno; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }
    public void setComuna(String comuna) { this.comuna = comuna; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setCuentaBancaria(CuentaBancaria cuentaBancaria) { this.cuentaBancaria = cuentaBancaria; }


    
    @Override
    public void mostrarInformacion() {
        System.out.println("Rut: " + this.rut);
        System.out.println("Nombre: " + this.nombre);
        System.out.println("Apellido paterno: " + this.apellidoPaterno);
        System.out.println("Apellido materno: " + this.apellidoMaterno);
        System.out.println("Domicilio: " + this.domicilio);
        System.out.println("Comuna: " + this.comuna);
        System.out.println("Telefono: " + this.telefono);
        
        System.out.println("Tipo de cuenta: " + this.cuentaBancaria.getClass().getSimpleName()); 
        System.out.println("Numero de cuenta: " + this.cuentaBancaria.getNumero());
        this.cuentaBancaria.consultarSaldo(); 
    }

    
    private boolean validarRut(String rut) {
        return rut != null && rut.length() >= 11 && rut.length() <= 12;
    }
}
