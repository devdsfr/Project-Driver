# Project-Dryver
Cadastrar Veículo de acordo com a tabela fipe


## Tecnologias usadas
- Java 17
- Swagger
- Docker-Compose
- Junit4
- SpringBoot
- Maven
- MySql


## Teste

Executar no terminal do docker-Compose
```sh
docker-compose up -d  
```
Para testar sua aplicação Spring Boot, você pode usar a ferramenta Postman ou outra ferramenta similar para enviar requisições HTTP aos endpoints da sua API. Siga os passos abaixo:

Inicie a aplicação Spring Boot:

No seu IDE, execute a classe principal que contém o método main (geralmente, a classe que possui a anotação @SpringBootApplication).
Ou, se você já gerou o arquivo JAR da aplicação, execute o comando java -jar nome_do_arquivo.jar no terminal.
Abra o Postman ou outra ferramenta de teste de API.

Configure e envie as requisições HTTP para testar os diferentes endpoints da sua aplicação. Por exemplo:

Para testar o cadastro de veículo:

- Método: POST
> URL: http://localhost:8080/api/veiculos
> Headers: Content-Type: application/json

>Body:
```sh
{
  "placa": "XYZ-1234",
  "marca": {
    "id": 21
  },
  "modelo": {
    "id": 7027
  },
  "preco_anuncio": 1234.56,
  "ano": 2020
}
```
Para testar a busca de veículo pela placa:

- Método: GET
>URL: http://localhost:8080/api/veiculos/placa/XYZ-1234
>>Para testar a busca de veículos por marca com paginação:

- Método: GET
>URL: http://localhost:8080/api/veiculos/marca/21?page=0&size=10
