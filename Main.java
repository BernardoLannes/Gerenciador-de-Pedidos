public class Main {
    public static void main(String[] args) {
        Cliente elane = new Cliente("Elane Oliveira", "12384324", new Endereco("Perto", "de Casa", "RJ", 23, 28893108));
        Cliente bernardo = new Cliente("Bernardo Lannes", "22991025485",
                new Endereco("Em casa", "Balneario Remanso", "RJ", 207, 28893108));

        Pedido pedido_bernardo = new Pedido(1, 10, bernardo);
        Pedido pedido_elane = new Pedido(2, 20, elane, "2025-01-10");

        System.out.println(pedido_elane.toString());
        System.out.println(pedido_bernardo.toString());

    }
}