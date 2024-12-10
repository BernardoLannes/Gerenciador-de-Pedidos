package src;

public class Endereco {
    private String rua;
    private String bairro;
    private String uf;
    private int numero;
    private int cep;

    public Endereco(String rua, String bairro, String uf, int numero, int cep) {
        this.rua = rua;
        this.bairro = bairro;
        this.uf = uf;
        this.numero = numero;
        this.cep = cep;
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "rua='" + rua + '\'' +
                ", bairro='" + bairro + '\'' +
                ", uf='" + uf + '\'' +
                ", numero=" + numero +
                ", cep=" + cep +
                '}';
    }
}
