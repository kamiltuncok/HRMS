CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    role_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role_id INT REFERENCES roles(id),
    is_active BOOLEAN NOT NULL DEFAULT true
);

CREATE TABLE job_seekers (
    user_id INT PRIMARY KEY REFERENCES users(user_id) ON DELETE CASCADE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    birth_date DATE,
    phone_number VARCHAR(20)
);

CREATE TABLE employers (
    user_id INT PRIMARY KEY REFERENCES users(user_id) ON DELETE CASCADE,
    company_name VARCHAR(255) NOT NULL,
    web_address VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NOT NULL
);

CREATE TABLE job_titles (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE cities (
    id SERIAL PRIMARY KEY,
    city_name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE types_of_work (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE job_advertisements (
    id SERIAL PRIMARY KEY,
    employer_id INT NOT NULL REFERENCES employers(user_id),
    job_title_id INT NOT NULL REFERENCES job_titles(id),
    city_id INT NOT NULL REFERENCES cities(id),
    type_of_work_id INT REFERENCES types_of_work(id),
    description TEXT NOT NULL,
    min_salary DOUBLE PRECISION,
    max_salary DOUBLE PRECISION,
    open_positions INT NOT NULL DEFAULT 1,
    start_date DATE NOT NULL,
    end_date DATE,
    status BOOLEAN NOT NULL DEFAULT true,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE photos (
    id SERIAL PRIMARY KEY,
    photo_url VARCHAR(500) NOT NULL,
    public_id VARCHAR(255) NOT NULL
);

CREATE TABLE resumes (
    id SERIAL PRIMARY KEY,
    jobseeker_id INT NOT NULL REFERENCES job_seekers(user_id),
    photo_id INT REFERENCES photos(id),
    summary TEXT
);

CREATE TABLE job_experiences (
    id SERIAL PRIMARY KEY,
    resume_id INT NOT NULL REFERENCES resumes(id),
    job_title_id INT REFERENCES job_titles(id),
    company_name VARCHAR(255),
    position_name VARCHAR(255),
    start_date DATE,
    end_date DATE,
    is_active BOOLEAN
);

CREATE TABLE languages (
    id SERIAL PRIMARY KEY,
    resume_id INT NOT NULL REFERENCES resumes(id),
    language_name VARCHAR(100) NOT NULL,
    language_level INT CHECK (language_level BETWEEN 1 AND 5)
);

CREATE TABLE skills (
    id SERIAL PRIMARY KEY,
    resume_id INT NOT NULL REFERENCES resumes(id),
    skill_name VARCHAR(100) NOT NULL
);

CREATE TABLE schools (
    id SERIAL PRIMARY KEY,
    resume_id INT NOT NULL REFERENCES resumes(id),
    school_name VARCHAR(255) NOT NULL,
    department_name VARCHAR(255) NOT NULL,
    start_date DATE,
    graduate_date DATE
);

CREATE TABLE validation_codes (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(user_id),
    code VARCHAR(255) NOT NULL,
    is_confirmed BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    confirmed_date TIMESTAMP
);
