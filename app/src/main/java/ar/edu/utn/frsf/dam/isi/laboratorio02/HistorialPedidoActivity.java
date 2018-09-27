package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class HistorialPedidoActivity extends AppCompatActivity {

    private Button btnNuevo;
    private Button btnMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_pedido);

//        crea un intent explicito para ir a la actividad de creación de pedido
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
