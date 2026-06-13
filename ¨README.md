# Hotellbokningssystem - REST API

A Spring Boot backend for a hotel booking system with JWT security, role-based access control and in-memory data storage.

## Tech Stack

- Java 17
- Spring Boot 3.5
- Spring Security + JWT
- Bean Validation
- Lombok
- JUnit 5 + Mockito

## Users

| Username | Password   | Role  |
|----------|------------|-------|
| `user`   | `user123`  | USER  |
| `admin`  | `admin123` | ADMIN |

## Getting Started

```bash
mvn spring-boot:run
```

Server starts at `http://localhost:8080`

## Endpoints

### Login
```
POST /login
```
```json
{
  "username": "user",
  "password": "user123"
}
```
Returns a JWT token. Include it in all subsequent requests:
```
Authorization: Bearer <token>
```

### Get all rooms
```
GET /api/rooms
```
Required role: USER or ADMIN

### Create booking
```
POST /api/bookings
```
Required role: USER or ADMIN
```json
{
  "guestName": "Anna Svensson",
  "roomType": "Enkelrum",
  "numberOfGuests": 1
}
```

### Get all bookings
```
GET /api/bookings
```
Required role: ADMIN

### Delete booking
```
DELETE /api/bookings/{id}
```
Required role: ADMIN

## Room Capacity

| Room Type  | Total Rooms | Max Guests | Price/Night |
|------------|-------------|------------|-------------|
| Enkelrum   | 10          | 1          | 500 kr      |
| Dubbelrum  | 7           | 2          | 1 000 kr    |
| Svit       | 3           | 3          | 2 000 kr    |

## Error Handling

All errors are returned as structured JSON:
```json
{
  "timestamp": "2024-05-20T14:32:00",
  "status": 409,
  "message": "Inga lediga Enkelrum finns tillgängliga just nu."
}
```

## Project Structure

```
src/main/java/com/hotel/booking/
├── config/
│   └── SecurityConfig.java
├── controller/
│   ├── AuthController.java
│   ├── BookingController.java
│   └── RoomController.java
├── dto/
│   ├── ErrorResponse.java
│   ├── LoginRequest.java
│   └── LoginResponse.java
├── exception/
│   ├── BookingNotFoundException.java
│   ├── GuestCapacityException.java
│   ├── GlobalExceptionHandler.java
│   └── RoomFullyBookedException.java
├── filter/
│   └── JwtFilter.java
├── model/
│   ├── Booking.java
│   └── Room.java
├── repository/
│   └── BookingRepository.java
├── security/
│   └── JwtUtil.java
└── service/
    └── BookingService.java
```