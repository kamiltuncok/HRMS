# HRMS: Recruitment Platform API

Spring Boot 3 REST API for a two-sided job platform. Job seekers build a résumé and apply to postings. Employers publish postings and review applicants.

This repository is the backend. The React client lives in [hrms-frontend](https://github.com/kamiltuncok/hrms-frontend).

---

## Features

- **Accounts:** separate registration for job seekers and employers, and JWT login. Accounts lock for 15 minutes after 5 failed login attempts.
- **Password reset:** e-mailed, single-use reset links that expire after 30 minutes. Only the token's SHA-256 hash is stored.
- **Job postings:** create, update, activate and deactivate, filter active postings by description or company. Job titles are grouped into categories, and each posting has a city and a type of work.
- **Résumés:** summary, contact and social links, work experience, education, skills and languages, plus a profile photo and a CV file (upload and download).
- **Applications:** a job seeker applies to a posting. The employer lists the applications for their postings and updates each application's status.
- **Rate limiting:** Bucket4j token buckets per client IP on the login, registration and forgot-password endpoints.
- **API docs:** OpenAPI and Swagger UI through springdoc.

## Technology Stack

| Area | Technology |
|---|---|
| Runtime | Java 21, Spring Boot 3.2.4 (Web, Validation, Data JPA, Security, Mail) |
| Database | PostgreSQL, Flyway (`flyway-database-postgresql` 10.10) |
| Auth | Spring Security, JJWT 0.12.5 (HMAC-signed tokens), BCrypt (strength 12) |
| Mapping | MapStruct 1.5.5, Lombok |
| Rate limiting | Bucket4j 8.10 |
| Files | Local disk (`uploads/`), Cloudinary SDK (photo endpoint) |
| Docs | springdoc-openapi 2.5 (Swagger UI) |
| Build | Maven Wrapper |

## Architecture

A layered Spring application: controllers → services (`*Service` interfaces with `*Manager` implementations) → Spring Data repositories (`*Dao`) → JPA entities. Services return a `Result` / `DataResult<T>` envelope (`{ success, message, data }`) that controllers pass through. MapStruct mappers convert entities to response DTOs.

```
src/main/java/kodlamaio/HRMS/
├── config/          # SecurityConfig, ApplicationConfig (auth provider, BCrypt), WebMvcConfig (/uploads),
│                    # DataLoader (seed data)
├── controller/      # REST controllers (/api/...)
├── service/         # Service interfaces and *Manager implementations, EmailService, PasswordValidatorService
├── repository/      # Spring Data JPA repositories (*Dao)
├── entities/concretes/
├── dto/             # Request / response records
├── mapper/          # MapStruct mappers
├── security/        # JwtService, JwtAuthenticationFilter, RateLimitingFilter
├── exception/       # GlobalExceptionHandler and domain exceptions
└── core/            # Result types, Cloudinary adapter, BusinessRules, legacy verification/validation helpers
src/main/resources/
├── application-example.properties   # configuration template (tracked)
├── application.properties           # local configuration (git-ignored)
└── db/migration/                    # Flyway scripts V1–V12
```

### Security pipeline

`RateLimitingFilter` → `JwtAuthenticationFilter` → Spring Security authorization. Sessions are stateless, CSRF is disabled, and the response headers set `Content-Security-Policy: default-src 'self'`, `X-Frame-Options: DENY` and HSTS. CORS allows `http://localhost:5173` with credentials.

| Endpoint | Limit per IP |
|---|---|
| `/api/auth/login*`, `/api/auth/register*` | 10 requests / minute |
| `/api/auth/forgot-password` | 5 requests / minute |

`GlobalExceptionHandler` maps validation errors, bad credentials, locked accounts (423), weak passwords, invalid reset tokens, access denied and data-integrity violations to the `Result` envelope.

## Data Model

`users` is the base table for both account types. `job_seekers` and `employers` extend it through JPA `JOINED` inheritance.

| Table | Purpose |
|---|---|
| `users` | E-mail, BCrypt password, role, profile image URL, failed-attempt counter, lock time, version |
| `roles` | `ROLE_JOBSEEKER`, `ROLE_EMPLOYER` |
| `job_seekers` / `employers` | Profile data for each account type |
| `job_advertisements` | Employer, job title, city, type of work, description, start and end dates, open positions, active flag |
| `job_applications` | Job seeker × posting, application date, status (string, default `PENDING`) |
| `resumes` | One per job seeker: summary, contact details, social links, CV file metadata, photo |
| `job_experiences`, `schools`, `skills`, `languages` | Résumé sections |
| `categories`, `job_titles`, `cities`, `types_of_work` | Reference data |
| `password_reset_tokens` | SHA-256 token hash, expiry, used flag |
| `photos` | Cloudinary photo URLs |

On every startup, `DataLoader` seeds whatever reference data is missing: roles, cities, job titles, types of work and categories. It also seeds a set of **demo employers and job postings**, which are development data with a fixed password defined in `DataLoader`.

## Getting Started

### Prerequisites

- JDK 21
- PostgreSQL (or Docker)

### 1. Start PostgreSQL

```bash
docker compose up -d
```

`docker-compose.yml` starts `postgres:15-alpine` with database `HRMS` and user `postgres` on port 5432. Its password is set in the compose file, so pass the same value as `DB_PASSWORD` in the next step.

### 2. Configure

```bash
cp src/main/resources/application-example.properties src/main/resources/application.properties
```

The template reads every secret from an environment variable and falls back to a placeholder:

| Variable | Default | Purpose |
|---|---|---|
| `DB_URL` | `jdbc:postgresql://localhost:5432/HRMS` | JDBC URL |
| `DB_USERNAME` / `DB_PASSWORD` | `postgres` / `changeme` | Database credentials |
| `JWT_SECRET` | placeholder | HMAC key, at least 256 bits (`openssl rand -hex 32`) |
| `MAIL_HOST` / `MAIL_PORT` | `smtp.gmail.com` / `587` | SMTP for password-reset e-mails |
| `MAIL_USERNAME` / `MAIL_PASSWORD` | placeholders | SMTP credentials (a Gmail app password for Gmail) |
| `FRONTEND_URL` | `http://localhost:5173` | Base URL for the reset links |

Other settings in the template: tokens expire after 24 hours (`application.security.jwt.expiration`), and uploads are limited to 5 MB.

### 3. Run

```bash
./mvnw spring-boot:run        # Windows: mvnw.cmd spring-boot:run
```

The API starts on `http://localhost:8080`, and Flyway applies `V1`–`V12` on startup. Swagger UI is at `http://localhost:8080/swagger-ui.html` and the OpenAPI document at `/v3/api-docs`.

## API Overview

| Resource | Endpoints |
|---|---|
| Auth | `POST /api/auth/registerforjobseeker`, `registerforemployer`, `login`, `forgot-password`, `reset-password`; `GET /api/auth/validate-reset-token` |
| Job postings | `/api/jobadvertisements`: `getall`, `getactive`, `getactivebydesc`, `getactivebycompany`, `getbyid`, `add`, `update`, `updatestatus`, `delete` |
| Applications | `/api/jobapplications`: `add`, `getall`, `getbyjobseekerid`, `getbyemployerid`, `updatestatus` |
| Résumés | `/api/resumes`: `getall`, `getbyjobseekerid`, `add`, `update`, `uploadphoto`, `uploadcv`, `downloadcv` |
| Résumé sections | `/api/jobexperiences`, `/api/schools`, `/api/skills`, `/api/languages`: `getall`, `add`, `delete` (plus sorted variants) |
| Accounts | `/api/jobseekers`, `/api/employers`, `/api/users` |
| Files | `POST /api/files/upload-profile-image` (stored under `uploads/profiles/`, served at `/uploads/**`), `/api/photos` (Cloudinary) |
| Reference data | `/api/cities`, `/api/jobtitles` (incl. `getbycategory`), `/api/categories`, `/api/typeofwork` |

## Testing

```bash
./mvnw test
```

The suite contains a single `@SpringBootTest` context-load test, so it needs a reachable database.

## Known Limitations

- **Authorization is not enforced on most endpoints.** `SecurityConfig` marks almost every `/api/**` route `permitAll`, including résumés, applications, job seekers and employers. The JWT filter identifies the caller, but nothing restricts access by role or ownership. The role guards in the frontend are cosmetic.
- `RateLimitingFilter` keys buckets on the first `X-Forwarded-For` value, which clients can spoof.
- The configuration template sets both Flyway and `spring.jpa.hibernate.ddl-auto=update`, so Hibernate can still change the schema outside the migrations.
- `DataLoader` creates demo accounts in every environment.
- Profile images and CVs are stored on the local filesystem.

## License

[MIT](LICENSE)
