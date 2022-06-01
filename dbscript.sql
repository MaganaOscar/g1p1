CREATE DATABASE teamp1;

DROP TABLE IF EXISTS employee_details;
DROP TABLE IF EXISTS reimbursement_details;

CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE employee_details(
    emp_id INT GENERATED ALWAYS AS IDENTITY,
    password TEXT NOT NULL,
    job_code INT NOT NULL,
    fname VARCHAR (20),
    lname VARCHAR (20),
    email VARCHAR (50),
    PRIMARY KEY (emp_id));

CREATE TABLE reimbursement_details(
    rb_id INT GENERATED ALWAYS AS IDENTITY,
    rb_status VARCHAR (10),
    rb_amount NUMERIC(5,2),
    rb_timestamp TIMESTAMP,
    emp_id INT NOT NULL,
    PRIMARY KEY (rb_id),
    FOREIGN KEY (emp_id) REFERENCES employee_details(emp_id));

INSERT INTO employee_details(password, job_code, fname, lname, email) 
VALUES (crypt('password123',gen_salt('bf')), 100, 'John', 'Smith', 'jsmith@gmail.com');

INSERT INTO employee_details(password, job_code, fname, lname, email) 
VALUES (crypt('password456',gen_salt('bf')), 100, 'Mark', 'Johnson', 'mjohnson@gmail.com');

INSERT INTO employee_details(password, job_code, fname, lname, email) 
VALUES (crypt('password789',gen_salt('bf')), 100, 'Sally', 'Silverton', 'ss@gmail.com');

INSERT INTO employee_details(password, job_code, fname, lname, email) 
VALUES (crypt('MrManager',gen_salt('bf')), 200, 'Mr', 'Manager', 'mm@gmail.com');

INSERT INTO reimbursement_details(rb_status, rb_amount, rb_timestamp, emp_id) 
VALUES ('pending', 100.00, current_timestamp, 1);
INSERT INTO reimbursement_details(rb_status, rb_amount, rb_timestamp, emp_id) 
VALUES ('pending', 150.00, current_timestamp, 2);
INSERT INTO reimbursement_details(rb_status, rb_amount, rb_timestamp, emp_id) 
VALUES ('pending', 50.00, current_timestamp, 3);
INSERT INTO reimbursement_details(rb_status, rb_amount, rb_timestamp, emp_id) 
VALUES ('pending', 200.00, current_timestamp, 1);
INSERT INTO reimbursement_details(rb_status, rb_amount, rb_timestamp, emp_id) 
VALUES ('pending', 175.00, current_timestamp, 2);

SELECT * FROM employee_details;
SELECT * FROM reimbursement_details;