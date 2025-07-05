# 📚 Grade Submission API

A RESTful API built with Spring Boot that allows authenticated users to manage students, courses, and grades. It supports JWT-based authentication and is documented using Swagger/OpenAPI.

---

## 📑 Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
- [Authentication (JWT)](#authentication-jwt)
- [Example Usage](#example-usage)
- [Running Tests](#running-tests)
- [API Documentation (Swagger)](#api-documentation-swagger)
- [Contributing](#contributing)
- [Author](#author)

---

## 🚀 Features

- Register and authenticate users with JWT
- Create and retrieve students, courses, and grades
- Swagger UI for interactive API testing
- Unit tested with JUnit and Mockito

---

## 🛠️ Tech Stack

- Java 8+
- Spring Boot
- Spring Security (JWT)
- Maven
- JUnit + Mockito
- Swagger/OpenAPI

---

## 📦 Getting Started

### Prerequisites
- Java 8 or higher  
- Maven

### Build and Run

```bash
mvn clean install
java -jar target/grade-submission-api-0.0.1-SNAPSHOT.jar
