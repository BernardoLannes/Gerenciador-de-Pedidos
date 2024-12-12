# Gerenciador de Pedidos

Este é um aplicativo Java para gerenciar pedidos de clientes, com funcionalidades como cadastro, busca e edição de pedidos. Os dados são armazenados em um arquivo JSON e podem ser carregados ou salvos automaticamente.

## Funcionalidades

- **Adicionar Pedido**: Cadastro de um novo pedido com informações do cliente e detalhes da entrega.
- **Mostrar Pedidos**: Exibição dos pedidos realizados, com detalhes como preço, cliente e datas.
- **Buscar Pedido**: Pesquisa de pedidos por número.
- **Buscar por Cliente**: Pesquisa de pedidos por nome do cliente.
- **Mostrar Clientes**: Exibe a lista de clientes cadastrados.
- **Salvar e Sair**: Salva os pedidos no arquivo JSON e fecha o aplicativo.

## Tecnologias

- **Java Swing**: Interface gráfica.
- **Gson**: Manipulação do arquivo JSON para salvar e carregar pedidos.

## Como Usar
1. Execute o programa
   - Linux:
       ```
       java -cp "lib/gson-2.11.0.jar:bin" src.PedidoApp
        ```
   - Windows:
       ```
       java -cp "lib/gson-2.11.0.jar;bin" src.PedidoApp
       ```
   
2. Utilize os botões para adicionar, buscar ou editar pedidos.
3. Ao finalizar, clique em "Salvar e Sair" para garantir que as alterações sejam salvas.

Caso não seja possível executar o programa, tente recompilá-lo:
    ```
    javac -cp "lib/gson-2.11.0.jar" -d bin src/*.java
    ```

## Dependências

- **Gson**: Biblioteca para manipulação de JSON.
