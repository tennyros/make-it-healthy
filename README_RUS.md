# Make it Healthy

![CI Status](https://github.com/tennyros/make-it-healthy/workflows/CI%20Pipeline%20with%20Maven/badge.svg)
![Coverage](https://github.com/tennyros/make-it-healthy/raw/coverage-badge/.github/badges/jacoco.svg)
![Java 17](https://img.shields.io/badge/Java-17-blue)
![Spring Boot 3.4.4](https://img.shields.io/badge/Spring_Boot-3.4.4-brightgreen)

**Make It Healthy** — это REST API сервис для отслеживания ежедневного потребления калорий и диеты пользователя.

## Стек технологий

| Компонент                       | Версия  | Назначение                               |
|---------------------------------|---------|------------------------------------------|
| Spring Boot                     | 3.4.4   | Бэкэнд фреймворк                         |
| Hibernate ORM (Spring Data JPA) | 6.6.11  | ORM фреймворк для работы с данными Java  |
| Maven (обертка)                 | 3.9.9   | Инструмент для сборки проекта            |
| PostgreSQL                      | 15+     | Система управления базами данных         |
| Liquibase                       | 4.31.1  | Миграции базы данных                     |
| MapStruct                       | 1.6.3   | Маппинг объектов (DTO/Entity)            |
| JUnit 5                         | 5.11.4  | Фреймворк для юнит-тестов                |
| Mockito                         | 5.14.2  | Фреймворк для мокирования в юнит-тестах  |
| JaCoCo                          | 0.8.13  | Отчеты по покрытию тестами               |
| Springdoc OpenAPI               | 2.8.6   | Документация API (Swagger UI для Spring) |

## Структура проекта

```text
Структура исходного кода (ветка dev):
src/
├── main/
│   ├── java/
│   │   └── com/github/tennyros/makeithealthy/
│   │       ├── config/      # Конфигурации
│   │       ├── dto/         # Объекты передачи данных
│   │       │   ├── request/     # Запросы DTO
│   │       │   └── response/    # Ответы DTO
│   │       │       └── reporting/    # Отчеты DTO
│   │       ├── entity/      # Модели данных
│   │       ├── exception/   # Собственные исключения
│   │       ├── http/        # REST API
│   │       │   ├── advice/     # Обработчики исключений
│   │       │   └── rest/       # REST контроллеры
│   │       ├── mapper/      # Мапперы MapStruct
│   │       ├── repository/  # Объекты доступа к данным
│   │       └── service/     # Бизнес-логика
│   └── resources/
│       ├── db/changelog/    # Миграции Liquibase
│       ├── application.yml  # Основная конфигурация
│       ├── application-dev.yml   # Конфигурация для разработки
│       └── application-prod.yml  # Конфигурация для продакшн
├── test/
│   ├── java/
│   │   └── com/github/tennyros/makeithealthy/
│   │       └── unit/        # Юнит-тесты
│   │           ├── controller/   # Юнит-тесты контроллер слоя
│   │           └── service/      # Юнит-тесты бизнес слоя
│   └── resources/
│       └── application-test.yml  # Конфигурация для тестирования
├── postman/  # json Postman API тест-коллекция
pom.xml

Собранные артефакты:
target/
├── generated-sources/
│   ├── annotations/
│   │   └── com.github.tennyros.makeithealthy.mapper/  # Автогенерируемые классы MapStruct
├── reports-report/  # Отчеты JaCoCo о покрытии
```

## Быстрый старт

### Требования

1. **Java 17+**
2. **PostgreSQL 15+** (локальная или удаленная база данных, или экземпляр в Docker)
3. **Maven 3.9.9** (обертка уже включена)

### Настройка

**1. Клонируйте репозиторий:**

```bash
git clone https://github.com/tennyros/make-it-healthy.git
cd make-it-healthy
```

**2. Скопируйте файл .env и при необходимости измените учетные данные:**

```bash
cp .env.example .env
```

**3. Запустите PostgreSQL через Docker (необязательно, если не используете свою базу данных):**

```bash
# Скопируйте пример конфигурации (если еще не настроено)
cp docker-compose.example.yml docker-compose.yml  

# Запустите PostgreSQL в Docker
docker-compose up -d

Если используете docker-compose, PostgreSQL будет доступен по адресу 
localhost:5433 (по умолчанию: postgres/root).

Если у вас уже есть PostgreSQL, убедитесь, что конфигурации 
application.yml/.env соответствуют вашим настройкам БД.
```

**4. Сборка приложения:**

```bash
./mvnw clean install -Dspring.profiles.active=dev
```

**5. Запуск приложения:**

```bash
# Запуск через терминал:
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Запуск через IntelliJ IDEA (Shift + F10):
Установите профиль dev в Active profiles в настройках конфигурации основного класса

Порт приложения — 8008
```

**После этого API будет доступно по адресу:**

```url
http://localhost:8008/swagger-ui.html
```

## Тестирование

**Типы тестов:**

```text
unit/ - Юнит-тесты
```

**Запуск тестов:**

```bash
# Все тесты
./mvnw test -Dspring.profiles.active=test

# Запуск конкретного теста
./mvnw test -Dspring.profiles.active=test -Dtest=YourTestName
```

## Тестирование API с помощью Postman

Вы можете протестировать и изучить API, используя прилагаемую коллекцию Postman.

### Прилагаемый файл

- **make-it-healthy-app.postman_collection.json** – запросы API для пользователей, приёмов пищи, блюд и отчётности.

### Как использовать

1. Установите Postman – [Скачать здесь](https://www.postman.com/downloads/).
2. Откройте Postman → `File > Import` → выберите файл `make-it-healthy-app.postman_collection.json`.
3. В любом запросе нажмите на переменную `{{url}}` и задайте базовый URL работающего API (например, `http://localhost:8008`).
4. Нажмите **Send**, чтобы выполнить запрос и посмотреть ответ.

> ℹ️ Коллекция уже содержит переменную `{{url}}`, указывающую на `http://localhost:8008`. При необходимости её можно изменить.

## CI Pipeline

```text
Проект настроен с CI для автоматической сборки и тестирования 
при пулл-реквестах с использованием GitHub Actions.
```
