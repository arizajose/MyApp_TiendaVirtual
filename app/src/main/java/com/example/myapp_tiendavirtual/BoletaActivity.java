package com.example.myapp_tiendavirtual;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapp_tiendavirtual.beans.ClaseGlobal;
import com.example.myapp_tiendavirtual.beans.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BoletaActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener,
        ListView.OnItemClickListener {
    TextView txtusu;
    List<String> lisbol;
    List<String> lisdet;
    JsonObjectRequest jobs;
    JSONArray vector;
    List<Usuario> usuarios;
    String usuarioConectado;
    ClaseGlobal cgob;
    Spinner sp1;
    ListView lw1;
    String url="https://arizajose.000webhostapp.com/tControla.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boleta);
        sp1=findViewById(R.id.spinnerboletas);
        lw1=findViewById(R.id.listviewboletas);
        txtusu=findViewById(R.id.txtusuario);
        sp1.setOnItemSelectedListener(this);
        lw1.setOnItemClickListener(this);
        cgob=new ClaseGlobal();
        usuarioConectado();
        llenarBoletas();
    }

    public void llenarBoletas(){
        String codusu=usuarioConectado;
        String enlace=url+"?tag=listboletas&cod="+codusu;
        lisbol=new ArrayList<>();
        jobs=new JsonObjectRequest(Request.Method.GET, enlace, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            vector = response.getJSONArray("dato");
                            Log.w("datos",vector.toString());
                            for(int p=0; p<vector.length();p++){
                                JSONObject fila=(JSONObject)vector.get(p);
                                lisbol.add(fila.getString("codb"));
                            }
                            ArrayAdapter<String> ada1=new ArrayAdapter<String>(getApplication(),
                                    android.R.layout.simple_list_item_1,lisbol);
                            sp1.setAdapter(ada1);
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
        RequestQueue r2 = Volley.newRequestQueue(this);
        r2.add(jobs);
    }

    public void usuarioConectado(){
        usuarios= cgob.getUsuario();
        for (Usuario u: usuarios) {
            usuarioConectado=u.getId();
        }
        txtusu.setText("CLIENTE: "+usuarioConectado);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //para el spinner cuando se elige un item
        String cad=sp1.getSelectedItem().toString();
        cargarDetalle(cad);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void cargarDetalle(String cod){
        String enlace=url+"?tag=listdetalle&cod="+cod;
        lisdet=new ArrayList<>();
        jobs=new JsonObjectRequest(Request.Method.GET, enlace, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            vector = response.getJSONArray("dato");
                            Log.w("datos",vector.toString());
                            for(int p=0; p<vector.length();p++){
                                JSONObject fila=(JSONObject)vector.get(p);
                                lisdet.add(fila.getString("coda")
                                        +"--"+fila.getString("noma")
                                        +"--"+fila.getString("precio")
                                        +"--"+fila.getString("cantidad")
                                        +"--"+fila.getString("subtotal"));
                            }
                            ArrayAdapter<String> ada1=new
                                    ArrayAdapter<String>(getApplication(),
                                    android.R.layout.simple_list_item_1,lisdet);
                            lw1.setAdapter(ada1);
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
        RequestQueue r2 = Volley.newRequestQueue(this);
        r2.add(jobs);
    }

    public void irMenu(View view){
        Intent it=new Intent(this,MenuActivity.class);
        startActivity(it);
    }
}


