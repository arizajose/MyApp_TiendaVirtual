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
import com.example.myapp_tiendavirtual.R;
import com.example.myapp_tiendavirtual.beans.Categoria;

import java.util.List;

public class AdaptadorCategoria extends RecyclerView.Adapter<AdaptadorCategoria.MyHolder> {
    List<Categoria> lista;
    Context contexto;
    String url ="https://arizajose.000webhostapp.com/images/";

    public AdaptadorCategoria(List<Categoria> lista, Context contexto) {
        this.lista = lista;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public AdaptadorCategoria.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.vistacategoria,
                parent,false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorCategoria.MyHolder holder, int position) {
        final Categoria cat = lista.get(position);
        holder.categoria.setText(cat.getId());
        holder.descripcion.setText(cat.getNombre());
        Glide.with(contexto)
                .load(url+cat.getImagen())
                .asBitmap()
                .error(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.imgcat);
        holder.miLayaout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contexto, ArticuloActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("codcategoria",cat.getId());
                contexto.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView imgcat;
        TextView categoria,descripcion;
        ConstraintLayout miLayaout;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imgcat = itemView.findViewById(R.id.imgcategoria);
            categoria =itemView.findViewById(R.id.txtcategcod);
            descripcion = itemView.findViewById(R.id.txtcategdescrip);
            miLayaout = itemView.findViewById(R.id.catlayaout);
        }
    }
}


