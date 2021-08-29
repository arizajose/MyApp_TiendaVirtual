package com.example.myapp_tiendavirtual.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapp_tiendavirtual.ArticuloActivity;
import com.example.myapp_tiendavirtual.CompraActivity;
import com.example.myapp_tiendavirtual.R;
import com.example.myapp_tiendavirtual.beans.Articulo;
import com.example.myapp_tiendavirtual.beans.Categoria;

import java.util.List;

public class AdaptadorArticulo extends RecyclerView.Adapter<AdaptadorArticulo.MyHolder> {
    List<Articulo> lista;
    Context contexto;
    String url ="https://arizajose.000webhostapp.com/images/";

    public AdaptadorArticulo(List<Articulo> lista, Context contexto) {
        this.lista = lista;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public AdaptadorArticulo.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.vistaarticulo,
                parent,false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorArticulo.MyHolder holder, int position) {
        final Articulo art = lista.get(position);
        holder.producto.setText(art.getId());
        holder.descripcion.setText(art.getNombre());
        holder.precio.setText(""+art.getPrecio());
        Glide.with(contexto)
                .load(url+art.getImagen())
                .asBitmap()
                .error(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.imgart);
        holder.miLayaout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contexto, CompraActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("codproducto",art.getId());
                contexto.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView imgart;
        TextView producto,descripcion,precio;
        ConstraintLayout miLayaout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imgart = itemView.findViewById(R.id.imgproducto);
            producto =itemView.findViewById(R.id.txtprodcod);
            descripcion = itemView.findViewById(R.id.txtproddescrip);
            precio = itemView.findViewById(R.id.txtprodprecio);
            miLayaout = itemView.findViewById(R.id.artlayaout);
        }
    }
}



