package com.example.cerdiexpress.adapter.recycle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cerdiexpress.R;
import com.example.cerdiexpress.db.entities.Request;

import java.util.ArrayList;

public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.RequestViewHolder> {

    ArrayList<Request> requestArrayList;

    public RequestListAdapter(ArrayList<Request> requestArrayList) {
        this.requestArrayList = requestArrayList;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_pedidos, null, false);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        holder.viewNombre.setText(requestArrayList.get(position).getNombre());
        holder.viewId.setText(Integer.toString(requestArrayList.get(position).getId()) );
        holder.viewContacto.setText(requestArrayList.get(position).getContacto());
        holder.viewCantidad.setText(Integer.toString(requestArrayList.get(position).getCantidad()));
        holder.viewOrdenante.setText(requestArrayList.get(position).getOrdenante());
        holder.viewProducto.setText(requestArrayList.get(position).getProducto());

    }

    @Override
    public int getItemCount() {
        return requestArrayList.size();
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder {

        TextView viewId, viewNombre, viewCantidad, viewProducto, viewContacto, viewOrdenante;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);

            viewId = itemView.findViewById(R.id.txtPedidoId);
            viewNombre = itemView.findViewById(R.id.txtNombreItem);
            viewCantidad = itemView.findViewById(R.id.txtCantidadItem);
            viewProducto = itemView.findViewById(R.id.txtProductoItem);
            viewContacto = itemView.findViewById(R.id.txtContactoItem);
            viewOrdenante = itemView.findViewById(R.id.txtOrdenanteItem);
        }
    }
}
