# Federal Holiday API

## Overview

Federal Holiday Project is a RESTful Spring Boot application that provides APIs to manage federal holidays for different countries.

The application supports:

1. Add federal holidays
2. Update existing holidays
3. List all holidays
4. Fetch holidays by country
5. Upload holidays through CSV file
6. Delete holidays

Currently supported countries:

- USA
- CANADA

The application is designed using clean architecture principles and follows standard Spring Boot practices with controller, service, repository, DTO, and exception handling layers.

---

# Technology Stack

- Java 17
- Spring Boot 4.1.0
- Spring Web
- Spring Data JPA
- Hibernate
- H2 Database
- Maven
- Swagger OpenAPI
- JUnit 5
- Mockito
- JaCoCo

---

# Project Structure


src
 ├── main
 │    └── java
 │         └── com.demo.federalholidayproject
 │              ├── controller
 │              ├── service
 │              ├── repository
 │              ├── entity
 │              ├── dto
 │              ├── exception
 │              └── config
 │
 └── test
      └── java
           └── com.demo.federalholidayproject
                ├── controller
                └── service


# Clone Repository

Clone the repository: git clone https://github.com/prabhakaran270798/project-federal-holiday.git


Navigate to project directory: cd project-federal-holiday


# How to Run the Application

## Prerequisites

Install the following:

- Java 17
- Maven

Verify installation:

java -version
mvn -version


## Build Application
Run: mvn clean install


## Start Application
Run:mvn spring-boot:run

Application will start at: http://localhost:8080


# Database Details

The application uses **H2 in-memory database**.

The assignment allows usage of an in-memory datastore. H2 was selected to simplify local execution without requiring any external database installation or Docker setup.

Database details:
==================
Database URL: jdbc:h2:mem:holidaydb
Username:sa
Password:(empty)
H2 Console: http://localhost:8080/h2-console
JDBC URL in H2 Console: jdbc:h2:mem:holidaydb


# Swagger Documentation
Swagger UI is available at:http://localhost:8080/swagger-ui/index.html

Swagger provides API documentation and allows testing APIs directly from the browser.

---

# API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | /api/holidays/addHolidays | Add a holiday |
| GET | /api/holidays/getAllHolidays | Get all holidays |
| GET | /api/holidays/getHolidays/country/{country} | Get holidays by country |
| PUT | /api/holidays/updateHolidays/{id} | Update holiday |
| DELETE | /api/holidays/deleteHolidays/{id} | Delete holiday |
| POST | /api/holidays/uploadCSV/upload | Upload CSV file |

---

# API Details

## Add Holiday
Endpoint: POST /api/holidays/addHolidays

Request:

json
{
  "country": "USA",
  "name": "Independence Day",
  "date": "2026-07-04"
}

Date format: yyyy-MM-dd


## Get All Holidays
Endpoint: GET /api/holidays/getAllHolidays

## Get Holidays By Country
Endpoint: GET /api/holidays/getHolidays/country/{country}
Example: GET /api/holidays/getHolidays/country/USA


## Update Holiday
Endpoint: PUT /api/holidays/updateHolidays/{id}
Example: PUT /apiholidays//updateHolidays/1


## Delete Holiday
Endpoint: DELETE /api/holidays/deleteHolidays/{id}
Example: DELETE /api/holidays/deleteHolidays/1


## Upload Holidays Using CSV
Endpoint:POST /api/holidays/uploadCSV
Request type: multipart/form-data
Parameter:file

CSV format:
csv
country,name,date
USA,Independence Day,2026-07-04
CANADA,Canada Day,2026-07-01


# Validation Rules

- Country is mandatory
- Holiday name is mandatory
- Holiday date is mandatory
- Date format should be `yyyy-MM-dd`

# Exception Handling

The application implements global exception handling for:

- Holiday not found
- Invalid file upload
- Validation errors
- Invalid input data


# Postman Collection

A Postman collection is provided for testing all APIs.
Location: Postman/Federal Holiday API.postman_collection.json
Import this collection into Postman to execute API requests.



# Testing

Testing frameworks:

- JUnit 5
- Mockito
- MockMvc

Run tests: mvn clean test


# Code Coverage

JaCoCo is configured for test coverage reporting.
Generate coverage report: mvn clean test
Coverage report location: target/site/jacoco/index.html
Current test coverage:87%


# Author
Prabhakaran Kumar