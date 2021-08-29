package com.example.myapp_tiendavirtual;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {
    ClaseGlobal claseGlobal;
    EditText usuario,password;
    String url="https://arizajose.000webhostapp.com/tControla.php";
    JsonObjectRequest jsonObjectRequest;
    JSONArray jsonArray;
    List<Usuario> userlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usuario=findViewById(R.id.edtusuario);
        password=findViewById(R.id.edtpassword);
        claseGlobal = new ClaseGlobal();
    }

    public void login(View view){
        String us = usuario.getText().toString();
        String ps = password.getText().toString();

        if(!(us.equals("")) && !(ps.equals(""))){
        String enlace=url+"?tag=login"+"&codu="+us+"&pass="+ps;
        userlist = new ArrayList<>();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, enlace, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            jsonArray = response.getJSONArray("dato");
                            Log.w("datos",jsonArray.toString());
                            Log.w("tamaño", String.valueOf(jsonArray.length()));
                            if(jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject fila = (JSONObject) jsonArray.get(i);
                                    Usuario u = new Usuario();
                                    u.setId(fila.getString("codu"));
                                    u.setPassword(fila.getString("pass"));
                                    u.setApellido(fila.getString("ape"));
                                    u.setNombre(fila.getString("nom"));
                                    userlist.add(u);
                                }
                                claseGlobal.setUsuario(userlist);
                                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                                startActivity(intent);
                                String rpta = null;
                                for (Usuario us: claseGlobal.getUsuario()) {
                                    rpta="["+ us.getId()+","+us.getApellido()+","+us.getNombre()+"]";
                                }
                                Log.w("usuario conectado",rpta);
                                Toast.makeText(getApplication(), "Iniciando sesión", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplication(), "Usuario y/o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                            }
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
        RequestQueue r2 = Volley.newRequestQueue(this);
        r2.add(jsonObjectRequest);
    } else {
            Toast.makeText(getApplication(),"Ingrese su usuario y contraseña",Toast.LENGTH_SHORT).show();
        }
    }
}



