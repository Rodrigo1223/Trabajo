/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mibibliotecafinalapp;

/**
 *
 * @author rodri
 */
// Clase Usuario


import java.io.Serializable; /// Implementa Serializable para permitir la escritura del objeto en archivos.
/**
 * Representa un usuario dentro del sistema de coleccion de comics.
 * Contiene informacion basica como un identificador unico y el nombre del usuario.
 */
public class Usuario implements Serializable {
    private String idUsuario; // Identificador unico para cada usuario.
    private String nombre;    // Nombre del usuario

    /**
     * Constructor para crear una nueva instancia de Usuario.
     * @param idUsuario El ID unico que identificara al usuario.
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
     * Util para mostrar la información del usuario en informes o en la consola.
     * @return Una cadena que describe el usuario con su ID y nombre.
     */
    @Override
    public String toString() {
        return "ID Usuario: " + idUsuario + ", Nombre: " + nombre;
    }
}