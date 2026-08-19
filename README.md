# Project Federal Holiday

A lightweight Spring Boot REST service for managing federal holidays across **USA** and **CANADA** — add, update, list, filter by country, bulk-import via CSV, and delete.

Built with a clean layered design: `controller → service → repository`, backed by an in-memory H2 database and documented with Swagger.

---

## Quick Start

```bash
git clone https://github.com/prabhakaran270798/project-federal-holiday.git
cd project-federal-holiday
mvn spring-boot:run
```

App runs at **http://localhost:8080**

| Tool | URL |
|------|-----|
| Swagger UI | http://localhost:8080/swagger-ui/index.html |
| H2 Console | http://localhost:8080/h2-console |

> H2 connection → URL: `jdbc:h2:mem:holidaydb` · User: `sa` · Password: *(blank)*

**Requirements:** Java 17 · Maven

---

## What You Can Do

| # | Action | Endpoint |
|---|--------|----------|
| 1 | Add a holiday | `POST /api/holidays/addHolidays` |
| 2 | List all holidays | `GET /api/holidays/getAllHolidays` |
| 3 | Filter by country | `GET /api/holidays/getHolidays/country/{country}` |
| 4 | Update a holiday | `PUT /api/holidays/updateHolidays/{id}` |
| 5 | Delete a holiday | `DELETE /api/holidays/deleteHolidays/{id}` |
| 6 | Bulk import via CSV | `POST /api/holidays/uploadCSV/upload` |

### Sample Payload

```json
{
  "country": "USA",
  "name": "Independence Day",
  "date": "2026-07-04"
}
```

### CSV Format (for bulk upload)

```csv
country,name,date
USA,Independence Day,2026-07-04
CANADA,Canada Day,2026-07-01
```

*Upload as `multipart/form-data` with the field name `file`.*

---

## Rules & Validation

- `country`, `name`, and `date` are all **required**
- `date` must follow the `yyyy-MM-dd` format
- Only `USA` and `CANADA` are accepted countries

Errors are handled centrally (global exception handler) and returned as clean responses for: holiday not found, invalid CSV upload, validation failures, and bad input.

---

## Tech Stack

`Java 17` · `Spring Boot` · `Spring Web` · `Spring Data JPA` · `Hibernate` · `H2` · `Maven` · `Swagger/OpenAPI` · `JUnit 5` · `Mockito` · `JaCoCo`

---

## Testing & Coverage

```bash
mvn clean test
```

- Frameworks: JUnit 5, Mockito, MockMvc
- Coverage tool: JaCoCo → report at `target/site/jacoco/index.html`
- Current coverage: **93%**

---

## Project Layout

```
src/main/java/com/demo/projectfederalholiday
 ├── controller   → REST endpoints
 ├── service      → business logic
 ├── repository   → data access (JPA)
 ├── entity       → JPA entities
 ├── dto          → request/response models
 ├── exception    → global error handling
 └── config       → Swagger & app config
```

---

## Postman

Import `Postman/Project Federal Holiday API.postman_collection.json` to try every endpoint instantly.

---

**Author:** Prabhakaran Kumar
