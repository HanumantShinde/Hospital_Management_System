# 🏥 Hospital Management System (HMS)

A full-stack **Hospital Management System** built with **Spring Boot 3, Java 21, Angular 19, and MySQL 8**. The application provides secure, role-based access for Admins, Doctors, and Patients, with dedicated dashboards for managing users, doctor profiles, appointments, and patient information.

## 🚀 Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 21, Spring Boot 3 |
| Frontend | Angular 19, TypeScript |
| UI Framework | Bootstrap 5 |
| Database | MySQL 8 |
| Authentication | JSON Web Tokens (JWT) |
| Security | Spring Security, BCrypt |
| ORM | Spring Data JPA, Hibernate |
| Build Tool | Maven |
| Testing | JUnit 5, Mockito, Spring Boot Test, MockMvc |
| Test Database | H2 |

## ✨ Features

### 🔐 Authentication & Security
- JWT-based stateless authentication.
- Secure password hashing using BCrypt.
- Role-based access control for Admin, Doctor, and Patient.
- Backend authorization using Spring Security.
- Frontend route protection using Angular Guards.
- Automatic JWT attachment through an Angular HTTP interceptor.
- Public registration restricted to the Patient role.
- Identity derived from the authenticated security context.
- Data Transfer Objects (DTOs) to prevent sensitive fields from being exposed in API responses.

### 👨‍💼 Admin Dashboard
- View registered doctors and patients.
- View user information through administrative APIs.
- Add new doctors and patients.
- Delete doctor and patient records with confirmation.
- Update the UI immediately after successful deletion without refreshing the page.

### 👨‍⚕️ Doctor Dashboard
- View assigned appointments.
- Update appointment statuses.
- Add clinical notes to appointments.
- View and update personal profile information.
- Manage specialization, experience, and phone details.

### 🧑‍⚕️ Patient Dashboard
- Register and log in independently.
- Browse available doctors and their specializations.
- Book appointments with a date, time, and symptom description.
- View personal appointment history and status updates.
- Update profile details, including age, blood group, phone number, and address.

### 📅 Appointment Management
Appointments support the following statuses:

- `PENDING`
- `CONFIRMED`
- `COMPLETED`
- `CANCELLED`

## 🏗️ System Architecture

The application follows a layered backend architecture with an Angular frontend.

```text
┌───────────────────────────────────┐
│       Angular 19 Frontend         │
│  Components, Services, Guards     │
│       HTTP Interceptor            │
└─────────────────┬─────────────────┘
                  │ HTTP + JWT
                  ▼
┌───────────────────────────────────┐
│       Spring Security Layer       │
│ JWT Authentication Filter         │
│ Authentication & Authorization    │
└─────────────────┬─────────────────┘
                  ▼
┌───────────────────────────────────┐
│        Controller Layer           │
│ Auth, Admin, Doctor, Patient      │
└─────────────────┬─────────────────┘
                  ▼
┌───────────────────────────────────┐
│          Service Layer            │
│ Authentication & Business Logic  │
└─────────────────┬─────────────────┘
                  ▼
┌───────────────────────────────────┐
│         Repository Layer          │
│       Spring Data JPA             │
└─────────────────┬─────────────────┘
                  ▼
┌───────────────────────────────────┐
│           MySQL 8                 │
│       Persistent Storage          │
└───────────────────────────────────┘
```

### Request Processing Flow

1. Angular sends an HTTP request to the backend.
2. The HTTP interceptor attaches the JWT to protected requests.
3. The JWT authentication filter validates the token and establishes the authenticated identity.
4. Spring Security checks the authorization rules for the requested endpoint.
5. The controller delegates the operation to the service layer.
6. The service executes the business logic and interacts with repositories.
7. Spring Data JPA communicates with the MySQL database.
8. The backend returns an appropriate response, using DTOs where applicable.

## 📁 Project Structure

