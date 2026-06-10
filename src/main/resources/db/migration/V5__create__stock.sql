CREATE TABLE STOCK(

    id SERIAL PRIMARY KEY ,
                  product_id INTEGER REFERENCES product(id),
                  qty_change INTEGER,

                movement VARCHAR(10),
                  timestamp TIMESTAMP






);