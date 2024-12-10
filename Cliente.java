public class Cliente {
    String nome;
    String telefone;
    Endereco endereco;

    public Cliente(String nome, String telefone, Endereco endereco) {
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Cliente {" +
                "\n\t\tnome=" + nome +
                ",\n\t\ttelefone= " + telefone +
                ",\n\t\tendereco= " + endereco.toString() +
                "\n\t}";
    }
}