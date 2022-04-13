CREATE TABLE IF NOT EXISTS test.person
(
    id BIGSERIAL,
    first_name VARCHAR(50),
    surname  VARCHAR(50),
    address1  VARCHAR(50),
    address2  VARCHAR(50),
    city  VARCHAR(50),
    state  VARCHAR(50),
    post_code  VARCHAR(50),
    country_code  VARCHAR(50),
    gender gender_enum,
    date_of_birth DATE,
    primary key (id)
);

