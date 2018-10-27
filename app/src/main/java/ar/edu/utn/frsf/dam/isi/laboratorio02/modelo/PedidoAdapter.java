package ar.edu.utn.frsf.dam.isi.laboratorio02.modelo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.AltaPedidoActivity;
import ar.edu.utn.frsf.dam.isi.laboratorio02.HistorialPedidoActivity;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(this.ctx);
        View filaHistorial = convertView;
        if(filaHistorial==null) {
            filaHistorial = inflater.inflate(R.layout.fila_historial, parent, false);
        }
        final Pedido algunPedido = (Pedido) super.getItem(position);
        PedidoHolder holderPedido = (PedidoHolder) filaHistorial.getTag();
        if(holderPedido==null){
            holderPedido = new PedidoHolder(filaHistorial);
            filaHistorial.setTag(holderPedido);
        }
        holderPedido.tvMailPedido.setText("Contacto: "+algunPedido.getMailContacto().toString());
        holderPedido.tvCantidadItems.setText("Items: "+algunPedido.getDetalle().size());
        holderPedido.tvHoraEntrega.setText("Fecha de Entrega: "+algunPedido.getFecha().toString());
        String precio =String.valueOf(algunPedido.total());
        holderPedido.tvPrecio.setText("Total a pagar: $"+precio);

        if(algunPedido.getRetirar()){
            holderPedido.tipoEntrega.setImageResource(R.drawable.ic_launcher_background);
        }else{
            holderPedido.tipoEntrega.setImageResource(R.drawable.ic_launcher_foreground);
        }

        switch (algunPedido.getEstado()){
            case LISTO:
                holderPedido.estado.setText("LISTO");
                holderPedido.estado.setTextColor(Color.DKGRAY);
                break;
            case ENTREGADO:
                holderPedido.estado.setText("ENTREGADO");
                holderPedido.estado.setTextColor(Color.BLUE);
                break;
            case CANCELADO:
                holderPedido.estado.setText("CANCELADO");
                holderPedido.estado.setTextColor(Color.RED);
            case RECHAZADO:
                holderPedido.estado.setText("RECHAZADO");
                holderPedido.estado.setTextColor(Color.RED);
                break;
            case ACEPTADO:
                holderPedido.estado.setText("ACEPTADO");
                holderPedido.estado.setTextColor(Color.GREEN);
                break;
            case EN_PREPARACION:
                holderPedido.estado.setText("EN_PREPARACION");
                holderPedido.estado.setTextColor(Color.MAGENTA);
                break;
            case REALIZADO:
                holderPedido.estado.setText("REALIZADO");
                holderPedido.estado.setTextColor(Color.BLUE);
                break;
        }
        holderPedido.btnVerDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int item = algunPedido.getId();

                Intent i = new Intent(ctx,AltaPedidoActivity.class);
                i.putExtra("ID_PEDIDO", item);
                ctx.startActivity(i);

            }
        }

        );

        return filaHistorial;

    }


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

        @Override
        public  int  getCount()  {
            return  this.datos.size();
        }


    @Override
    public Pedido getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public void remove( Pedido object) {
        super.remove(object);
    }

}
