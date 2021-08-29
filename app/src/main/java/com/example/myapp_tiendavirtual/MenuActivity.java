package com.example.myapp_tiendavirtual;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void irCategoria(View view){
        Intent it=new Intent(this,CategoriaActivity.class);
        startActivity(it);
    }

    public void irBoleta(View view){
        Intent it=new Intent(this,BoletaActivity.class);
        startActivity(it);
    }

    public void irLogin(View view){
        Intent it=new Intent(this,MainActivity.class);
        startActivity(it);
    }
}



