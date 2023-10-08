package com.example.cerdiexpress;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.cerdiexpress.activities.CreateProductActivity;
import com.example.cerdiexpress.activities.RequestCreateActivity;
import com.example.cerdiexpress.db.DbHelper;
import com.example.cerdiexpress.db.entities.Product;
import com.example.cerdiexpress.db.entities.ProductRequest;
import com.example.cerdiexpress.db.entities.Request;
import com.example.cerdiexpress.db.repository.ProductRepository;
import com.example.cerdiexpress.db.repository.ProductRequestRepository;
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

    Button btnGenerateXmls, btnRevealCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGenerateXmls = findViewById(R.id.btnGenerateRequestXmls);
        btnRevealCard = findViewById(R.id.cardTestButton);

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
                hStatus = new TextView(this),
                hCandtidad = new TextView(this),
                hOrdenante = new TextView(this),
                hContacto = new TextView(this);

        /*
         *
         * Add text value
         * */
        hId.setText(R.string.text_id_field);
        hNombre.setText(R.string.text_name_field);
        hStatus.setText(R.string.text_status_field);
        hOrdenante.setText(R.string.txt_ordenante_field);
        hContacto.setText(R.string.txt_contacto_field);
        hCandtidad.setText(R.string.txt_cantidad_field);
        /*
         *
         * Add padding
         * */
        hContacto.setPadding(0, 0, 10, 0);
        hOrdenante.setPadding(0, 0, 10, 0);
        hStatus.setPadding(0, 0, 10, 0);
        hCandtidad.setPadding(0, 0, 10, 0);
        hNombre.setPadding(0, 0, 10, 0);
        hId.setPadding(0, 0, 10, 0);


        TableRow hRow = new TableRow(this);

        hRow.addView(hId);
        hRow.addView(hNombre);
        hRow.addView(hCandtidad);
        hRow.addView(hStatus);
        hRow.addView(hContacto);
        hRow.addView(hOrdenante);


        tb.addView(hRow);

        for (Request elemento : requestArrayList) {

            TableRow row = new TableRow(this);

            TextView id = new TextView(this),
                    nombre = new TextView(this),
                    status = new TextView(this),
                    candtidad = new TextView(this),
                    ordenante = new TextView(this),
                    contacto = new TextView(this);

            /*
             *
             * Add text value
             * */
            id.setText(elemento.getOrderId());
            status.setText(elemento.getStatus());
            nombre.setText(elemento.getNombre());
            ordenante.setText(elemento.getOrdenante());
            contacto.setText(elemento.getContacto());
            candtidad.setText(Integer.toString(elemento.getCantidad()));

            /*
             *
             * Add padding
             * */
            status.setPadding(0, 0, 10, 0);
            id.setPadding(0, 0, 10, 0);
            nombre.setPadding(0, 0, 10, 0);
            candtidad.setPadding(0, 0, 10, 0);
            ordenante.setPadding(0, 0, 10, 0);
            contacto.setPadding(0, 0, 10, 0);

            Button btnDelete = new Button(this),
                    btnFinish = new Button(this),
                    btnRevealCar = new Button(this);

            btnRevealCar.setText(R.string.btn_details);
            btnRevealCar.setId(R.id.btn_item_detail);
            btnRevealCar.setOnClickListener(revealCardRequest);
            btnRevealCar.setTag(elemento.getOrderId());

            btnDelete.setText(R.string.btn_delete);
            btnDelete.setId(R.id.btn_delete);
            btnDelete.setOnClickListener(buttonClickListener);
            btnDelete.setTag(elemento.getOrderId());

            btnFinish.setText(R.string.btn_finish);
            btnFinish.setId(R.id.btn_finish);
            btnFinish.setOnClickListener(buttonClickListener);
            btnFinish.setTag(elemento.getOrderId());

            row.addView(id);
            row.addView(nombre);
            row.addView(candtidad);
            row.addView(status);
            row.addView(contacto);
            row.addView(ordenante);
            row.addView(btnRevealCar);
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

            RequestRepository requestRepository = new RequestRepository(MainActivity.this);
            ProductRequestRepository productRequestRepository = new ProductRequestRepository(MainActivity.this);
            String requestId = (String) view.getTag();

            if (R.id.btn_delete == view.getId()) {
                requestRepository.deleteRequest(requestId);
                productRequestRepository.deleteProductsRequest(requestId);
                Toast.makeText(MainActivity.this, "❌ Pedido eliminado \n" + requestId, Toast.LENGTH_SHORT).show();
                fillTableRequest();
                fillMosProductsTable();
            } else if (R.id.btn_finish == view.getId()) {
//                fillTableRequest();
//                fillMosProductsTable();
                Toast.makeText(MainActivity.this, "🎉 Pedido finalizado \n" + requestId, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void fillMosProductsTable() {
        ProductRepository productRepository = new ProductRepository(this);
        TableLayout tbMostProducts = findViewById(R.id.tMosProducts);
        tbMostProducts.removeAllViews();

        products = productRepository.getProducts();
        if (!requestArrayList.isEmpty()) {

            AtomicReference<TableRow> tbRowItem = new AtomicReference<>(new TableRow(this));
            AtomicReference<TableRow> tbRowHeader = new AtomicReference<>(new TableRow(this));
            AtomicReference<TextView> tvItem = new AtomicReference<>(new TextView(this));
            AtomicReference<TextView> tvHeader = new AtomicReference<>(new TextView(this));

            ProductRequestRepository productRequestRepository = new ProductRequestRepository(this);

            final List<ProductRequest> productRequests = productRequestRepository.getProductRequests();

            products.forEach(product -> {
                List<ProductRequest> filterRequest =
                        productRequests.stream().filter(
                                        productRequest ->
                                                product.getName().equals(productRequest.getProducto()))
                                .collect(Collectors.toList());
                AtomicInteger quantity = new AtomicInteger();
                filterRequest.forEach(e -> quantity.addAndGet(e.getCantidad()));

                if (quantity.get() > 0) {
                    tvHeader.set(new TextView(this));
                    tvItem.set(new TextView(this));

                    tvHeader.get().setPadding(0, 0, 10, 0);
                    tvItem.get().setPadding(0, 0, 10, 0);

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
        Sheet sheetPedidos = workbook.createSheet("pedidos");
        Sheet sheetProductos = workbook.createSheet("productos_pedidos");

        TableLayout tableRequests = findViewById(R.id.tMain);
        TableLayout tableProducts = findViewById(R.id.tMosProducts);

        fillSheet(sheetPedidos, tableRequests);
        fillSheet(sheetProductos, tableProducts);

        try {
            File publicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            // Guardar el libro en un archivo
            FileOutputStream fos = new FileOutputStream(new File(publicDirectory, "datos.xlsx"));
            workbook.write(fos);
            fos.close();
            Toast.makeText(MainActivity.this, "Archivo generado con exito 👏", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    View.OnClickListener generateRequestExcelOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TableLayout requestTable = findViewById(R.id.tMain);
            exportToExcel(requestTable);

        }
    };


    private void fillSheet(Sheet sheet, TableLayout tableLayout) {
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
    }

    View.OnClickListener revealCardRequest = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Inflar el diseño de la carta
            View cartaView = getLayoutInflater().inflate(R.layout.request_list_card, null);

            // Crear el AlertDialog
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            alertDialogBuilder.setView(cartaView);

            // Configurar el AlertDialog
            final AlertDialog alertDialog = alertDialogBuilder.create();

            // Configurar el botón para cerrar la carta
            Button btnClose = cartaView.findViewById(R.id.btnClose);
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Cierra el AlertDialog al hacer clic en el botón
                    alertDialog.dismiss();
                }
            });

            // Mostrar el AlertDialog
            alertDialog.show();
        }
    };


}