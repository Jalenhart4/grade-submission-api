# 📚 Grade Submission API

A RESTful API built with Spring Boot that allows authenticated users to manage students, courses, and grades. It supports JWT-based authentication and is documented using Swagger/OpenAPI.

---

## 📑 Contents
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Getting Started](#-getting-started)
- [Authentication (JWT)](#-authentication-jwt)
- [Example Usage](#-example-usage)
- [Running Tests](#-running-tests)
- [API Documentation (Swagger)](#-api-documentation-swagger)
- [Note](#-note)
- [Author](#-author)

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
```

---

## 🔐 Authentication (JWT)

This API uses JSON Web Tokens (JWT) for authentication.

### Register a New User

```bash
curl -X POST -H "Content-Type: application/json" \
-d '{"username": "yourUsername", "password": "yourPassword"}' \
http://localhost:8080/register
```

### Authenticate and Get a JWT Token

```bash
curl -X POST -H "Content-Type: application/json" \
-d '{"username": "yourUsername", "password": "yourPassword"}' \
http://localhost:8080/authenticate
```

This will return a JWT token in the response body. You will use this token to authorize your requests.

### Use JWT Token in Protected Requests

```bash
curl -H "Authorization: Bearer <JWT_TOKEN>" \
http://localhost:8080/student/1
```

Replace `<JWT_TOKEN>` with the actual token received from the authentication step.

---

## 📘 Example Usage

Below are examples of how to interact with the API endpoints using `curl`. All requests to protected endpoints require a valid JWT token in the `Authorization` header.

### ➕ Add a Student

```bash
curl -X POST -H "Content-Type: application/json" \
-H "Authorization: Bearer <JWT_TOKEN>" \
-d '{"name": "John Doe", "birthDate": "03-20-2005"}' \
http://localhost:8080/student
```

### ➕ Add a Course

```bash
curl -X POST -H "Content-Type: application/json" \
-H "Authorization: Bearer <JWT_TOKEN>" \
-d '{"subject": "Intro to CS", "code": "COMSCI101", "description": "Introductory computer science course."}' \
http://localhost:8080/courses
```

### 📝 Submit a Grade

```bash
curl -X POST -H "Content-Type: application/json" \
-H "Authorization: Bearer <JWT_TOKEN>" \
-d '{"score": "A+"}' \
http://localhost:8080/grade/1/course/1
```

Replace `1` with the appropriate `studentId` and `courseId`.

### 📄 Get Grades for a Student

```bash
curl -H "Authorization: Bearer <JWT_TOKEN>" \
http://localhost:8080/grade/student/1
```

Replace `1` with the student's ID.

---

## ✅ Running Tests

This project includes unit tests written using JUnit and Mockito.

To run the tests, use the following command:

```bash
mvn test
```

This will execute all unit tests and display the results in your terminal.

---

## 📚 API Documentation (Swagger)

This API is documented using the Swagger OpenAPI specification.

Once the application is running, you can access the Swagger UI in your browser at:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

The Swagger UI provides an interactive interface to explore available endpoints, view request/response formats, and test the API directly from the browser.

---

## 📌 Note

This project was developed as part of a hands-on Udemy course to gain practical experience with Spring Boot, REST APIs, and JWT authentication.

---

## 👤 Author

**Jalen Terrell-Hart**  
[GitHub Profile](https://github.com/Jalenhart4)
