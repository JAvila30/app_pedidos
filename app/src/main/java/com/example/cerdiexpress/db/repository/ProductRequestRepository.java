package com.example.cerdiexpress.db.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cerdiexpress.db.DbHelper;
import com.example.cerdiexpress.db.entities.ProductRequest;

import java.util.ArrayList;

public class ProductRequestRepository extends DbHelper {
    Context context;
    public ProductRequestRepository(Context context) {
        super(context);
        this.context = context;
    }

    public ArrayList<ProductRequest> getProductRequests(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<ProductRequest> products = new ArrayList<>();
        ProductRequest request = null;
        Cursor requestCursor = null;

        requestCursor = db.rawQuery("SELECT * FROM productos_pedido;", null);

        if(requestCursor.moveToFirst()){

            do{
                request = new ProductRequest();
                request.setId(requestCursor.getInt(0));
                request.setOrderId(requestCursor.getString(1));
                request.setCantidad(requestCursor.getInt(2));
                request.setProducto(requestCursor.getString(3));

                products.add(request);
            }while (requestCursor.moveToNext());
        }

        requestCursor.close();
        return products;
    }

    public void deleteProductsRequest(String id){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("productos_pedido","order_id=?",new String[]{String.valueOf(id)});

        db.close();
    }
}
