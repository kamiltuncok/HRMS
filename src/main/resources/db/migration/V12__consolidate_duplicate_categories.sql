-- Consolidate duplicate seed categories.
--
-- V10 seeded an English category set (IT, Marketing, Finance, Design, Sales) and
-- keyword-mapped job titles to it. The application (DataLoader) then seeds the
-- Turkish set actually used by jobs (IT / Yazılım, Pazarlama, Finans, ...). This
-- left both sets in the table, so the category filter listed English entries that
-- match no jobs. Merge English -> Turkish and drop the unused English rows.

UPDATE job_titles SET category_id = (SELECT id FROM categories WHERE name = 'IT / Yazılım')
  WHERE category_id = (SELECT id FROM categories WHERE name = 'IT');
UPDATE job_titles SET category_id = (SELECT id FROM categories WHERE name = 'Pazarlama')
  WHERE category_id = (SELECT id FROM categories WHERE name = 'Marketing');
UPDATE job_titles SET category_id = (SELECT id FROM categories WHERE name = 'Finans')
  WHERE category_id = (SELECT id FROM categories WHERE name = 'Finance');
UPDATE job_titles SET category_id = (SELECT id FROM categories WHERE name = 'Tasarım')
  WHERE category_id = (SELECT id FROM categories WHERE name = 'Design');
UPDATE job_titles SET category_id = (SELECT id FROM categories WHERE name = 'Satış')
  WHERE category_id = (SELECT id FROM categories WHERE name = 'Sales');

-- Clear any remaining references (e.g. a fresh database where the Turkish rows do
-- not exist yet at migration time) so the delete below cannot violate the FK.
UPDATE job_titles SET category_id = NULL
  WHERE category_id IN (SELECT id FROM categories WHERE name IN ('IT', 'Marketing', 'Finance', 'Design', 'Sales'));

DELETE FROM categories WHERE name IN ('IT', 'Marketing', 'Finance', 'Design', 'Sales');
