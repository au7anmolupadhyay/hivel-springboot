# Employee Performance Tracker API

A robust Spring Boot backend for managing employee performance, reviews, and goal tracking in organizations.

---

## 🚀 Project Overview

This system enables HRs and managers to:
- Track employee performance
- Manage goals across review cycles
- Submit and analyze performance reviews
- Identify top performers

Designed with scalability, clean architecture, and production-grade practices.

---

## 🛠 Tech Stack

- Spring Boot
- Spring Data JPA (Hibernate)
- PostgreSQL
- Jakarta Bean Validation
- Maven

---

## 📌 Features / APIs

### 1. Create Employee
**POST /employees**

```json
{
  "name": "Jane Doe",
  "department": "Engineering",
  "role": "Developer",
  "joiningDate": "2024-01-15"
}
```

---

### 2. Submit Review
**POST /reviews**

```json
{
  "employeeId": 1,
  "cycleId": 2,
  "rating": 4,
  "reviewerNotes": "Consistent performer"
}
```

---

### 3. Get Employee Reviews
**GET /employees/{id}/reviews**

Returns all reviews of an employee with cycle details.

---

### 4. Cycle Summary
**GET /cycles/{id}/summary**

```json
{
  "averageRating": 4.2,
  "topPerformer": "Jane Doe",
  "completedGoals": 12,
  "missedGoals": 2
}
```

---

### 5. Filter Employees (with Pagination)
**GET /employees?department=Engineering&minRating=3.5&page=0&size=10&sort=name**

Query Params:
- `department` (optional)
- `minRating` (optional)
- `page` (default: 0)
- `size` (default: 10)

---

### 6. Create Goal
**POST /goals**

```json
{
  "employeeId": 1,
  "reviewCycleId": 2,
  "title": "Complete Project X",
  "status": "PENDING"
}
```

---

## 🏗 Architecture

- Controller → Service → Repository pattern
- DTO-based request/response design
- Global Exception Handling (`@ControllerAdvice`)
- Clean separation of concerns

---

## 🗄 Database Design

Entities:
- Employee
- PerformanceReview
- ReviewCycle
- Goal

Relationships:
- Employee → OneToMany → Reviews & Goals
- Review → ManyToOne → ReviewCycle

---

## 🗄 Database Schema Management

The database schema is defined using a SQL migration file instead of relying on Hibernate auto DDL.

- **Migration File:** `src/main/resources/schema.sql`
- Defines all tables: `employee`, `review_cycle`, `performance_review`, `goals`
- Includes constraints: `NOT NULL`, `CHECK`, `FOREIGN KEY`
- Optimized with indexes for query performance

**Configuration:**

- `spring.jpa.hibernate.ddl-auto=none`
- `spring.sql.init.mode=always` (initial setup)

After initial setup, SQL initialization can be disabled to prevent re-execution.

> **Note:**
> In production, schema evolution would be managed using tools like Flyway.

---

## ⚡ Key Optimizations

- **Pagination:** Spring Pageable for large datasets
- **N+1 Fix:** `@EntityGraph` for efficient fetching
- **Transactions:** `@Transactional` for data consistency
- **Validation:** Jakarta Bean Validation for request safety
- **Enum Usage:** Strong typing for goal status


## ⚙️ Configuration

Copy example config:

```bash
cp src/main/resources/application-example.yml src/main/resources/application.yml
```

Update DB credentials before running.

---

## ▶️ How to Run

```bash
mvn spring-boot:run
```

Ensure PostgreSQL is running locally.

---

## 💡 Sample API Request

```http
POST /employees
Content-Type: application/json
```

```json
{
  "name": "Jane Doe",
  "department": "Engineering",
  "role": "Developer",
  "joiningDate": "2024-01-15"
}
```

---

## 🧠 Summary

This project demonstrates clean backend design, optimized data handling, and production-ready practices including validation, transaction management, and performance tuning.