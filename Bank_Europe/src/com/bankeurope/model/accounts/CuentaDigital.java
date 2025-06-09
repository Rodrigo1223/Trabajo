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

public class CuentaDigital extends CuentaBancaria implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final double TASA_INTERES_DIGITAL = 0.02; 

    public CuentaDigital(String numeroCuenta, double saldoInicial) {
        super(numeroCuenta, saldoInicial);
    }

    public CuentaDigital(String numeroCuenta) {
        super(numeroCuenta);
    }

    @Override
    public void calcularInteres() {
        double interesCalculado = this.saldo * TASA_INTERES_DIGITAL;
        this.saldo += interesCalculado;
        System.out.println("Cuenta Digital: Se aplicaron intereses de $" + interesCalculado + ". Nuevo saldo: $" + this.saldo);
    }
}