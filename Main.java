import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Cliente fulano = new Cliente("Fulano", "9999",
                new Endereco("A", "nao informado", "nao informado", 00, 00000000));
        Cliente sicrano = new Cliente("Sicrano", "8888",
                new Endereco("B", "nao informado", "nao informado", 00, 00000000));

        Pedido pedido_fulano = new Pedido(1, 500, fulano);
        Pedido pedido_sicrano = new Pedido(2, 500, sicrano, LocalDate.now().toString());

        System.out.println(pedido_fulano.toString());
        System.out.println(pedido_sicrano.toString());
        System.out.println(pedido_sicrano.entregueNoPrazo());

    }
}

// (f) Implemente operações para salvar e recuperar em arquivos as informações
// clientes e pedidos