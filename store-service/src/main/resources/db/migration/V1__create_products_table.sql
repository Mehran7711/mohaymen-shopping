-- Create sequence
CREATE SEQUENCE product_id_seq
    START WITH 1
    INCREMENT BY 50
    NOCACHE 
    NOCYCLE;

-- Create table
CREATE TABLE products (
                          id NUMBER(19) DEFAULT product_id_seq.NEXTVAL NOT NULL,
                          version NUMBER(19) DEFAULT 0 NOT NULL,
                          code VARCHAR2(255) NOT NULL,
                          name VARCHAR2(255) NOT NULL,
                          description CLOB,
                          image_url VARCHAR2(1000),
                          price NUMBER(19,2) NOT NULL,
                          CONSTRAINT pk_products PRIMARY KEY (id),
                          CONSTRAINT uk_products_code UNIQUE (code)
);
