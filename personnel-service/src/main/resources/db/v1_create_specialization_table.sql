CREATE SEQUENCE IF NOT EXISTS specialization_table_table_seq
      MINVALUE 1
      MAXVALUE 999999999
      INCREMENT BY 1
      START WITH 1;

CREATE TABLE IF NOT EXISTS specialization_table (
    id BIGSERIAL PRIMARY KEY,
    spec VARCHAR(255) NOT NULL
);

