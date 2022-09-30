CREATE DATABASE drone_db;
use drone_db;

CREATE TABLE drone(
    serial_number VARCHAR(100) NOT NULL,
    model enum('Lightweight', 'Middleweight', 'Cruiserweight', 'Heavyweight') NOT NULL,
    weight decimal(5,2) NOT NULL,
    battery_capacity decimal(5,2) NOT NULL,
    state enum('IDLE', 'LOADING', 'DELIVERING', 'DELIVERED', 'RETURNING'),
    created_date BIGINT NOT NULL,
    modified_date BIGINT NOT NULL,
    CONSTRAINT chk_weight CHECK (weight < 500),
    CONSTRAINT chk_battery_capacity CHECK (battery_capacity <= 100),
    PRIMARY KEY(serial_number)
);

CREATE TABLE medication (
    name VARCHAR(50) NOT NULL CHECK (name not like '%[^a-zA-Z0-9_-]%'),
    weight decimal(5,2) NOT NULL,
    code VARCHAR(50) NOT NULL CHECK (code not like '%[^A-Z0-9_]%'),
    image BLOB,
    created_date BIGINT NOT NULL,
    modified_date BIGINT NOT NULL,
    PRIMARY KEY(code)
);

CREATE TABLE drone_items(
    serial_number VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL,
    quantity INT NOT NULL,
    created_date BIGINT NOT NULL,
    modified_date BIGINT NOT NULL,
    CONSTRAINT pk_D PRIMARY KEY (serial_number, code),
    CONSTRAINT fk_serial_number FOREIGN KEY (serial_number) REFERENCES drone(serial_number),
    CONSTRAINT fk_code FOREIGN KEY (code) REFERENCES medication(code)
);

CREATE TABLE drone_history(
    serial_number VARCHAR(100) NOT NULL,
    battery_capacity decimal(5,2) NOT NULL,
    created_date BIGINT NOT NULL,
    modified_date BIGINT NOT NULL,
    CONSTRAINT pk_DH PRIMARY KEY (serial_number, battery_capacity),
    CONSTRAINT fk_serial_num FOREIGN KEY (serial_number) REFERENCES drone(serial_number)
);