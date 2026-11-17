# HelpDesk Lite - Internal Support Ticket System

HelpDesk Lite is a robust, RESTful backend API built with Spring Boot for managing internal support tickets, tracking status workflows, assigning agents, and maintaining a comprehensive audit history of ticket transitions.

---

## 🚀 Features

- **Ticket Management**: Create, retrieve, filter, and assign support tickets.
- **Strict Status Workflow**: Enforces valid status transitions (`OPEN` $\rightarrow$ `IN_PROGRESS` $\rightarrow$ `RESOLVED` $\rightarrow$ `CLOSED`).
- **Audit Trail**: Automatically logs every status change with timestamps and actor references in `TicketStatusHistory`.
- **Advanced Filtering**: Filter tickets dynamically by `status` and `priority`.
- **DTO Architecture**: Clean separation between database entities and API request/response payloads.
- **Global Exception Handling**: Standardized JSON error responses for validation errors, invalid state transitions, and missing resources.
- **Interactive API Docs**: Integrated Swagger UI (OpenAPI 3) for live endpoint testing.

---

## 🛠️ Tech Stack

- **Java**: 17
- **Framework**: Spring Boot 3.x (Spring Web, Spring Data JPA, Spring Validation)
- **Database**: PostgreSQL (Neon Serverless Postgres)
- **Utilities**: Lombok, Springdoc OpenAPI (Swagger UI)
- **Build Tool**: Maven

---

## ⚙️ Prerequisites

Before running this project, ensure you have the following installed:
- **JDK 17** or higher
- **Maven 3.8+**
- A **PostgreSQL** database instance (configured for Neon PostgreSQL)

---

## 🔧 Configuration & Setup

1. **Clone the repository:**
   ```bash
   git clone <your-repository-url>
   cd Internal_Support_Ticket_System
   ```

2. **Configure Database Credentials:**
   Update `src/main/resources/application.yml` with your PostgreSQL connection details:
   ```yaml
   spring:
     application:
       name: Internal_Support_Ticket_System

     datasource:
       url: jdbc:postgresql://<your-host>/<your-db>?sslmode=require&channelBinding=require
       username: <your-username>
       password: <your-password>
       driver-class-name: org.postgresql.Driver

     jpa:
       hibernate:
         ddl-auto: update
       show-sql: true
       properties:
         hibernate:
           format_sql: true
           dialect: org.hibernate.dialect.PostgreSQLDialect
   ```

3. **Build and Run the Application:**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
   The server will start on `http://localhost:8080`.

---

## 📖 API Documentation (Swagger UI)

Once the application is running, you can explore and test all endpoints interactively via Swagger UI:
- **Swagger UI**: `http://localhost:8080/swagger-ui/index.html`

---

## 🔌 API Endpoints Overview

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/tickets` | Create a new support ticket |
| `GET` | `/api/tickets` | List all tickets (optional query params: `?status=OPEN&priority=HIGH`) |
| `GET` | `/api/tickets/{id}` | Retrieve details of a specific ticket |
| `PATCH` | `/api/tickets/{id}/status` | Update ticket status with workflow validation |
| `PATCH` | `/api/tickets/{id}/assignee` | Assign or reassign a support agent to a ticket |
| `GET` | `/api/tickets/{id}/history` | View the status change audit history for a ticket |

---

## 🔄 Ticket Status Workflow

Tickets follow a strict lifecycle to maintain data integrity:
1. **`OPEN`** $\rightarrow$ Ticket is created and awaiting triage.
2. **`IN_PROGRESS`** $\rightarrow$ Agent is actively working on the issue (Valid from `OPEN`).
3. **`RESOLVED`** $\rightarrow$ Issue has been fixed (Valid from `IN_PROGRESS`).
4. **`CLOSED`** $\rightarrow$ Ticket is finalized and archived (Valid **only** from `RESOLVED`).

*Any attempt to skip states or move backward will result in a `400 Bad Request` response.*

---

## 🧪 Testing with Postman

You can test the API using the provided Postman collection (`HelpDesk_Lite_API.postman_collection.json`):
1. Open **Postman** and click **Import**.
2. Upload the `HelpDesk_Lite_API.postman_collection.json` file.
3. Run the requests sequentially starting from **1. Create Ticket**.
