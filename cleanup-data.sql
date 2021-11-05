-- MANUALLY
-- cleanup data
DELETE FROM recurrence;
DELETE FROM bookmark;
DELETE FROM folder;
DELETE FROM realm;
DELETE FROM app_user;
-- OR delete tables
DROP TABLE recurrence;
DROP TABLE bookmark;
DROP TABLE folder;
DROP TABLE realm;
DROP TABLE app_user;
