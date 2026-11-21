# ai-healthcare-backendCareAI Backend
CareAI is the Spring Boot REST API used by the CareAI frontend. It
provides authentication and role-based APIs for patients and doctors.
Tech Stack
- Java
- Spring Boot
- Spring Security
- JWT-based authentication
- Maven
- JPA / Hibernate
- Relational database configured in the project
- Swagger / OpenAPI (if enabled in the project)
Use the Java, Spring Boot, and database versions declared in this
project's pom.xml and configuration files.

Prerequisites
Before running the backend, install:
- JDK version compatible with the project
- Maven (or use the included Maven Wrapper, if present)
- The database configured by the project
- An IDE such as Eclipse or IntelliJ IDEA (optional)
Project Setup
1. Clone or download the backend project.
2. Open the project in your IDE, or open a terminal in the folder
   containing pom.xml.
3. Check src/main/resources/application.properties (or
   application.yml).
4. Configure the database connection and credentials for your local
   environment.
5. Make sure the configured database exists and is running.
Example database configuration (use this only if your project uses
MySQL; update the database name, username, and password):
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
Do not commit real database passwords, JWT secrets, or other credentials
to a public repository. Use environment variables or a local, untracked
configuration file for secrets.
Run the Application
From the project root:
mvn clean install
mvn spring-boot:run
If the project includes Maven Wrapper, you can use:

Windows
mvnw.cmd spring-boot:run
macOS / Linux
./mvnw spring-boot:run
The frontend integration currently expects the backend at:
http://localhost:8080
If the backend uses a different port, update the frontend API base URL
in src/services/api.js.
Authentication and Authorization
The backend uses JWT authentication.
1. A user registers or logs in through the authentication API.
2. After successful login, the frontend stores the returned JWT using
   the token key in localStorage.
3. The frontend sends the token with protected requests in this header:
Authorization: Bearer <JWT_TOKEN>
4. Spring Security validates the token and loads the user's
   authorities.
5. Access to patient and doctor endpoints is restricted by role.
The current security configuration permits these routes without
authentication:
- /api/auth/**
- /api/patients/register
- /api/doctors/register
- Swagger/OpenAPI routes configured in SecurityConfig
Role-protected route patterns:

  Route pattern       Required role
  /api/patient/**   PATIENT
/api/doctor/**    DOCTOR

The role stored for a user must match the role expected by Spring
Security. With User.withUsername(...).roles(user.getRole()), store
values such as PATIENT or DOCTOR (without the ROLE_ prefix),
unless the implementation is changed accordingly.
API Endpoints Used by the Frontend
The frontend currently calls the following dashboard endpoints:
  Method                  Endpoint                                         Purpose
  GET                     /api/patient/dashboard                         Load patient dashboard
summary

  GET                     /api/patient/dashboard/upcoming-appointments   Load upcoming patient
appointments

  GET                     /api/patient/dashboard/recent-reports          Load recent patient
reports
GET                     /api/doctor/dashboard                          Load doctor dashboard
summary
These routes require a valid JWT and the matching user role.

Authentication, registration, appointment, report, and other API routes
may also be present. Check the backend controllers for the complete and
authoritative endpoint list.
Swagger / API Testing
If Swagger/OpenAPI is included and configured, start the backend and
open:
http://localhost:8080/swagger-ui/index.html
If this page is unavailable, verify that the OpenAPI dependency is
present in pom.xml and check the Swagger path configured by the
project.

You can also test endpoints using Postman or the frontend. For protected
endpoints, include the JWT bearer token.
CORS Configuration
The current security configuration allows the frontend origin:
http://localhost:5173
Allowed methods:
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