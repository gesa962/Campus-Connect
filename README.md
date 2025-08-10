# Campus Connect API

A RESTful Spring Boot API for managing campus events where admins can create, edit, and delete events, and students can join events.

## Features

- **User Management**: Two roles - Admin and Student
- **Event Management**: Create, read, update, delete events
- **Event Participation**: Students can join and leave events
- **JWT Authentication**: Secure API with JWT tokens
- **Role-based Access Control**: Different permissions for different roles
- **H2 Database**: In-memory database for development

## Technology Stack

- **Spring Boot 3.2.0**
- **Spring Security** with JWT
- **Spring Data JPA**
- **H2 Database**
- **Maven**
- **Java 17**

## Project Structure

```
src/main/java/com/campusconnect/
├── CampusConnectApplication.java
├── config/
│   ├── SecurityConfig.java
│   └── DataInitializer.java
├── controller/
│   ├── AuthController.java
│   ├── EventController.java
│   └── UserController.java
├── dto/
│   ├── EventDto.java
│   ├── LoginRequest.java
│   ├── LoginResponse.java
│   └── UserDto.java
├── entity/
│   ├── Event.java
│   └── User.java
├── exception/
│   └── GlobalExceptionHandler.java
├── repository/
│   ├── EventRepository.java
│   └── UserRepository.java
├── security/
│   └── JwtAuthenticationFilter.java
└── service/
    ├── EventService.java
    ├── JwtService.java
    └── UserService.java
```

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

### Database

- H2 Console: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:campusconnect`
- Username: `sa`
- Password: `password`

## API Endpoints

### Authentication

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/login` | Login user |
| POST | `/api/auth/register` | Register user |
| POST | `/api/auth/register/student` | Register student |
| POST | `/api/auth/register/admin` | Register admin |

### Events (Public)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/events/public/all` | Get all active events |
| GET | `/api/events/public/upcoming` | Get upcoming events |
| GET | `/api/events/public/open-registration` | Get events with open registration |
| GET | `/api/events/public/category/{category}` | Get events by category |
| GET | `/api/events/public/search?keyword={keyword}` | Search events |
| GET | `/api/events/public/{eventId}` | Get event by ID |

### Events (Protected)

| Method | Endpoint | Description | Role Required |
|--------|----------|-------------|---------------|
| GET | `/api/events/my-events` | Get my created events | Admin |
| GET | `/api/events/my-participations` | Get my participations | Any |
| POST | `/api/events` | Create event | Admin |
| PUT | `/api/events/{eventId}` | Update event | Admin (creator) |
| DELETE | `/api/events/{eventId}` | Delete event | Admin (creator) |
| PUT | `/api/events/{eventId}/deactivate` | Deactivate event | Admin (creator) |
| POST | `/api/events/{eventId}/join` | Join event | Student |
| POST | `/api/events/{eventId}/leave` | Leave event | Student |

### Users

| Method | Endpoint | Description | Role Required |
|--------|----------|-------------|---------------|
| GET | `/api/users/profile` | Get my profile | Any |
| PUT | `/api/users/profile` | Update my profile | Any |
| GET | `/api/users/students` | Get all students | Admin |
| GET | `/api/users/admins` | Get all admins | Admin |
| GET | `/api/users/{userId}` | Get user by ID | Admin |
| PUT | `/api/users/{userId}` | Update user | Admin |
| DELETE | `/api/users/{userId}` | Delete user | Admin |

## Sample Data

The application comes with pre-loaded sample data:

### Users
- **Admin**: username: `admin`, password: `admin123`
- **Student 1**: username: `john.doe`, password: `student123`
- **Student 2**: username: `jane.smith`, password: `student123`
- **Student 3**: username: `mike.johnson`, password: `student123`

### Sample Events
- Campus Tech Meetup
- Spring Sports Tournament
- Career Fair 2024
- Cultural Festival

## Authentication

The API uses JWT (JSON Web Tokens) for authentication. Include the token in the Authorization header:

```
Authorization: Bearer <your-jwt-token>
```

## Request/Response Examples

### Login
```json
POST /api/auth/login
{
  "username": "admin",
  "password": "admin123"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer",
  "id": 1,
  "username": "admin",
  "email": "admin@campus.edu",
  "firstName": "Admin",
  "lastName": "User",
  "role": "ADMIN"
}
```

### Create Event
```json
POST /api/events
Authorization: Bearer <token>

{
  "title": "New Event",
  "description": "Event description",
  "location": "Main Hall",
  "eventDateTime": "2024-02-15T14:00:00",
  "registrationDeadline": "2024-02-13T14:00:00",
  "maxParticipants": 50,
  "category": "Academic",
  "organizer": "Department of Computer Science"
}
```

### Join Event
```json
POST /api/events/1/join
Authorization: Bearer <token>
```

## Error Handling

The API returns consistent error responses:

```json
{
  "status": 400,
  "message": "Error message"
}
```

For validation errors:
```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "title": "Event title is required",
    "email": "Email should be valid"
  }
}
```

## Security

- JWT-based authentication
- Role-based access control
- Password encryption with BCrypt
- CORS enabled for cross-origin requests
- Input validation and sanitization

## Development

### Building the Project
```bash
mvn clean install
```

### Running Tests
```bash
mvn test
```

### Database Schema
The application uses JPA/Hibernate with automatic schema generation. Tables are created automatically on startup.

## License

This project is for educational purposes.
