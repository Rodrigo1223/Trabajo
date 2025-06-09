/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author rodri
 */
package com.bankeurope.model.accounts;

import com.bankeurope.model.CuentaBancaria;
import java.io.Serializable;

public class CuentaAhorros extends CuentaBancaria implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final double TASA_INTERES_AHORRO = 0.01; // 1% de interes anual

    public CuentaAhorros(String numeroCuenta, double saldoInicial) {
        super(numeroCuenta, saldoInicial);
    }

    public CuentaAhorros(String numeroCuenta) {
        super(numeroCuenta);
    }

    @Override
    public void calcularInteres() {
        double interesCalculado = this.saldo * TASA_INTERES_AHORRO;
        this.saldo += interesCalculado;
        System.out.println("Cuenta de Ahorros: Se aplicaron intereses de $" + interesCalculado + ". Nuevo saldo: $" + this.saldo);
    }
}