```text
Hospital_Management_System/
│
├── hospital-backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/hospital/hospital_management/
│   │   │   │   ├── config/
│   │   │   │   │   └── SecurityConfig.java
│   │   │   │   ├── controller/
│   │   │   │   │   ├── AuthController.java
│   │   │   │   │   ├── AdminController.java
│   │   │   │   │   ├── DoctorController.java
│   │   │   │   │   └── PatientController.java
│   │   │   │   ├── service/
│   │   │   │   │   ├── AuthService.java
│   │   │   │   │   └── AppointmentService.java
│   │   │   │   ├── repository/
│   │   │   │   │   ├── UserRepository.java
│   │   │   │   │   ├── DoctorRepository.java
│   │   │   │   │   ├── PatientRepository.java
│   │   │   │   │   └── AppointmentRepository.java
│   │   │   │   ├── model/
│   │   │   │   │   ├── User.java
│   │   │   │   │   ├── Doctor.java
│   │   │   │   │   ├── Patient.java
│   │   │   │   │   ├── Appointment.java
│   │   │   │   │   ├── Role.java
│   │   │   │   │   └── AppointmentStatus.java
│   │   │   │   ├── dto/
│   │   │   │   │   ├── UserResponseDTO.java
│   │   │   │   │   ├── DoctorResponseDTO.java
│   │   │   │   │   ├── PatientResponseDTO.java
│   │   │   │   │   └── AppointmentResponseDTO.java
│   │   │   │   └── security/
│   │   │   │       ├── JwtUtil.java
│   │   │   │       └── JwtAuthFilter.java
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   │       └── java/com/hospital/hospital_management/
│   │           ├── service/
│   │           │   └── AppointmentServiceTest.java
│   │           ├── controller/
│   │           │   └── AdminControllerTest.java
│   │           └── repository/
│   │               └── UserRepositoryTest.java
│   └── pom.xml
│
├── hospital-frontend/
│   ├── src/
│   │   └── app/
│   │       ├── components/
│   │       │   ├── login/
│   │       │   ├── register/
│   │       │   ├── admin/
│   │       │   ├── doctor/
│   │       │   ├── patient/
│   │       │   └── unauthorized/
│   │       ├── services/
│   │       │   └── auth.service.ts
│   │       ├── models/
│   │       ├── guards/
│   │       │   └── auth.guard.ts
│   │       └── interceptors/
│   │           └── auth.interceptor.ts
│   ├── package.json
│   └── angular.json
│
└── README.md
```

*Note: This structure is based on the current project layout. Other files and folders may exist in the repository.*

## 🔌 REST API Endpoints

All protected endpoints require a valid JWT, unless otherwise configured.

### Authentication

| Method | Endpoint | Description | Access |
|---|---|---|---|
| `POST` | `/api/auth/login` | Authenticate a user and return a JWT | Public |
| `POST` | `/api/auth/register` | Register a new patient | Public |

### Admin APIs

| Method | Endpoint | Description | Access |
|---|---|---|---|
| `GET` | `/api/admin/users` | Retrieve all users | Admin |
| `GET` | `/api/admin/doctors` | Retrieve all doctors | Admin |
| `POST` | `/api/admin/doctors` | Add a doctor | Admin |
| `DELETE` | `/api/admin/doctors/{id}` | Delete a doctor | Admin |
| `GET` | `/api/admin/patients` | Retrieve all patients | Admin |
| `POST` | `/api/admin/patients` | Add a patient | Admin |
| `DELETE` | `/api/admin/patients/{id}` | Delete a patient | Admin |

### Doctor APIs

| Method | Endpoint | Description | Access |
|---|---|---|---|
| `GET` | `/api/doctor/profile` | Retrieve own profile | Doctor |
| `PUT` | `/api/doctor/profile` | Update own profile | Doctor |
| `GET` | `/api/doctor/appointments` | Retrieve own appointments | Doctor |
| `PUT` | `/api/doctor/appointments/{id}` | Update appointment status and notes | Doctor |

### Patient APIs

| Method | Endpoint | Description | Access |
|---|---|---|---|
| `GET` | `/api/patient/profile` | Retrieve own profile | Patient |
| `PUT` | `/api/patient/profile` | Update own profile | Patient |
| `GET` | `/api/patient/doctors` | Browse available doctors | Patient |
| `POST` | `/api/patient/appointments` | Book an appointment | Patient |
| `GET` | `/api/patient/appointments` | Retrieve own appointments | Patient |

## ⚙️ Installation & Setup

### Prerequisites

Install the following before running the application:

- Java Development Kit (JDK) 21 or later
- Node.js 18 or a version compatible with Angular 19
- Angular CLI compatible with Angular 19
- MySQL 8 or later
- Maven

### 1. Clone the Repository

```bash
git clone <YOUR_GITHUB_REPOSITORY_URL>
cd Hospital_Management_System
```

Replace `<YOUR_GITHUB_REPOSITORY_URL>` with your actual GitHub repository URL.

