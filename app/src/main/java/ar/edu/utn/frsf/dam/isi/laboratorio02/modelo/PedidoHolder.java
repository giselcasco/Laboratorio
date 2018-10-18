package ar.edu.utn.frsf.dam.isi.laboratorio02.modelo;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ar.edu.utn.frsf.dam.isi.laboratorio02.R;

public class PedidoHolder {
    public TextView tvMailPedido;
    public TextView tvHoraEntrega;
    public TextView tvCantidadItems;
    public TextView tvPrecio;
    public TextView estado;
    public ImageView tipoEntrega;
    public Button btnCancelar;
    public Button btnVerDetalle;

    public PedidoHolder(View base){
        this.tvMailPedido = (TextView) base.findViewById(R.id.tvMailPedido);
        this.tvHoraEntrega= (TextView) base.findViewById(R.id.tvHoraEntrega);
        this.tvCantidadItems= (TextView) base.findViewById(R.id.tvCantidadItems);
        this.estado = (TextView) base.findViewById(R.id.estado);
        this.tipoEntrega = (ImageView) base.findViewById(R.id.tipoEntrega);
        this.btnCancelar = (Button) base.findViewById(R.id.btnCancelar);
        this.btnVerDetalle = (Button) base.findViewById(R.id.btnVerDetallePedido);
    }
}
