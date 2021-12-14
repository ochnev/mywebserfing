-- MANUALLY
-- cleanup data
DELETE FROM reminder;
DELETE FROM bookmark;
DELETE FROM folder;
DELETE FROM top_bookmark;
DELETE FROM realm;
DELETE FROM app_user;
-- OR delete tables
DROP TABLE reminder;
DROP TABLE bookmark;
DROP TABLE folder;
DROP TABLE top_bookmark;
DROP TABLE realm;
DROP TABLE app_user;
