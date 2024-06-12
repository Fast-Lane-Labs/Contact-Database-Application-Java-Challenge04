DELETE FROM contact;
DELETE FROM sqlite_sequence WHERE name='contact';
INSERT INTO contact (first_name, last_name, email) VALUES ('John', 'Doe', 'john.doe@example.com');
INSERT INTO contact (first_name, last_name, email) VALUES ('Jane', 'Doe', 'jane.doe@example.com');
