-- V3__remove_identity_and_address.sql
ALTER TABLE job_seekers DROP COLUMN IF EXISTS identity_number;
ALTER TABLE resumes DROP COLUMN IF EXISTS adress;
