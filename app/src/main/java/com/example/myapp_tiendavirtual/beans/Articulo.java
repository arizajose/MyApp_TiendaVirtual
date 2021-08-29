package com.example.myapp_tiendavirtual.beans;

import java.io.Serializable;

public class Articulo implements Serializable {
    String id;
    String nombre;
    Double precio;
    String imagen;

    public Articulo() {
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getPrecio() { return precio; }

    public void setPrecio(Double precio) { this.precio = precio;
    }

    public String getImagen() { return imagen; }

    public void setImagen(String imagen) { this.imagen = imagen; }
}


