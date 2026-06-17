CREATE TABLE PRODUCT(

    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE ,
                    price NUMERIC(10,2) NOT NULL ,
                    description VARCHAR(1000),
                    stock_qty INTEGER,
                    category_id BIGINT REFERENCES category(id),
                    supplier_id BIGINT REFERENCES supplier(id),
                    image_url VARCHAR(50)


);