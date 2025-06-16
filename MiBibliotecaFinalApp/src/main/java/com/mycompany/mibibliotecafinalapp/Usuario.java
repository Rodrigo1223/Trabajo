/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author rodri
 */
package com.mycompany.mibibliotecafinalapp;

import java.io.Serializable; // Implementa Serializable para permitir la escritura en archivos.

public class Usuario implements Serializable {
    private String idUsuario; // Identificador unico del usuario
    private String nombre;    // Nombre del usuario

    /**
     * Constructor para crear una nueva instancia de Usuario.
     * @param idUsuario El ID unico del usuario.
     * @param nombre El nombre del usuario.
     */
    public Usuario(String idUsuario, String nombre) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
    }

    // Métodos "getter" para acceder a los atributos del usuario.
    public String getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    /**
     * Sobreescribe el metodo toString() para proporcionar una representacion legible del objeto Usuario.
     * @return Una cadena que describe el usuario.
     */
    @Override
    public String toString() {
        return "ID Usuario: " + idUsuario + ", Nombre: " + nombre;
    }
}