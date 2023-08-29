CREATE SEQUENCE IF NOT EXISTS patient_table_seq
    MINVALUE 1
    MAXVALUE 999999999
    INCREMENT BY 1
    START WITH 1;

CREATE TABLE IF NOT EXISTS patient_table (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL ,
    last_name VARCHAR(255) NOT NULL,
    father_name VARCHAR(255),
    age INTEGER NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    ts BIGINT
);
