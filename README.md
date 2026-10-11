# 🏥 Hospital Management System (HMS)

A full-stack Hospital Management System built with **Spring Boot 3**, **Angular 19**, and **MySQL**. Features role-based access control with three distinct user roles — Admin, Doctor, and Patient — each with their own secured dashboard and functionality.

---

## 🚀 Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Spring Boot 3, Java 21 |
| Frontend | Angular 19, Bootstrap 5 |
| Database | MySQL 8 |
| Authentication | JWT (JSON Web Tokens) |
| Security | Spring Security, BCrypt |
| ORM | JPA / Hibernate |
| Build Tool | Maven |

---

## ✨ Features

### 🔐 Security
- Stateless JWT authentication — token carries email and role, no server-side sessions
- BCrypt password encryption — plain text passwords never stored in database
- Role-based access control enforced at both API level (Spring Security) and UI level (Angular Guards)
- JWT filter intercepts every request before it reaches any controller
- Public registration endpoint force-sets role to PATIENT — no user can self-assign ADMIN or DOCTOR role

### 👨‍💼 Admin Dashboard
- View total doctors and patients with live count cards
- Add new doctors and patients directly from dashboard
- Delete doctors and patients with confirmation dialog
- Instant UI update on delete — no page refresh needed

### 👨‍⚕️ Doctor Dashboard
- View all assigned appointments with color-coded status badges
- Update appointment status (PENDING → CONFIRMED → COMPLETED → CANCELLED)
- Add clinical notes to appointments
- Edit own profile — specialization, experience, phone

### 🧑‍⚕️ Patient Dashboard
- Self-register and login independently
- Browse available doctors with specialization details
- Book appointments with date, time, and symptom notes
- View own appointment history with real-time status updates
- Edit own profile — age, blood group, phone, address

---

## 🏗️ Architecture

```
Angular Frontend (port 4200)
        ↓
HTTP Request + JWT Token in Authorization header
        ↓
JwtAuthFilter — validates token, extracts email + role, stores in SecurityContext
        ↓
SecurityConfig — checks if role is allowed to access this URL
        ↓
Controller Layer — receives request, calls service
        ↓
Service Layer — business logic (BCrypt, role assignment, identity from SecurityContext)
        ↓
Repository Layer — Spring Data JPA queries to MySQL
        ↓
MySQL Database
        ↓
Response flows back up through Service → DTO (strips sensitive fields) → Controller → Angular
```

---

## 📁 Project Structure

```
Hospital_Management_System/
├── hospital-backend/
│   └── src/main/java/com/hospital/hospital_management/
│       ├── config/
│       │   └── SecurityConfig.java          # Spring Security + CORS configuration
│       ├── controller/
│       │   ├── AuthController.java          # Login and registration endpoints
│       │   ├── AdminController.java         # Admin-only endpoints
│       │   ├── DoctorController.java        # Doctor-only endpoints
│       │   └── PatientController.java       # Patient-only endpoints
│       ├── service/
│       │   ├── AuthService.java             # Registration, login, BCrypt logic
│       │   └── AppointmentService.java      # Appointment business logic
│       ├── repository/
│       │   ├── UserRepository.java
│       │   ├── DoctorRepository.java
│       │   ├── PatientRepository.java
│       │   └── AppointmentRepository.java
│       ├── model/
│       │   ├── User.java
│       │   ├── Doctor.java
│       │   ├── Patient.java
│       │   ├── Appointment.java
│       │   ├── Role.java                    # Enum: ADMIN, DOCTOR, PATIENT
│       │   └── AppointmentStatus.java       # Enum: PENDING, CONFIRMED, COMPLETED, CANCELLED
│       ├── dto/
│       │   ├── UserResponseDTO.java         # Strips password from user responses
│       │   ├── DoctorResponseDTO.java       # Flattened doctor + user data
│       │   ├── PatientResponseDTO.java      # Flattened patient + user data
│       │   └── AppointmentResponseDTO.java  # Clean appointment response
│       └── security/
│           ├── JwtUtil.java                 # Token generation and validation
│           └── JwtAuthFilter.java           # Request interceptor
│
└── hospital-frontend/
    └── src/app/
        ├── components/
        │   ├── login/                       # Login page
        │   ├── register/                    # Patient self-registration
        │   ├── admin/                       # Admin dashboard
        │   ├── doctor/                      # Doctor dashboard
        │   ├── patient/                     # Patient dashboard
        │   └── unauthorized/               # Access denied page
        ├── services/
        │   └── auth.service.ts              # Login, register, token storage
        ├── models/                          # TypeScript interfaces matching backend DTOs
        ├── guards/
        │   └── auth.guard.ts               # Route protection by role
        └── interceptors/
            └── auth.interceptor.ts          # Auto-attaches JWT to every HTTP request
```

