package com.example.myapp_tiendavirtual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapp_tiendavirtual.adapters.AdaptadorArticulo;
import com.example.myapp_tiendavirtual.beans.Articulo;
import com.example.myapp_tiendavirtual.beans.ClaseGlobal;
import com.example.myapp_tiendavirtual.beans.Compra;
import com.example.myapp_tiendavirtual.beans.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CompraActivity extends AppCompatActivity {
    TextView codprod,descrip,precio;
    EditText edtcantidad;
    ImageView imgproducto;
    JsonObjectRequest jsonObjectRequest;
    JSONArray jsonArray;
    Compra compra;
    String url ="https://arizajose.000webhostapp.com/tControla.php";
    String urlimg ="https://arizajose.000webhostapp.com/images/";
    List<Compra> lista;
    ClaseGlobal claseGlobal;
    String carritolog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);
        codprod=findViewById(R.id.txtprodcod);
        descrip=findViewById(R.id.txtproddescrip);
        precio=findViewById(R.id.txtprodprecio);
        edtcantidad=findViewById(R.id.edtprodcantidad);
        imgproducto=findViewById(R.id.imgproducto);
        claseGlobal = new ClaseGlobal();
        String codPro = getIntent().getStringExtra("codproducto");
        Log.w("producto",codPro);
        compra = new Compra();
        productoCompra(codPro);
    }

    public void productoCompra(String cod){
        String enlace = url+"?tag=consulta3&cod="+cod;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, enlace, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            jsonArray = response.getJSONArray("dato");
                            Log.w("datos",jsonArray.toString());
                            for (int i=0; i<jsonArray.length();i++){
                                JSONObject fila = (JSONObject) jsonArray.get(i);
                                compra.setId(fila.getString("codc"));
                                compra.setNombre(fila.getString("nomc"));
                                compra.setPrecio(fila.getDouble("precio"));
                                compra.setImagen(fila.getString("imagen"));
                            }
                            codprod.setText(compra.getId());
                            descrip.setText(compra.getNombre());
                            precio.setText(compra.getPrecio().toString());
                            Glide.with(CompraActivity.this)
                                    .load(urlimg+compra.getImagen())
                                    .asBitmap()
                                    .error(R.drawable.ic_launcher_background)
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .into(imgproducto);
                        } catch (JSONException ex) {
                            Toast.makeText(getApplication(),ex.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplication(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue cola = Volley.newRequestQueue(this);
        cola.add(jsonObjectRequest);
    }

    public void irCategoria(View view){
        Intent it=new Intent(this,CategoriaActivity.class);
        startActivity(it);
    }

    public void agregarCarrito(View view){
        if(claseGlobal.getLista() == null){
            lista = new ArrayList<>();
        }else{
            lista = claseGlobal.getLista();
        }
        compra.setCantidad(Integer.parseInt(edtcantidad.getText().toString()));
        lista.add(compra); //agregando a la lista de compras
        claseGlobal.setLista(lista); //agregando al carrito
        Toast.makeText(getApplication(),"Agregando al carrito",Toast.LENGTH_SHORT).show();
        Log.w("producto",compra.toString());
        //Mostrando el carrito
        for (Compra c: claseGlobal.getLista()) {
            carritolog+="{"+"id:"+ c.getId()+
                    ", producto: "+c.getNombre()+
                    ", precio: "+c.getPrecio()+
                    ", cantidad: "+c.getCantidad()+
                    ", total: "+c.total()+"} \n";
        }
        Log.w("carrito",carritolog);
    }

    public void irCarrito(View view){
        Intent it=new Intent(this,GlobalCarritoActivity.class);
        startActivity(it);
    }
}



