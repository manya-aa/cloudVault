CREATE TABLE STOCK(

    id BIGSERIAL PRIMARY KEY ,
                  product_id BIGINT REFERENCES product(id),
                  qty_change INTEGER,

                movement VARCHAR(10),
                  timestamp TIMESTAMP






);