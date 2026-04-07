# 📚 Aluno Online - API REST (Spring Boot)

Este projeto é uma API REST desenvolvida com **Spring Boot** para gerenciamento de alunos.  
A aplicação implementa operações de **CRUD (Create, Read, Update, Delete)**.

---

## 🚀 Tecnologias utilizadas

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Maven
- PostgreSQL (ou H2)

---

## 📌 Funcionalidades

- ✅ Cadastrar aluno  
- ✅ Listar todos os alunos  
- ✅ Buscar aluno por ID  
- ✅ Atualizar aluno  
- ✅ Deletar aluno  

---

## 🧱 Estrutura do Projeto
src/
 └── main/
     ├── java/
     │    └── com.exemplo.alunoonline/
     │         ├── controller
     │         ├── service
     │         ├── repository
     │         └── model
     └── resources/
          └── application.properties


Criar aluno
POST /alunos

Listar alunos
GET /alunos

Buscar por ID
GET /alunos/{id}

Atualizar aluno
PUT /alunos/{id}

Deletar aluno
DELETE /alunos/{id}

Configuração do Banco (PostgreSQL)
spring.datasource.url=jdbc:postgresql://localhost:5432/aluno_db
spring.datasource.username=postgres
spring.datasource.password=senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
