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

public class CuentaCorriente extends CuentaBancaria implements Serializable {
    private static final long serialVersionUID = 1L;

    public CuentaCorriente(String numeroCuenta, double saldoInicial) {
        super(numeroCuenta, saldoInicial);
    }

    public CuentaCorriente(String numeroCuenta) {
        super(numeroCuenta);
    }

    @Override
    public void calcularInteres() {
        
        System.out.println("Cuenta Corriente: No se aplican intereses.");
    }
}