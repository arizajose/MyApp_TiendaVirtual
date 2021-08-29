package com.example.myapp_tiendavirtual.beans;

import android.app.Application;

import java.io.Serializable;

public class Usuario  implements Serializable {
    String id;
    String password;
    String apellido;
    String nombre;

    public Usuario() {
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getApellido() { return apellido; }

    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }
}



