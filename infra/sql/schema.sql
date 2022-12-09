CREATE SCHEMA meteostation;

CREATE TABLE IF NOT EXISTS meteostation.meteodata
(
    id                     SERIAL PRIMARY KEY,
    registration_date_time TIMESTAMP,
    temperature            FLOAT(2),
    pressure               FLOAT(2),
    elevation              FLOAT(2)
)