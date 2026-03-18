-- V6__schema_cleanup.sql
-- Clean up resumes table
ALTER TABLE resumes RENAME COLUMN resume_id TO id;
ALTER TABLE resumes RENAME COLUMN adress TO address;
ALTER TABLE resumes RENAME COLUMN github TO github_url;
ALTER TABLE resumes RENAME COLUMN linkedin TO linkedin_url;

-- Clean up job_experiences table
ALTER TABLE job_experiences RENAME COLUMN job_position TO position_name;

-- Clean up schools table (assuming V1 names if Hibernate didn't overwrite)
-- V1 had: school_name, department, date_of_start, date_of_graduation
-- We'll standardize to: school_name, department_name, start_date, graduate_date
ALTER TABLE schools RENAME COLUMN department TO department_name;
ALTER TABLE schools RENAME COLUMN date_of_start TO start_date;
ALTER TABLE schools RENAME COLUMN date_of_graduation TO graduate_date;
