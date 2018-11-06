package ar.edu.utn.frsf.dam.isi.laboratorio02.modelo;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

public class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
        Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                Toast.LENGTH_SHORT).show();
    }


    public void onNothingSelected (AdapterView <?> parent) {
            // Hacer nada.
        }
}

