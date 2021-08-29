package com.example.myapp_tiendavirtual;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapp_tiendavirtual.beans.Boleta;
import com.example.myapp_tiendavirtual.beans.ClaseGlobal;
import com.example.myapp_tiendavirtual.beans.Compra;
import com.example.myapp_tiendavirtual.beans.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;

public class GlobalCarritoActivity extends AppCompatActivity {
    TextView usu,boleta;
    Button btncompra,btnboleta;
    ListView lisw;
    List<Compra> lista;
    List<Usuario> usuarios;
    ClaseGlobal cgob;
    List<String> liscmp;
    String usuarioConectado;
    List<Boleta> boletaList;
    String url="https://arizajose.000webhostapp.com/tControla.php";
    JsonObjectRequest jsonObjectRequestgenerarBoleta, jsonObjectRequestgenerarCompra;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_carrito);
        usu = findViewById(R.id.txtusuario);
        boleta = findViewById(R.id.txtboleta);
        btncompra=findViewById(R.id.butncompra);
        btnboleta=findViewById(R.id.butnboleta);
        btncompra.setEnabled(false);
        btnboleta.setEnabled(true);
        cgob=new ClaseGlobal();
        lista=cgob.getLista();
        lisw=findViewById(R.id.listacompras);
        carga();
        ArrayAdapter<String> ad= new  ArrayAdapter<String>(getApplication(),
                android.R.layout.simple_list_item_1,liscmp);
        lisw.setAdapter(ad);
        usuarioConectado();
    }

    public void usuarioConectado(){
        usuarios= cgob.getUsuario();
        for (Usuario u: usuarios) {
            usuarioConectado=u.getId();
        }
        usu.setText("CLIENTE: "+usuarioConectado);
    }

    public void carga(){
        liscmp=new ArrayList<>();
        for(Compra cp:lista){
            String cad=cp.getId()
                    +"--"+cp.getNombre()
                    +"-- "+cp.getPrecio()
                    +"-- "+cp.getCantidad()
                    +"--"+cp.total();
            liscmp.add(cad);
        }
    }

    public Double totalCompra(){
        Double total=0.0;
        for (Compra c:lista) {
            total+=c.total();
        }
        return  total;
    }

    public void generarBoleta(View view){
        String codusu=usuarioConectado;
        Double total = totalCompra();
        boletaList = new ArrayList<>();
        String urlboleta=url+"?tag=adicion&cod="+codusu+"&tot="+total;
        jsonObjectRequestgenerarBoleta = new JsonObjectRequest(Request.Method.GET, urlboleta, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            jsonArray = response.getJSONArray("dato");
                            Log.w("datos",jsonArray.toString());
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject fila = (JSONObject) jsonArray.get(i);
                                Boleta b = new Boleta();
                                b.setCod(fila.getString("codb"));
                                boletaList.add(b);
                            }
                            //agregar
                            String bol=null;
                            for (Boleta b: boletaList) {
                                bol=b.getCod();
                            }
                            boleta.setText(bol);
                            Toast.makeText(getApplication(),"Boleta generada: "+boleta.getText(),Toast.LENGTH_SHORT).show();
                            btnboleta.setEnabled(false);
                            btncompra.setEnabled(true);
                        } catch (JSONException ex) {
                            Log.w("JSONException ",ex.getMessage());
                            Toast.makeText(getApplication(),ex.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w("Error response ",error);
                        Toast.makeText(getApplication(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue cola = Volley.newRequestQueue(this);
        cola.add(jsonObjectRequestgenerarBoleta);
        Log.w("usuario",codusu);
        Log.w("total", String.valueOf(total));
    }

    public void generarCompra(View view){
        //String fac = boleta.getText().toString();
        for (Compra c:cgob.getLista()) {
            String urldetalle=url+"?tag=deta&fac="+boleta.getText().toString()+"&cod="+c.getId()+"&can="+c.getCantidad();
            jsonObjectRequestgenerarCompra = new JsonObjectRequest(Request.Method.GET, urldetalle,
                    null, null,null);
            RequestQueue cola = Volley.newRequestQueue(this);
            cola.add(jsonObjectRequestgenerarCompra);
        }
        //Eliminando la lista
        cgob.deleteLista();
        btncompra.setEnabled(false);
        btnboleta.setEnabled(true);
        //lisw.setAdapter(null);
        Intent intent = new Intent(GlobalCarritoActivity.this, MenuActivity.class);
        startActivity(intent);
        Toast.makeText(getApplication(),"Compra registrada",Toast.LENGTH_SHORT).show();
    }

    public void irCategoria(View view){
        Intent it=new Intent(this,CategoriaActivity.class);
        startActivity(it);
    }
}


