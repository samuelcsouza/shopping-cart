# Shopping Cart

Uma implementação de um carrinho de compras utilizando Spring Boot.

## Instalação e Build

Para instalar as dependências e rodar a aplicação, utilize os comandos abaixo:

```bash
mvn clean install
mvn spring-boot:run
```

Por padrão a aplicação estará disponível no endereço http://localhost:8080/

## Endpoints

No diretório raiz do projeto está uma collection do Postman (v2.1) com todas as requisições disponíveis na API.
Os endpoints disponíveis estão listados abaixo:

- Produtos

    ```json
    {
        "code": Long,
        "description": String
    }
    ```
    - `POST /product` Cria um novo produto passando no corpo da requisição um json contendo **code** e **description**;
    - `GET /product` Lista todos os produtos;
    - `GET /product/{id}` Lista um determinado produto dado seu **code**;
    - `DELETE /product/{id}` Delete um determinado produto;
    - `PUT /product/{id}` Altera a descrição de um determinado produto, com um corpo da requisição contento o campo **description**;

- Items

    ```json
    {
        "unitPrice": float,
        "quantity": int,
        "product": {
            "code": Long
        }
    }
    ```
    - `POST /item` Cria um novo item, o body deve ter o formato do json acima;
    - `PUT /item/{id}` Atualiza o preço unitário do item, é necessário enviar somente o campo **unitPrice** no corpo da requisição;
    - `GET /item` Lista todos os items já cadastrados;
    - `GET /item/{id}` Exibe um item em específico;
    - `DELETE /item/{id}` Deleta um item em específico;

- Cart

    ```json
    {
        "clientId": String
    }
    ```
    - `POST /cart` Cria um novo carrinho dado um **clientId** ou retorna o carrinho se ele já existir;
    - `POST /cart/{clientId}` Adiciona itens no carrinho de um **clientId** passando no corpo da requisição o parâmetro **code** do produto desejado;
    - `GET /cart` Lista todos os carrinhos já cadastrados;
    - `DELETE /cart/{clientId}` Invalida um carrinho, removendo-o dos carrinhos cadastrados;
    - `GET /cart/average-ticket-amount` Retorna o valor do ticket médio de todos os carrinhos já cadastrados;

    