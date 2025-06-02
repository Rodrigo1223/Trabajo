/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bankbostonapp; 

/**
 *
 * @author rodri
 */


import java.io.Serializable; 

public abstract class CuentaBancaria implements Serializable {
    private static final long serialVersionUID = 1L; 

    protected String numero; 
    protected int saldo;     

    
    public CuentaBancaria(String numero, int saldoInicial) {
        this.numero = numero;
        this.saldo = saldoInicial;
        System.out.println("DEBUG: Cuenta Bancaria creada: " + numero + " con saldo: " + saldoInicial);
    }

    
    public CuentaBancaria(String numero) {
        this(numero, 0); 
    }

    
    public String getNumero() {
        return numero;
    }

    public int getSaldo() {
        return saldo;
    }

   
    public void setNumero(String numero) {
        this.numero = numero;
    }
    

    
    public abstract void realizarTransaccion(int monto, String tipoTransaccion);

    
    public void consultarSaldo() {
        System.out.println("Saldo actual de la cuenta " + this.numero + ": " + this.saldo + " pesos.");
    }
}
