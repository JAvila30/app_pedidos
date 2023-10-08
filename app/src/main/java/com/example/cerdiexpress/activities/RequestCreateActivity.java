package com.example.cerdiexpress.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cerdiexpress.R;
import com.example.cerdiexpress.adapter.spinner.ProductArrayAdapter;
import com.example.cerdiexpress.db.entities.Product;
import com.example.cerdiexpress.db.entities.Request;
import com.example.cerdiexpress.db.repository.ProductRepository;
import com.example.cerdiexpress.db.repository.RequestRepository;
import com.example.cerdiexpress.enums.HeaderRequestEnum;
import com.example.cerdiexpress.enums.StatusRequestEnum;
import com.example.cerdiexpress.utils.IGenerateID;
import com.example.cerdiexpress.utils.IValidateFields;
import com.example.cerdiexpress.utils.impl.GenerateIDImpl;
import com.example.cerdiexpress.utils.impl.ValidateFieldsImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class RequestCreateActivity extends AppCompatActivity {

    EditText txtNombre;
    EditText txtCantidad;
    EditText txtContacto;
    EditText txtOrdenante;
    Spinner spinnerProducts;
    Button btnGuardar;
    Button btnAdd;
    TableLayout tbRequest;
    private IValidateFields iValidateFields;

    private IGenerateID iGenerateID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_create);

        iValidateFields = new ValidateFieldsImpl();

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

                    List<TextView> itemsToSave = new ArrayList<>();

                    itemsToSave.add(txtNombre);
                    itemsToSave.add(txtCantidad);

                    if (iValidateFields.validateTextView(itemsToSave, RequestCreateActivity.this)) {

                        iGenerateID = new GenerateIDImpl();

                        final String orderId = iGenerateID.generateId();

                        HashMap<HeaderRequestEnum, TextView> itemsToSaveMap = new HashMap<>();

                        TextView selectedItem = new TextView(RequestCreateActivity.this);
                        selectedItem.setText(((Product) spinnerProducts.getSelectedItem()).getName());


                        itemsToSaveMap.put(HeaderRequestEnum.Nombre, txtNombre);
                        itemsToSaveMap.put(HeaderRequestEnum.Cantidad, txtCantidad);
                        itemsToSaveMap.put(HeaderRequestEnum.Ordenante, txtOrdenante);
                        itemsToSaveMap.put(HeaderRequestEnum.Contacto, txtContacto);
                        itemsToSaveMap.put(HeaderRequestEnum.Producto, selectedItem);

                        List<Request> listToSave = processPayload(itemsToSaveMap, orderId);

                        long result = productRepository.insertarPedido(listToSave);

                        if (result > 0) {
                            cleanTableRequestList();
                            cleanFields();
                            Toast.makeText(RequestCreateActivity.this,
                                    "Productos registrados.😎", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RequestCreateActivity.this,
                                    "No se pudo registrar los productos.🥲", Toast.LENGTH_SHORT).show();
                        }
                    }


                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

    }

    private void cleanFields() {
        txtNombre.setText("");
        txtOrdenante.setText("");
        txtContacto.setText("");
        txtCantidad.setText(null);
    }

    private void fillSpinner(Spinner spinner) {
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
            List<TextView> textViewList = new ArrayList<>();

            tbRequest = findViewById(R.id.tbRequests);

            txtNombre = findViewById(R.id.txtNombre);
            txtContacto = findViewById(R.id.txtContacto);
            txtOrdenante = findViewById(R.id.txtOrdenante);
            txtCantidad = findViewById(R.id.txtCantidad);
            spinnerProducts = findViewById(R.id.spinnerProduct);

            textViewList.add(txtCantidad);

            if (iValidateFields.validateTextView(textViewList, RequestCreateActivity.this)) {

                TextView tvHIndice = new TextView(RequestCreateActivity.this),
                        tvHCantiad = new TextView(RequestCreateActivity.this),
                        tvHProducto = new TextView(RequestCreateActivity.this);

                int rowCount = tbRequest.getChildCount();

                if (rowCount == 0) {

                    tvHIndice.setText("Item");
                    tvHCantiad.setText(HeaderRequestEnum.Cantidad.getField());
                    tvHProducto.setText(HeaderRequestEnum.Producto.getField());

                    tvHIndice.setPadding(0, 0, 30, 0);
                    tvHProducto.setPadding(0, 0, 30, 0);
                    tvHCantiad.setPadding(0, 0, 30, 0);

                    //Create table headers
                    TableRow tbHeader = new TableRow(RequestCreateActivity.this);

                    tbHeader.addView(tvHIndice);
                    tbHeader.addView(tvHProducto);
                    tbHeader.addView(tvHCantiad);
                    tbRequest.addView(tbHeader);

//               se a;ade el primer registro.
                    TableRow tbRow = new TableRow(RequestCreateActivity.this);
                    Button btnDelete = new Button(RequestCreateActivity.this);
                    TextView indice = new TextView(RequestCreateActivity.this),
                            tvCantidad = new TextView(RequestCreateActivity.this),
                            tvProducto = new TextView(RequestCreateActivity.this);

                    int indexItem = rowCount + 1;
                    indice.setText(Integer.toString(indexItem));
                    tvCantidad.setText(txtCantidad.getText());
                    tvProducto.setText(((Product) spinnerProducts.getSelectedItem()).getName());
                    btnDelete.setId(R.id.btn_delete_request);
                    btnDelete.setText(R.string.btn_delete);
                    btnDelete.setOnClickListener(btnDeleteItemRequested);
                    btnDelete.setTag(indexItem);

                    indice.setPadding(0, 0, 30, 0);
                    tvCantidad.setPadding(0, 0, 30, 0);
                    tvProducto.setPadding(0, 0, 30, 0);

                    tbRow.addView(indice);
                    tbRow.addView(tvProducto);
                    tbRow.addView(tvCantidad);
                    tbRow.addView(btnDelete);

                    tbRequest.addView(tbRow);

                } else {

                    TextView indice = new TextView(RequestCreateActivity.this);

                    tvHCantiad.setText(txtCantidad.getText());
                    tvHProducto.setText(HeaderRequestEnum.Producto.getField());

                    TableRow tbRow = new TableRow(RequestCreateActivity.this);
                    Button btnDelete = new Button(RequestCreateActivity.this);

                    tvHProducto.setText(((Product) spinnerProducts.getSelectedItem()).getName());
                    indice.setText(Integer.toString(rowCount));

                    indice.setPadding(0, 0, 30, 0);
                    tvHCantiad.setPadding(0, 0, 30, 0);
                    tvHProducto.setPadding(0, 0, 30, 0);

                    AtomicBoolean summarizeItemFlag = new AtomicBoolean();

                    for (int index = 1; index < rowCount; index++) {
                        TableRow row = (TableRow) tbRequest.getChildAt(index);

                        int columnCount = row.getChildCount();

                        for (int j = 0; j < columnCount; j++) {

                            TextView idItem = (TextView) row.getChildAt(0);
                            TextView cantidadCell = (TextView) row.getChildAt(2);
                            TextView nombreCell = (TextView) row.getChildAt(1);

                            CharSequence cantidad = cantidadCell.getText();
                            CharSequence producto = nombreCell.getText();

                            if (producto.equals(tvHProducto.getText()) && !summarizeItemFlag.get()) {
                                int summarizedQuantity = Integer.parseInt(cantidad.toString())
                                        + Integer.parseInt(txtCantidad.getText().toString());

                                cantidadCell.setText(Integer.toString(summarizedQuantity));

                                summarizeItemFlag.set(true);
                            }
                        }

                        btnDelete.setId(R.id.btn_delete_request);
                        btnDelete.setText(R.string.btn_delete);
                        if (!summarizeItemFlag.get()) {
                            btnDelete.setOnClickListener(btnDeleteItemRequested);
                            btnDelete.setTag(rowCount);
                        }
                    }

                    if (!summarizeItemFlag.get()) {
                        tbRow.addView(indice);
                        tbRow.addView(tvHProducto);
                        tbRow.addView(tvHCantiad);
                        tbRow.addView(btnDelete);
                        tbRequest.addView(tbRow);
                    }
                }
            }


        }
    };
    View.OnClickListener btnDeleteItemRequested = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            removeItemRequested(view);
        }

    };

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void removeItemRequested(View view) {
        TableLayout tableLayout = findViewById(R.id.tbRequests);
        final int itemId = (int) view.getTag();

        for (int index = 1; index < tableLayout.getChildCount(); index++) {
            TableRow row = (TableRow) tbRequest.getChildAt(index);
            TextView tableItemId = ((TextView) row.getChildAt(0));
            TextView tableItemProduct = ((TextView) row.getChildAt(1));
            int itemValue = Integer.parseInt(tableItemId.getText().toString());

            if (itemId == itemValue) {
                tableLayout.removeViewAt(index);
                Toast.makeText(RequestCreateActivity.this, "Producto eliminado: "
                        + tableItemProduct.getText(), Toast.LENGTH_SHORT).show();

                // Se verifica que solo quede los headers y si es asi se quita la vista.
                if (tableLayout.getChildCount() == 1) {
                    tbRequest.removeAllViews();
                }

                break;
            }
        }
    }

    private void cleanTableRequestList() {
        TableLayout tlRequestedList = findViewById(R.id.tbRequests);
        tlRequestedList.removeAllViews();
        for (int i = 0; i < tlRequestedList.getChildCount(); i++) {
            TableRow row = (TableRow) tlRequestedList.getChildAt(i);
            row.removeAllViews();
        }
    }

    private List<Request> processPayload(HashMap<HeaderRequestEnum, TextView> itemsToSave, String orderId) {

        tbRequest = findViewById(R.id.tbRequests);

        int rowCounts = tbRequest.getChildCount();

        List<Request> listToSave = new ArrayList<>();

        if (rowCounts > 0) {

            for (int index = 1; index < rowCounts; index++) {

                Request request = new Request();

                TableRow row = (TableRow) tbRequest.getChildAt(index);

                TextView productCell = (TextView) row.getChildAt(1);
                TextView quantityCell = (TextView) row.getChildAt(2);

                String productValue = productCell.getText().toString();
                int quantityValue = Integer.parseInt(quantityCell.getText().toString());

                request.setOrdenante(itemsToSave.get(HeaderRequestEnum.Ordenante).getText().toString());
                request.setNombre(itemsToSave.get(HeaderRequestEnum.Nombre).getText().toString());
                request.setContacto(itemsToSave.get(HeaderRequestEnum.Contacto).getText().toString());
                request.setProducto(productValue);
                request.setCantidad(quantityValue);
                request.setOrderId(orderId);
                request.setStatus(StatusRequestEnum.PENDING.getKey());

                listToSave.add(request);
            }
        } else {
            Request request = new Request();

            int quantityValue = Integer.parseInt(itemsToSave.get(HeaderRequestEnum.Cantidad).getText().toString());
            request.setOrdenante(itemsToSave.get(HeaderRequestEnum.Ordenante).getText().toString());
            request.setNombre(itemsToSave.get(HeaderRequestEnum.Nombre).getText().toString());
            request.setContacto(itemsToSave.get(HeaderRequestEnum.Contacto).getText().toString());
            request.setProducto(itemsToSave.get(HeaderRequestEnum.Producto).getText().toString());
            request.setCantidad(quantityValue);
            request.setOrderId(orderId);
            request.setStatus(StatusRequestEnum.PENDING.getKey());

            listToSave.add(request);
        }

        return listToSave;
    }
}