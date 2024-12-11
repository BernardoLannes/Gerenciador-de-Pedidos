package src;

import java.time.LocalDate;

public class Pedido {
    private int numero;
    private double preco;
    private Cliente cliente;
    private LocalDate data_pedido;
    private LocalDate data_entrega;

    public Pedido(int numero, double preco, Cliente cliente) {
        this.numero = numero;
        this.preco = preco;
        this.cliente = cliente;
        this.data_pedido = LocalDate.now();
    }

    public Pedido(int numero, double preco, Cliente cliente, LocalDate data_entrega) {
        this(numero, preco * 1.2, cliente);
        this.data_entrega = data_entrega;
    }

    public boolean entregueNoPrazo() {
        return data_entrega != null && data_entrega.equals(data_pedido);
    }

    public int getNumero() {
        return this.numero;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public double getPreco() {
        return this.preco;
    }

    public LocalDate getDataEntrega() {
        return this.data_entrega;
    }

    public LocalDate getDataPedido() {
        return this.data_pedido;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setDataEntrega(LocalDate data_entrega) {
        this.data_entrega = data_entrega;
    }

    public void setDataPedido(LocalDate data_pedido) {
        this.data_pedido = data_pedido;
    }

    @Override
    public String toString() {
        return "Pedido {" +
                "\n\tnumero=" + numero +
                ",\n\tpreco= " + preco +
                ",\n\tcliente= " + cliente +
                ",\n\tdata_pedido= " + data_pedido +
                ",\n\tdata_entrega= " + (data_entrega != null ? data_entrega : "não definida") +
                "\n}";
    }

}
