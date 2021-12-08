CREATE TABLE IF NOT EXISTS currency_name
(
    id serial primary key,
    letter_code varchar(3) NOT NULL,
    name varchar(300) NOT NULL
);

CREATE TABLE IF NOT EXISTS exchange_rate
(
    id serial primary key,
    letter_code varchar(3) NOT NULL,
    rate double precision NOT NULL,
    date date NOT NULL
);

CREATE TABLE IF NOT EXISTS archived_exchange_rate
(
    id serial primary key,
    letter_code varchar(3) NOT NULL,
    rate double precision NOT NULL,
    date date NOT NULL
);
