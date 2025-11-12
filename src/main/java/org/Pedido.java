package org;

public class Pedido {
    int id;
    String[] platos;
    estadoPedido estado;

    //  Constructor
    public Pedido(String[] platos, int id) {
        this.platos = platos;
        this.id = id;
        this.estado = estadoPedido.PENDIENTE; // estado inicial
    }

    //
    public void setEstado(estadoPedido estado) {
        this.estado = estado;
    }

    //)
    public estadoPedido getEstado() {
        return estado;
    }
}

