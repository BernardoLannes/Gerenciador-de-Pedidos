import java.time.LocalDate;

public class Pedido {
    int numero;
    float preco;
    Cliente cliente;
    LocalDate data_pedido;
    String data_entrega;

    public Pedido(int numero, float preco, Cliente cliente) {
        this.numero = numero;
        this.preco = preco;
        this.cliente = cliente;
        this.data_pedido = LocalDate.now();
    }

    public Pedido(int numero, float preco, Cliente cliente, String data_entrega) {
        this(numero, preco * 1.2f, cliente);
        this.data_entrega = data_entrega;
    }

    public boolean entregueNoPrazo() {
        if (this.data_entrega.equals(this.data_pedido.toString())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Pedido {" +
                "\n\tnumero=" + numero +
                ",\n\tpreco= " + preco +
                ",\n\tcliente= " + cliente.toString() +
                ",\n\tdata_pedido= " + data_pedido.toString() +
                ",\n\tdata_entrega= " + data_entrega +
                "\n}";
    }
}
