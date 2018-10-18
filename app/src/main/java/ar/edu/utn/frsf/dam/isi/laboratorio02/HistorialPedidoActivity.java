package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoAdapter;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class HistorialPedidoActivity extends AppCompatActivity {

    private Button btnNuevo;
    private Button btnMenu;
    private ListView listaPedidos;
    private ArrayAdapter<Pedido> pedidosAdapter;
    private final Pedido unPedido = new Pedido();
    private final PedidoRepository pedidoRepository = new PedidoRepository();
    private ProductoRepository productoRepository = new ProductoRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_pedido);
        listaPedidos = (ListView) findViewById(R.id.lstHistorialPedidos);
        pedidosAdapter = new PedidoAdapter(this, pedidoRepository.getLista() );
        listaPedidos.setAdapter(pedidosAdapter);

        listaPedidos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int item = pedidosAdapter.getItem(position).getId();

                Intent i = new Intent(HistorialPedidoActivity.this,AltaPedidoActivity.class);
                i.putExtra("ID_PEDIDO", item);
                startActivity(i);

                return true;
            }
        });
        btnNuevo = (Button) findViewById(R.id.btnHistorialNuevo);
        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HistorialPedidoActivity.this,AltaPedidoActivity.class);

                startActivity(i);
            }
        });

//        Crea un intent explicito que retorna el control a la actividad del menú principal
        btnMenu = (Button)findViewById(R.id.btnHistorialMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HistorialPedidoActivity.this,MainActivity.class);
                startActivity(i);
            }
        });






}

    }