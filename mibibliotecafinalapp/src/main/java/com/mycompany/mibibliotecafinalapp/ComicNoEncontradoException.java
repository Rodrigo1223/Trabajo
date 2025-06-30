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
// Esto significa que los metodos que puedan lanzar esta excepcion deben
// declararla explícitamente en su firma (con 'throws') o manejarla con un 'try-catch'.
// Se utiliza cuando un cómic buscado no se encuentra en la coleccion.
public class ComicNoEncontradoException extends Exception {
    /**
     * Constructor para ComicNoEncontradoException.
     * @param mensaje El mensaje que describe la razón de la excepción.
     */
    public ComicNoEncontradoException(String mensaje) {
        super(mensaje); // Llama al constructor de la clase base Exception.
    }
}