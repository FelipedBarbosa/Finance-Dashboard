# Finance Dashboard

> Monorepo — Full-stack application: React frontend + Spring Boot backend + PostgreSQL

[![CI](https://github.com/FelipedBarbosa/Finance-Dashboard/actions/workflows/ci.yml/badge.svg)](https://github.com/FelipedBarbosa/Finance-Dashboard/actions/workflows/ci.yml)

**Demo Online:** https://finance-dashboard-phi.vercel.app *(atualizar após deploy)*

---

## Estrutura do Repositório

```
finance-dashboard/
├── backend/          # Spring Boot 3 · Java 17 · PostgreSQL
├── frontend/         # React 19 · TanStack Router · Vite · Tailwind CSS
├── docker-compose.yml
├── README.md
└── .github/
    └── workflows/
        └── ci.yml
```

---

## Funcionalidades

- 📈 Dashboard de portfólio com gráfico dos últimos 30 dias
- 💹 Listagem e cadastro de ativos financeiros
- 🔴 Preços em tempo real via WebSocket
- 🌍 Integração com a API CoinGecko
- 📊 Métricas por ativo (market cap, volume 24h, high/low)
- 📋 Documentação interativa via Swagger/OpenAPI
- ⚙️ Monitoramento com Spring Boot Actuator

---

## Stack

| Camada | Tecnologia |
|---|---|
| Frontend | React 19, TanStack Router, React Query, Recharts, Tailwind CSS 4 |
| Backend | Spring Boot 3.3, Java 17, Spring Data JPA, WebSocket, WebFlux |
| Banco | PostgreSQL 15 |
| CI | GitHub Actions |
| Deploy Frontend | Vercel |
| Deploy Backend | Render / Railway |
| Containerização | Docker, Docker Compose |

---

## Como Executar

### Pré-requisitos

- Java 17
- Node 20
- Docker + Docker Compose

### Opção 1 — Docker Compose (tudo de uma vez)

```bash
git clone https://github.com/FelipedBarbosa/Finance-Dashboard.git
cd finance-dashboard

docker compose up --build
```

| Serviço | URL |
|---|---|
| Frontend | http://localhost:3000 |
| Backend API | http://localhost:8080 |
| Swagger UI | http://localhost:8080/swagger-ui.html |
| Health Check | http://localhost:8080/actuator/health |

---

### Opção 2 — Desenvolvimento local

**Backend:**

```bash
cd backend
./mvnw package -DskipTests
java -jar target/finance-dashboard-0.0.1-SNAPSHOT.jar
```

**Frontend:**

```bash
cd frontend
npm install --legacy-peer-deps
npm run dev
```

Acesse: http://localhost:3000

---

## Deploy

### Frontend → Vercel

1. Importe o repositório no [Vercel](https://vercel.com)
2. Configure **Root Directory** como `frontend`
3. Adicione as variáveis de ambiente:
   ```
   VITE_API_URL=https://seu-backend.onrender.com
   VITE_WS_URL=wss://seu-backend.onrender.com/ws/prices
   ```
4. Deploy automático a cada push na `main`

### Backend → Render

1. Crie um novo **Web Service** no [Render](https://render.com)
2. Selecione o repositório e configure **Root Directory** como `backend`
3. Render detecta automaticamente o `Dockerfile`
4. Adicione as variáveis de ambiente do PostgreSQL

---

## Endpoints da API

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/assets` | Lista todos os ativos |
| `POST` | `/assets` | Cadastra novo ativo |
| `GET` | `/assets/{id}/metrics` | Métricas de um ativo |
| `GET` | `/assets/chart/portfolio-last-30-days` | Histórico do portfólio |
| `WS` | `/ws/prices` | Stream de preços em tempo real |
| `GET` | `/actuator/health` | Status da aplicação |
| `GET` | `/swagger-ui.html` | Documentação interativa |

---

## Testes

```bash
cd backend
./mvnw test
```

---

## Autor

Desenvolvido por **Felipe Barbosa**
