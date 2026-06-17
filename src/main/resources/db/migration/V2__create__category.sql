CREATE TABLE CATEGORY(


                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(50) NOT NULL UNIQUE ,
                         description VARCHAR(100)


);