--liquibase formatted sql

--changeset vadim:1
--comment: Rename daily_calories_norm to daily_calorie_norm for consistency
ALTER TABLE users RENAME COLUMN daily_calories_norm TO daily_calorie_norm;
--rollback:"ALTER TABLE users RENAME COLUMN daily_calorie_norm TO daily_calories_norm"

--changeset vadim:2
--comment: Update column comment after rename
COMMENT ON COLUMN users.daily_calorie_norm IS 'Daily calorie norm calculated using Harris-Benedict formula';