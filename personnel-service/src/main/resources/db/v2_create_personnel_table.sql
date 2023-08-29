CREATE SEQUENCE IF NOT EXISTS personnel_table_seq
      MINVALUE 1
      MAXVALUE 999999999
      INCREMENT BY 1
      START WITH 1;

CREATE TABLE IF NOT EXISTS personnel_table (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    father_name VARCHAR(255),
    experience INTEGER NOT NULL,
    specialization INTEGER NOT NULL,
    clinic_experience INTEGER NOT NULL,
    additional_info VARCHAR(255),
    ts BIGINT
);

ALTER TABLE personnel_table
    ADD CONSTRAINT fk_specialization FOREIGN KEY (specialization) REFERENCES specialization_table (id);
