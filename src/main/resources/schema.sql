-- Drop Tables if they exist (to start with an empty database each time)
DROP TABLE IF EXISTS movements;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS clients;
DROP TABLE IF EXISTS persons;
DROP TABLE IF EXISTS system_parameters;

-- Create Persons Table
CREATE TABLE persons (
    id VARCHAR(36) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    gender VARCHAR(20),
    age INTEGER CHECK (age >= 0),
    email VARCHAR(100),
    phone VARCHAR(20),
    address VARCHAR(200),
    PRIMARY KEY (id),
    CONSTRAINT uk_persons_email UNIQUE (email)
);

-- Create Clients Table
CREATE TABLE clients (
    id VARCHAR(36) NOT NULL,
    identification VARCHAR(20) NOT NULL,
    password VARCHAR(100) NOT NULL,
    status BOOLEAN NOT NULL,
    role VARCHAR(20),
    PRIMARY KEY (id),
    CONSTRAINT fk_clients_person FOREIGN KEY (id) REFERENCES persons(id),
    CONSTRAINT uk_clients_identification UNIQUE (identification)
);

-- Create Accounts Table
CREATE TABLE accounts (
    account_id VARCHAR(36) NOT NULL,
    client_id VARCHAR(36) NOT NULL,
    account_number VARCHAR(20) NOT NULL,
    account_type VARCHAR(20) NOT NULL,
    initial_balance DOUBLE PRECISION NOT NULL,
    status BOOLEAN NOT NULL,
    PRIMARY KEY (account_id),
    CONSTRAINT uk_accounts_number UNIQUE (account_number),
    CONSTRAINT fk_accounts_client FOREIGN KEY (client_id) REFERENCES clients(id)
);

-- Create Movements Table
CREATE TABLE movements (
    movement_id VARCHAR(36) NOT NULL,
    account_id VARCHAR(36) NOT NULL,
    movement_date TIMESTAMP NOT NULL,
    movement_type VARCHAR(20) NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    balance DOUBLE PRECISION NOT NULL,
    status BOOLEAN NOT NULL,
    description VARCHAR(255),
    PRIMARY KEY (movement_id),
    CONSTRAINT fk_movements_account FOREIGN KEY (account_id) REFERENCES accounts(account_id)
);

-- Create System Parameters Table
CREATE TABLE system_parameters (
    id SERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    parameter_value VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);

-- Insert Default Daily Debit Limit
INSERT INTO system_parameters (code, parameter_value, description)
VALUES ('DAILY_DEBIT_LIMIT', '1000', 'Límite diario de retiro para débitos');