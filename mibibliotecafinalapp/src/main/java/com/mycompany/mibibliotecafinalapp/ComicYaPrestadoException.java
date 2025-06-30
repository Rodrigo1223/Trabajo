/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mibibliotecafinalapp;
/**
 *
 * @author rodri
 */
// Extiende Exception para ser una excepcion "checked" (comprobada).
// Al igual que ComicNoEncontradoException, debe ser declarada o manejada.
// Se utiliza cuando se intenta prestar o vender un comic que ya no esta disponible.
public class ComicYaPrestadoException extends Exception {
    /**
     * Constructor para ComicYaPrestadoException.
     * @param mensaje El mensaje que describe la razón de la excepción.
     */
    public ComicYaPrestadoException(String mensaje) {
        super(mensaje); // Llama al constructor de la clase base Exception.
    }
}  


