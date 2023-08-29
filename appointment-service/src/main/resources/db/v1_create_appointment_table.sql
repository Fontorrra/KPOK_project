CREATE SEQUENCE IF NOT EXISTS appointment_table_seq
    MINVALUE 1
    MAXVALUE 999999999
    INCREMENT BY 1
    START WITH 1;

CREATE TABLE IF NOT EXISTS appointment_table (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    id_doctor BIGINT NOT NULL ,
    id_patient BIGINT NOT NULL,
    date_time TIMESTAMP NOT NULL,
    confirmation BOOLEAN NOT NULL,
    comment VARCHAR(255),
    ts BIGINT
);