package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class EstadoPedidoReceiver extends BroadcastReceiver {
    public static String ESTADO_ACEPTADO="ar.edu.utn.frsf.dam.isi.laboratorio2.ESTADO_ACEPTADO";
    public static String ESTADO_CANCELADO = "ar.edu.utn.frsf.dam.isi.laboratorio02.ESTADO_CANCELADO";
    public static String ESTADO_EN_PREPARACION = "ar.edu.utn.frsf.dam.isi.laboratorio02.ESTADO_EN_PREPARACION";
    public static String ESTADO_LISTO = "ar.edu.utn.frsf.dam.isi.laboratorio02.ESTADO_LISTO";

    private Pedido pedido = new Pedido();
    private ProductoRepository productoRepository= new ProductoRepository();
    private PedidoRepository pedidoRepository=new PedidoRepository();
    @Override
    public void onReceive(Context context, Intent intent) {

        Bitmap myBitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.retira);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, "CANAL02")
                        .setSmallIcon(R.drawable.retira)
                        .setContentTitle("Laboratorio02")
                        .setContentText("Tu Pedido fue Aceptado  -- CANAL02")
                        .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if(intent.getAction().equals(ESTADO_ACEPTADO)){
        int id = intent.getExtras().getInt("idPedido");
        pedido = pedidoRepository.buscarPorId(id);

            //Toast.makeText(context,"Pedido para " + pedido.getMailContacto().toString() + " ha cambiado de estado a aceptado",Toast.LENGTH_LONG).show();
            //Lab03-Parte02-Item02
            Notification notification = new NotificationCompat.Builder(context, "CANAL01")
                    .setSmallIcon(R.drawable.retira)
                    .setContentTitle("Laboratorio02")
                    .setContentText("Tu Pedido fue Aceptado  -- CANAL01")
                    //.setContentInfo("El costo será de $" + pedido.total().toString())
                    //.setContentInfo("Previsto para " + pedido.getFecha().toString())
                    .setLargeIcon(myBitmap)
                    .setStyle(new NotificationCompat.InboxStyle()
                            .addLine("El costo será de $" + pedido.total().toString())
                            .addLine("Previsto para " + pedido.getFecha().toString()))
                    .build();
            notificationManager.notify(1, notification);

        }

        if(intent.getAction().equals(ESTADO_EN_PREPARACION)){
            Toast.makeText(context,"Pedido para "
                    + pedido.getMailContacto().toString()
                    + " ha cambiado de estado a ESTADO_EN_PREPARACION",Toast.LENGTH_LONG).show();

          //  mBuilder.setContentInfo("El pedido para " + pedido.getMailContacto().toString() + "está en Preparación");
          //  notificationManager.notify(2,mBuilder.build());

        }

    }
}
