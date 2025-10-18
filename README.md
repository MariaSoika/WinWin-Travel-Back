# 🧩 BE Test Task — Two Microservices Project

Цей проєкт складається з двох Spring Boot сервісів:
- **auth-api** — сервіс авторизації та управління користувачами.
- **data-api** — внутрішній сервіс для обробки даних, доступний лише через `auth-api`.

Обидва сервіси використовують **PostgreSQL** і обмінюються даними через **HTTP** із захистом внутрішнім токеном.

---

## 🚀 Технології
- Java 21  
- Spring Boot 3.5  
- Spring Security + JWT  
- Spring Data JPA + PostgreSQL  
- Docker + Docker Compose  
- Lombok  

---

## ⚙️ Структура проєкту
```

project-root/
│
├── auth-api/
│   ├── src/main/java/com/api/auth/...
│   ├── src/main/resources/application.yml
│   └── pom.xml
│
├── data-api/
│   ├── src/main/java/com/api/data/...
│   ├── src/main/resources/application.yml
│   └── pom.xml
│
└── docker-compose.yml

````

---

## 🐳 Запуск через Docker

### 1️⃣ Збірка проєкту
```bash
mvn clean package -DskipTests
````

### 2️⃣ Запуск контейнерів

```bash
docker compose up --build
```

### 3️⃣ Перевірка роботи

* **auth-api** доступний на: `http://localhost:8080`
* **data-api** доступний на: `http://localhost:8081`
* **PostgreSQL** доступна на порту `5432`

---

## 🔐 Конфігурація

### `auth-api/src/main/resources/application.yml`

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:postgresql://postgres:5432/mydatabase
    username: myuser
    password: secret
  jpa:
    hibernate:
      ddl-auto: update

data:
  api:
    url: http://data-api:8081/api/transform

internal:
  token: shared-secret
```

### `data-api/src/main/resources/application.yml`

```yaml
server:
  port: 8081

spring:
  datasource:
    url: jdbc:postgresql://postgres:5432/mydatabase
    username: myuser
    password: secret
  jpa:
    hibernate:
      ddl-auto: update

internal:
  token: shared-secret
```

---

## 🔗 Взаємодія між сервісами

1. **auth-api** приймає запит від клієнта:

   * `/auth/register`
   * `/auth/login`
   * `/process`

2. **auth-api** перевіряє JWT користувача.

3. Якщо все гаразд, `auth-api` надсилає запит на `data-api`:

   ```http
   POST http://data-api:8081/api/transform
   Headers:
     X-Internal-Token: shared-secret
   Body:
     { "text": "some data" }
   ```

4. **data-api** перевіряє заголовок `X-Internal-Token`.
   Якщо токен правильний — обробляє текст і повертає результат.

---

## 🧪 Приклади запитів у Postman

### 🔹 Реєстрація

```
POST http://localhost:8080/auth/register
Content-Type: application/json

{
  "email": "test@example.com",
  "password": "123456"
}
```

### 🔹 Логін

```
POST http://localhost:8080/auth/login
Content-Type: application/json

{
  "email": "test@example.com",
  "password": "123456"
}
```

→ Відповідь містить `token`

### 🔹 Обробка тексту

```
POST http://localhost:8080/process
Authorization: Bearer <your_jwt_token>
Content-Type: application/json

{
  "text": "Hello World!"
}
```

---

## 📄 Ліцензія

Проєкт створено як тестове завдання.
Використання дозволене лише для ознайомлення та демонстрації навичок.

---

✍️ **Автор:** Марія Сойка

```

---

Хочеш, я зроблю коротку **версію README** (на 15–20 рядків) — якщо це для відправки як тестове завдання?
```
