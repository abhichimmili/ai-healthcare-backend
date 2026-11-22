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
If the frontend runs on a different origin or port, update the CORS
allowed origins in SecurityConfig.
Common Issues
401 Unauthorized
- The request may not include a bearer token.
- The token may be expired or invalid.
- Confirm that the frontend stores the login token under token.
- Confirm that the Axios request interceptor sends
  Authorization: Bearer <token>.
403 Forbidden
- The token may be valid but the logged-in user may not have the
  required role.
- Check the user's role in the database.
- Check the authorities created by CustomUserDetailsService.
- Check route rules and any @PreAuthorize annotations.
- Confirm that the frontend is calling the endpoint for the correct
  role.
Database connection failure
- Confirm that the database server is running.
- Check the JDBC URL, database name, username, and password.
- Confirm that the required JDBC driver dependency is present.
CORS error
- Confirm that the frontend origin is included in the backend CORS
  configuration.
- Confirm that OPTIONS is allowed.
- Restart the backend after changing the configuration.
Suggested Project Structure
The exact packages may differ. The backend currently uses the package
root:
com.spam.ai_healthcare_backend
A typical Spring Boot structure is:
src/
└── main/
    ├── java/
    │   └── com/spam/ai_healthcare_backend/
    │       ├── config/
    │       ├── controller/
    │       ├── model/
    │       ├── repository/
    │       ├── service/
    │       └── security/
    └── resources/
        └── application.properties
Build
To create a packaged application:
mvn clean package
The generated JAR is normally placed in the target/ directory. Run it
with:
java -jar target/<generated-jar-name>.jar
Replace <generated-jar-name>.jar with the actual JAR filename created
by Maven.
