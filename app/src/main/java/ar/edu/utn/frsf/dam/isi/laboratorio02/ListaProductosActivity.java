package ar.edu.utn.frsf.dam.isi.laboratorio02;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

public class ListaProductosActivity extends AppCompatActivity {

    //c) Crear el adaptador para el Spinner y vincularlo al objeto Spinner
    // para que se visualicen las categorias de los productos

    private Spinner spinner;
    private ArrayAdapter<Categoria> adapterCategoria;
    private ArrayAdapter<Producto> productoAdapter;
    private TextView tvcategoria;
    private ListView listaProductos;
    private EditText cantidad;
    private int idProducto;
    private Button agregarProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ProductoRepository productoRepository = new ProductoRepository();

        setContentView(R.layout.activity_spinner);
        spinner = (Spinner)findViewById(R.id.spinnerProductosCategoria);
        tvcategoria=(TextView) findViewById(R.id.tvSelectCategoria);
        adapterCategoria = new ArrayAdapter(this, android.R.layout.simple_spinner_item,productoRepository.getCategorias());
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterCategoria);
        listaProductos = (ListView)findViewById(R.id.lstProductos);
        productoAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productoRepository.getLista());
        listaProductos.setAdapter(productoAdapter);
        cantidad=(EditText)findViewById(R.id.edtProdCantidad);
        agregarProducto = (Button) findViewById(R.id.btnProdAddPedido);

        Bundle parametros = this.getIntent().getExtras();
        if(parametros!=null){
            int datos = parametros.getInt("NUEVO_PEDIDO");
            if(datos>0){
                cantidad.setText("1");
                cantidad.setEnabled(true);
                agregarProducto.setEnabled(true);
            }
        }
        else{
            cantidad.setEnabled(false);
            agregarProducto.setEnabled(false);
        }


        agregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posicion  = listaProductos.getCheckedItemPosition();
                Producto unProducto=productoAdapter.getItem(posicion);
                idProducto=unProducto.getId();
                if(Integer.parseInt(cantidad.getText().toString()) > 0){
                    Intent i = new Intent();
                    i.putExtra("cantidad", Integer.parseInt(cantidad.getText().toString()));
                    i.putExtra("idProducto",idProducto);

                    //startActivity(i);
                    setResult(Activity.RESULT_OK,i);
                    finish();
                }
                else{
                    Toast.makeText(ListaProductosActivity.this,"Ingrese una Cantidad Mayor a Cero",Toast.LENGTH_LONG).show();
                    return;
                }

            }
        });

        //e)
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               Categoria categoria = (Categoria)adapterCategoria.getItem(position);
               tvcategoria.setText(categoria.getNombre());
               productoAdapter =  new ArrayAdapter(ListaProductosActivity.this, android.R.layout.simple_list_item_multiple_choice, productoRepository.buscarPorCategoria(categoria));
               productoAdapter.notifyDataSetChanged();
               listaProductos.setAdapter(productoAdapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });


    }


}