### 2. Configure the Database

Start MySQL and create the database:

```sql
CREATE DATABASE hospital_db;
```

Configure the backend's `application.properties` file:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
```

Set `DB_USERNAME` and `DB_PASSWORD` in your environment before starting the backend, or use your local development configuration.

**Security note:** Never commit real database credentials, JWT signing secrets, or other sensitive configuration to GitHub. Use environment variables or an untracked local configuration file.

### 3. Run the Backend

Open a terminal:

```bash
cd hospital-backend
mvn clean install
mvn spring-boot:run
```

The backend should start at:

`http://localhost:8080`

### 4. Run the Frontend

Open a separate terminal:

```bash
cd hospital-frontend
npm install
npx ng serve
```

Open the application in your browser:

`http://localhost:4200`

Ensure that the backend CORS configuration permits requests from the Angular development server.

## 🧪 Testing

The backend includes automated tests using JUnit 5, Mockito, Spring Boot Test, MockMvc, and H2.

| Test Class | Test Type | Purpose |
|---|---|---|
| `AppointmentServiceTest` | Unit test | Tests appointment business logic |
| `AdminControllerTest` | Controller test | Tests admin APIs using MockMvc |
| `UserRepositoryTest` | Repository test | Tests JPA repository operations using H2 |

### Run All Tests

From the backend directory:

```bash
mvn test
```

You can also run individual test classes from your IDE.

### Testing Notes

- H2 provides an in-memory database for repository tests.
- Mockito can isolate services from their dependencies.
- MockMvc supports controller testing without starting a full HTTP server.
- The admin controller tests disable servlet security filters using `@AutoConfigureMockMvc(addFilters = false)`. These tests do not independently verify the complete Spring Security filter chain.

## 🔐 Authentication Flow

```text
1. User submits email and password
                |
                v
2. POST /api/auth/login
                |
                v
3. Backend verifies the password using BCrypt
                |
                v
4. Backend generates a signed JWT
   containing identity and role claims
                |
                v
5. Angular stores the token in localStorage
                |
                v
6. HTTP interceptor attaches the Bearer token
                |
                v
7. JwtAuthFilter validates the token
                |
                v
8. Spring Security authorizes the request
                |
                v
9. Controller and service process the request
                |
                v
10. API returns the response to Angular
```

## 🛡️ Security Design Decisions

### Why BCrypt?

BCrypt hashes passwords before they are stored in the database. Password hashes are not directly reversible, helping protect user credentials if the database is exposed.

### Why JWT Authentication?

JWT-based authentication allows the backend to authenticate requests without maintaining a traditional server-side HTTP session. This can simplify horizontal scaling when token validation and key management are configured correctly.

### Why Role-Based Access Control?

Roles separate administrative, doctor, and patient functionality. Spring Security enforces access at the backend, while Angular Guards improve frontend navigation and user experience.

**Important:** Frontend route guards alone are not a security boundary. Backend authorization must always be enforced.

### Why Use SecurityContext?

The authenticated identity is obtained from Spring Security's `SecurityContext` rather than trusting a user ID supplied by the client. This helps prevent users from accessing another user's data when the service layer consistently enforces ownership checks.

### Why Use DTOs?

DTOs control which fields are returned by API endpoints. For example, `UserResponseDTO` prevents password hashes from being included in user responses.

### Why Restrict Public Registration?

The public registration endpoint assigns the `PATIENT` role on the server side. Users cannot choose privileged roles through the registration request.

### Security Considerations

- Keep JWT signing secrets outside source control.
- Configure appropriate token expiration and validation.
- Enforce appointment ownership and role permissions on the backend.
- Validate request data and handle errors safely.
- Use HTTPS in production.
- Consider secure token storage and protection against cross-site scripting (XSS).
- Review database deletion rules and related-record handling before deployment.

## 🔮 Future Enhancements

Potential improvements for future versions include:

- Email or SMS appointment notifications.
- Appointment search, filtering, and pagination.
- Doctor availability and time-slot management.
- Password reset functionality.
- Audit logs for administrative actions.
- Docker-based deployment.
- CI/CD integration.
- Expanded integration and security tests.

## 👨‍💻 Author

**Hanumant Shinde**

Full Stack Java Developer

**Technologies:** Java, Spring Boot, Spring Security, Angular, MySQL, REST APIs, JWT

---

⭐ If you find this project useful, consider giving the repository a star!
