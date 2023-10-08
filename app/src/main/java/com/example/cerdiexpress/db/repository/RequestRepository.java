package com.example.cerdiexpress.db.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cerdiexpress.db.DbHelper;
import com.example.cerdiexpress.db.entities.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class RequestRepository extends DbHelper {
    Context context;

    public RequestRepository(Context context) {
        super(context);
        this.context = context;
    }

    public long insertarPedido(List<Request> itemsToSave ) {

        AtomicLong resultId = new AtomicLong(-1);

        //Devolver estados.
        try {
            DbHelper dbHelper = new DbHelper(context);

            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues requestValues = new ContentValues();
            ContentValues productRequestValues = new ContentValues();
            AtomicInteger productsQuantity = new AtomicInteger();
            itemsToSave.forEach(item -> {
                productRequestValues.put("order_id", item.getOrderId());
                productRequestValues.put("cantidad",item.getCantidad());
                productRequestValues.put("producto",item.getProducto());

                productsQuantity.addAndGet(item.getCantidad());
                resultId.set(db.insert("productos_pedido", null, productRequestValues));
            });
            Request item = itemsToSave.get(0);
            requestValues.put("order_id", item.getOrderId());
            requestValues.put("cli_nombre", item.getNombre());
            requestValues.put("ordenante",item.getOrdenante());
            requestValues.put("contacto", item.getContacto());
            requestValues.put("status",item.getStatus());
            requestValues.put("cantidad", productsQuantity.get());
            resultId.set(db.insert("pedido", null, requestValues));

        } catch (Exception e) {
            System.out.println(e);
        }

        return resultId.get();
    }

    public ArrayList<Request> getRequests(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Request> products = new ArrayList<>();
        Request request = null;
        Cursor requestCursor = null;

        requestCursor = db.rawQuery("SELECT * FROM pedido;", null);

        if(requestCursor.moveToFirst()){

            do{
                request = new Request();
                request.setId(requestCursor.getInt(0));
                request.setOrderId(requestCursor.getString(1));
                request.setStatus(requestCursor.getString(2));
                request.setNombre(requestCursor.getString(3));
                request.setCantidad(requestCursor.getInt(4));
                request.setContacto(requestCursor.getString(5));
                request.setOrdenante(requestCursor.getString(6));

                products.add(request);
            }while (requestCursor.moveToNext());
        }

        requestCursor.close();
        return products;
    }

    public void deleteRequest(String id){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("pedido","order_id=?",new String[]{String.valueOf(id)});

        db.close();
    }

}
