# Grade Submission API

This API is built using the Spring Framework and allows users to submit grades for students.

## Contents
- [Getting Started](#getting-started)
- [Authentication](#authentication)
- [Usage](#usage)
- [Tests](#tests)
- [Documentation](#documentation)
- [Contributing](#contributing)

## Getting Started

To get started, you'll need to have the following installed on your system:
- Java 8 or higher
- Maven

Once you have these installed, you can clone this repository and navigate to the project directory. From there, you can run the following command to build the project:

```
mvn clean install
```

This will create a JAR file in the `target` directory. You can then run the JAR file using the following command:

```
java -jar target/grade-submission-api-0.0.1-SNAPSHOT.jar
```

This will start the API on port 8080.

## Authentication

This API uses JSON Web Tokens (JWT) for authentication. To authenticate, you'll first need to register a new user by making a POST request to the `/register` endpoint with your desired username and password.

Here's an example of how to register a new user using `curl`:

```
curl -X POST -H “Content-Type: application/json” -d ‘{“username”: “myusername”, “password”: “mypassword”}’ http://localhost:8080/register
```

Once you have registered a new user, you can obtain a JWT token by making a POST request to the `/authenticate` endpoint with your username and password.

Here's an example of how to obtain a JWT token using `curl`:

```
curl -X POST -H “Content-Type: application/json” -d ‘{“username”: “myusername”, “password”: “mypassword”}’ http://localhost:8080/authenticate
```

This will return a response with the JWT token included in the `Authorization` header. You'll need to include this token in the `Authorization` header of all subsequent requests to the API.

Here's an example of how to include the JWT token in a request using `curl`:

```
curl -H “Authorization: Bearer <JWT_TOKEN>” http://localhost:8080/student/1
```

Make sure to replace `<JWT_TOKEN>` with your actual JWT token.

## Usage

The API has several endpoints for managing students, courses, and grades. Only a select portion of them will be covered in this section , the full documentation for the API and its enpoints can be found by going to the [Documentation](#documentation) section of this readme.

### Students

To retreive a student, you can make a POST request to the `/student/{id}` endpoint with the path variable id as the students id you wish to retrieve. Here's an example:

```
http://localhost:8080/student/1
```
You can use a tool like curl to make this request:
```
curl -H "Authorization: Bearer <JWT_TOKEN>" http://localhost:8080/student/1
```
Make sure to replace <JWT_TOKEN> with your actual JWT token. 

To create a new student, you can make a POST request to the `/student` endpoint with a JSON payload containing the student's information. Here's an example:

```json
{
    "name": "John Doe",
    "birthDate": "03-20-2005"
}
```
You can use a tool like curl to make this request:
```
curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer <JWT_TOKEN>" -d '{"name": "John Doe", "birthDate": "03-20-2005"}' http://localhost:8080/student/
```
Make sure to replace <JWT_TOKEN> with your actual JWT token. 


### Adding Courses
To create a new course, you can make a POST request to the `/courses` endpoint with a JSON payload containing the course information. Here’s an example:
```json
{
    "subject": "Introduction to Computer Science",
    "code": "COMSCI101",
    "description": "An introductory course on computer science concepts and programming."
}
```
You can use a tool like curl to make this request:
```
curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer <JWT_TOKEN>" -d '{"subject": "Introduction to Computer Science","code":"COMSCI101", "description": "An introductory course on computer science concepts and programming."}' http://localhost:8080/courses
```
Make sure to replace <JWT_TOKEN> with your actual JWT token.

### Submitting Grades
To submit a grade for a student in a course, you can make a POST request to the `/grade/{studentId}/course/{courseId}` endpoint with the path variables studentId and courseId to select the student and course of which you will create the grade. The request body is JSON payload containing the student’s grade. Here’s an example:
```json
{
    "score": "A+",
}
```
You can use a tool like curl to make this request:
```
curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer <JWT_TOKEN>" -d '{ "score": "A+"}' http://localhost:8080/grade/1/course/1
```
Make sure to replace <JWT_TOKEN> with your actual JWT token.

### Retrieving Grades
To retrieve a student’s grades, you can make a GET request to the `/grade/student/{studentId}` endpoint with the pathvariable studentId for the student of which you will retreive the list of grades. This will return a JSON object containing an array of the student’s grades for each course they are enrolled in.

You can use a tool like curl to make this request:
```
curl -H "Authorization: Bearer <JWT_TOKEN>" http://localhost:8080/grade/student/1
```
Make sure to replace <JWT_TOKEN> with your actual JWT token.

## Tests
This project includes unit tests written using JUnit and Mockito. To run the tests, you can use the following command:

```
mvn test
```

This will run all of the unit tests and display the results.

## Documentation
This API is documented using the Swagger OpenAPI specification. You can view the API documentation by navigating to the `/swagger-ui.html` endpoint in your web browser when the API is running. For example, if the API is running on localhost on port 8080, you can view the documentation by visiting `http://localhost:8080/swagger-ui.html`.

The Swagger UI provides an interactive interface for exploring the API’s endpoints and their parameters, as well as allowing you to test the API by making requests directly from the UI.

## Contributing
If you’d like to contribute to this project, feel free to open a pull request with your changes.
