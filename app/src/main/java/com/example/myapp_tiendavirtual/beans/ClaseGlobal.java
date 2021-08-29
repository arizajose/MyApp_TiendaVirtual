package com.example.myapp_tiendavirtual.beans;

import android.app.Application;

import java.util.List;

public class ClaseGlobal extends Application {

    static List<Usuario> userlogin;
    static List<Compra> lista;

    public  List<Usuario> getUsuario() {
        return userlogin;
    }

    public void setUsuario(List<Usuario> userlogin) {
        this.userlogin = userlogin;
    }

    public void deleteUsuario(){
        userlogin.clear();
    }

    public List<Compra> getLista() { return lista; }

    public void setLista(List<Compra> lista) { this.lista = lista; }

    public  void deleteLista(){ lista.clear(); }

}




