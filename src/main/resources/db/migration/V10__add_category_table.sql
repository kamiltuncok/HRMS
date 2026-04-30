CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

ALTER TABLE job_titles ADD COLUMN category_id BIGINT;
ALTER TABLE job_titles ADD CONSTRAINT fk_job_titles_categories FOREIGN KEY (category_id) REFERENCES categories(id);

INSERT INTO categories (name) VALUES
('IT'),
('Marketing'),
('Finance'),
('Design'),
('Sales');

-- Soft mapping for existing job titles based on keywords
UPDATE job_titles SET category_id = (SELECT id FROM categories WHERE name = 'IT') WHERE title ILIKE '%Developer%' OR title ILIKE '%Mühendis%' OR title ILIKE '%Software%' OR title ILIKE '%Data%' OR title ILIKE '%Test%';
UPDATE job_titles SET category_id = (SELECT id FROM categories WHERE name = 'Marketing') WHERE title ILIKE '%Marketing%' OR title ILIKE '%Pazarlama%' OR title ILIKE '%SEO%' OR title ILIKE '%Social Media%';
UPDATE job_titles SET category_id = (SELECT id FROM categories WHERE name = 'Finance') WHERE title ILIKE '%Finance%' OR title ILIKE '%Finans%' OR title ILIKE '%Muhasebe%' OR title ILIKE '%Accounting%';
UPDATE job_titles SET category_id = (SELECT id FROM categories WHERE name = 'Design') WHERE title ILIKE '%Design%' OR title ILIKE '%Tasarım%' OR title ILIKE '%UI%' OR title ILIKE '%UX%' OR title ILIKE '%Grafik%';
UPDATE job_titles SET category_id = (SELECT id FROM categories WHERE name = 'Sales') WHERE title ILIKE '%Sales%' OR title ILIKE '%Satış%' OR title ILIKE '%Account Manager%';
