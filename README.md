# Employee Performance Tracker API

## System Design Write-up

### Scaling for 500 Concurrent Managers
To support 500 concurrent managers running reports, the system should:
- **Use a robust RDBMS** (e.g., PostgreSQL) with connection pooling (HikariCP) to handle concurrent queries efficiently.
- **Horizontal scaling**: Deploy multiple instances of the Spring Boot app behind a load balancer (e.g., NGINX, AWS ELB).
- **Read replicas**: For heavy read/reporting load, use database replicas to offload reporting queries from the primary DB.
- **Optimize queries**: Use proper indexes (as in the schema) and avoid N+1 queries with JPA fetch joins or DTO projections.
- **Stateless services**: Ensure the backend is stateless for easy scaling.

### If /cycles/{id}/summary Gets Slow at 100k+ Reviews
- **Optimize SQL**: Use aggregate queries at the DB level (e.g., AVG, MAX, COUNT with GROUP BY) instead of in-memory aggregation.
- **Materialized views**: Precompute and cache summary stats for each cycle, refreshing periodically or on data change.
- **Batch processing**: For very large datasets, use background jobs to update summary tables.
- **Profile and tune**: Use query profilers and DB EXPLAIN plans to identify bottlenecks.

### Caching Strategy
- **Cache review cycle summaries**: Use Redis or in-memory cache for /cycles/{id}/summary results, with invalidation on new review/goal changes.
- **Cache static data**: Departments, roles, and review cycle metadata can be cached at the service layer.
- **Avoid caching per-employee reviews** unless access patterns show repeated queries for the same data.

## Assumptions
- Employees can have multiple reviews per cycle (e.g., from different reviewers).
- Goal status is per employee per cycle per title.
- API is designed for internal use, so authentication is not included here.

---

See `src/main/resources/db/migration/V1__init_performance_tracker.sql` for schema.

Run with H2 or PostgreSQL. All endpoints are under `/employees`, `/reviews`, and `/cycles`.
