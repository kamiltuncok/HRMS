-- V4__add_phone_number_to_job_seekers.sql
ALTER TABLE job_seekers ADD COLUMN IF NOT EXISTS phone_number VARCHAR(20);
