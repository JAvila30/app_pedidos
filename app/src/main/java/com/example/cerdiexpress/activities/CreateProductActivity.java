package com.example.cerdiexpress.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cerdiexpress.R;
import com.example.cerdiexpress.db.repository.ProductRepository;

public class CreateProductActivity extends AppCompatActivity {

    EditText txtNombre;
    Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        txtNombre = findViewById(R.id.txt_name);
        btnGuardar = findViewById(R.id.btnCreate);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ProductRepository productRepository =
                            new ProductRepository(CreateProductActivity.this);

                    long result = productRepository.insertarProducto(txtNombre.getText().toString());

                    if (result > 0){
                        Toast.makeText(CreateProductActivity.this,
                                "Producto registrado. 😎", Toast.LENGTH_SHORT).show();
                        cleanFields();
                    }
                    else {
                        Toast.makeText(CreateProductActivity.this,
                                "No se pudo registrar el producto. 🥲", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }


        });
    }

    private void cleanFields(){
        txtNombre.setText("");
    }

}