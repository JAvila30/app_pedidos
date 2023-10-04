package com.example.cerdiexpress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.cerdiexpress.activities.CreateProductActivity;
import com.example.cerdiexpress.activities.RequestCreateActivity;
import com.example.cerdiexpress.db.DbHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            DbHelper dbHelper = new DbHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
        }catch (Exception e){
            System.out.println(e);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void onClickButton(View view){
        CharSequence text = "Mi primer toast";


        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){

        if (menuItem.getItemId() == R.id.menuProductoId) {
            newProduct();
            return true;
        } else if (menuItem.getItemId() == R.id.menuPedidoId) {
            newRequest();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void newProduct(){
        Intent intent = new Intent(this, CreateProductActivity.class);

        startActivity(intent);
    }

    private void newRequest(){
        Intent intent = new Intent(this, RequestCreateActivity.class);

        startActivity(intent);
    }
}