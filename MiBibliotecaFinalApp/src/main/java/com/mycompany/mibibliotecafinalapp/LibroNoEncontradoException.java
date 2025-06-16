/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author rodri
 */
package com.mycompany.mibibliotecafinalapp;

// Excepcion personalizada para cuando un libro no es encontrado en la biblioteca.
// Es una excepción "checked" (comprobada), lo que significa que debe ser declarada
// en la firma del método que la lanza o ser manejada con un bloque try-catch.
public class LibroNoEncontradoException extends Exception {
    /**
     * Constructor para LibroNoEncontradoException.
     * @param mensaje El mensaje que describe la razón de la excepción.
     */
    public LibroNoEncontradoException(String mensaje) {
        super(mensaje); // Llama al constructor de la clase base Exception.
    }
}