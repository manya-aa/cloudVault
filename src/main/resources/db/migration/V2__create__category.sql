CREATE TABLE CATEGORY(


                         id SERIAL PRIMARY KEY,
                         name VARCHAR(50),
                         parent_category_id INTEGER REFERENCES category(id)


);