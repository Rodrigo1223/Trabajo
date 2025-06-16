/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author rodri
 */

package com.mycompany.mibibliotecafinalapp;

import java.io.Serializable; // Permite la escritura en archivos.

public class Libro implements Serializable {
    private String titulo;      // Título del libro
    private String autor;       // Autor del libro
    private boolean prestado;   // Estado del libro: true si esta prestado, false si esta disponible

    /**
     * Constructor para crear una nueva instancia de Libro.
     * @param titulo El titulo del libro.
     * @param autor El autor del libro.
     */
    public Libro(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
        this.prestado = false; // Por defecto, un libro recien creado no esta prestado.
    }

    // Métodos "getter" para acceder a los atributos del libro.
    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public boolean isPrestado() {
        return prestado;
    }

    // Método "setter" para cambiar el estado de prestado del libro.
    public void setPrestado(boolean prestado) {
        this.prestado = prestado;
    }

    /**
     * Sobreescribe el método toString() para proporcionar una representación legible del objeto Libro.
     * @return Una cadena que describe el libro.
     */
    @Override
    public String toString() {
        return "Titulo: " + titulo + ", Autor: " + autor + ", Prestado: " + (prestado ? "Si" : "No");
    }
}

 