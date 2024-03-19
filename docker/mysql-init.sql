CREATE TABLE manufacturers
(
    id               int          NOT NULL AUTO_INCREMENT,
    name             varchar(255) NOT NULL,
    country_iso_code CHAR(3)      NOT NULL,
    created          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated          TIMESTAMP    NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE products
(
    id              int          NOT NULL AUTO_INCREMENT,
    name            varchar(255) NOT NULL,
    manufacturer_id int          NOT NULL,
    created         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated         TIMESTAMP    NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT FK_products_manufacturers
        FOREIGN KEY (manufacturer_id) REFERENCES manufacturers (id)
);

START TRANSACTION;

INSERT INTO manufacturers (id, name, country_iso_code)
VALUES (1, 'BRASQUEM', 'BRA'),
       (2, 'VALE S/A', 'BRA'),
       (3, 'STEEL CO.', 'USA');

INSERT INTO products (name, manufacturer_id)
VALUES ('PRODUCT 1', 1),
       ('PRODUCT 2', 1),
       ('PRODUCT 3', 1),
       ('PRODUCT 4', 1),
       ('PRODUCT 5', 1),
       ('PRODUCT 6', 2),
       ('PRODUCT 7', 2),
       ('PRODUCT 8', 2),
       ('PRODUCT 9', 3),
       ('PRODUCT 10', 3),
       ('PRODUCT 11', 3);

COMMIT;