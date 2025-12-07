# 🚗 RideShare Backend (Spring Boot + MongoDB + JWT)

A fully functional Ride Sharing backend system built using:

- Spring Boot 3
- Spring Security (JWT Authentication)
- MongoDB
- Role-based authorization (USER / DRIVER)
- Clean architecture (Controller → Service → Repository)

This application supports user registration, login, ride requests, driver acceptance, and ride completion.

---

## 📁 Project Structure

```
src/
 ├── main/
 │    ├── java/org/example/rideshare/
 │    │           ├── model/
 │    │           ├── repository/
 │    │           ├── service/
 │    │           ├── controller/
 │    │           ├── config/
 │    │           ├── dto/
 │    │           ├── exception/
 │    │           └── RideShareApplication.java
 │    └── resources/
 │            ├── application.properties
 └── test/
```

---

## 🚀 Features

### 🔐 Authentication
- Register user or driver
- Login with JWT
- BCrypt password hashing

### 👤 Users (ROLE_USER)
- Request a ride
- View their rides
- Complete rides

### 🚗 Drivers (ROLE_DRIVER)
- View pending ride requests
- Accept ride requests
- Complete rides

### 🛡 Security
- JWT-based authentication
- Role-based authorization
- Custom JWT filter

### ⚠️ Exception Handling
- Global exception handler
- Validation error messages

---

## ⚙️ Tech Stack

| Component | Technology |
|----------|------------|
| Backend | Spring Boot 3.2 |
| Database | MongoDB |
| Authentication | JWT (jjwt 0.11.5) |
| Build Tool | Maven |
| Language | Java 17 |

---

## 🔧 Setup Instructions

### 1. Clone the repository
```bash
git clone https://github.com/<your-username>/rideshare-backend.git
cd rideshare-backend
```

### 2. Start MongoDB
```bash
mongod
```

Ensure the app config matches:
```
spring.data.mongodb.uri=mongodb://localhost:27017/rideshare_db
```

### 3. Run the application

#### IntelliJ:
Click ▶️ next to `RideShareApplication`.

#### Command line:
```bash
mvn spring-boot:run
```

Backend starts at:
```
http://localhost:8081
```

---

## 🧪 API Endpoints

---

### 🔐 AUTH

#### ➤ Register (User or Driver)
```
POST /api/auth/register
```
Body:
```json
{
  "username": "john",
  "password": "1234",
  "role": "ROLE_USER"
}
```

#### ➤ Login
```
POST /api/auth/login
```
Body:
```json
{
  "username": "john",
  "password": "1234"
}
```

Response:
```json
{
  "token": "<jwt-token>"
}
```

Use it:
```
Authorization: Bearer <token>
```

---

### 👤 USER (ROLE_USER)

#### ➤ Request Ride
```
POST /api/v1/rides
```
Body:
```json
{
  "pickupLocation": "Koramangala",
  "dropLocation": "Indiranagar"
}
```

#### ➤ View My Rides
```
GET /api/v1/user/rides
```

#### ➤ Complete Ride
```
POST /api/v1/rides/{rideId}/complete
```

---

### 🚗 DRIVER (ROLE_DRIVER)

#### ➤ View Pending Ride Requests
```
GET /api/v1/driver/rides/requests
```

#### ➤ Accept a Ride
```
POST /api/v1/driver/rides/{rideId}/accept
```

---

## 🗄 Database Collections

### users
```json
{
  "_id": "...",
  "username": "john",
  "password": "$2a$10...",
  "role": "ROLE_USER"
}
```

### rides
```json
{
  "_id": "...",
  "userId": "...",
  "driverId": "...",
  "pickupLocation": "Koramangala",
  "dropLocation": "Indiranagar",
  "status": "REQUESTED",
  "createdAt": "2025-01-20"
}
```

---

## 📦 Build for Production

```bash
mvn clean package
java -jar target/rideshare-0.0.1-SNAPSHOT.jar
```

---

## 📝 Contribution Workflow

1. Fork repo  
2. Create feature branch  
3. Commit changes  
4. Push  
5. Open pull request  

---

## 🛠 Troubleshooting

| Issue | Fix |
|-------|------|
| Cannot resolve Lombok | Install Lombok plugin + enable annotation processing |
| JWT errors | Ensure jjwt dependencies are added |
| Mongo DB connection failed | Ensure `mongod` is running |
| 403 Forbidden | Missing Authorization header |
