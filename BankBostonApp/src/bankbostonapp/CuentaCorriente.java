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

public class CuentaCorriente extends CuentaBancaria implements Serializable {
    private static final long serialVersionUID = 1L; 

    
    public CuentaCorriente(String numero) {
        super(numero); 
    }

    
    public CuentaCorriente(String numero, int saldoInicial) {
        super(numero, saldoInicial); 
    }

    
    @Override
    public void realizarTransaccion(int monto, String tipoTransaccion) {
        if (monto <= 0) {
            System.out.println("Monto invalido. Ingrese un valor positivo.");
            return;
        }

        if (tipoTransaccion.equalsIgnoreCase("deposito")) {
            this.saldo += monto;
            System.out.println("Deposito de " + monto + " pesos realizado en Cuenta Corriente " + numero + "!");
            consultarSaldo();
        } else if (tipoTransaccion.equalsIgnoreCase("giro")) {
            if (this.saldo >= monto) {
                this.saldo -= monto;
                System.out.println("Giro de " + monto + " pesos realizado desde Cuenta Corriente " + numero + "!");
                consultarSaldo();
            } else {
                System.out.println("Saldo insuficiente para realizar el giro en Cuenta Corriente " + numero + ". Saldo actual: " + this.saldo);
            }
        } else {
            System.out.println("Tipo de transaccion no reconocido para Cuenta Corriente.");
        }
    }
}
