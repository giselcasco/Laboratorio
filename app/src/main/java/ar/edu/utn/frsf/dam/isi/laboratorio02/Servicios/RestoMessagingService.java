package ar.edu.utn.frsf.dam.isi.laboratorio02.Servicios;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import ar.edu.utn.frsf.dam.isi.laboratorio02.EstadoPedidoReceiver;
import ar.edu.utn.frsf.dam.isi.laboratorio02.MainActivity;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

import static ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido.*;

public class RestoMessagingService extends FirebaseMessagingService {
    private PedidoRepository pedidoRepository = new PedidoRepository();
    private Pedido pedido ;
    private static final String TAG = "MyFirebaseMsgService";



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        sendNotification(
                remoteMessage.getNotification().getBody()
        );
        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String message = remoteMessage.getNotification().getBody();
        }
        if(remoteMessage.getData().size()>0){
            int id = Integer.parseInt(remoteMessage.getData().get("ID_PEDIDO"));
            String estado = remoteMessage.getData().get("NUEVO_ESTADO");
            pedido = pedidoRepository.buscarPorId(id);
            pedido.setEstado(Pedido.Estado.valueOf(estado));

            if(pedido.getEstado().equals(Estado.LISTO)){
                Intent i = new Intent(this, EstadoPedidoReceiver.class);
                i.setAction(EstadoPedidoReceiver.ESTADO_LISTO);
                i.putExtra("idPedido",id);
            }
        }


    }
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0
                        , intent
                        , PendingIntent.FLAG_ONE_SHOT);
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
    }
}
