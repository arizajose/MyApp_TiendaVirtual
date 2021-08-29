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
import com.example.myapp_tiendavirtual.adapters.AdaptadorCategoria;
import com.example.myapp_tiendavirtual.beans.Categoria;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoriaActivity extends AppCompatActivity {
    JsonObjectRequest jsonObjectRequest;
    JSONArray jsonArray;
    RecyclerView recyclerView;
    List<Categoria> categoriaList;
    String url ="https://arizajose.000webhostapp.com/tControla.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        recyclerView = findViewById(R.id.recyclerViewCategorias);
        llenarCategorias();
    }

    public void llenarCategorias(){
        String enlace = url+"?tag=consulta1";
        categoriaList = new ArrayList<>();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, enlace, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            jsonArray = response.getJSONArray("dato");
                            Log.w("datos",jsonArray.toString());
                            for (int i=0; i<jsonArray.length();i++){
                                JSONObject fila = (JSONObject) jsonArray.get(i);
                                Categoria c = new Categoria();
                                c.setId(fila.getString("codc"));
                                c.setNombre(fila.getString("nomc"));
                                c.setImagen(fila.getString("imagen"));
                                categoriaList.add(c);
                            }
                            //Adaptador
                            AdaptadorCategoria ap = new AdaptadorCategoria
                                    (categoriaList,getApplication());
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



