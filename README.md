# Food Delivery Application – Microservice Architecture
![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-Microservices-green)
![React](https://img.shields.io/badge/Frontend-React-blue)
![Docker](https://img.shields.io/badge/Container-Docker-blue)
![Kafka](https://img.shields.io/badge/Event%20Streaming-Kafka-black)
![CI/CD](https://img.shields.io/badge/CI%2FCD-GitHub%20Actions-purple)
![AWS](https://img.shields.io/badge/Cloud-AWS-yellow)
![SSLCommerz](https://img.shields.io/badge/Payment-SSLCommerz-red)


A production-style **Food Delivery** backend built with **Spring Boot microservices**, centralized configuration, service discovery, API gateway, messaging, and full Dockerized infrastructure.


## Key Feature
👤 User Features

- User registration and login

- JWT-based secure authentication

- Browse food items and restaurant menus

- Add/remove items in cart

- Place orders

- View order history

Contact support

🛒 Ordering Features

- Cart management

- Order creation

- Payment processing



💳 Payment Features

- SSLCommerz Payment Gateway integration

- Payment request and transaction handling

- Payment status processing in microservice flow
- Email notifications after payment completion

☁️ Cloud / Deployment Features

- Dockerized microservices

- Docker Compose orchestration

-  Build and push through GitHub Actions CI/CD pipeline 

## Architecture

- **Service Registry**: Eureka (`service-registry`)
- **Config Server**: Spring Cloud Config (`config-server`)
- **API Gateway**: Spring Cloud Gateway (`api-gateway`)
- **Business Services**:
  - `authentication-service` (MySQL, JWT)
  - `food-service` (MongoDB, AWS S3 integration)
  - `cart-service` (MongoDB)
  - `order-service` (MongoDB, Feign clients)
  - `payment-service` (MongoDB, Kafka)
  - `email-service` (Kafka consumer, MailDev SMTP)
  - `contact-service` (MongoDB)
- **Infrastructure**:
  - **MongoDB** (`mongo`)
  - **MySQL** (`mysql`)
  - **Kafka + Zookeeper**
  - **MailDev** (fake SMTP + web UI)
- All services packaged as Docker images and orchestrated via **`docker-compose`**.
## Flow of the Application
```text
React Frontend
↓
API Gateway
↓
---------------------------------------------------------
| Auth Service | Food Service | Cart Service | Order Service |
| Payment Service | Email Service | Contact Service         |
---------------------------------------------------------
      ↓
Supporting Infrastructure
- Eureka Service Registry
- Spring Cloud Config Server
- Kafka
- Feing Client
- MySQL / MongoDB
- Docker / Docker Compose
```
### Tech Stack

- **Language**: Java 21
- **Frameworks**: Spring Boot 4.x, Spring Cloud 2025.x
- **Patterns**: Config Server, Eureka, API Gateway, Circuit Breaker (Resilience4j), Feign
- **Data Stores**: MySQL 8, MongoDB 7
- **Messaging**: Kafka 7.5.x
- **Other**: AWS S3 SDK (for food images), JWT (JJWT), Lombok, Docker

### Repository Layout

- `service-registry/` – Eureka server
- `config-server/` – Spring Cloud Config server
- `api-gateway/` – API gateway & JWT verification
- `authentication-service/` – user auth & JWT issuing (MySQL)
- `food-service/` – food catalog + S3 file storage (MongoDB)
- `cart-service/` – user carts (MongoDB)
- `order-service/` – orders orchestration (MongoDB)
- `payment-service/` – payment processing + Kafka producer (MongoDB)
- `email-service/` – email notifications via Kafka + MailDev
- `contact-service/` – contact / feedback forms (MongoDB)
- `foodappfrontend/` – (optional) frontend app
- `docker-compose.yml` – full infra & services

### Prerequisites

- **Docker** and **Docker Compose** installed
- **Java 21+** (only needed if you want to run services locally outside Docker)
- Internet access for Maven to download dependencies (first build)

### Quick Start (Docker only)

From the project root:

```bash
./mvnw clean package    # Windows: .\mvnw.cmd clean package

docker compose build

docker compose up -d

docker compose ps
```

Services will be available at:

- **API Gateway**: `http://localhost:8080`
- **Config Server**: `http://localhost:8081`
- **Eureka Dashboard**: `http://localhost:8761`
- **MongoDB**: `localhost:27017`
- **MySQL**: `localhost:3307` (mapped to container `3306`)
- **MailDev UI**: `http://localhost:1080` (SMTP on `1025`)
- **Kafka**: `kafka:9092` (inside Docker network)

### Configuration & Environment

#### Config Server

Each service reads its configuration from the Config Server:

```yaml
spring:
  application:
    name: authentication-service
  config:
    import: optional:configserver:${CONFIG_SERVER:http://config-server:8081}
```

- **`CONFIG_SERVER`** is provided via Docker Compose:
  - `CONFIG_SERVER=http://config-server:8081`
- The Config Server itself reads from a **Git-backed config repo** (not included here) where you define central properties per service (e.g. `authentication-service.yml`, `food-service.yml`, etc.).

#### Database & External Services

Docker Compose passes essential environment variables into each service. Examples:

- **Authentication Service (MySQL)**:

```yaml
environment:
  CONFIG_SERVER: http://config-server:8081
  MYSQL_URL: jdbc:mysql://mysql:3306/Authentication_Database
  MYSQL_USER: root
  MYSQL_PASSWORD: root
  SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/Authentication_Database
  SPRING_DATASOURCE_USERNAME: root
  SPRING_DATASOURCE_PASSWORD: root
  SPRING_DATASOURCE_DRIVER_CLASS_NAME: com.mysql.cj.jdbc.Driver
  PORT: 8083
```

- **Food Service (MongoDB + AWS S3)**:

```yaml
environment:
  CONFIG_SERVER: http://config-server:8081
  MONGO_URI: mongodb://mongo:27017/food_service_database
  PORT: 8082
  AWS_ACCESS_KEY: your-real-access-key        # optional – overrides defaults
  AWS_SECRET_KEY: your-real-secret-key
  AWS_REGION: your-aws-region
```

Corresponding Spring configuration (defaults + env overrides):

```yaml
spring:
  application:
    name: food-service
  config:
    import: optional:configserver:${CONFIG_SERVER:http://config-server:8081}
  data:
    mongodb:
      uri: ${MONGO_URI:mongodb://mongo:27017/food_service_database}

aws:
  access:
    key: ${AWS_ACCESS_KEY:dummy-access-key}
  secret:
    key: ${AWS_SECRET_KEY:dummy-secret-key}
  region: ${AWS_REGION:us-east-1}
```

#### JWT Configuration (API Gateway)

The API Gateway validates JWT tokens and needs a shared secret:

```yaml
spring:
  application:
    name: api-gateway
  config:
    import: optional:configserver:${CONFIG_SERVER:http://config-server:8081}

jwt:
  secret:
    key: ${JWT_SECRET_KEY:498502c0fe7d53807379353f7cb75de227f635981762319b39a2aab6cf92a995}
```

Set **`JWT_SECRET_KEY`** in your environment for production.

### Key Endpoints

All external clients should call the **API Gateway**:

- **Base URL**: `http://localhost:8080`

Examples (paths may be adjusted by Config Server / route definitions):

- **Food Service**
  - `GET  /api/foods` – list all foods
  - `GET  /api/foods/{id}` – get food by id
  - `POST /api/foods` – create food (multipart: JSON + image file)
- **Authentication**
  - `POST /auth/register` – user registration
  - `POST /auth/login` – authenticate & receive JWT
- **Cart / Order / Payment / Email**
  - `cart-service`, `order-service`, `payment-service`, and `email-service` APIs are typically exposed via `/api/cart/...`, `/api/orders/...`, `/api/payment/...`, etc., behind the gateway.

(See each microservice’s controller package for the full list of endpoints.)

### Running Only a Subset (for Development)

You can start only the core infra + a couple of services to speed up development:

```bash
docker compose up -d service-registry config-server api-gateway authentication-service food-service
```



### Troubleshooting

- **Ports unreachable (`ECONNREFUSED`)**:
  - Check `docker compose ps` – ensure `api-gateway` is `Up` and mapped to `0.0.0.0:8080`.
- **Config Server connection refused**:
  - Ensure `config-server` is `Up` before other services.
- **`PlaceholderResolutionException`** (e.g. `jwt.secret.key`, `aws.access.key`):
  - Ensure the property is defined in:
    - Config Server config repo **or**
    - `application.yaml` with env-based defaults **or**
    - Environment variables in `docker-compose.yml`.

### License

This project is provided as-is for learning and demonstration purposes.  

