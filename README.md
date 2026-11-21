# CareAI Backend

CareAI is a Spring Boot REST API that powers the CareAI healthcare application. It provides authentication and role-based APIs for patients and doctors.

## Tech Stack

- Java
- Spring Boot
- Spring Security
- JWT Authentication
- Maven
- Spring Data JPA / Hibernate
- MySQL (if configured in the project)
- Swagger / OpenAPI

## Prerequisites

Before running the project, install:

- JDK compatible with the project
- Maven
- MySQL Server (if using MySQL)
- Eclipse or IntelliJ IDEA (optional)

## Project Setup

1. Clone or download the backend project.
2. Open the project in Eclipse or IntelliJ IDEA.
3. Open the `application.properties` file.
4. Configure your database connection.
5. Make sure your database server is running.

### Database Configuration

Example MySQL configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

Replace the database name, username, and password with your actual credentials.

**Note:** Never upload database passwords or JWT secrets to a public GitHub repository.

## Run the Application

Open the terminal in the backend project directory and execute:

```bash
mvn clean install
mvn spring-boot:run
```

If you are using Maven Wrapper on Windows:

```bash
mvnw.cmd spring-boot:run
```

The backend runs at:

```text
http://localhost:8080
```

## Authentication and Authorization

The application uses JWT-based authentication.

1. Users register as patients or doctors.
2. Users log in using their credentials.
3. The backend generates a JWT token after successful authentication.
4. The frontend stores the token in local storage.
5. The token is sent with protected API requests using the Authorization header.

Example:

```http
Authorization: Bearer <JWT_TOKEN>
```

### Role-Based Access

The application uses Spring Security to restrict access based on user roles.

| API Route | Required Role |
|---|---|
| `/api/patient/**` | PATIENT |
| `/api/doctor/**` | DOCTOR |
| `/api/auth/**` | Public access |
| `/api/patients/register` | Public access |
| `/api/doctors/register` | Public access |

## API Endpoints

The following dashboard APIs are used by the frontend.

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/patient/dashboard` | Get patient dashboard details |
| GET | `/api/patient/dashboard/upcoming-appointments` | Get upcoming appointments |
| GET | `/api/patient/dashboard/recent-reports` | Get recent medical reports |
| GET | `/api/doctor/dashboard` | Get doctor dashboard details |

These dashboard endpoints require valid authentication and the appropriate user role.

Refer to the backend controllers for the complete list of available APIs.

## Swagger API Documentation

If Swagger is configured in the project, start the backend and open:

```text
http://localhost:8080/swagger-ui/index.html
```

Swagger allows you to:

- View available REST API endpoints.
- Test GET, POST, PUT and DELETE requests.
- Check API request and response details.
- Test authenticated endpoints using a JWT token.

## CORS Configuration

The backend currently allows requests from the React frontend:

```text
http://localhost:5173
```

Allowed HTTP methods:

- GET
- POST
- PUT
- DELETE
- OPTIONS

If the frontend runs on a different port, update the allowed origin in `SecurityConfig.java`.

## Common Issues

### 1. 401 Unauthorized

Possible reasons:

- JWT token is missing.
- JWT token is invalid or expired.
- Authorization header is not sent correctly.

Solution:
- Check whether the token is stored in local storage.
- Verify that the frontend sends the token in the Authorization header.

### 2. 403 Forbidden

Possible reasons:

- User does not have the required role.
- Database role does not match the expected Spring Security role.
- Access is restricted by Spring Security configuration.

Solution:
- Check the user's role in the database.
- Verify the authorities loaded by `CustomUserDetailsService`.
- Check the URL security rules in `SecurityConfig.java`.

### 3. Database Connection Error

Possible reasons:

- MySQL server is not running.
- Incorrect database name or credentials.
- Missing database driver dependency.

Solution:
- Start the database server.
- Verify the database configuration.
- Check the MySQL connector dependency in `pom.xml`.

### 4. CORS Error

Solution:
- Verify the frontend URL in `SecurityConfig.java`.
- Ensure OPTIONS requests are allowed.
- Restart the backend after configuration changes.

## Project Structure

The backend package root is:

```text
com.spam.ai_healthcare_backend
```

Typical Spring Boot structure:

```text
src
 └── main
     ├── java
     │   └── com.spam.ai_healthcare_backend
     │       ├── config
     │       ├── controller
     │       ├── model
     │       ├── repository
     │       ├── service
     │       └── security
     │
     └── resources
         └── application.properties
```

## Build the Project

To generate the executable JAR file:

```bash
mvn clean package
```

The generated JAR file will be available inside the `target` directory.

Run the JAR using:

```bash
java -jar target/<generated-jar-name>.jar
```

## Future Enhancements

- Additional healthcare management APIs.
- Improved appointment management.
- Medical report management.
- Enhanced authentication and authorization.
- Additional patient and doctor services.

---

**CareAI – Healthcare Management Application**
