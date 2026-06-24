# 🏥 Hospital Management System (HMS)

A role-based **Hospital Management System** built using **Spring Boot 3**, **Java 21**, and **MySQL**. This project provides a RESTful backend API for managing doctors, patients, and appointments with secure JWT-based authentication and authorization.

---

## 🚀 Tech Stack

### Backend

* Java 21
* Spring Boot 3
* Spring Data JPA
* Spring Security
* JWT Authentication
* Maven

### Database

* MySQL

### Additional Libraries

* Lombok
* Bean Validation
* JJWT (Java JWT)

---

## 📌 Features

The system supports three user roles:

### 👨‍💼 Admin

* View all users
* View all doctors
* View all patients
* Add new doctors
* Add new patients
* Delete doctors
* Delete patients

### 🧑‍⚕️ Doctor

* View own profile
* View assigned appointments
* Update appointment details
* Change appointment status
* Add notes to appointments

### 🧑 Patient

* View own profile
* Browse available doctors
* Book appointments
* View appointment history

---

## 🗄️ Database Entities

### User

Stores authentication and role information.

| Field    | Description              |
| -------- | ------------------------ |
| id       | Unique identifier        |
| name     | User name                |
| email    | User email               |
| password | Encrypted password       |
| role     | ADMIN / DOCTOR / PATIENT |

### Patient

Additional information for patients.

| Field      | Description             |
| ---------- | ----------------------- |
| id         | Unique identifier       |
| age        | Patient age             |
| bloodGroup | Blood group             |
| phone      | Contact number          |
| address    | Residential address     |
| user       | Associated user account |

### Doctor

Additional information for doctors.

| Field          | Description             |
| -------------- | ----------------------- |
| id             | Unique identifier       |
| specialization | Medical specialization  |
| experience     | Years of experience     |
| phone          | Contact number          |
| user           | Associated user account |

### Appointment

Manages doctor-patient appointments.

| Field               | Description        |
| ------------------- | ------------------ |
| id                  | Appointment ID     |
| patient             | Linked patient     |
| doctor              | Linked doctor      |
| appointmentDateTime | Date and time      |
| status              | Appointment status |
| notes               | Additional notes   |

---

## 🔐 Authentication & Authorization

The application uses:

* Spring Security
* JWT (JSON Web Token)
* Role-Based Access Control (RBAC)

### Supported Roles

* ADMIN
* DOCTOR
* PATIENT

After successful login, users receive a JWT token which must be included in subsequent API requests.

---

## 📂 Project Structure

```text
src/main/java
│
├── controller
│   ├── AuthController
│   ├── AdminController
│   ├── DoctorController
│   └── PatientController
│
├── service
│
├── repository
│
├── dto
│
├── entity
│   ├── User
│   ├── Doctor
│   ├── Patient
│   └── Appointment
│
├── security
│
└── config
```

---

## 🔗 API Modules

### Authentication APIs

```http
POST /api/auth/register
POST /api/auth/login
```

### Admin APIs

```http
GET    /api/admin/users
GET    /api/admin/doctors
GET    /api/admin/patients
DELETE /api/admin/doctors/{id}
DELETE /api/admin/patients/{id}
```

### Patient APIs

```http
GET  /api/patient/profile
GET  /api/patient/doctors
POST /api/patient/appointments
GET  /api/patient/appointments
```

### Doctor APIs

```http
GET  /api/doctor/profile
GET  /api/doctor/appointments
PUT  /api/doctor/appointments/{id}
```

---

## ⚙️ Setup & Installation

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/Hospital_Management_System.git
cd Hospital_Management_System
```

### 2. Configure MySQL

Create a database:

```sql
CREATE DATABASE hospital_management;
```

### 3. Update application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_management
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 4. Build the Project

```bash
mvn clean install
```

### 5. Run the Application

```bash
mvn spring-boot:run
```

Application will start on:

```text
http://localhost:8080
```

---

## 🧪 Testing APIs

You can test APIs using:

* Postman
* Swagger (if configured)
* Insomnia

---

## 🎯 Learning Objectives

This project demonstrates:

* REST API Development
* Spring Boot Fundamentals
* Spring Security
* JWT Authentication
* Role-Based Authorization
* JPA & Hibernate
* MySQL Integration
* Layered Architecture
* DTO Pattern

---

## 🔮 Future Enhancements

* Frontend using Angular
* Appointment reminders
* Prescription management
* Medical records upload
* Email notifications
* Dashboard analytics
* Doctor availability scheduling

---

## 👨‍💻 Author

**Hanumant Shinde**

Computer Engineering Student | Java Backend Developer

---

## 📄 License

This project is developed for educational and learning purposes.
