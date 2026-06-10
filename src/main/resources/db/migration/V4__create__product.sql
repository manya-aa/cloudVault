CREATE TABLE PRODUCT(

    id SERIAL PRIMARY KEY,
    name VARCHAR(50),
                    price NUMERIC(10,2),
                    description VARCHAR(1000),
                    stock_qty INTEGER,
                    category_id INTEGER REFERENCES category(id),
                    supplier_id INTEGER REFERENCES supplier(id),
                    image_url VARCHAR(50)


);