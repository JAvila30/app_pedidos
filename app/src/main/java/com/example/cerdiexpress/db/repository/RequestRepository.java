package com.example.cerdiexpress.db.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cerdiexpress.db.DbHelper;
import com.example.cerdiexpress.db.entities.Request;

import java.util.ArrayList;

public class RequestRepository extends DbHelper {
    Context context;

    public RequestRepository(Context context) {
        super(context);
        this.context = context;
    }

    public long insertarPedido(String nombre,
                               int cantidad,
                               String producto,
                               String ordenante,
                               String contacto ) {

        long resultId = -1;

        try {
            DbHelper dbHelper = new DbHelper(context);

            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put("nombre", nombre);
            contentValues.put("cantidad",cantidad);
            contentValues.put("producto",producto);
            contentValues.put("ordenante",ordenante);
            contentValues.put("contacto",contacto);
            resultId = db.insert("pedido", null, contentValues);

        } catch (Exception e) {
            System.out.println(e);
        }

        return resultId;
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
                request.setNombre(requestCursor.getString(1));
                request.setCantidad(requestCursor.getInt(2));
                request.setContacto(requestCursor.getString(3));
                request.setOrdenante(requestCursor.getString(4));
                request.setProducto(requestCursor.getString(5));

                products.add(request);
            }while (requestCursor.moveToNext());
        }

        requestCursor.close();
        return products;
    }

}
