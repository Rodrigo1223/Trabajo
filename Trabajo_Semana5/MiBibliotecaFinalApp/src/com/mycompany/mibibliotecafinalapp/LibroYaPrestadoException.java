/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mibibliotecafinalapp;

/**
 *
 * @author rodri
 */


 // Excepcion personalizada para indicar que un libro ya esta prestado.
 // Es una excepción "checked" (comprobada).
 
public class LibroYaPrestadoException extends Exception {
    
 // Constructor para LibroYaPrestadoException.
 // @param mensaje El mensaje que describe la razón de la excepcion.
     
    public LibroYaPrestadoException(String mensaje) {
        super(mensaje); // Llama al constructor de la clase base Exception.
    }
}