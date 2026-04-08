
# Employee Performance Tracker API

A robust Spring Boot backend for managing employee performance, reviews, and goal tracking in organizations.

## Project Overview

Enables HR and managers to track employee goals, submit performance reviews, analyze review cycles, and identify top performers. Designed for scalability and production use.

## Tech Stack

- Spring Boot
- Spring Data JPA
- PostgreSQL
- Jakarta Bean Validation
- Maven


## Features / APIs

- **Create Employee**
- **Submit Review**
- **Get Employee Reviews**
- **Cycle Summary**: Average rating, top performer, goal stats
- **Filter Employees**: By department & minRating, with pagination
- **Create Goal**

---

## API Documentation

### 1. Create Employee

- **POST** `/api/employees`
- **Body:**
	```json
	{
		"name": "Jane Doe",
		"department": "Engineering",
		"role": "Developer",
		"joiningDate": "2024-01-15"
	}
	```
- **Response:** Employee details

### 2. Submit Review

- **POST** `/api/reviews`
- **Body:**
	```json
	{
		"employeeId": 1,
		"cycleId": 2,
		"rating": 4,
		"reviewerNotes": "Consistent performer"
	}
	```
- **Response:** Review details

### 3. Get Employee Reviews

- **GET** `/api/employees/{id}/reviews`
- **Response:** List of reviews for the employee

### 4. Cycle Summary

- **GET** `/api/cycles/{id}/summary`
- **Response:**
	```json
	{
		"averageRating": 4.2,
		"topPerformer": "Jane Doe",
		"completedGoals": 12,
		"missedGoals": 2
	}
	```

### 5. Filter Employees

- **GET** `/api/employees?department=Engineering&minRating=3.5&page=0&size=10&sort=name`
- **Query Params:**
	- `department` (optional)
	- `minRating` (optional)
	- `page` (default: 0)
	- `size` (default: 10)
	- `sort` (optional, e.g., `name`)
- **Response:** Paginated list of employees

### 6. Create Goal

- **POST** `/api/goals`
- **Body:**
	```json
	{
		"employeeId": 1,
		"reviewCycleId": 2,
		"title": "Complete Project X",
		"status": "pending"
	}
	```
- **Response:** Goal details

---

## Architecture

- **Controller → Service → Repository** pattern
- **DTO-based** request/response design
- **Global Exception Handling** for consistent API errors

## Database Design

- **Entities**: Employee, Goal, PerformanceReview, ReviewCycle
- **Relations**: Employees have Goals & Reviews; Reviews linked to Cycles

## Key Optimizations

- **Pagination**: `Pageable` for large datasets
- **N+1 Query Fix**: `@EntityGraph` / `JOIN FETCH` for efficient loading
- **Transaction Management**: `@Transactional` on service methods
- **Validation**: Jakarta Bean Validation on DTOs

## System Design Considerations

- **Scaling**: Stateless REST, ready for horizontal scaling
- **Caching**: Recommend Redis for frequent queries
- **Heavy Aggregations**: Use optimized SQL/DB views for summaries

## How to Run

1. **Configure PostgreSQL** in `src/main/resources/application.yml`:
	 ```yaml
	 spring:
		 datasource:
			 url: jdbc:postgresql://localhost:5432/employee_db
			 username: your_user
			 password: your_password
	 ```
2. **Start Application**:
	 ```bash
	 mvn spring-boot:run
	 ```

## Sample API Request

**Create Employee**
```http
POST /api/employees
Content-Type: application/json

{
	"name": "Jane Doe",
	"department": "Engineering",
	"email": "jane.doe@example.com"
}
```
