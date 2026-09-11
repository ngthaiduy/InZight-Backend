# InZight Backend

Spring Boot backend for a mobile personal-finance and social application.

## Overview

InZight combines income/expense tracking, wallets, budgets, social posts, real-time chat, premium payments, and financial planning calculators. The mobile client is not included in this repository.

## Features

- User authentication with Spring Security and JWT.
- Wallets, transactions, budgets, categories, and financial statistics.
- Posts, comments, likes, sharing, friendships, and chat history.
- Real-time chat and friendship notifications over WebSocket/STOMP.
- Email/password change verification using expiring Redis OTPs.
- PayOS checkout and webhook processing for premium subscriptions.
- Savings-goal, retirement, and what-if planning calculations.
- Gemini API integration for AI chat, implemented elsewhere in the team project.


## Technology stack

| Area | Technologies |
|---|---|
| Runtime | Java 17, Spring Boot 3.4.5, Maven |
| API and persistence | Spring MVC, Spring Data JPA/Hibernate, MySQL |
| Authentication | Spring Security, JWT |
| Messaging | WebSocket/STOMP |
| Verification | Redis, Spring Mail |
| Integrations | PayOS, Gemini API |
| Mapping and documentation | MapStruct, Lombok, Springdoc OpenAPI |
| Development | Docker Compose, Git, Spring Boot Test dependencies |

## Structure

```text
InZight/
  pom.xml
  docker-compose.yaml
  src/main/java/org/inzight/
    controller/    REST and messaging endpoints
    service/       Finance, social, payment, and user services
    entity/        Persistence models
    repository/    Spring Data repositories
    dto/           Request and response models
    mapper/        DTO mapping
    security/      JWT authentication utilities and filters
    config/        Security, messaging, and integration configuration
  src/main/resources/application.yaml
```

## Local setup

1. Install JDK 17, Maven, and optionally Docker for local MySQL/Redis.
2. Work from the application directory:

```powershell
cd InZight
```

3. Provision MySQL and Redis. The included Compose file exposes MySQL on port 3307 and Redis on port 6379; set `SPRING_DATASOURCE_PASSWORD` in your process environment before starting Compose. SQL account/transaction fixtures are excluded from this repository.
4. Set process environment variables: `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`, `JWT_SECRET`, `SPRING_MAIL_USERNAME`, `SPRING_MAIL_PASSWORD`, `PAYOS_CLIENT_ID`, `PAYOS_API_KEY`, `PAYOS_CHECKSUM_KEY`, and `GEMINI_API_KEY`. Redis accepts `SPRING_DATA_REDIS_HOST`, `SPRING_DATA_REDIS_PORT`, and `SPRING_DATA_REDIS_PASSWORD`. `JWT_SECRET` must contain at least 32 bytes for HS256. Use valid integration credentials for integration testing.
5. Build and run:

```powershell
mvn clean verify
mvn spring-boot:run
```

Default API: `http://localhost:8080`.
Swagger UI: `http://localhost:8080/swagger-ui.html`.
OpenAPI JSON: `http://localhost:8080/v3/api-docs`.

Environment variables must be available to the Maven/Java process; a `.env` file is not automatically loaded by this application. Hibernate currently uses `ddl-auto: update`, while SQL initialization is disabled. Inspect schema and seed assumptions before first use.

## Integration notes and limitations

- PayOS webhooks require a reachable callback URL. The optional `NgrokRunner` is disabled by default. Set `ENABLE_NGROK=true`, `NGROK_PATH`, and `NGROK_DOMAIN` only when you explicitly want to start your own tunnel.
- AI behavior depends on a configured Gemini model, credentials, and a valid bot account. The existing code uses a fixed bot ID and may require adjustment for a new database.
- Financial planning history currently uses in-memory storage and is not durable across restarts. The calculations are application features, not validated financial forecasts.
- Review authorization and payment callback idempotency before production deployment.
- No production readiness, uptime, or measured performance claim is made.
