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

insert into drone (serial_number, model, weight, battery_capacity, state, created_date,modified_date) values ('1001', 'Lightweight', 100.5, 80.3, 'IDLE', 1664698524754, 1664698524754);
insert into drone (serial_number, model, weight, battery_capacity, state, created_date,modified_date) values ('1002', 'Lightweight', 150, 50, 'IDLE',
1664678760000, 1664678760000);
insert into drone (serial_number, model, weight, battery_capacity, state, created_date,modified_date) values ('1003', 'Heavyweight', 400, 40, 'IDLE',
1664678780000, 1664678780000);
insert into drone (serial_number, model, weight, battery_capacity, state, created_date,modified_date) values ('1004', 'Middleweight', 200, 20, 'IDLE', 1664678790000, 1664678790000);
insert into drone (serial_number, model, weight, battery_capacity, state, created_date,modified_date) values ('1005', 'Middleweight', 150.5, 60.5, 'IDLE', 1664678795000, 1664678795000);
insert into drone (serial_number, model, weight, battery_capacity, state, created_date,modified_date) values ('1006', 'Heavyweight', 480, 30, 'IDLE', 1664678800000, 1664678800000);
insert into drone (serial_number, model, weight, battery_capacity, state, created_date,modified_date) values ('1007', 'Lightweight', 120.5, 90.6, 'IDLE', 1664678810000, 1664678810000);

insert into medication (name, weight, code, created_date, modified_date) values ('paracetomol', 10.4, '101', 1664679480000, 1664679480000);
insert into medication (name, weight, code, created_date, modified_date) values ('asprin', 15.3, '102', 1664679490000, 1664679490000);
insert into medication (name, weight, code, created_date, modified_date) values ('plasters', 8.5, '103', 1664679500000, 1664679500000);
insert into medication (name, weight, code, created_date, modified_date) values ('cetrizine', 12, '104', 1664679510000, 1664679510000);
insert into medication (name, weight, code, created_date, modified_date) values ('insulin', 20, '105', 1664679520000, 1664679520000);