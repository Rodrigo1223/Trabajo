/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java 
 */
package bancario;

public class Cuenta {
    private int numeroCuenta;
    private int saldo;

    public Cuenta(int numeroCuenta, int saldoInicial) {
        if (String.valueOf(numeroCuenta).length() != 9) {
            throw new IllegalArgumentException("Error: El numero de cuenta debe tener exactamente 9 digitos.");
        }
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldoInicial;
    }

    public int getNumeroCuenta() { return numeroCuenta; }
    public int getSaldo() { return saldo; }

    public void depositar(int monto) {
        if (monto > 0) {
            saldo += monto;
            System.out.println("Deposito realizado con exito.");
            System.out.println("Saldo actual: " + saldo + " pesos.");
        } else {
            System.out.println("Error: El monto debe ser mayor a cero.");
        }
    }

    public void girar(int monto) {
        if (monto > saldo) {
            System.out.println("Error: Saldo insuficiente.");
        } else if (monto <= 0) {
            System.out.println("Error: El monto debe ser mayor a cero.");
        } else {
            saldo -= monto;
            System.out.println("Giro realizado con exito.");
            System.out.println("Saldo actual: " + saldo + " pesos.");
        }
    }

    public void consultarSaldo() {
        System.out.println("Saldo actual: " + saldo + " pesos.");
    }
}