---

## 🔌 API Endpoints

### Auth (Public)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/login` | Login — returns JWT token and role |
| POST | `/api/auth/register` | Patient self-registration |

### Admin (ADMIN role only)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/admin/users` | Get all users |
| GET | `/api/admin/doctors` | Get all doctors |
| POST | `/api/admin/doctors` | Add new doctor |
| DELETE | `/api/admin/doctors/{id}` | Delete doctor |
| GET | `/api/admin/patients` | Get all patients |
| POST | `/api/admin/patients` | Add new patient |
| DELETE | `/api/admin/patients/{id}` | Delete patient |

### Doctor (DOCTOR role only)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/doctor/profile` | Get own profile |
| PUT | `/api/doctor/profile` | Update own profile |
| GET | `/api/doctor/appointments` | Get own appointments |
| PUT | `/api/doctor/appointments/{id}` | Update appointment status and notes |

### Patient (PATIENT role only)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/patient/profile` | Get own profile |
| PUT | `/api/patient/profile` | Update own profile |
| GET | `/api/patient/doctors` | Browse available doctors |
| POST | `/api/patient/appointments` | Book appointment |
| GET | `/api/patient/appointments` | View own appointments |

---

## ⚙️ Setup & Installation

### Prerequisites
- Java 21+
- Node.js 18+
- MySQL 8+
- Maven

### Backend Setup

```bash
cd hospital-backend

# Configure MySQL in application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

# Run
mvn spring-boot:run
```

Backend runs on `http://localhost:8080`

### Frontend Setup

```bash
cd hospital-frontend
npm install
ng serve
```

Frontend runs on `http://localhost:4200`

---

## 🔐 Authentication Flow

```
1. POST /api/auth/login  →  { email, password }
2. BCrypt validates password against stored hash
3. JWT token generated with email + role embedded inside
4. Token returned to Angular — saved in localStorage
5. Every subsequent request:
   Angular Interceptor attaches → Authorization: Bearer <token>
   JwtAuthFilter validates token → extracts email + role
   SecurityConfig checks role permission for URL
   Controller retrieves identity from SecurityContext
```

---

## 🛡️ Security Design Decisions

**Why BCrypt?** Plain text passwords never stored — even if database is compromised, passwords cannot be reversed from hashes.

**Why JWT stateless?** Server stores no session data — every request is self-contained with identity proof inside the token. Scales horizontally without shared session storage.

**Why role embedded in token?** Eliminates database lookup on every request to check the user's role — filter extracts it directly from the token in memory.

**Why SecurityContext for identity?** Controllers never trust client-supplied identity — they extract who the logged-in user is from the verified token via SecurityContext. A patient cannot claim to be a different patient by changing a request parameter.

**Why DTOs?** Raw JPA entities contain BCrypt password hashes — DTOs strip sensitive fields before any data leaves the backend, regardless of which endpoint is called.

---

## 🧑‍💻 Built By

[Hanumant Shinde](https://github.com/HanumantShinde) — Full Stack Java Developer
