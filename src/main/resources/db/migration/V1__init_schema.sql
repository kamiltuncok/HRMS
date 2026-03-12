CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true
);

CREATE TABLE job_seekers (
    id INT PRIMARY KEY REFERENCES users(user_id) ON DELETE CASCADE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    identity_number VARCHAR(11) UNIQUE NOT NULL,
    birth_year INT NOT NULL
);

CREATE TABLE employers (
    id INT PRIMARY KEY REFERENCES users(user_id) ON DELETE CASCADE,
    company_name VARCHAR(255) NOT NULL,
    web_address VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NOT NULL
);

CREATE TABLE system_personnels (
    id INT PRIMARY KEY REFERENCES users(user_id) ON DELETE CASCADE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL
);

CREATE TABLE candidates (
    id INT PRIMARY KEY REFERENCES users(user_id) ON DELETE CASCADE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    identity_number VARCHAR(11) UNIQUE NOT NULL,
    birth_year INT NOT NULL
);

CREATE TABLE job_titles (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE cities (
    id SERIAL PRIMARY KEY,
    city_name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE job_advertisements (
    id SERIAL PRIMARY KEY,
    employer_id INT NOT NULL REFERENCES employers(id),
    job_title_id INT NOT NULL REFERENCES job_titles(id),
    city_id INT NOT NULL REFERENCES cities(id),
    description TEXT NOT NULL,
    min_salary DOUBLE PRECISION,
    max_salary DOUBLE PRECISION,
    open_positions INT NOT NULL,
    application_deadline DATE,
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE photos (
    id SERIAL PRIMARY KEY,
    photo_url VARCHAR(500) NOT NULL,
    public_id VARCHAR(255) NOT NULL
);

CREATE TABLE resumes (
    resume_id SERIAL PRIMARY KEY,
    jobseeker_id INT NOT NULL REFERENCES job_seekers(id),
    photo_id INT REFERENCES photos(id),
    birth_date DATE,
    adress VARCHAR(255),
    phone VARCHAR(20),
    linkedin VARCHAR(255),
    github VARCHAR(255),
    summary TEXT
);

CREATE TABLE job_experinces (
    job_experince_id SERIAL PRIMARY KEY,
    resume_id INT NOT NULL REFERENCES resumes(resume_id),
    job_title_id INT NOT NULL REFERENCES job_titles(id),
    company_name VARCHAR(255),
    job_position VARCHAR(255),
    date_of_start DATE,
    date_of_end DATE,
    is_leave BOOLEAN
);

CREATE TABLE languages (
    language_id SERIAL PRIMARY KEY,
    resume_id INT NOT NULL REFERENCES resumes(resume_id),
    language_name VARCHAR(100) NOT NULL,
    language_level INT CHECK (language_level BETWEEN 1 AND 5)
);

CREATE TABLE skills (
    skill_id SERIAL PRIMARY KEY,
    resume_id INT NOT NULL REFERENCES resumes(resume_id),
    skill_name VARCHAR(100) NOT NULL
);

CREATE TABLE education_informations (
    id SERIAL PRIMARY KEY,
    resume_id INT NOT NULL REFERENCES resumes(resume_id),
    school_name VARCHAR(255) NOT NULL,
    department VARCHAR(255) NOT NULL,
    date_of_start DATE,
    date_of_graduation DATE
);

CREATE TABLE validation_codes (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(user_id),
    code VARCHAR(255) NOT NULL,
    is_confirmed BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    confirmed_date TIMESTAMP
);
