package com.example.cerdiexpress.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, dbContract.DATABASE_NAME, null, dbContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Definimos las estructuras de la base de datos.

        String createTableProductoQuery = "CREATE TABLE " + dbContract.TABLA_PRODUCTO + " (" +
                dbContract.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                dbContract.COLUMNA_NOMBRE + " TEXT);";

        String createTablePedidoQuery = "CREATE TABLE " + dbContract.TABLA_PEDIDO + " (" +
                dbContract.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                dbContract.COLUMNA_NOMBRE + " TEXT," +
                dbContract.COLUMNA_CANTIDAD + " INTEGER," +
                dbContract.COLUMNA_CONTACTO + " TEXT," +
                dbContract.COLUMNA_ORDENANTE + " TEXT," +
                dbContract.COLUMNA_PRODUCTO + " TEXT);";

        db.execSQL(createTablePedidoQuery);
        db.execSQL(createTableProductoQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Maneja actualizaciones de la base de datos si es necesario
    }
}
