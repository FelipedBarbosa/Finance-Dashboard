# Finance Dashboard

Aplicação backend desenvolvida com Java e Spring Boot para gerenciamento de ativos financeiros, atualização automática de preços e métricas de portfólio.

## Sobre o Projeto

O Finance Dashboard é uma aplicação REST desenvolvida para simular uma plataforma de monitoramento financeiro. O sistema permite cadastrar ativos, consultar métricas financeiras e acompanhar atualizações de preços em tempo real.

O projeto foi construído com foco em boas práticas de desenvolvimento backend utilizando Spring Boot, arquitetura em camadas, testes automatizados e integração com APIs externas.

## Funcionalidades

* Cadastro e listagem de ativos financeiros
* Atualização automática de preços
* Integração com a API CoinGecko
* Histórico de preços
* Métricas de portfólio
* Endpoints RESTful
* Atualizações em tempo real com WebSocket
* Scheduler para atualização periódica de dados
* Testes unitários e de integração
* Monitoramento com Spring Boot Actuator

## Tecnologias Utilizadas

### Backend

* Java 17
* Spring Boot 3.3.0
* Spring Web
* Spring Data JPA
* Spring WebFlux
* Spring WebSocket
* Spring Boot Actuator
* Maven
* Lombok

### Banco de Dados

* PostgreSQL
* H2 Database (testes)

### Ferramentas

* Docker
* Docker Compose
* JUnit 5
* Mockito

## Estrutura do Projeto

```text
src
 ├── main
 │    ├── java
 │    │    └── me/felipebarbosa/finance
 │    │         ├── client
 │    │         ├── controller
 │    │         ├── dto
 │    │         ├── model
 │    │         ├── repository
 │    │         ├── scheduler
 │    │         ├── service
 │    │         └── websocket
 │    └── resources
 └── test
```

## Como Executar o Projeto

### Pré-requisitos

* Java 17
* Docker
* Docker Compose

### 1. Clonar o repositório

```bash
git clone https://github.com/FelipedBarbosa/Finance-Dashboard.git
cd finance-dashboard
```

### 2. Subir o banco de dados

```bash
docker-compose up -d
```

### 3. Executar a aplicação

No Linux/macOS:

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

A aplicação estará disponível em:

```text
http://localhost:8080
```

## Endpoints Principais

### Criar ativo

```http
POST /assets
```

Exemplo de payload:

```json
{
  "symbol": "bitcoin",
  "name": "Bitcoin",
  "currentPrice": 30000
}
```

### Listar ativos

```http
GET /assets
```

### Buscar métricas de um ativo

```http
GET /assets/{id}/metrics
```

### Histórico do portfólio

```http
GET /assets/chart/portfolio-last-30-days
```

## WebSocket

A aplicação disponibiliza comunicação em tempo real para atualização automática de preços dos ativos.

## Testes

Executar os testes automatizados:

```bash
./mvnw test
```

## Monitoramento

O projeto utiliza Spring Boot Actuator.

Endpoint:

```http
GET /actuator/health
```

## Melhorias Futuras

* Autenticação com JWT
* Dashboard frontend em React
* Deploy em cloud
* Cache com Redis
* Documentação com Swagger/OpenAPI
* Integração com mais provedores financeiros

## Autor

Desenvolvido por Felipe Barbosa.
