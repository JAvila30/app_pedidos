package com.example.cerdiexpress.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cerdiexpress.R;
import com.example.cerdiexpress.adapter.spinner.ProductArrayAdapter;
import com.example.cerdiexpress.db.entities.Product;
import com.example.cerdiexpress.db.repository.ProductRepository;
import com.example.cerdiexpress.db.repository.RequestRepository;

import java.util.ArrayList;

public class RequestCreateActivity extends AppCompatActivity {

    EditText txtNombre;
    EditText txtCantidad;
    EditText txtContacto;
    EditText txtOrdenante;
    Spinner spinnerProducts;
    Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_create);

        txtNombre = findViewById(R.id.txtNombre);
        txtContacto = findViewById(R.id.txtContacto);
        txtOrdenante = findViewById(R.id.txtOrdenante);
        txtCantidad = findViewById(R.id.txtCantidad);
        spinnerProducts = findViewById(R.id.spinnerProduct);
        btnGuardar = findViewById(R.id.btnCreateRequest);

        fillSpinner(spinnerProducts);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    RequestRepository productRepository =
                            new RequestRepository(RequestCreateActivity.this);

                    long result = productRepository.insertarPedido(txtNombre.getText().toString(),
                            Integer.parseInt(txtCantidad.getText().toString()) ,
                            spinnerProducts.getSelectedItem().toString(),
                            txtOrdenante.getText().toString(),
                            txtContacto.getText().toString());

                    if (result > 0){
                        Toast.makeText(RequestCreateActivity.this,
                                "Producto registrado. 😎", Toast.LENGTH_SHORT).show();
                        cleanFields();
                    }
                    else {
                        Toast.makeText(RequestCreateActivity.this,
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

    private void fillSpinner(Spinner spinner){
        ProductRepository productRepository = new ProductRepository
                (RequestCreateActivity.this);

        ArrayList<Product> products = productRepository.getProducts();

        ProductArrayAdapter adapter =
                new ProductArrayAdapter(this, products);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(adapter);
    }
}