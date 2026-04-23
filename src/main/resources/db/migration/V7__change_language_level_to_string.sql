-- Drop the integer CHECK constraint on language_level
ALTER TABLE languages DROP CONSTRAINT IF EXISTS languages_language_level_check;

-- Change language_level column type from integer to varchar
ALTER TABLE languages ALTER COLUMN language_level TYPE VARCHAR(10) USING language_level::VARCHAR;
