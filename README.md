# Spring Boot Student Management System

A beginner-friendly REST API for managing students, built with Spring Boot, Spring Data JPA, and H2 database.

## Technologies Used

* Java 21
* Spring Boot 3.3
* Spring Web (REST APIs)
* Spring Data JPA
* H2 Database (In-Memory)
* Lombok
* Spring Boot Validation
* JUnit 5 & Mockito
* Docker
* GitHub Actions CI/CD

## Project Structure

* `controller`: Handles HTTP requests (REST endpoints).
* `service`: Contains business logic.
* `repository`: Interacts with the database (extends JpaRepository).
* `entity`: Represents the database table (Student).
* `exception`: Contains custom exception and global exception handler.

## How to Run the Project

1. Clone the repository.
2. Navigate to the project directory.
3. Run the application using Maven:

```bash
./mvnw spring-boot:run
```

The server will start on `http://localhost:8080`.

## API List

| Method | URL | Description |
|---|---|---|
| POST | `/students` | Create a new student |
| GET | `/students` | Get all students |
| GET | `/students/{id}` | Get student by ID |
| PUT | `/students/{id}` | Update student by ID |
| DELETE | `/students/{id}` | Delete student by ID |

## Sample JSON Requests

### Create a Student (POST `/students`)

```json
{
  "name": "Sathwik",
  "email": "sathwik@gmail.com",
  "department": "CSE",
  "age": 21
}
```

### Update a Student (PUT `/students/1`)

```json
{
  "name": "Sathwik Updated",
  "email": "sathwik.updated@gmail.com",
  "department": "IT",
  "age": 22
}
```

## How to Run Tests

Run JUnit unit tests and integration tests using:

```bash
./mvnw clean test
```

## H2 Console Details

Since this project uses an H2 in-memory database, you can view the database tables visually.

* URL: `http://localhost:8080/h2-console`
* JDBC URL: `jdbc:h2:mem:studentdb`
* Username: `sa`
* Password: *(leave blank)*
