/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author rodri
 */

package com.mycompany.mibibliotecafinalapp;

// Excepción personalizada para cuando se intenta prestar un libro que ya está prestado.
// También es una excepción "checked" (comprobada).
public class LibroYaPrestadoException extends Exception {
    /**
     * Constructor para LibroYaPrestadoException.
     * @param mensaje El mensaje que describe la razón de la excepción.
     */
    public LibroYaPrestadoException(String mensaje) {
        super(mensaje); // Llama al constructor de la clase base Exception.
    }
}