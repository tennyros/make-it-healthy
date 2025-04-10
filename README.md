# Make it Healthy

![Java 17](https://img.shields.io/badge/Java-17-blue)
![Spring Boot 3.4.4](https://img.shields.io/badge/Spring_Boot-3.4.4-brightgreen)

**Make It Healthy** is a REST API service for tracking daily calorie intake and user diet.

## Tech Stack

| Component                          | Version | Purpose                                             |
|------------------------------------|---------|-----------------------------------------------------|
| Spring Boot                        | 3.4.4   | Backend framework                                   |
| Hibernate ORM<br>(Spring Data JPA) | 6.6.11  | ORM framework for managing<br>Java persistence with |
| Maven (wrapper)                    | 3.9.9   | Project build tool                                  |
| PostgreSQL                         | 15+     | Database management system                          |
| Liquibase                          | 4.33.1  | Database migrations                                 |
| MapStruct                          | 1.6.3   | Object mapping (DTO/Entity)                         |
| JUnit 5                            | 5.11.4  | Test framework for unit tests                       |
| Mockito                            | 5.14.2  | Test framework for mocking in unit tests            |
| JaCoCo                             | 0.8.13  | Test coverage reports                               |
| Springdoc OpenAPI                  | 2.8.6   | API documentation (Swagger UI for Spring)           |

## Project Structure

```text
src/
├── main/
│   ├── java/
│   │   └── com/github/tennyros/makeithealthy
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
│   │   └── com/example/healthy/
│   │       └── unit/        # Unit tests
│   └── resources/
│       └── application-test.yml  # Test configuration
```

## Quick Start

### Prerequisites

1. **Java 17+**
2. **PostgreSQL 15+** (local, remote or docker container instance)
3. **Maven 3.9.9** (wrapper included)

### Setup

**1. Clone the repository:**

```bash
git clone https://github.com/yourusername/make-it-healthy.git
cd make-it-healthy
```

**2. Copy .env file:**

```bash
cp .env.example .env
```

**3. Run PostgreSQL via Docker (optional, if you don’t run own DB):**

```bash
# Copy the example config (if not customized yet)  
cp docker-compose.example.yml docker-compose.yml  

# Start PostgreSQL in Docker  
docker-compose up -d
```

**3. Run the application:**

```bash
./mvnw spring-boot:run
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
./mvnw test

# Run a specific test
./mvnw test -Dtest=YourTestName
```

## CI Pipeline

```text
The project is set up with CI to automatically build and test on pull requests using GitHub Actions.
```