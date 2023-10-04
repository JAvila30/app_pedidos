package com.example.cerdiexpress.db.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.cerdiexpress.db.DbHelper;

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



}
