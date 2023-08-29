CREATE SEQUENCE IF NOT EXISTS vacation_table_seq
    MINVALUE 1
    MAXVALUE 999999999
    INCREMENT BY 1
    START WITH 1;

CREATE TABLE IF NOT EXISTS vacation_table (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    id_doctor BIGINT NOT NULL ,
    begin_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    ts BIGINT
);