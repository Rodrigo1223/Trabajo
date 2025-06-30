/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mibibliotecafinalapp;

/**
 *
 * @author rodri
 */

import java.io.Serializable;
import java.util.Objects;            // Necesario para Objects.hash y Objects.equals

public class Comic implements Serializable {
    private String titulo;           // Titulo principal del comic.
    private String autor;            // Autor o creador principal del comic.
    private String genero;           // Genero del cómic (ej. "Superhéroes", "Ciencia Ficcion").
    private String fechaPublicacion; // Fecha de lanzamiento del comic (formato YYYY-MM-DD).
    private double precio;           // Precio de venta del comic.
    private boolean disponible;      // true si el comic está en stock y disponible, false si esta prestado/vendido.

    public Comic(String titulo, String autor, String genero, String fechaPublicacion, double precio) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.fechaPublicacion = fechaPublicacion;
        this.precio = precio;
        this.disponible = true;      // Por defecto, un comic recien creado esta disponible
    }

    // Getters
    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getGenero() {
        return genero;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public double getPrecio() {
        return precio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    // Setters
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    /**
     * Sobreescribe el metodo toString() para proporcionar una representación legible del objeto Comic.
     * Util para mostrar los detalles completos del cómic en informes o en la consola de manera amigable.
     * @return Una cadena que describe el comic con todos sus atributos.
     */
    @Override
    public String toString() {
        return "Titulo: '" + titulo + '\'' +
               ", Autor: '" + autor + '\'' +
               ", Genero: '" + genero + '\'' +
               ", Fecha Publicacion: '" + fechaPublicacion + '\'' +
               ", Precio: $" + String.format("%.2f", precio) +
               ", Disponible: " + (disponible ? "Si" : "No");
    }

    /**
     * Sobreescribe el metodo equals() para comparar objetos Comic.
     * Dos comics se consideran iguales si tienen el mismo titulo y autor (ignorando mayusculas/minusculas).
     * @param o El objeto a comparar.
     * @return true si los objetos son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comic comic = (Comic) o;
        return titulo.equalsIgnoreCase(comic.titulo) &&
               autor.equalsIgnoreCase(comic.autor);
    }
    /**
     * Sobreescribe el metodo hashCode() para generar un codigo hash para objetos Comic.
     * Necesario cuando se sobreescribe equals(). Basado en titulo y autor.
     * @return El codigo hash del objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(titulo.toLowerCase(), autor.toLowerCase());
    }
}