package com.example.myapp_tiendavirtual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapp_tiendavirtual.adapters.AdaptadorArticulo;
import com.example.myapp_tiendavirtual.adapters.AdaptadorCategoria;
import com.example.myapp_tiendavirtual.beans.Articulo;
import com.example.myapp_tiendavirtual.beans.Categoria;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArticuloActivity extends AppCompatActivity {
    JsonObjectRequest jsonObjectRequest;
    JSONArray jsonArray;
    RecyclerView recyclerView;
    List<Articulo> articuloList;
    String url ="https://arizajose.000webhostapp.com/tControla.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulo);
        recyclerView = findViewById(R.id.recyclerViewArticulos);
        String codArt = getIntent().getStringExtra("codcategoria");
        Log.w("categoria",codArt);
        llenarArticulos(codArt);
    }

    public void llenarArticulos(String cod){
        String enlace = url+"?tag=consulta2&cod="+cod;
        articuloList = new ArrayList<>();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, enlace, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            jsonArray = response.getJSONArray("dato");
                            Log.w("datos",jsonArray.toString());
                            for (int i=0; i<jsonArray.length();i++){
                                JSONObject fila = (JSONObject) jsonArray.get(i);
                                Articulo a = new Articulo();
                                a.setId(fila.getString("codc"));
                                a.setNombre(fila.getString("nomc"));
                                a.setPrecio(fila.getDouble("precio"));
                                a.setImagen(fila.getString("imagen"));
                                articuloList.add(a);
                            }
                            //Adaptador
                            AdaptadorArticulo ap = new AdaptadorArticulo
                                    (articuloList,getApplication());
                            recyclerView.setLayoutManager(
                                    new LinearLayoutManager(getApplication()));
                            recyclerView.setAdapter(ap);
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
}



