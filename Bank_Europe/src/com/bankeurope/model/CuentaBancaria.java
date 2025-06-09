/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author rodri
 */
package com.bankeurope.model;

import java.io.Serializable;

public abstract class CuentaBancaria implements Serializable {
    private static final long serialVersionUID = 1L; // Para serializacion

    protected String numeroCuenta;
    protected double saldo;

    public CuentaBancaria(String numeroCuenta, double saldoInicial) {
        if (numeroCuenta == null || numeroCuenta.isEmpty() || !numeroCuenta.matches("\\d{9}")) {
            throw new IllegalArgumentException("Numero de cuenta invalido. Debe ser un numero de 9 digitos.");
        }
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldoInicial;
    }

    public CuentaBancaria(String numeroCuenta) {
        this(numeroCuenta, 0.0);
    }

    
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

   
    public void setNumeroCuenta(String numeroCuenta) {
        if (numeroCuenta == null || numeroCuenta.isEmpty() || !numeroCuenta.matches("\\d{9}")) {
            throw new IllegalArgumentException("Numero de cuenta invalido. Debe ser un numero de 9 digitos.");
        }
        this.numeroCuenta = numeroCuenta;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    // Metodo abstracto para el calculo de intereses
    public abstract void calcularInteres();

    // Metodos comunes para todas las cuentas (deposito y giro)
    public void depositar(double monto) {
        if (monto <= 0) {
            System.out.println("El monto a depositar debe ser positivo.");
            return;
        }
        this.saldo += monto;
        System.out.println("Deposito de $" + monto + " realizado. Saldo actual: $" + this.saldo);
    }

    public void girar(double monto) {
        if (monto <= 0) {
            System.out.println("El monto a girar debe ser positivo.");
            return;
        }
        if (this.saldo >= monto) {
            this.saldo -= monto;
            System.out.println("Giro de $" + monto + " realizado. Saldo actual: $" + this.saldo);
        } else {
            System.out.println("Saldo insuficiente para realizar el giro. Saldo actual: $" + this.saldo);
        }
    }

    public void consultarSaldo() {
        System.out.println("Saldo actual de la cuenta " + this.numeroCuenta + ": $" + this.saldo);
    }
}