# Make it Healthy

![CI Status](https://github.com/tennyros/make-it-healthy/workflows/CI%20Pipeline%20with%20Maven/badge.svg)
![Coverage](https://github.com/tennyros/make-it-healthy/raw/coverage-badge/.github/badges/jacoco.svg)
![Java 17](https://img.shields.io/badge/Java-17-blue)
![Spring Boot 3.4.4](https://img.shields.io/badge/Spring_Boot-3.4.4-brightgreen)

**Make It Healthy** is a REST API service for tracking daily calorie intake and user diet.

## Tech Stack

| Component                       | Version | Purpose                                          |
|---------------------------------|---------|--------------------------------------------------|
| Spring Boot                     | 3.4.4   | Backend framework                                |
| Hibernate ORM (Spring Data JPA) | 6.6.11  | ORM framework for managing Java persistence with |
| Maven (wrapper)                 | 3.9.9   | Project build tool                               |
| PostgreSQL                      | 15+     | Database management system                       |
| Liquibase                       | 4.31.1  | Database migrations                              |
| MapStruct                       | 1.6.3   | Object mapping (DTO/Entity)                      |
| JUnit 5                         | 5.11.4  | Test framework for unit tests                    |
| Mockito                         | 5.14.2  | Test framework for mocking in unit tests         |
| JaCoCo                          | 0.8.13  | Test coverage reports                            |
| Springdoc OpenAPI               | 2.8.6   | API documentation (Swagger UI for Spring)        |

## Project Structure

```text
Source code structure (dev branch):
src/
├── main/
│   ├── java/
│   │   └── com/github/tennyros/makeithealthy/
│   │       ├── config/      # Configurations
│   │       ├── dto/         # Data Transfer Objects
│   │       │   ├── request/     # Request DTOs
│   │       │   └── response/    # Response DTOs
│   │       │       └── reporting/    # Report DTOs
│   │       ├── entity/      # Data models
│   │       ├── exception/   # Own exceptions
│   │       ├── http/        # REST API
│   │       │   ├── advice/     # Exception Handlers
│   │       │   └── rest/       # REST Controllers
│   │       ├── mapper/      # MapStruct mappers
│   │       ├── repository/  # Data Access Objects
│   │       └── service/     # Business logic
│   └── resources/
│       ├── db/changelog/    # Liquibase migrations
│       ├── application.yml  # Main configuration
│       ├── application-dev.yml   # Development configuration
│       └── application-prod.yml  # Production configuration
├── test/
│   ├── java/
│   │   └── com/github/tennyros/makeithealthy/
│   │       └── unit/        # Unit tests
│   │           ├── controller/   # Controller layer unit tests
│   │           └── service/      # Service layer unit tests
│   └── resources/
│       └── application-test.yml  # Test configuration
pom.xml

Build artifacts:
target/
├── generated-sources/
│   ├── annotations/
│   │   └── com.github.tennyros.makeithealthy.mapper/  # Auto-generated MapStruct classes
├── reports-report/  # JaCoCo coverage reports
```

## Quick Start

### Prerequisites

1. **Java 17+**
2. **PostgreSQL 15+** (local, remote or docker container instance)
3. **Maven 3.9.9** (wrapper included)

### Setup

**1. Clone the repository:**

```bash
git clone https://github.com/tennyros/make-it-healthy.git
cd make-it-healthy
```

**2. Copy the .env file and change the credentials if necessary:**

```bash
cp .env.example .env
```

**3. Run PostgreSQL via Docker (optional, if you don’t run own DB):**

```bash
# Copy the example config (if not customized yet)  
cp docker-compose.example.yml docker-compose.yml  

# Start PostgreSQL in Docker  
docker-compose up -d

If using docker-compose, PostgreSQL will be available at 
localhost:5433 (default credentials: postgres/root).

If you already have PostgreSQL, ensure application.yml/.env matches your DB settings.
```

**4. Application build:**

```bash
./mvnw clean install -Dspring.profiles.active=dev
```

**5. Run the application:**

```bash
# Run via terminal:
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Run via Intellij Idea (Shift + F10):
Type dev in Active profiles Edit Configurations of main class to setup profile

Application port is 8008
```

**After this, the API will be available at:**

```url
http://localhost:8008/swagger-ui.html
```

## Testing

**Test types:**

```text
unit/ - Unit tests
```

**Running tests:**

```bash
# All tests
./mvnw test -Dspring.profiles.active=test

# Run a specific test
./mvnw test -Dspring.profiles.active=test -Dtest=YourTestName
```

## CI Pipeline

```text
The project is set up with CI to automatically build 
and test on pull requests using GitHub Actions.
```
