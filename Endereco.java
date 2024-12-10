public class Endereco {
    String rua;
    String bairro;
    String uf;
    int numero;
    int cep;

    public Endereco(String rua, String bairro, String uf, int numero, int cep) {
        this.rua = rua;
        this.bairro = bairro;
        this.uf = uf;
        this.numero = numero;
        this.cep = cep;
    }

    @Override
    public String toString() {
        return "Endereco {" +
                "\n\t\t\trua=" + rua +
                ",\n\t\t\tbairro= " + bairro +
                ",\n\t\t\tuf= " + uf +
                ",\n\t\t\tnumero= " + numero +
                ",\n\t\t\tcep= " + cep +
                "\n\t\t}";
    }
}
