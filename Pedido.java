import java.time.LocalDate;

public class Pedido {
    int numero;
    float preco;
    Cliente cliente;
    LocalDate data;

    public Pedido(int numero, float preco, Cliente cliente) {
        this.numero = numero;
        this.preco = preco;
        this.cliente = cliente;
        this.data = LocalDate.now();
    }
}
