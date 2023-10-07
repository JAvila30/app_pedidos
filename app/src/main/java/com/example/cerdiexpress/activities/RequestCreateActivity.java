package com.example.cerdiexpress.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cerdiexpress.R;
import com.example.cerdiexpress.adapter.spinner.ProductArrayAdapter;
import com.example.cerdiexpress.db.entities.Product;
import com.example.cerdiexpress.db.repository.ProductRepository;
import com.example.cerdiexpress.db.repository.RequestRepository;
import com.example.cerdiexpress.enums.HeaderRequestEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class RequestCreateActivity extends AppCompatActivity {

    EditText txtNombre;
    EditText txtCantidad;
    EditText txtContacto;
    EditText txtOrdenante;
    Spinner spinnerProducts;
    Button btnGuardar;
    Button btnAdd;
    TableLayout tbRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_create);

        txtNombre = findViewById(R.id.txtNombre);
        txtContacto = findViewById(R.id.txtContacto);
        txtOrdenante = findViewById(R.id.txtOrdenante);
        txtCantidad = findViewById(R.id.txtCantidad);
        spinnerProducts = findViewById(R.id.spinnerProduct);
        btnAdd = findViewById(R.id.btnAddRequest);
        btnAdd.setOnClickListener(btnAddRequestOnclickListener);
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
                            ((Product) spinnerProducts.getSelectedItem()).getName(),
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
        txtOrdenante.setText("");
        txtContacto.setText("");
        txtCantidad.setText(0);
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


    View.OnClickListener btnAddRequestOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            tbRequest = findViewById(R.id.tbRequests);

            txtNombre = findViewById(R.id.txtNombre);
            txtContacto = findViewById(R.id.txtContacto);
            txtOrdenante = findViewById(R.id.txtOrdenante);
            txtCantidad = findViewById(R.id.txtCantidad);
            spinnerProducts = findViewById(R.id.spinnerProduct);

            TextView tvHNombre = new TextView(RequestCreateActivity.this),
                    tvHContacto = new TextView(RequestCreateActivity.this),
                    tvHOrdenante = new TextView(RequestCreateActivity.this),
                    tvHCantiad = new TextView(RequestCreateActivity.this),
                    tvHProducto = new TextView(RequestCreateActivity.this);

            int rowCount = tbRequest.getChildCount();

            if (rowCount == 0) {

                tvHNombre.setText("Item");
                tvHContacto.setText(HeaderRequestEnum.Contacto.getField());
                tvHOrdenante.setText(HeaderRequestEnum.Ordenante.getField());
                tvHCantiad.setText(HeaderRequestEnum.Cantidad.getField());
                tvHProducto.setText(HeaderRequestEnum.Producto.getField());

                tvHNombre.setPadding(0,0,30,0);
                tvHProducto.setPadding(0,0,30,0);
                tvHCantiad.setPadding(0,0,30,0);

                //Create table headers
                TableRow tbHeader = new TableRow(RequestCreateActivity.this);

                tbHeader.addView(tvHNombre);
                tbHeader.addView(tvHProducto);
                tbHeader.addView(tvHCantiad);

                tbRequest.addView(tbHeader);
            }else{
                TextView indice = new TextView(RequestCreateActivity.this);


                tvHCantiad.setText(txtCantidad.getText());
                tvHProducto.setText(HeaderRequestEnum.Producto.getField());

                TableRow tbRow = new TableRow(RequestCreateActivity.this);
                Button btnDelete = new Button(RequestCreateActivity.this);

                btnDelete.setId(R.id.btn_delete_request);
                btnDelete.setText(R.string.btn_delete);
                btnDelete.setTag(rowCount);

                tvHProducto.setText(((Product) spinnerProducts.getSelectedItem()).getName());
                indice.setText(Integer.toString(rowCount) );

                indice.setPadding(0,0,30,0);
                tvHCantiad.setPadding(0,0,30,0);
                tvHProducto.setPadding(0,0,30,0);

                List<Integer> rowToRemove = new ArrayList<>();
                AtomicBoolean flag = new AtomicBoolean();

                for (int index = 1; index < rowCount; index++){
                    TableRow row = (TableRow) tbRequest.getChildAt(index);

                    int columnCount = row.getChildCount();

                    for (int j = 0; j < columnCount; j++) {

                        TextView idItem = (TextView) row.getChildAt(0);
                        TextView cantidadCell = (TextView) row.getChildAt(2);
                        TextView nombreCell = (TextView) row.getChildAt(1);

                        CharSequence cantidad = cantidadCell.getText();
                        CharSequence producto = nombreCell.getText();

                        if (producto.equals(tvHProducto.getText())) {
                            int summarizedQuantity = Integer.parseInt(cantidad.toString())
                                    + Integer.parseInt(txtCantidad.getText().toString()) ;
                            tvHCantiad.setText(Integer.toString(summarizedQuantity));
                            flag.set(true);
                        }
                    }

                    if (flag.get()){rowToRemove.add(index);}
                }
                if (rowToRemove.size() > 0){rowToRemove.forEach(e -> tbRequest.removeViewAt(e));}

                tbRow.addView(indice);
                tbRow.addView(tvHProducto);
                tbRow.addView(tvHCantiad);
                tbRow.addView(btnDelete);

                tbRequest.addView(tbRow);

            }

        }
    };

}