-- =========================
-- EMPLOYEE
-- =========================
CREATE TABLE employee (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    department VARCHAR(100) NOT NULL,
    role VARCHAR(100) NOT NULL,
    joining_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_employee_department ON employee(department);

-- =========================
-- REVIEW CYCLE
-- =========================
CREATE TABLE review_cycle (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    CHECK (start_date < end_date)
);

-- =========================
-- PERFORMANCE REVIEW
-- =========================
CREATE TABLE performance_review (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    review_cycle_id BIGINT NOT NULL,
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    reviewer_notes TEXT,
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_review_employee
        FOREIGN KEY (employee_id)
        REFERENCES employee(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_review_cycle
        FOREIGN KEY (review_cycle_id)
        REFERENCES review_cycle(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_review_employee_cycle
ON performance_review(employee_id, review_cycle_id);

CREATE INDEX idx_review_cycle
ON performance_review(review_cycle_id);

-- =========================
-- GOALS
-- =========================
CREATE TABLE goals (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    review_cycle_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL,

    CONSTRAINT fk_goals_employee
        FOREIGN KEY (employee_id)
        REFERENCES employee(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_goals_cycle
        FOREIGN KEY (review_cycle_id)
        REFERENCES review_cycle(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_goal_status
        CHECK (status IN ('PENDING', 'COMPLETED', 'MISSED'))
);

CREATE INDEX idx_goals_employee_cycle
ON goals(employee_id, review_cycle_id);

CREATE INDEX idx_goals_cycle_status
ON goals(review_cycle_id, status);