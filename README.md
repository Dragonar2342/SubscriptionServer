# Subscription Management Service

## Описание проекта

Сервис управления подписками предоставляет REST API для:
- Регистрации и управления пользователями
- Добавления и отслеживания подписок
- Анализа популярности сервисов подписок

## Функционал API

### Пользователи
- ✅ `POST /users` - Создание пользователя
- ✅ `GET /users/{id}` - Получение информации о пользователе
- ✅ `PUT /users/{id}` - Обновление данных пользователя
- ✅ `DELETE /users/{id}` - Удаление пользователя

### Подписки
- ✅ `POST /users/{userId}/subscriptions` - Добавление подписки
- ✅ `GET /users/{userId}/subscriptions` - Получение подписок пользователя
- ✅ `DELETE /users/{userId}/subscriptions/{subscriptionId}` - Удаление подписки
- ✅ `GET /api/subscriptions/top` - Топ-3 популярных сервисов

## Технологии

```
// Основной стек технологий
Java 17
Spring Boot 3.x
PostgreSQL 15+
Liquibase (миграции БД)
OpenAPI 3 (Swagger UI)
JUnit 5 + MockMvc (тестирование)
```

### Запуск через Docker

```bash
# Сборка и запуск контейнеров
docker-compose up -d --build
```

### Документация
```angular2html
http://localhost:8080/swagger-ui/index.html
```

### Примеры запросов для тестирования функционала

```http request
#Создание нового пользователя

http://localhost:8080/users
{
  "name": "Иван Иванов",
  "email": "ivan@example.com"
}
```

```http request
#Создание новой подписки

http://localhost:8080//users/1/subscriptions
{
  "serviceName": "Netflix",
  "startDate": "2023-01-01",
  "endDate": "2023-12-31"
}
```
