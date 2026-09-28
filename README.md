# Biblioteca Online API

API REST desenvolvida como parte do Tech Challenge da Fase 2 da Pós-Graduação FIAP em Arquitetura e Desenvolvimento Java.

O sistema implementa o gerenciamento de uma biblioteca online, permitindo o cadastro de livros e usuários, controle de empréstimos e devoluções, reservas, consultas e geração de relatórios.

## Tecnologias utilizadas

- Java 21
- Spring Boot 4
- Spring Web
- Spring Data JPA
- Bean Validation
- PostgreSQL 17
- Maven
- Docker
- Docker Compose
- Swagger / OpenAPI
- Hibernate

## Arquitetura

A aplicação foi organizada em camadas, separando as responsabilidades entre:

- `controller`: exposição dos endpoints REST.
- `service`: regras de negócio e controle transacional.
- `repository`: acesso e consultas aos dados.
- `domain`: entidades persistidas no banco de dados.
- `dto`: objetos utilizados na entrada e saída da API.
- `exception`: tratamento centralizado das exceções da aplicação.
- `config`: configurações da aplicação e da documentação OpenAPI.

A API utiliza PostgreSQL para persistência e pode ser executada juntamente com o banco de dados por meio do Docker Compose.

## Funcionalidades

### Livros

- Cadastro de livros.
- Consulta individual.
- Listagem paginada.
- Alteração.
- Exclusão.
- Busca por título, autor e ISBN.
- Filtro por disponibilidade.
- Proteção contra exclusão de livros que possuam histórico de empréstimos ou reservas.

### Usuários

- Cadastro de usuários.
- Consulta individual.
- Listagem paginada.
- Alteração.
- Exclusão.
- Validação de e-mail.
- Proteção contra exclusão de usuários que possuam histórico de empréstimos ou reservas.

### Empréstimos

- Registro de empréstimos.
- Associação entre usuário e livro.
- Controle de disponibilidade do livro.
- Devolução.
- Histórico de empréstimos por usuário.
- Consulta de empréstimos ativos.
- Cálculo automático da previsão de devolução.

### Reservas

- Registro de reservas.
- Consulta de reservas.
- Histórico de reservas por usuário.
- Cancelamento de reservas.
- Prevenção de reservas ativas duplicadas.

### Relatórios

- Lista dos 20 livros mais emprestados.
- Lista dos empréstimos atualmente ativos com a respectiva previsão de devolução.

## Data e hora

A aplicação utiliza a API moderna de data e hora do Java por meio de `OffsetDateTime`.

O padrão adotado é:

- Formato: ISO-8601
- Timezone: UTC
- GMT: GMT+0

Exemplo:

```text
2026-10-07T22:30:10Z
```

Os prazos de empréstimo são calculados a partir da data do empréstimo, com previsão de devolução em 14 dias.

## Paginação

As consultas que podem retornar múltiplos registros utilizam paginação para evitar transferência e processamento desnecessários de grandes volumes de dados.

Exemplo:

```text
GET /api/v1/books?page=0&size=10
```

## Principais endpoints

### Livros

```text
POST   /api/v1/books
GET    /api/v1/books
GET    /api/v1/books/{id}
PUT    /api/v1/books/{id}
DELETE /api/v1/books/{id}
```

A listagem de livros aceita os filtros:

```text
title
author
isbn
available
```

### Usuários

```text
POST   /api/v1/users
GET    /api/v1/users
GET    /api/v1/users/{id}
PUT    /api/v1/users/{id}
DELETE /api/v1/users/{id}
```

### Empréstimos

```text
POST /api/v1/loans
GET  /api/v1/loans
GET  /api/v1/loans/{id}
GET  /api/v1/loans/user/{userId}
GET  /api/v1/loans/active
PUT  /api/v1/loans/{id}/return
```

### Reservas

```text
POST /api/v1/reservations
GET  /api/v1/reservations
GET  /api/v1/reservations/{id}
GET  /api/v1/reservations/user/{userId}
PUT  /api/v1/reservations/{id}/cancel
```

### Relatórios

```text
GET /api/v1/reports/most-borrowed-books
GET /api/v1/reports/active-loans
```

## Documentação da API

Com a aplicação em execução, a documentação interativa Swagger UI pode ser acessada em:

```text
http://localhost:8080/swagger-ui/index.html
```

A especificação OpenAPI também está disponível em:

```text
http://localhost:8080/v3/api-docs
```

## Executando com Docker

### Pré-requisitos

- Docker
- Docker Compose

Na raiz do projeto, execute:

```bash
docker compose up --build -d
```

O Docker Compose iniciará:

- API Spring Boot na porta `8080`.
- PostgreSQL na porta `5434` do host.

Após a inicialização:

```text
API:     http://localhost:8080
Swagger: http://localhost:8080/swagger-ui/index.html
```

Para visualizar os containers:

```bash
docker compose ps
```

Para encerrar o ambiente:

```bash
docker compose down
```

O banco utiliza volume Docker para persistência dos dados.

## Executando localmente

Com o PostgreSQL do Docker em execução:

```bash
docker compose up -d postgres
```

execute a aplicação:

```bash
./mvnw spring-boot:run
```

Por padrão, a aplicação local utiliza:

```text
jdbc:postgresql://localhost:5434/biblioteca
```

## Testes

Para executar os testes:

```bash
./mvnw clean test
```

## Configuração do banco de dados

A aplicação permite configurar a conexão por variáveis de ambiente:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
```

Quando executada pelo Docker Compose, a aplicação se comunica com o PostgreSQL pela rede interna do Docker.

## Escalabilidade

A aplicação e o banco de dados são executados em serviços separados, permitindo que sejam implantados independentemente em diferentes ambientes.

A configuração por variáveis de ambiente evita acoplamento da aplicação a um endereço específico de banco de dados.

Em um ambiente de produção, a arquitetura pode evoluir com:

- múltiplas instâncias da API;
- balanceador de carga;
- banco de dados gerenciado;
- orquestração de containers;
- cache para consultas frequentes;
- observabilidade e monitoramento;
- pipelines de integração e entrega contínua.

As operações de empréstimo, devolução e reserva foram mantidas individualmente e transacionais, pois modificam o estado de entidades relacionadas e exigem consistência. Operações em lote podem ser adicionadas futuramente para cenários administrativos em que seu uso seja apropriado.

## Repositório

Código-fonte:

https://github.com/GustavoOtsuka/biblioteca-online
