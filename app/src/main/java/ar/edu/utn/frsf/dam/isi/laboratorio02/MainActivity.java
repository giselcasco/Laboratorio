package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnNuevoPedido;
    private Button btnHistorial;
    private Button btnListaProductos;
    private Button btnPrepararPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();


        btnPrepararPedidos= (Button)findViewById(R.id.btnPrepararPedidos);
        btnPrepararPedidos.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, PrepararPedidoService.class);

                startService(i);
                finish();
            }
        });


        btnNuevoPedido = (Button) findViewById(R.id.btnMainNuevoPedido);
        btnNuevoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,AltaPedidoActivity.class);
                startActivity(i);
            }
        });

        btnHistorial = (Button) findViewById(R.id.btnHistorialPedidos);
        btnHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, HistorialPedidoActivity.class);
                startActivity(i);
            }
        });

        btnListaProductos = (Button) findViewById(R.id.btnListaProductos);
        btnListaProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,ListaProductosActivity.class);
                startActivity(i);
            }
        });

    }
    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(MainActivity.this,"SALIENDO", Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(MainActivity.this,"DESTRUYENDO", Toast.LENGTH_LONG).show();
    }
    private void createNotificationChannel(){
        // Crear el canal de notificaciones pero solo para API 26 io superior
        // dado que NotificationChannel es una clase nueva que no está incluida
        // en las librerías de soporte qeu brindan compatibilidad hacía atrás
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            CharSequence name = getString(R.string.canal_estado_nombre);
            String description = getString(R.string.canal_estado_descr);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            //CANAL01
            NotificationChannel channel01;
            channel01 = new NotificationChannel("CANAL01", name, importance);
            channel01.setDescription(description);
            // Registrar el canal en el sistema
            NotificationManager notificationManager01 = getSystemService(NotificationManager.class);
            notificationManager01.createNotificationChannel(channel01);

            //CANAL02
            NotificationChannel channel02;
            channel02 = new NotificationChannel("CANAL02", name, importance);
            channel02.setDescription(description);
            // Registrar el canal en el sistema
            NotificationManager notificationManager02 = getSystemService(NotificationManager.class);
            notificationManager02.createNotificationChannel(channel01);
        }
    }
}
