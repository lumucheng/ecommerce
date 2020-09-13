DROP TABLE IF EXISTS COMMERCE;

CREATE TABLE COMMERCE
(
    id IDENTITY PRIMARY KEY NOT NULL,
    invoice_no VARCHAR(256) NOT NULL,
    stock_code VARCHAR(256) NOT NULL,
    description VARCHAR(256) NOT NULL,
    quantity INT NOT NULL,
    invoice_date DATETIME,
    unit_price DECIMAL NOT NULL,
    customer_id BIGINT,
    country VARCHAR(256) NOT NULL
);