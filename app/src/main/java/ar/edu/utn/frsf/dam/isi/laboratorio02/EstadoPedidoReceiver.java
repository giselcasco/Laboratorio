package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class EstadoPedidoReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        ProductoRepository productoRepository= new ProductoRepository();
        PedidoRepository pedidoRepository=new PedidoRepository();

        int id = intent.getExtras().getInt("idPedido");
        Pedido pedido = pedidoRepository.buscarPorId(id);
        String accion = intent.getAction();
        if(accion == "ESTADO_ACEPTADO"){
            
        }

    }
}
