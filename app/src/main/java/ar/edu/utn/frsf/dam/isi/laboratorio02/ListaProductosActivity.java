package ar.edu.utn.frsf.dam.isi.laboratorio02;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

public class ListaProductosActivity extends AppCompatActivity {

    //c) Crear el adaptador para el Spinner y vincularlo al objeto Spinner
    // para que se visualicen las categorias de los productos
    private Spinner spinner;
    private ArrayAdapter<Categoria> adapterCategoria;
    private TextView tvcategoria;
    private ListView listCategoria;
    private ProductoRepository listaCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_spinner);
        spinner = (Spinner)findViewById(R.id.spinnerProductosCategoria);
        adapterCategoria = new ArrayAdapter<Categoria>(this, android.R.layout.simple_spinner_item,listaCategoria.getCategorias());
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterCategoria);

       Button btn2 = (Button) findViewById(R.id.btnProdAddPedido);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent ();
                startActivity(intent2);
            }
        });

        //e)
      listCategoria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Categoria categoria = (Categoria)adapterCategoria.getItem(position);
                tvcategoria.setText(categoria.getNombre());
            }
        });

    }

}
