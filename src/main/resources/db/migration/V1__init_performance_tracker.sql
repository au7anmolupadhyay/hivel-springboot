-- Employee Performance Tracker Schema Migration
-- Compatible with Flyway/Liquibase

-- Enable pgcrypto for UUID generation
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- =========================
-- 1. EMPLOYEE TABLE
-- =========================
CREATE TABLE employee (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR NOT NULL,
    department VARCHAR NOT NULL,
    role VARCHAR NOT NULL,
    joining_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT now(),
    -- Index for department filter queries
    CONSTRAINT employee_department_idx CHECK (department <> '')
);

-- Index for department (for filter queries)
CREATE INDEX idx_employee_department ON employee(department);

-- =========================
-- 2. REVIEW CYCLE TABLE
-- =========================
CREATE TABLE review_cycle (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR NOT NULL UNIQUE,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    CONSTRAINT review_cycle_dates CHECK (start_date < end_date)
);

-- =========================
-- 3. PERFORMANCE REVIEW TABLE
-- =========================
CREATE TABLE performance_review (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    employee_id UUID NOT NULL,
    review_cycle_id UUID NOT NULL,
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    reviewer_notes TEXT,
    submitted_at TIMESTAMP DEFAULT now(),
    CONSTRAINT fk_performance_review_employee FOREIGN KEY (employee_id)
        REFERENCES employee(id) ON DELETE CASCADE,
    CONSTRAINT fk_performance_review_cycle FOREIGN KEY (review_cycle_id)
        REFERENCES review_cycle(id) ON DELETE CASCADE
    -- Multiple reviews per employee per cycle are allowed
);

-- Composite index for fast lookup by employee and cycle
CREATE INDEX idx_performance_review_employee_cycle ON performance_review(employee_id, review_cycle_id);
-- Index for aggregation by review_cycle
CREATE INDEX idx_performance_review_cycle ON performance_review(review_cycle_id);
-- Covering index for employee filter queries (department + minRating)
-- (Assumes join with employee, so index on employee.department is already present)

-- =========================
-- 4. GOAL TABLE
-- =========================
CREATE TABLE goal (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    employee_id UUID NOT NULL,
    review_cycle_id UUID NOT NULL,
    title VARCHAR NOT NULL,
    status VARCHAR NOT NULL,
    CONSTRAINT fk_goal_employee FOREIGN KEY (employee_id)
        REFERENCES employee(id) ON DELETE CASCADE,
    CONSTRAINT fk_goal_review_cycle FOREIGN KEY (review_cycle_id)
        REFERENCES review_cycle(id) ON DELETE CASCADE,
    CONSTRAINT goal_status_check CHECK (status IN ('PENDING', 'COMPLETED', 'MISSED'))
);

-- Composite index for fast lookup by employee and cycle
CREATE INDEX idx_goal_employee_cycle ON goal(employee_id, review_cycle_id);
-- Index for summary queries by review_cycle and status
CREATE INDEX idx_goal_cycle_status ON goal(review_cycle_id, status);
-- Partial index for quick access to completed goals in a cycle (bonus)
CREATE INDEX idx_goal_cycle_completed ON goal(review_cycle_id) WHERE status = 'COMPLETED';

-- =========================
-- COMMENTS & DESIGN NOTES
-- =========================
-- All UUIDs are generated using pgcrypto for portability and security.
-- Foreign keys use ON DELETE CASCADE to maintain referential integrity and simplify cleanup.
-- Composite and covering indexes are added for high-read and aggregation queries.
-- Check constraints enforce data integrity at the database level.
-- Schema is normalized: no redundant data, all relations are via foreign keys.
-- No SERIALs are used; UUIDs are used for all primary keys.
-- Employee Performance Tracker Schema Migration
-- Suitable for PostgreSQL or H2

CREATE TABLE employee (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    joining_date DATE NOT NULL
);

CREATE TABLE review_cycle (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    CONSTRAINT chk_dates CHECK (start_date < end_date)
);

CREATE TABLE performance_review (
    id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL REFERENCES employee(id) ON DELETE CASCADE,
    review_cycle_id INTEGER NOT NULL REFERENCES review_cycle(id) ON DELETE CASCADE,
    rating INTEGER NOT NULL CHECK (rating BETWEEN 1 AND 5),
    reviewer_notes TEXT,
    submitted_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT unique_review UNIQUE (employee_id, review_cycle_id, submitted_at)
);

CREATE INDEX idx_review_employee_cycle ON performance_review(employee_id, review_cycle_id);
CREATE INDEX idx_review_cycle ON performance_review(review_cycle_id);

CREATE TABLE goal (
    id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL REFERENCES employee(id) ON DELETE CASCADE,
    review_cycle_id INTEGER NOT NULL REFERENCES review_cycle(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    status VARCHAR(10) NOT NULL CHECK (status IN ('pending', 'completed', 'missed')),
    CONSTRAINT unique_goal UNIQUE (employee_id, review_cycle_id, title)
);

CREATE INDEX idx_goal_employee_cycle ON goal(employee_id, review_cycle_id);
