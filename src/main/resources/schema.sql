DROP TABLE IF EXISTS INVOICE_DETAIL;
DROP TABLE IF EXISTS INVOICE;
DROP TABLE IF EXISTS CLIENT;
DROP TABLE IF EXISTS PRODUCT;

CREATE TABLE CLIENT(
    client_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    client_name VARCHAR(75) NOT NULL, 
    client_lastname VARCHAR(75) NOT NULL, 
    client_docnumber VARCHAR(11) NOT NULL
);

CREATE TABLE PRODUCT(
    prod_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, 
    prod_description VARCHAR(150) NOT NULL,
    prod_code VARCHAR(50) NOT NULL,
    prod_stock INT NOT NULL,
    prod_price DOUBLE NOT NULL 
);

CREATE TABLE INVOICE(
    inv_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    inv_client_id INT NOT NULL,
    inv_created_at DATETIME NOT NULL,
    inv_total DOUBLE NOT NULL,
    CONSTRAINT fk_inv_client FOREIGN KEY (inv_client_id) REFERENCES client (client_id)
);

CREATE TABLE INVOICE_DETAIL(
    inv_detail_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    inv_detail_inv_id INT NOT NULL,
    inv_detail_amount INT NOT NULL,
    inv_detail_prod_id INT NOT NULL,
    inv_detail_price DOUBLE NOT NULL,
    CONSTRAINT fk_inv_detail_inv_id FOREIGN KEY (inv_detail_inv_id) REFERENCES invoice (inv_id),
    CONSTRAINT fk_inv_detail_prod_id FOREIGN KEY (inv_detail_prod_id) REFERENCES product (prod_id)
);