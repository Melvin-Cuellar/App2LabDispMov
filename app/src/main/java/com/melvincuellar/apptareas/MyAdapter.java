package com.melvincuellar.apptareas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Tareas> list;
    private Context context;

    public MyAdapter(Context context, List<Tareas> list) {
        this.context = context;
        this.list = list;
    }

    public  void removeItem(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView tvTitulo, tvDescripcion, tvFecha, tvHora;

        public  MyViewHolder(View v) {
            super(v);
            tvTitulo = v.findViewById(R.id.tvTitulo);
            tvDescripcion = v.findViewById(R.id.tvDescripcion);
            tvFecha = v.findViewById(R.id.tvFecha);
            tvHora = v.findViewById(R.id.tvHora);
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Tareas tarea = list.get(position);

            // Crear el diálogo para Modificar
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Modificar Tarea");
            builder.setMessage("¿Quieres modificar esta tarea?");
            builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Enviar a AddTaskActivity para modificar la tarea
                    Intent intent = new Intent(context, AddTaskActivity.class);
                    intent.putExtra("position", position); // Enviar la posición de la tarea
                    intent.putExtra("titulo", tarea.getTitulo());
                    intent.putExtra("descripcion", tarea.getDescripcion());
                    intent.putExtra("fecha", tarea.getFecha());
                    intent.putExtra("hora", tarea.getHora());
                    context.startActivity(intent);
                }
            });
            builder.setNegativeButton("No", null);
            builder.show();
        }

        @Override
        public boolean onLongClick(View view) {
            int position = getAdapterPosition();
            Tareas tarea = list.get(position);

            // Crear el diálogo para Eliminar
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Eliminar Tarea");
            builder.setMessage("¿Seguro que quieres eliminar esta tarea?");
            builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    removeItem(position); // Eliminar la tarea
                    Toast.makeText(view.getContext(), "Tarea eliminada", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("No", null);
            builder.show();

            return true;
        }
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.tvTitulo.setText(list.get(position).getTitulo().toString());
        holder.tvDescripcion.setText(list.get(position).getDescripcion().toString());
        holder.tvFecha.setText(list.get(position).getFecha().toString());
        holder.tvHora.setText(list.get(position).getHora().toString());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}