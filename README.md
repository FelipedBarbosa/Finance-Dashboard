# Finance Dashboard

Aplicação full-stack para gerenciamento de ativos financeiros, com autenticação segura, atualização automática de preços e métricas de portfólio por usuário.

## Sobre o Projeto

O Finance Dashboard é uma plataforma de monitoramento financeiro multiusuário. Cada usuário possui seu próprio portfólio isolado, podendo cadastrar ativos, consultar métricas financeiras e acompanhar atualizações de preços em tempo real.

O projeto foi construído com foco em boas práticas de desenvolvimento backend e frontend, utilizando Spring Boot, autenticação stateless com JWT, arquitetura em camadas, migrações versionadas com Flyway e uma interface React moderna.

## Funcionalidades

* Cadastro e autenticação de usuários (registro/login) com JWT
* Portfólio isolado por usuário (cada ativo pertence a um único dono)
* Cadastro e listagem de ativos financeiros
* Atualização automática de preços
* Integração com a API CoinGecko
* Histórico de preços
* Métricas de portfólio
* Endpoints RESTful protegidos
* Atualizações em tempo real com WebSocket
* Scheduler para atualização periódica de dados
* Testes unitários e de integração
* Monitoramento com Spring Boot Actuator
* Interface web em React com tema dark premium

## Tecnologias Utilizadas

### Backend

* Java 17
* Spring Boot 3.3.0
* Spring Web
* Spring Security + JWT (jjwt)
* Spring Data JPA
* Spring WebFlux
* Spring WebSocket
* Spring Boot Actuator
* Flyway (migrações de banco)
* Maven
* Lombok

### Frontend

* React + TypeScript (Vite)
* React Router
* Axios (com interceptor JWT)
* Recharts (gráficos de portfólio)

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
backend
 ├── src
 │    ├── main
 │    │    ├── java
 │    │    │    └── me/felipebarbosa/finance
 │    │    │         ├── client
 │    │    │         ├── config        # SecurityConfig, CorsConfig
 │    │    │         ├── controller    # AuthController, AssetController
 │    │    │         ├── dto
 │    │    │         ├── model         # User, Asset
 │    │    │         ├── repository
 │    │    │         ├── scheduler
 │    │    │         ├── security      # JwtUtils, JwtFilter, UserDetailsServiceImpl
 │    │    │         ├── service
 │    │    │         └── websocket
 │    │    └── resources
 │    │         └── db/migration       # scripts Flyway (V1...Vn)
 │    └── test
frontend
 ├── src
 │    ├── components
 │    ├── lib                          # api.ts, authService.ts
 │    ├── hooks
 │    ├── routes
 │    └── app.tsx
```

## Como Executar o Projeto

### Pré-requisitos

* Java 17
* Node.js (para o frontend)
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

### 3. Executar o backend

No Linux/macOS:

```bash
./mvnw -f backend/pom.xml spring-boot:run
```

No Windows:

```bash
mvnw.cmd -f backend/pom.xml spring-boot:run
```

A API estará disponível em:

```text
http://localhost:8080
```

As migrações Flyway são aplicadas automaticamente na inicialização.

### 4. Executar o frontend

```bash
cd frontend
npm install
npm run dev
```

A interface estará disponível em:

```text
http://localhost:5173
```

## Autenticação

A API utiliza autenticação stateless via JWT.

### Registrar usuário

```http
POST /auth/register
```

```json
{
  "username": "felipe",
  "email": "felipe@example.com",
  "password": "SenhaForte123456"
}
```

### Login

```http
POST /auth/login
```

```json
{
  "username": "felipe",
  "password": "SenhaForte123456"
}
```

Resposta:

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIs...",
  "tokenType": "Bearer"
}
```

Todas as requisições aos endpoints de ativos devem incluir o header:

```text
Authorization: Bearer <accessToken>
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

### Listar ativos (do usuário autenticado)

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

Executar os testes automatizados do backend:

```bash
./mvnw -f backend/pom.xml test
```

## Monitoramento

O projeto utiliza Spring Boot Actuator.

Endpoint:

```http
GET /actuator/health
```

## Melhorias Futuras

* Refresh token automático no frontend
* Deploy em cloud
* Cache com Redis
* Documentação com Swagger/OpenAPI
* Integração com mais provedores financeiros
* Testes end-to-end com Cypress

## Autor

Desenvolvido por Felipe Barbosa.
