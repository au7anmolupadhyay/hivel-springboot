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
