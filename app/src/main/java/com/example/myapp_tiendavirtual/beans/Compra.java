package com.example.myapp_tiendavirtual.beans;

import java.io.Serializable;

public class Compra implements Serializable {
    String id;
    String nombre;
    String imagen;
    Double precio;
    int cantidad;

    public Compra() {
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getPrecio() { return precio; }

    public void setPrecio(Double precio) { this.precio = precio; }

    public String getImagen() { return imagen; }

    public void setImagen(String imagen) { this.imagen = imagen; }

    public int getCantidad() { return cantidad; }

    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public Double total(){ return  precio*cantidad; }

    @Override
    public String toString() {
        return "Compra{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", imagen='" + imagen + '\'' +
                ", precio=" + precio +
                ", cantidad=" + cantidad +
                '}';
    }
}


