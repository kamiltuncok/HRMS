# HRMS — Role-Based Recruitment & Applicant Tracking Backend

A robust, enterprise-grade RESTful backend for a role-based Human Resource Management & Applicant Tracking System (ATS) built with **Java 21** and **Spring Boot 3.2**. The platform supports dual user domains (**Job Seekers** and **Employers**): employers publish and manage job advertisements and evaluate incoming applications, while job seekers build comprehensive CV profiles (work experience, education, technical skills, languages, profile media) and submit job applications.

This repository is the **backend service**. The companion React frontend is located in [hrms-frontend](https://github.com/kamiltuncok/hrms-frontend).

---

## Recruiter & Engineering Summary

- **Primary Stack**: Java 21, Spring Boot 3.2.4 (Spring Data JPA, Spring Security, Spring Mail, Spring Validation), PostgreSQL, Flyway, JWT (jjwt 0.12.5), MapStruct 1.5.5, Bucket4j 8.10.1, SpringDoc OpenAPI.
- **Key Engineering Highlights**: Strict N-tier modular architecture (`Controller → Service / Manager → Repository`), Flyway version-controlled schema migrations (`V1`–`V11`), decoupled entity-DTO layer via compile-time MapStruct mappers, defensive security measures (IP rate limiting with token buckets, account lockout policies, SHA-256 hashed password reset tokens, BCrypt strength 12), and dual-mode file storage (Cloudinary + local filesystem fallback).
- **Primary Technical Challenge**: Designing a flexible resume and multi-step application workflow with granular access controls, transactional integrity, and hardened authentication endpoints resilient against brute-force attacks.

---

## System Architecture

```mermaid
flowchart TB
    %% ================= GLOBAL STYLES =================
    classDef clientStyle fill:#1e293b,stroke:#38bdf8,stroke-width:2px,color:#f8fafc,rx:8,ry:8;
    classDef securityStyle fill:#2e1065,stroke:#a855f7,stroke-width:2px,color:#f8fafc,rx:8,ry:8;
    classDef controllerStyle fill:#082f49,stroke:#0ea5e9,stroke-width:2px,color:#f8fafc,rx:8,ry:8;
    classDef serviceStyle fill:#064e3b,stroke:#10b981,stroke-width:2px,color:#f8fafc,rx:8,ry:8;
    classDef dataStyle fill:#451a03,stroke:#f59e0b,stroke-width:2px,color:#f8fafc,rx:8,ry:8;
    classDef infraStyle fill:#0f172a,stroke:#64748b,stroke-width:2px,color:#f8fafc,rx:8,ry:8;

    %% ================= NODES & SUBGRAPHS =================
    subgraph ClientTier [" 🌐 CLIENT PRESENTATION LAYER "]
        Client["React 19 SPA Client<br/><b>hrms-frontend :5173</b><br/><i>(TypeScript + Tailwind + Zustand)</i>"]:::clientStyle
    end

    subgraph BackendApp [" ⚡ SPRING BOOT 3.2 APPLICATION RUNTIME (:8080) "]
        
        subgraph SecurityPipeline [" 🛡️ Security & Defensive Gateway "]
            RateLimiter{{"Bucket4j Rate Limiter<br/><i>Token Bucket IP Defense</i>"}}:::securityStyle
            JwtFilter["JwtAuthenticationFilter<br/><i>HMAC-SHA Signed Bearer Verification</i>"]:::securityStyle
            SecHeaders["Security Headers Pipeline<br/><i>HSTS, CSP, X-Frame-Options</i>"]:::securityStyle
        end

        subgraph PresentationLayer [" 📡 REST API Controller Endpoints "]
            AuthController["AuthController<br/><code>/api/auth</code>"]:::controllerStyle
            JobAdvController["JobAdvertisementsController<br/><code>/api/jobadvertisements</code>"]:::controllerStyle
            AppController["JobApplicationsController<br/><code>/api/jobapplications</code>"]:::controllerStyle
            ResumeController["ResumesController<br/><code>/api/resumes</code>"]:::controllerStyle
            RefControllers["Reference Controllers<br/><code>/api/cities, /api/jobtitles</code>"]:::controllerStyle
        end

        subgraph BusinessLayer [" ⚙️ Domain Services & Business Logic "]
            Managers["Core Business Managers<br/><i>(Transactional Domain Logic)</i>"]:::serviceStyle
            Mappers[["MapStruct DTO Mappers<br/><i>(Compile-Time Zero-Reflection)</i>"]]:::serviceStyle
            EmailService["MailService<br/><i>(SMTP Single-Use Token Delivery)</i>"]:::serviceStyle
            StorageAdapter["File Storage Provider Adapter<br/><i>(Cloudinary & Disk Fallback)</i>"]:::serviceStyle
        end

        subgraph PersistenceLayer [" 🗄️ Persistence & Schema Orchestration "]
            Repositories[("Spring Data JPA Repositories<br/><i>(Hibernate / Dynamic Queries)</i>")]:::dataStyle
            FlywayEngine["Flyway Migration Engine<br/><i>(Versioned SQL Migrations V1-V11)</i>"]:::dataStyle
        end
    end

    subgraph ExternalInfra [" ☁️ INFRASTRUCTURE & EXTERNAL CLOUD "]
        Postgres[("PostgreSQL 14+ Relational Database<br/><code>localhost:5432 / Docker</code>")]:::infraStyle
        CloudinaryCDN["Cloudinary Media CDN<br/><i>(Applicant Resumes & Avatars)</i>"]:::infraStyle
        SmtpRelay["Transactional SMTP Server<br/><i>(Gmail / Mailgun Relay)</i>"]:::infraStyle
    end

    %% ================= CONNECTIONS =================
    Client ==>|"HTTPS / REST JSON + Bearer JWT"| RateLimiter
    RateLimiter --> JwtFilter
    JwtFilter --> SecHeaders
    SecHeaders ==> PresentationLayer

    PresentationLayer ==> Managers
    Managers <-->|"Type-Safe DTO Transformation"| Mappers
    Managers ==> Repositories
    Managers -->|"Trigger Password Reset Loop"| EmailService
    Managers -->|"Stream Upload Payloads"| StorageAdapter

    Repositories ==>|"JPA / SQL Queries"| Postgres
    FlywayEngine -.->|"Schema Baseline & Migrate on Boot"| Postgres
    EmailService -->|"TLS Port 587"| SmtpRelay
    StorageAdapter -->|"Media Asset Upload"| CloudinaryCDN
```

---

## Key Features & Technical Highlights

### 1. Hardened Authentication & Defensive Security
- **Stateless JWT Authorization**: Requests are authenticated via standard `Bearer` tokens signed with HMAC-SHA algorithms.
- **Brute-Force & Denial-of-Service Protection**: Sensitive authentication routes are protected by **Bucket4j** rate limiting (e.g. login capped at 10 requests/minute per client IP).
- **Account Lockout Policy**: Tracks consecutive failed login attempts (`failedAttempts`) and enforces temporary account locks (`lockTime`) to mitigate credential stuffing.
- **Secure Password Reset Loop**: Implements single-use, time-limited reset tokens stored exclusively as SHA-256 hashes in the database; raw tokens are delivered solely via transactional SMTP emails.
- **Password Encryption**: Password hashing utilizing **BCrypt** with an elevated work factor (strength 12).
- **HTTP Security Headers**: Configured Content-Security-Policy (CSP), Strict-Transport-Security (HSTS), and `X-Frame-Options: DENY`.

### 2. Versioned Database Schema with Flyway
- Schema changes are managed declaratively through versioned SQL migration scripts located in `src/main/resources/db/migration/` (`V1` through `V11`).
- Eliminates schema drift across development, CI/CD, and production environments without relying on error-prone runtime ORM auto-generation.

### 3. Clean DTO Separation via MapStruct
- Complete isolation between internal JPA database entities and external REST API payloads.
- Compile-time generated **MapStruct** mappers ensure zero-reflection performance and type-safe data transformations.

### 4. Media & Document Management
- Pluggable media upload architecture supporting **Cloudinary** cloud CDN uploads alongside local filesystem storage for applicant résumés, documents, and profile avatars.

### 5. Standardized Response & Exception Handling
- Unified response contracts across all endpoints using `Result`, `DataResult<T>`, `SuccessDataResult<T>`, and `ErrorDataResult<T>`.
- Centralized `GlobalExceptionHandler` intercepting validation errors (`MethodArgumentNotValidException`), domain business exceptions, and security access violations.

---

## Technology Stack

| Category | Technologies |
|---|---|
| **Runtime & Framework** | Java 21, Spring Boot 3.2.4 |
| **Persistence & Data** | Spring Data JPA (Hibernate), PostgreSQL, Flyway Migration Engine |
| **Security & Auth** | Spring Security 6, JJWT (io.jsonwebtoken 0.12.5), BCrypt, Bucket4j 8.10.1 |
| **Object Mapping** | MapStruct 1.5.5.Final, Project Lombok |
| **Documentation & Mail** | SpringDoc OpenAPI (Swagger UI), Spring Boot Starter Mail (JavaMailSender) |
| **File Storage** | Cloudinary Java SDK, Local Disk Storage Adapter |
| **Build & Tooling** | Maven (Maven Wrapper `mvnw` included) |

---

## Project Structure

```
HRMS/
├── src/main/java/kodlamaio/HRMS/
│   ├── api/controllers/         # REST API Controllers (Auth, Jobs, Applications, Resumes, etc.)
│   ├── business/
│   │   ├── abstracts/           # Service layer contracts (IJobAdvertisementService, etc.)
│   │   ├── concretes/           # Business managers with domain logic
│   │   ├── constants/           # Business exception and response messages
│   │   ├── dtos/                # Request / Response Data Transfer Objects
│   │   └── mappers/             # MapStruct mapper interfaces
│   ├── core/
│   │   ├── adapters/            # Cloudinary & external integration adapters
│   │   ├── exception/           # GlobalExceptionHandler & custom exception types
│   │   ├── security/            # JWT TokenProvider, UserDetails, SecurityConfig, Bucket4j
│   │   └── utilities/results/   # Result, DataResult, Success/Error response structures
│   ├── dataAccess/abstracts/    # Spring Data JPA repository interfaces
│   ├── entities/concretes/      # JPA Entity models (JobSeeker, Employer, JobAdvertisement, etc.)
│   └── HrmsApplication.java     # Spring Boot application entry point
├── src/main/resources/
│   ├── db/migration/            # Flyway versioned SQL migrations (V1__... to V11__...)
│   ├── application.properties   # Main configuration (git-ignored for security)
│   └── application-example.properties # Template configuration with environment placeholders
├── pom.xml                      # Maven project definition & dependencies
└── docker-compose.yml           # PostgreSQL container orchestration
```

---

## API Overview

Interactive Swagger UI documentation is available at `http://localhost:8080/swagger-ui.html` when running locally.

| Controller Group | Base Route | Key Responsibilities |
|---|---|---|
| **Authentication** | `/api/auth` | Job seeker / Employer registration, login, forgot password, token validation, password reset |
| **Job Advertisements** | `/api/jobadvertisements` | Postings CRUD, active postings feed, filter by city/title/employer, status toggling |
| **Job Applications** | `/api/jobapplications` | Apply to vacancies, list applications by candidate or employer, update hiring status |
| **Résumés & Profiles** | `/api/resumes` | Candidate CV generation, biography, GitHub/LinkedIn links, profile photo & PDF upload |
| **Résumé Details** | `/api/jobexperiences`, `/api/schools`, `/api/skills`, `/api/languages` | Granular experience, education, language, and skillset management |
| **User & Employer** | `/api/employers`, `/api/jobseekers`, `/api/users` | Profile administration and company verification |
| **Lookups & Metadata** | `/api/categories`, `/api/cities`, `/api/jobtitles`, `/api/typeofwork` | Standardized reference classification data |

---

## Database & Entity Relationships

The PostgreSQL relational schema features strict foreign key constraints and audit metadata:

```mermaid
erDiagram
    USERS ||--o| JOB_SEEKERS : "specializes to"
    USERS ||--o| EMPLOYERS : "specializes to"
    
    USERS {
        int id PK
        varchar email "UK"
        varchar password_hash
        varchar user_type "SEEKER | EMPLOYER"
        boolean is_verified
        timestamp created_at
    }

    JOB_SEEKERS {
        int user_id PK,FK
        varchar first_name
        varchar last_name
        varchar national_identity "UK"
        date date_of_birth
    }

    EMPLOYERS {
        int user_id PK,FK
        varchar company_name
        varchar web_address
        varchar phone_number
        boolean is_confirmed_by_system
    }

    EMPLOYERS ||--o{ JOB_ADVERTISEMENTS : "publishes"
    CITIES ||--o{ JOB_ADVERTISEMENTS : "locates"
    JOB_TITLES ||--o{ JOB_ADVERTISEMENTS : "classifies"
    TYPE_OF_WORK ||--o{ JOB_ADVERTISEMENTS : "defines"

    JOB_ADVERTISEMENTS {
        int id PK
        int employer_id FK
        int job_title_id FK
        int city_id FK
        int type_of_work_id FK
        text description
        decimal min_salary
        decimal max_salary
        int open_positions
        date application_deadline
        boolean is_active
        timestamp created_at
    }

    JOB_SEEKERS ||--o| RESUMES : "owns"
    RESUMES ||--o{ JOB_EXPERIENCES : "contains"
    RESUMES ||--o{ SCHOOLS : "contains"
    RESUMES ||--o{ SKILLS : "contains"
    RESUMES ||--o{ LANGUAGES : "contains"

    RESUMES {
        int id PK
        int job_seeker_id FK
        varchar photo_url
        varchar github_link
        varchar linkedin_link
        text cover_letter
        timestamp created_at
    }

    JOB_EXPERIENCES {
        int id PK
        int resume_id FK
        varchar company_name
        varchar position
        date start_date
        date quit_date
    }

    SCHOOLS {
        int id PK
        int resume_id FK
        varchar school_name
        varchar department
        date start_date
        date graduation_date
    }

    SKILLS {
        int id PK
        int resume_id FK
        varchar skill_name
    }

    LANGUAGES {
        int id PK
        int resume_id FK
        varchar language_name
        int level "1-5"
    }

    JOB_SEEKERS ||--o{ JOB_APPLICATIONS : "submits"
    JOB_ADVERTISEMENTS ||--o{ JOB_APPLICATIONS : "receives"

    JOB_APPLICATIONS {
        int id PK
        int job_seeker_id FK
        int job_advertisement_id FK
        varchar status "APPLIED | UNDER_REVIEW | ACCEPTED | REJECTED"
        text notes
        timestamp applied_at
    }
```

---

## Getting Started

### Prerequisites

- **Java Development Kit (JDK)**: Version 21
- **PostgreSQL**: Version 14+ running on port `5432` (or via Docker)
- **Maven**: (or use the included `./mvnw` / `mvnw.cmd` wrapper)

### 1. Configuration Setup

Copy the example configuration template:

```bash
cp src/main/resources/application-example.properties src/main/resources/application.properties
```

Configure your environment variables (or update `application.properties` directly):

| Variable | Required | Purpose |
|---|---|---|
| `DB_URL` | No | JDBC Connection URL (default: `jdbc:postgresql://localhost:5432/HRMS`) |
| `DB_USERNAME` | No | PostgreSQL user (default: `postgres`) |
| `DB_PASSWORD` | **Yes** | PostgreSQL password |
| `JWT_SECRET` | **Yes** | Cryptographic 256+ bit secret key for signing JWT tokens |
| `MAIL_USERNAME` | For Reset | SMTP username for outbound transactional emails |
| `MAIL_PASSWORD` | For Reset | SMTP password / App Password |
| `FRONTEND_URL` | No | Base frontend URL for reset links (default: `http://localhost:5173`) |

### 2. Start PostgreSQL via Docker (Optional)

```bash
docker-compose up -d
```

### 3. Build & Run the Backend

```bash
# Windows (PowerShell)
$env:JAVA_HOME = 'C:\Program Files\Java\jdk-21'
.\mvnw.cmd spring-boot:run

# macOS / Linux
./mvnw spring-boot:run
```

The service initializes on `http://localhost:8080`. Flyway automatically runs pending database migrations on startup.

---

## Engineering Decisions & Trade-offs

1. **Flyway Migrations over Hibernate `ddl-auto`**:
   - *Rationale*: Automatic schema generation (`ddl-auto=update`) poses severe data corruption risks in production and lacks rollback tracking. Flyway guarantees repeatable, audited SQL executions across all deployment environments.
2. **Bucket4j In-Memory Token Bucket**:
   - *Rationale*: Prevents brute-force attacks at the application tier without requiring an external Redis infrastructure dependency during early-stage deployments.
3. **MapStruct Compile-Time Mapping over Reflection (e.g. ModelMapper)**:
   - *Rationale*: Reflection-based mappers introduce runtime overhead and fail silently on field name discrepancies. MapStruct generates standard Java code at build time, failing fast during compilation if mapping contracts break.

---

## Known Limitations & Roadmap

- **Distributed Rate Limiting**: The current Bucket4j configuration operates in-memory; scaling across multiple instances requires backed storage (e.g. Redis).
- **Search Optimization**: Filtering job advertisements uses relational database indexes; integrating Elasticsearch or PostgreSQL full-text search would enhance relevance ranking for large-scale catalogues.
- **Automated Integration Test Suite**: Unit and MockMvc test coverage across business managers and controllers is targeted for expansion.
