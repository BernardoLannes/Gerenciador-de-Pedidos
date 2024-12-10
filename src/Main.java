package src;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String fileName = "pedidos.json";
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // Registra o adaptador
                .setPrettyPrinting()
                .create();

        // Criando uma lista de pedidos
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(new Pedido(1, 500.0,
                new Cliente("Fulano", "9999", new Endereco("A", "nao informado", "nao informado", 0, 0))));
        pedidos.add(new Pedido(2, 500.0,
                new Cliente("Sicrano", "8888", new Endereco("B", "nao informado", "nao informado", 0, 0)),
                LocalDate.now()));

        // Gravando no arquivo
        try (Writer writer = new FileWriter(fileName)) {
            gson.toJson(pedidos, writer);
            System.out.println("Dados gravados com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Lendo do arquivo
        try (Reader reader = new FileReader(fileName)) {
            Type listType = new TypeToken<List<Pedido>>() {
            }.getType();
            List<Pedido> pedidosRecuperados = gson.fromJson(reader, listType);
            System.out.println("Dados recuperados:");
            pedidosRecuperados.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
