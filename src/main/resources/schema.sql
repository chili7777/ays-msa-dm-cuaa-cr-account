-- Create Persons Table
CREATE TABLE IF NOT EXISTS persons (
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
CREATE TABLE IF NOT EXISTS clients (
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
CREATE TABLE IF NOT EXISTS accounts (
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
CREATE TABLE IF NOT EXISTS movements (
    movement_id VARCHAR(36) NOT NULL,
    account_id VARCHAR(36) NOT NULL,
    movement_date TIMESTAMP NOT NULL,
    movement_type VARCHAR(20) NOT NULL,
    amount DOUBLE PRECISION NOT NULL CHECK (amount > 0),
    balance DOUBLE PRECISION NOT NULL,
    status BOOLEAN NOT NULL,
    description VARCHAR(255),
    PRIMARY KEY (movement_id),
    CONSTRAINT fk_movements_account FOREIGN KEY (account_id) REFERENCES accounts(account_id)
);

-- Create System Parameters Table
CREATE TABLE IF NOT EXISTS system_parameters (
    id SERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    parameter_value VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);

-- Insert Default Daily Debit Limit
INSERT INTO system_parameters (code, parameter_value, description)
SELECT 'DAILY_DEBIT_LIMIT', '1000', 'Límite diario de retiro para débitos'
WHERE NOT EXISTS (SELECT 1 FROM system_parameters WHERE code = 'DAILY_DEBIT_LIMIT');