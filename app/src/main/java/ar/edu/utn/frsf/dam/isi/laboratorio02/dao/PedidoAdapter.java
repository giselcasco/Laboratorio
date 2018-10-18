package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.R;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoHolder;

public class PedidoAdapter extends ArrayAdapter<Pedido> {
    private Context ctx;
    private List<Pedido> datos;

    public PedidoAdapter(Context context, List<Pedido> lista) {
        super(context, 0, lista);
        this.ctx = context;
        this.datos = lista;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(this.ctx);
        View filaHistorial = convertView;
        if(filaHistorial==null) {
            filaHistorial = inflater.inflate(R.layout.fila_historial, parent, false);
        }
        Pedido algunPedido = (Pedido) super.getItem(position);
        PedidoHolder holderPedido = (PedidoHolder) filaHistorial.getTag();
        if(holderPedido==null){
            holderPedido = new PedidoHolder(filaHistorial);
            filaHistorial.setTag(holderPedido);
        }
        holderPedido.tvMailPedido.setText(algunPedido.getMailContacto().toString());
        holderPedido.tvCantidadItems.setText(algunPedido.getDetalle().size());
        holderPedido.tvHoraEntrega.setText(algunPedido.getFecha().toString());
        holderPedido.tvPrecio.setText(algunPedido.total().toString());

        if(algunPedido.getRetirar()){
            holderPedido.tipoEntrega.setImageResource(R.drawable.ic_launcher_background);
        }else{
            holderPedido.tipoEntrega.setImageResource(R.drawable.ic_launcher_foreground);
        }

        switch (algunPedido.getEstado()){
            case LISTO:
                holderPedido.estado.setTextColor(Color.DKGRAY);
                break;
            case ENTREGADO:
                holderPedido.estado.setTextColor(Color.BLUE);
                break;
            case CANCELADO:
            case RECHAZADO:
                holderPedido.estado.setTextColor(Color.RED);
                break;
            case ACEPTADO:
                holderPedido.estado.setTextColor(Color.GREEN);
                break;
            case EN_PREPARACION:
                holderPedido.estado.setTextColor(Color.MAGENTA);
                break;
            case REALIZADO:
                holderPedido.estado.setTextColor(Color.BLUE);
                break;
        }

        return filaHistorial;


        // new View.OnClickListener() {
        //@Override
        //public void onClick(View view) {
//            int  indice = (int) view.getTag();
//            Pedido pedidoSeleccionado = datos.get(indice);
//            if( pedidoSeleccionado.getEstado().equals(Pedido.Estado.REALIZADO)||
//                    pedidoSeleccionado.getEstado().equals(Pedido.Estado.ACEPTADO)||
//                    pedidoSeleccionado.getEstado().equals(Pedido.Estado.EN_PREPARACION)){
//                pedidoSeleccionado.setEstado(Pedido.Estado.CANCELADO);
//                PedidoAdapter.this.notifyDataSetChanged();
//                return;
//            }
//        }
//    };
    }


}
