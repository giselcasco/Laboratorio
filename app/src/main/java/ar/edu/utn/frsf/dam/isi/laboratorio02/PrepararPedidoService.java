package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoAdapter;

public class PrepararPedidoService extends IntentService {
    private PedidoRepository pedidoRepository = new PedidoRepository();
    private List<Pedido> listaPedidos;

    public PrepararPedidoService() {
        super("PrepararPedidoService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {

        try {

            Thread.sleep(2000);

            listaPedidos = pedidoRepository.getLista();
            for (Pedido p : listaPedidos) {
                if (p.getEstado().equals(Pedido.Estado.ACEPTADO)) {
                    p.setEstado(Pedido.Estado.EN_PREPARACION);

                    Intent i = new Intent(this, EstadoPedidoReceiver.class);
                    i.setAction(EstadoPedidoReceiver.ESTADO_EN_PREPARACION);
                    i.putExtra("idPedido",p.getId());
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);

                    sendBroadcast(i);

                }

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }



}
