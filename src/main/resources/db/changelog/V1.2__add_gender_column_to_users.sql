--liquibase formatted sql

--changeset vadim:1
--comment: Add gender column to users table for BMR calculation
ALTER TABLE users
ADD COLUMN gender VARCHAR(16);
--rollback:"ALTER TABLE users DROP COLUMN gender"

--changeset vadim:2
--comment: Set default gender for existing users
UPDATE users SET gender = 'UNKNOWN' WHERE gender IS NULL;
--rollback:"UPDATE users SET gender = NULL WHERE gender = 'UNKNOWN'"

--changeset vadim:3
--comment: Make gender column mandatory
ALTER TABLE users
ALTER COLUMN gender SET NOT NULL;
--rollback:"ALTER TABLE users ALTER COLUMN gender DROP NOT NULL"