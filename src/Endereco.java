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

    public String getRua() {
        return this.rua;
    }

    public String getBairro() {
        return this.bairro;
    }

    public String getUf() {
        return this.uf;
    }

    public int getNumero() {
        return this.numero;
    }

    public int getCep() {
        return this.cep;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setUf(String uf) {
        this.uf = uf;
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
