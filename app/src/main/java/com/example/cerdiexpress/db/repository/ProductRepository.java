package com.example.cerdiexpress.db.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cerdiexpress.db.DbHelper;
import com.example.cerdiexpress.db.entities.Product;

import java.util.ArrayList;

public class ProductRepository extends DbHelper {

    Context context;

    public ProductRepository(Context context) {
        super(context);
        this.context = context;
    }

    public long insertarProducto(String nombre) {

        long resultId = -1;

        try {
            DbHelper dbHelper = new DbHelper(context);

            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put("nombre", nombre);
            resultId = db.insert("producto", null, contentValues);

        } catch (Exception e) {
            System.out.println(e);
        }

        return resultId;
    }

    public ArrayList<Product> getProducts(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Product> products = new ArrayList<>();
        Product product = null;
        Cursor productCursor = null;


       productCursor = db.rawQuery("SELECT * FROM producto;", null);

       if(productCursor.moveToFirst()){
           do{
               product = new Product();
               product.setId(productCursor.getInt(0));
               product.setName(productCursor.getString(1));

               products.add(product);
           }while (productCursor.moveToNext());


       }

        productCursor.close();
       return products;
    }
}
