/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mibibliotecafinalapp;

import java.io.Serializable;
/**
 *
 * @author rodri
 */


import java.io.Serializable; // Importar Serializable si planeas guardar objetos directamente

public class Libro implements Serializable, Comparable<Libro> {
    private String idLibro;
    private String titulo;
    private String autor;
    private boolean disponible; // Añadido para gestionar la disponibilidad del libro

    
     // Constructor de la clase Libro.
     // @param idLibro Identificador unico del libro.
     // @param titulo Titulo del libro.
     // @param autor Autor del libro.
     
    public Libro(String idLibro, String titulo, String autor) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.autor = autor;
        this.disponible = true; // Por defecto, un libro esta disponible al ser creado
    }

    // Getters (Metodos para obtener los valores de los atributos) 
    public String getIdLibro() {
        return idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public boolean isDisponible() {
        return disponible;
    }

    // Setters (Metodos para modificar los valores de los atributos) 
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    // Metodos sobrescritos 

    
    // Sobreescribe el metodo toString() para proporcionar una representacion
    // legible del objeto Libro.
    // @return Una cadena que describe el libro.
     
    @Override
    public String toString() {
        return "ID: " + idLibro + ", Titulo: " + titulo + ", Autor: " + autor + ", Disponible: " + (disponible ? "Si" : "No");
    }

    
    // Sobreescribe el metodo equals() para comparar objetos Libro por su ID.
    // Es crucial para el correcto funcionamiento de HashSet y HashMap.
    // @param o El objeto a comparar.
    // @return true si los objetos son iguales, false en caso contrario.
     
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return idLibro.equals(libro.idLibro); // Los libros son iguales si tienen el mismo ID
    }

    
    // Sobreescribe el metodo hashCode() para generar un codigo hash consistente
    // basado en el ID del libro. Es crucial para el correcto funcionamiento de HashSet y HashMap.
    // @return El codigo hash del libro.
     
    @Override
    public int hashCode() {
        return idLibro.hashCode();
    }

    
    // Implementa el metodo compareTo para permitir la ordenacion de libros.
    // Por ejemplo, para ordenar por titulo.
    // @param otroLibro El otro libro con el que comparar.
    // @return Un valor negativo, cero o positivo si este objeto es menor, igual o mayor que el objeto especificado.
     
    @Override
    public int compareTo(Libro otroLibro) {
    // Se puede elegir el criterio de ordenacion, por ejemplo, por titulo
        return this.titulo.compareTo(otroLibro.titulo);
    }
}