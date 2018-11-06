package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

public class AltaPedidoActivity extends AppCompatActivity {
    private static final int NUEVO_PEDIDO = 1;
    private static final int ID_PEDIDO = 0;
    private RadioGroup optPedidoModoEntrega;
    private EditText edtDireccion;
    private ListView listaProductos;
    private ArrayAdapter<PedidoDetalle> productoAdapter;
    private Button agregarProducto;
    private Button quitarProducto;
    private Button hacerPedido;
    private Button volver;
    private Button cancelarPedido;
    private EditText horaEntrega;
    private EditText edtCorreo;
    private TextView totalPedido;
    private double precioTotal;
    private Pedido unPedido = new Pedido();
    private final PedidoRepository pedidoRepository = new PedidoRepository();
    private ProductoRepository productoRepository = new ProductoRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_pedido);


        agregarProducto=(Button) findViewById(R.id.btnPedidoAddProducto);
        hacerPedido=(Button) findViewById(R.id.btnPedidoHacerPedido);
        volver = (Button) findViewById(R.id.btnPedidoVolver);
        cancelarPedido = (Button) findViewById(R.id.btnCancelar);
        quitarProducto=(Button)findViewById(R.id.btnPedidoQuitarProducto);
        optPedidoModoEntrega =  (RadioGroup) findViewById(R.id.optPedidoModoEntrega);
        listaProductos = (ListView) findViewById(R.id.lstPedidoItems);
        horaEntrega=(EditText) findViewById(R.id.edtPedidoHoraEntrega);
        edtDireccion = (EditText) findViewById(R.id.edtPedidoDireccion);
        edtCorreo = (EditText) findViewById(R.id.edtPedidoCorreo);
        totalPedido = (TextView) findViewById(R.id.lblTotalPedido);
        productoAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, unPedido.getDetalle());
        listaProductos.setAdapter(productoAdapter);
        precioTotal = 0;
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Punto 4 - Item a
        Intent i1= getIntent();
        int idPedido = 0;
        if(i1.getExtras()!=null){
            idPedido = i1.getExtras().getInt("ID_PEDIDO");
        }
        if(idPedido>0){
            unPedido = pedidoRepository.buscarPorId(idPedido);
            edtCorreo.setText(unPedido.getMailContacto());
            edtDireccion.setText(unPedido.getDireccionEnvio());
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            horaEntrega.setText(sdf.format(unPedido.getFecha()));
            totalPedido.setText("Total del Pedido: $" + unPedido.total().toString());
            boolean retira = unPedido.getRetirar();
            totalPedido.setText("Total del Pedido: $"+ unPedido.total().toString());
           if(retira) {
               edtDireccion.setEnabled(false);
           }
           else{
               edtDireccion.setEnabled(true);
                    edtDireccion.setText(unPedido.getDireccionEnvio());
            }
        }else {
            unPedido = new Pedido();
        }

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
                else{
                    Toast.makeText(AltaPedidoActivity.this,"Ingrese un horario válido. Ejemplo 12:45",Toast.LENGTH_LONG).show();
                    return;
                }

                if(edtDireccion.isEnabled() && edtDireccion.length()<=0){
                    Toast.makeText(AltaPedidoActivity.this,"Debe Ingresar una Direccion de Envío",Toast.LENGTH_LONG).show();
                    return;
                }
                unPedido.setDireccionEnvio(edtDireccion.getText().toString());

                //Validar Correo Electronico
                String email = edtCorreo.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(!email.matches(emailPattern) ){
                    Toast.makeText(AltaPedidoActivity.this,"Debe Ingresar un Correo Electrónico válido",Toast.LENGTH_LONG).show();
                    return;
                }
                unPedido.setMailContacto(edtCorreo.getText().toString().trim());

                if(listaProductos.getCount()<=0){
                    Toast.makeText(AltaPedidoActivity.this,"Agregue al menos un producto",Toast.LENGTH_LONG).show();
                    return;
                }
                unPedido.setEstado(Pedido.Estado.REALIZADO);
                pedidoRepository.guardarPedido(unPedido);
                Intent i = new Intent(AltaPedidoActivity.this, HistorialPedidoActivity.class);
                startActivity(i);

                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.currentThread().sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // buscar pedidos no aceptados y aceptarlos utomáticamente
                        List<Pedido> lista = pedidoRepository.getLista();
                        for(Pedido p:lista){
                            if(p.getEstado().equals(Pedido.Estado.REALIZADO))
                                p.setEstado(Pedido.Estado.ACEPTADO);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AltaPedidoActivity.this,
                                        "Informacion de pedidos actualizada!",
                                Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                };
                Thread unHilo = new Thread(r);
                unHilo.start();


                finish();

    }});

        //Ejercicio 3.H que depende del 2
        agregarProducto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(AltaPedidoActivity.this, ListaProductosActivity.class);
                        i.putExtra("NUEVO_PEDIDO", 1);
                        startActivityForResult(i,1);

                    }
                });



        optPedidoModoEntrega.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch(checkedId){
                    case R.id.optPedidoRetira:
                        edtDireccion.setEnabled(false);
                        unPedido.setRetirar(true);
                        break;
                    case R.id.optPedidoEnviar:
                        edtDireccion.setEnabled(true);
                        unPedido.setRetirar(false);
                        break;
                }
            }
        });

        //Punto 4 - Item b
        cancelarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(unPedido.getEstado() == Pedido.Estado.ACEPTADO
                        || unPedido.getEstado() == Pedido.Estado.REALIZADO
                        || unPedido.getEstado() == Pedido.Estado.EN_PREPARACION){
                        unPedido.setEstado(Pedido.Estado.CANCELADO);
                }
            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( resultCode== Activity.RESULT_OK){
            if(requestCode==NUEVO_PEDIDO) {

                int cantidadParam =
                        data.getExtras().getInt("cantidad");
                int productoParamId =
                        data.getExtras().getInt("idProducto");
                Producto unProducto = productoRepository.buscarPorId(productoParamId);
                unPedido.agregarDetalle(new PedidoDetalle(cantidadParam, unProducto));
                precioTotal = precioTotal + unProducto.getPrecio() * cantidadParam;

                totalPedido.setText("Total del Pedido: $"+ precioTotal);
                productoAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, unPedido.getDetalle());
                //productoAdapter.addAll(unPedido.getDetalle());
                productoAdapter.notifyDataSetChanged();
                listaProductos.setAdapter(productoAdapter);
            }
        }
    }



}
