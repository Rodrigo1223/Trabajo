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

public class CuentaCredito extends CuentaBancaria implements Serializable {
    private static final long serialVersionUID = 1L; 

    private int limiteCredito; 

    
    public CuentaCredito(String numero, int limiteCredito) {
        super(numero, limiteCredito); 
        this.limiteCredito = limiteCredito;
        System.out.println("DEBUG: Cuenta de Credito creada: " + numero + " con limite: " + limiteCredito);
    }

    
    public CuentaCredito(String numero) {
        this(numero, 500000); 
    }

    
    public int getLimiteCredito() {
        return limiteCredito;
    }

    
    public void setLimiteCredito(int limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    
    @Override
    public void realizarTransaccion(int monto, String tipoTransaccion) {
        if (monto <= 0) {
            System.out.println("Monto invalido. Ingrese un valor positivo.");
            return;
        }

        if (tipoTransaccion.equalsIgnoreCase("deposito")) {
            
            
            this.saldo += monto;
            
            if (this.saldo > this.limiteCredito) {
                this.saldo = this.limiteCredito;
            }
            System.out.println("Pago de " + monto + " pesos realizado en Cuenta de Credito " + numero + "!");
            consultarSaldo();
        } else if (tipoTransaccion.equalsIgnoreCase("giro")) {
            
            if (this.saldo >= monto) { 
                this.saldo -= monto;
                System.out.println("Uso de credito de " + monto + " pesos realizado desde Cuenta de Credito " + numero + "!");
                consultarSaldo();
            } else {
                System.out.println("Limite de credito insuficiente para realizar la operacion en Cuenta de Credito " + numero + ". Limite disponible: " + this.saldo);
            }
        } else {
            System.out.println("Tipo de transaccion no reconocido para Cuenta de Credito.");
        }
    }

    
    @Override
    public void consultarSaldo() {
        System.out.println("Limite disponible en Cuenta de Credito " + this.numero + ": " + this.saldo + " pesos (Limite total: " + this.limiteCredito + " pesos).");
    }
}