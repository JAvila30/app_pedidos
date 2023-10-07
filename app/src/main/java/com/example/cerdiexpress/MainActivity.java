package com.example.cerdiexpress;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.cerdiexpress.activities.CreateProductActivity;
import com.example.cerdiexpress.activities.RequestCreateActivity;
import com.example.cerdiexpress.db.DbHelper;
import com.example.cerdiexpress.db.entities.Product;
import com.example.cerdiexpress.db.entities.Request;
import com.example.cerdiexpress.db.repository.ProductRepository;
import com.example.cerdiexpress.db.repository.RequestRepository;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    ArrayList<Request> requestArrayList;
    ArrayList<Product> products;

    Button btnGenerateXmls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGenerateXmls = findViewById(R.id.btnGenerateRequestXmls);

        btnGenerateXmls.setOnClickListener(generateRequestExcelOnClickListener);

        try {
            DbHelper dbHelper = new DbHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            System.out.println(e);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.menuProductoId) {
            newProduct();
            return true;
        } else if (menuItem.getItemId() == R.id.menuPedidoId) {
            newRequest();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void newProduct() {
        Intent intent = new Intent(this, CreateProductActivity.class);

        startActivity(intent);
    }

    private void newRequest() {
        Intent intent = new Intent(this, RequestCreateActivity.class);

        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        fillTableRequest();
        fillMosProductsTable();
    }

    private void fillTableRequest() {
        RequestRepository requestRepository = new RequestRepository(MainActivity.this);
        TableLayout tb = findViewById(R.id.tMain);

        requestArrayList = requestRepository.getRequests();
        tb.removeAllViews();

        TextView hId = new TextView(this),
                hNombre = new TextView(this),
                hProducto = new TextView(this),
                hCandtidad = new TextView(this),
                hOrdenante = new TextView(this),
                hContacto = new TextView(this);

        /*
         *
         * Add text value
         * */
        hId.setText(R.string.text_id_field);
        hNombre.setText(R.string.text_name_field);
        hCandtidad.setText(R.string.txt_cantidad_field);
        hProducto.setText(R.string.txt_producto_field);
        hOrdenante.setText(R.string.txt_ordenante_field);
        hContacto.setText(R.string.txt_contacto_field);

        /*
         *
         * Add padding
         * */
        hContacto.setPadding(0, 0, 10, 0);
        hOrdenante.setPadding(0, 0, 10, 0);
        hProducto.setPadding(0, 0, 10, 0);
        hCandtidad.setPadding(0, 0, 10, 0);
        hNombre.setPadding(0, 0, 10, 0);
        hId.setPadding(0, 0, 10, 0);


        TableRow hRow = new TableRow(this);

        hRow.addView(hId);
        hRow.addView(hNombre);
        hRow.addView(hCandtidad);
        hRow.addView(hProducto);
        hRow.addView(hContacto);
        hRow.addView(hOrdenante);


        tb.addView(hRow);

        for (Request elemento : requestArrayList) {

            TableRow row = new TableRow(this);

            TextView id = new TextView(this),
                    nombre = new TextView(this),
                    producto = new TextView(this),
                    candtidad = new TextView(this),
                    ordenante = new TextView(this),
                    contacto = new TextView(this);

            /*
             *
             * Add text value
             * */
            id.setText(Integer.toString(elemento.getId()));
            producto.setText(elemento.getProducto());
            ordenante.setText(elemento.getOrdenante());
            nombre.setText(elemento.getNombre());
            contacto.setText(elemento.getContacto());
            candtidad.setText(Integer.toString(elemento.getCantidad()));

            /*
             *
             * Add padding
             * */
            producto.setPadding(0, 0, 10, 0);
            id.setPadding(0, 0, 10, 0);
            nombre.setPadding(0, 0, 10, 0);
            candtidad.setPadding(0, 0, 10, 0);
            ordenante.setPadding(0, 0, 10, 0);
            contacto.setPadding(0, 0, 10, 0);

            Button btnDelete = new Button(this),
                    btnFinish = new Button(this);

            btnDelete.setText(R.string.btn_delete);
            btnDelete.setId(R.id.btn_delete);
            btnDelete.setOnClickListener(buttonClickListener);
            btnDelete.setTag(elemento.getId());

            btnFinish.setText(R.string.btn_finish);
            btnFinish.setId(R.id.btn_finish);
            btnFinish.setOnClickListener(buttonClickListener);
            btnFinish.setTag(elemento.getId());

            row.addView(id);
            row.addView(nombre);
            row.addView(candtidad);
            row.addView(producto);
            row.addView(contacto);
            row.addView(ordenante);
            row.addView(btnDelete);
            row.addView(btnFinish);


            row.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    1));
            tb.addView(row);
        }

        if (!requestArrayList.isEmpty()) {
            Toast.makeText(this, "Cantidad de pedidos: " +
                    requestArrayList.size(), Toast.LENGTH_SHORT).show();
        }

    }

    View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            RequestRepository db = new RequestRepository(MainActivity.this);
            int requestId = (int) view.getTag();

            if (R.id.btn_delete == view.getId()) {
                db.deleteRequest(requestId);
                Toast.makeText(MainActivity.this, "Pedido eliminado ❌, id: " + requestId, Toast.LENGTH_SHORT).show();
                fillTableRequest();
                fillMosProductsTable();
            } else if (R.id.btn_finish == view.getId()) {
                fillTableRequest();
                fillMosProductsTable();
                Toast.makeText(MainActivity.this, "Pedido finalizado 🎉, id: " + requestId, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void fillMosProductsTable() {
        ProductRepository productRepository = new ProductRepository(this);
        TableLayout tbMostProducts = findViewById(R.id.tMosProducts);
        tbMostProducts.removeAllViews();

        products = productRepository.getProducts();
        if (!requestArrayList.isEmpty()) {

            AtomicReference<TableRow> tbRowItem = new AtomicReference<> (new TableRow(this));
            AtomicReference<TableRow> tbRowHeader = new AtomicReference<> (new TableRow(this));
            AtomicReference<TextView> tvItem = new AtomicReference<>(new TextView(this));
            AtomicReference<TextView> tvHeader = new AtomicReference<>(new TextView(this));

            products.forEach(product -> {
                List<Request> filterRequest =
                        requestArrayList.stream().filter(
                                        internalRequest ->
                                                product.getName().equals(internalRequest.getProducto()))
                                .collect(Collectors.toList());
                AtomicInteger quantity = new AtomicInteger();
                filterRequest.forEach(e -> quantity.addAndGet(e.getCantidad()));

                if(quantity.get() > 0) {
                    tvHeader.set(new TextView(this));
                    tvItem.set(new TextView(this));

                    tvHeader.get().setPadding(0,0,10,0);
                    tvItem.get().setPadding(0,0,10,0);

                    tvHeader.get().setText(product.getName());
                    tvItem.get().setText(Integer.toString(quantity.get()));

                    tbRowHeader.get().addView(tvHeader.get());
                    tbRowItem.get().addView(tvItem.get());
                }

            });

            tbMostProducts.addView(tbRowHeader.get());
            tbMostProducts.addView(tbRowItem.get());
        }
    }

    private void exportToExcel(TableLayout tableLayout) {
        // Crear un nuevo libro de Excel
        Workbook workbook = new XSSFWorkbook();
        // Crear una hoja en el libro
        Sheet sheet = workbook.createSheet("pedidos");

        int rowCount = tableLayout.getChildCount();

        // Recorrer las filas de la tabla
        for (int i = 0; i < rowCount; i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            Row excelRow = sheet.createRow(i);

            int columnCount = row.getChildCount();

            // Recorrer las celdas de la fila
            for (int j = 0; j < columnCount; j++) {
                TextView cell = (TextView) row.getChildAt(j);
                Cell excelCell = excelRow.createCell(j);
                excelCell.setCellValue(cell.getText().toString());
            }
        }

        try {
            // Guardar el libro en un archivo
            FileOutputStream fos = new FileOutputStream(new File(getExternalFilesDir(null), "datos.xlsx"));
            workbook.write(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    View.OnClickListener generateRequestExcelOnClickListener = new View.OnClickListener () {
        @Override
        public void onClick(View view) {
            TableLayout requestTable = findViewById(R.id.tMain);
            exportToExcel(requestTable);

        }
    };



}