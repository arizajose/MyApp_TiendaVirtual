package com.example.myapp_tiendavirtual.beans;

import java.io.Serializable;

public class Categoria implements Serializable {
    String id;
    String nombre;
    String imagen;

    public Categoria() {
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getImagen() { return imagen; }

    public void setImagen(String imagen) { this.imagen = imagen; }
}


