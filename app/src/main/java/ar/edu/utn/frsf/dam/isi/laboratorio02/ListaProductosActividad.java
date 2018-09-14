package ar.edu.utn.frsf.dam.isi.laboratorio02;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ListaProductosActividad extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spinner_activity);

        Button btn2 = (Button) findViewById(R.id.btnProdAddPedido);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent ();
                startActivity(intent2);
            }
        });

        }



}
