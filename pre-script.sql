
INSERT INTO user_mst 
(created_by, created_date, last_modified_by, last_modified_date, email, user_name, password, otp) 
VALUES 
('admin', '2024-05-13 14:04:09', 'admin', '2024-05-13 14:04:09', 'admin@gmail.com', 'admin', '8r4J4YsnLMK8JtqNog8Wxgp5oEoQ45GE', '123456');

INSERT INTO roles_mst 
(created_by, created_date, last_modified_by, last_modified_date, description, role) 
VALUES 
('admin', '2024-05-13 14:04:09', 'admin', '2024-05-13 14:04:09', 'Admin role', 'Admin');

INSERT INTO user_role_mpg 
(created_by, created_date, last_modified_by, last_modified_date, role_id, user_id) 
VALUES 
('admin', '2024-05-13 14:04:09', 'admin', '2024-05-13 14:04:09', 1, 1);
