package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

public class AltaPedidoActivity extends AppCompatActivity {

    private RadioGroup optPedidoModoEntrega;
    private EditText edtDireccion;
    private ListView listaProductos;
    private ArrayAdapter<PedidoDetalle> productoAdapter;
    private Button agregarProducto;
    private Button quitarProducto;
    private Button hacerPedido;
    private EditText horaEntrega;
    private EditText edtCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_pedido);

        final Pedido unPedido = new Pedido();
        final PedidoRepository pedidoRepository = new PedidoRepository();
        ProductoRepository productoRepository = new ProductoRepository();
        agregarProducto=(Button) findViewById(R.id.btnPedidoAddProducto);
        hacerPedido=(Button) findViewById(R.id.btnPedidoHacerPedido);
        quitarProducto=(Button)findViewById(R.id.btnPedidoQuitarProducto);
        optPedidoModoEntrega =  (RadioGroup) findViewById(R.id.optPedidoModoEntrega);
        listaProductos = (ListView) findViewById(R.id.lstPedidoItems);
        horaEntrega=(EditText) findViewById(R.id.edtPedidoHoraEntrega);
        edtDireccion = (EditText) findViewById(R.id.edtPedidoDireccion);
        edtCorreo = (EditText) findViewById(R.id.edtPedidoCorreo);

        //prueba lista detalle pedido
        Producto producto = new Producto("unProducto","Descripcion producto",1.26,new Categoria());
        PedidoDetalle detalle = new PedidoDetalle(2,producto);
        unPedido.agregarDetalle(detalle);
        producto = new Producto("otroProducto","Descripcion producto dos",36.26,new Categoria());
        detalle = new PedidoDetalle(2,producto);
        unPedido.agregarDetalle(detalle);
        productoAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, unPedido.getDetalle());
        listaProductos.setAdapter(productoAdapter);

        quitarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray productosSeleccionados = listaProductos.getCheckedItemPositions();
                ArrayList elementosQuitar;
                elementosQuitar = new ArrayList();
                int cantidadFilasLista = listaProductos.getCount();
                for(int i=0;i<cantidadFilasLista;i++){
                    if(productosSeleccionados.get(i)) {
                        PedidoDetalle detail = (PedidoDetalle) productoAdapter.getItem(i);
                        elementosQuitar.add(detail);
                        //Al parecer deberia usar un ArrayList en vez de el listview aca
                    }
                }
                cantidadFilasLista=elementosQuitar.size();
                for(int i=0;i<cantidadFilasLista;i++){
                    productoAdapter.remove((PedidoDetalle) elementosQuitar.get(i));
                }
                productosSeleccionados.clear();
                productoAdapter.notifyDataSetChanged();

            }
        });

        //Ejercicio 3 - Item i
        hacerPedido.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(horaEntrega.getText().toString().contains(":")) {
                    String[] horaIngresada = horaEntrega.getText().toString().split(":");
                    GregorianCalendar hora = new GregorianCalendar();
                    int valorHora = Integer.valueOf(horaIngresada[0]);
                    int valorMinuto = Integer.valueOf(horaIngresada[1]);
                    if (valorHora < 0 || valorHora > 23) {
                        Toast.makeText(AltaPedidoActivity.this,
                                "La hora ingresada (" + valorHora + " es incorrecta",
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (valorMinuto < 0 || valorMinuto > 59) {
                        Toast.makeText(AltaPedidoActivity.this,
                                "Los minutos (" + valorMinuto + " son incorrectos",
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                    hora.set(Calendar.HOUR_OF_DAY, valorHora);
                    hora.set(Calendar.MINUTE, valorMinuto);
                    hora.set(Calendar.SECOND, Integer.valueOf(0));
                    unPedido.setFecha(hora.getTime());
                }
                // setear el resto de los atributos del pedido
                if(edtDireccion.isEnabled() && edtDireccion.length()<=0){
                    Toast.makeText(AltaPedidoActivity.this,"Debe Ingresar una Direccion de Envío",Toast.LENGTH_LONG).show();
                }

                if(edtCorreo.isEnabled() && edtDireccion.length()<=9){//"@gmail.com".length() =9
                    Toast.makeText(AltaPedidoActivity.this,"Debe Ingresar un Correo Electrónico válido",Toast.LENGTH_LONG).show();
                }

                if(listaProductos.getCount()<=0){
                    Toast.makeText(AltaPedidoActivity.this,"Agregue al menos un producto",Toast.LENGTH_LONG).show();
                }
                pedidoRepository.guardarPedido(unPedido);
                // lo seteamos a una nueva instancia para el proximo pedido
                Pedido unPedido = new Pedido();
                Log.d("APP_LAB02","Pedido "+ unPedido.toString());

    }});

        //Ejercicio 3.H que depende del 2
        //agregarProducto.onClickListener(new View.OnClickListener(){
        //   @Override
        //    public void onClick(View v) {
               //visualizar la lista de productos del punto 2
               // onActivityResult(int requestCode, int resultCode, Intent data){
        //   }
        //});

        optPedidoModoEntrega.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch(checkedId){
                    case R.id.optPedidoRetira:
                        edtDireccion.setEnabled(false);
                        break;
                    case R.id.optPedidoEnviar:
                        edtDireccion.setEnabled(true);
                        break;
                }
            }
        });





    }
}