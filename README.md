# 📝 Task API

API REST para gerenciamento de tarefas desenvolvida em **Java com Spring Boot**, utilizando autenticação JWT e arquitetura em camadas.

O projeto foi criado com o objetivo de praticar conceitos importantes de backend como **arquitetura limpa, autenticação, persistência de dados, separação de responsabilidades e desenvolvimento de APIs REST escaláveis**.

---

# 🚀 Tecnologias utilizadas

- **Java**
- **Spring Boot**
- **Spring Security**
- **JWT (JSON Web Token)**
- **JPA / Hibernate**
- **PostgreSQL**
- **Maven**

---

# 📦 Arquitetura do projeto

O projeto segue uma arquitetura em camadas para organizar responsabilidades e facilitar manutenção.

```
Controller → Service → Repository → Database
```

### Controller
Responsável por:
- Receber requisições HTTP
- Validar dados da requisição
- Retornar respostas da API

### Service
Responsável por:
- Implementar regras de negócio
- Processar dados da aplicação

### Repository
Responsável por:
- Comunicação com o banco de dados
- Operações de persistência utilizando JPA

### DTOs
Responsáveis por:
- Definir os dados de entrada e saída da API
- Evitar exposição direta das entidades

### Entities
Responsáveis por:
- Representar as tabelas do banco de dados

---

# 📂 Estrutura do projeto

```
task-api
│
├── src
│   └── main
│       ├── java
│       │   └── dev.tavin.go_task
│       │       ├── controller
│       │       ├── service
│       │       ├     └──  token
│       │       ├── infra
│       │       ├    ├── repository
│       │       ├    ├── model
│       │       ├    ├── dto
│       │       ├    └── config
│       │       ├        └── doc
│       │       ├        └── security
│       └── resources
│           ├── application.yml
│
└── pom.xml
```

---

# 🔐 Autenticação

A API utiliza **JWT (JSON Web Token)** para autenticação.

Fluxo de autenticação:

1. Usuário realiza login
2. A API retorna um **Access Token**
3. O token deve ser enviado no header das requisições protegidas

```
Authorization: Bearer <token>
```

---

# 📌 Funcionalidades

- ✅ Registro de usuário
- ✅ Login de usuário
- ✅ Criação de tarefas
- ✅ Listagem de tarefas
- ✅ Buscar tarefa por ID
- ✅ Atualização de tarefas
- ✅ Remoção de tarefas
- 🔒 Autenticação com JWT

---

# 📡 Endpoints da API

## Auth

### Registrar usuário

```
POST /auth/register
```

Body exemplo:

```json
{
  "name": "Otavio",
  "email": "otavio@email.com",
  "password": "123456"
}
```

---

### Login

```
POST /auth/login
```

Body exemplo:

```json
{
  "email": "otavio@email.com",
  "password": "123456"
}
```

Resposta:

```json
{
  "accessToken": "jwt_token"
}
```

---

## Tasks

### Criar tarefa

```
POST /tasks
```

Body:

```json
{
  "title": "Estudar Spring Boot",
  "description": "Praticar desenvolvimento de API"
}
```

---

### Listar tarefas

```
GET /tasks
```

---

### Buscar tarefa por ID

```
GET /tasks/{id}
```

---

### Atualizar tarefa

```
PUT /tasks/{id}
```

Body:

```json
{
  "title": "Estudar Spring Security",
  "description": "Aprender autenticação com JWT"
}
```

---

### Deletar tarefa

```
DELETE /tasks/{id}
```

---

# ⚙️ Como rodar o projeto

## 1️⃣ Clonar repositório

```bash
git clone https://github.com/0tavioPascoal/go-task-api.git
```

---

## 2️⃣ Entrar na pasta do projeto

```bash
cd go-task-api
```

---

## 3️⃣ Instalar dependências

```bash
mvn clean install
```

---

## 4️⃣ Configurar banco de dados

Edite o arquivo:

```
src/main/resources/application.properties
```

Exemplo de configuração:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/taskdb
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=your_secret_key
```

---

## 5️⃣ Rodar aplicação

```bash
mvn spring-boot:run
```

Ou rodar pela sua IDE.

A API estará disponível em:

```
http://localhost:8080
```

---

# 🎯 Objetivo do projeto

Este projeto foi desenvolvido como forma de estudo e prática de:

- Desenvolvimento de **APIs REST com Spring Boot**
- **Arquitetura em camadas**
- **Autenticação com JWT**
- **Boas práticas de backend**

---

# 👨‍💻 Autor

**Otavio Pascoal**

GitHub:  
https://github.com/0tavioPascoal
