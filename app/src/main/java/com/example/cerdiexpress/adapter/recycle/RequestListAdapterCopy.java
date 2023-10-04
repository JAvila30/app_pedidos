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

public class RequestListAdapterCopy extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    ArrayList<Request> requestArrayList;

    public RequestListAdapterCopy(ArrayList<Request> requestArrayList) {
        this.requestArrayList = requestArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_header_pedidos, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_pedidos, parent, false);
            return new RequestViewHolder(view);
        }
    }

//    @Override
//    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
//        holder.viewNombre.setText(requestArrayList.get(position).getNombre());
//        holder.viewId.setText(Integer.toString(requestArrayList.get(position).getId()) );
//        holder.viewContacto.setText(requestArrayList.get(position).getContacto());
//        holder.viewCantidad.setText(Integer.toString(requestArrayList.get(position).getCantidad()));
//        holder.viewOrdenante.setText(requestArrayList.get(position).getOrdenante());
//        holder.viewProducto.setText(requestArrayList.get(position).getProducto());
//
//    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder requestViewHolder = (HeaderViewHolder) holder;
            requestViewHolder.viewId.setText("ID");
            requestViewHolder.viewCantidad.setText("CANTIDAD");
            requestViewHolder.viewNombre.setText("NOMBRE");
            requestViewHolder.viewContacto.setText("CONTACTO");
            requestViewHolder.viewOrdenante.setText("ORDENANTE");
            requestViewHolder.viewProducto.setText("PRODUCTO");

        } else if (holder instanceof RequestViewHolder) {
            // Configura la vista de datos aquí
            int dataIndex = position - 1; // Resta 1 para compensar el encabezado
            Request request = requestArrayList.get(dataIndex);
            RequestViewHolder requestViewHolder = (RequestViewHolder) holder;

            requestViewHolder.viewNombre.setText(request.getNombre());
            requestViewHolder.viewId.setText(Integer.toString(request.getId()));
            requestViewHolder.viewContacto.setText(request.getContacto());
            requestViewHolder.viewCantidad.setText(Integer.toString(request.getCantidad()));
            requestViewHolder.viewOrdenante.setText(request.getOrdenante());
            requestViewHolder.viewProducto.setText(request.getProducto());
        }
    }
    @Override
    public int getItemCount() {
        return requestArrayList.size() + 1;
    }

//    public class RequestViewHolder extends RecyclerView.ViewHolder {
//
//        TextView viewId, viewNombre, viewCantidad, viewProducto, viewContacto, viewOrdenante;
//
//        public RequestViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            viewId = itemView.findViewById(R.id.txtPedidoId);
//            viewNombre = itemView.findViewById(R.id.txtNombreItem);
//            viewCantidad = itemView.findViewById(R.id.txtCantidadItem);
//            viewProducto = itemView.findViewById(R.id.txtProductoItem);
//            viewContacto = itemView.findViewById(R.id.txtContactoItem);
//            viewOrdenante = itemView.findViewById(R.id.txtOrdenanteItem);
//        }
//    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView viewId, viewNombre, viewCantidad, viewProducto, viewContacto, viewOrdenante;
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            viewId = itemView.findViewById(R.id.txtHeaderPedidoId);
            viewNombre = itemView.findViewById(R.id.txtHeaderNombre);
            viewCantidad = itemView.findViewById(R.id.txtHeaderCantidad);
            viewProducto = itemView.findViewById(R.id.txtHeaderProducto);
            viewContacto = itemView.findViewById(R.id.txtHeaderContacto);
            viewOrdenante = itemView.findViewById(R.id.txtHeaderOrdenante);
        }
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

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HEADER : VIEW_TYPE_ITEM;
    }
}
