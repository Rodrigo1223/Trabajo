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
public class Usuario implements Serializable, Comparable<Usuario> {
    private String idUsuario; // Identificador único del usuario
    private String nombre;    // Nombre del usuario

  
    // Constructor para crear una nueva instancia de Usuario.
    // @param idUsuario El ID único del usuario.
    // @param nombre El nombre del usuario.
     
    public Usuario(String idUsuario, String nombre) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
    }

    // Metodos "getter" para acceder a los atributos del usuario.
    public String getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    
    // Sobreescribe el metodo toString() para proporcionar una representacion legible del objeto Usuario.
    // @return Una cadena que describe el usuario.
    
    @Override
    public String toString() {
        return "ID Usuario: " + idUsuario + ", Nombre: " + nombre;
    }

    
    // Sobreescribe el metodo equals() para comparar objetos Usuario por su ID de usuario.
    // Es crucial para el correcto funcionamiento de HashSet y HashMap.
    // @param obj El objeto a comparar.
    // @return true si los objetos son iguales, false en caso contrario.
     
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario usuario = (Usuario) obj;
        return idUsuario.equals(usuario.idUsuario);
    }

    
    // Sobreescribe el metodo hashCode() para generar un codigo hash consistente.
    // Es crucial para el correcto funcionamiento de HashSet y HashMap.
    // @return El codigo hash del usuario.
     
    @Override
    public int hashCode() {
        return idUsuario.hashCode();
    }

    
    // Implementa el metodo compareTo para permitir la ordenacion de usuarios por nombre.
    // Necesario para TreeSet.
    // @param otroUsuario El otro usuario con el que comparar.
    // @return Un valor negativo, cero o positivo si este objeto es menor, igual o mayor que el objeto especificado.
    
    @Override
    public int compareTo(Usuario otroUsuario) {
        return this.nombre.compareTo(otroUsuario.nombre);
    }
}