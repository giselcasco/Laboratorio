package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class EstadoPedidoReceiver extends BroadcastReceiver {
    public static String ESTADO_ACEPTADO="ar.edu.utn.frsf.dam.isi.laboratorio2.ESTADO_ACEPTADO";
    private Pedido pedido = new Pedido();
    private ProductoRepository productoRepository= new ProductoRepository();
    private PedidoRepository pedidoRepository=new PedidoRepository();
    @Override
    public void onReceive(Context context, Intent intent) {

        Bitmap myBitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.retira);

        Notification notification = new NotificationCompat.Builder(context, "CANAL01")
                .setSmallIcon(R.drawable.retira)
                .setContentTitle("Laboratorio02")
                .setContentText("Tu Pedido fue Aceptado")
                .setContentInfo("El costo será de $" + pedido.total().toString())
                .setContentInfo("Previsto para " + pedido.getFecha().toString())
                .setLargeIcon(myBitmap)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(myBitmap)
                        .bigLargeIcon(null))
                .build();


        if(intent.getAction().equals(ESTADO_ACEPTADO)){
        int id = intent.getExtras().getInt("idPedido");
        pedido = pedidoRepository.buscarPorId(id);

            Toast.makeText(context,"Pedido para "
                    + pedido.getMailContacto().toString()
                    + " ha cambiado de estado a aceptado",Toast.LENGTH_LONG).show();
            notification.notify();

        }

    }
}
