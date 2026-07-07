-- Create sequence
CREATE SEQUENCE product_id_seq
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE order_id_seq
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE order_item_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE cart_id_seq
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE cart_item_id_seq
    START WITH 1
    INCREMENT BY 1;


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

CREATE TABLE product_inventory (
                                   product_id NUMBER(19) NOT NULL,
                                   quantity NUMBER(19) NOT NULL,
                                   reserved_quantity NUMBER(19) DEFAULT 0 NOT NULL,
                                   updated_at TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,

                                   CONSTRAINT pk_product_inventory PRIMARY KEY (product_id),
                                   CONSTRAINT fk_inventory_product
                                       FOREIGN KEY (product_id)
                                           REFERENCES products(id)
);

CREATE TABLE orders (
                        id NUMBER(19) DEFAULT order_id_seq.NEXTVAL NOT NULL,
                        order_number VARCHAR2(50) NOT NULL,
                        customer_id NUMBER(19) NOT NULL,

                        status VARCHAR2(30) DEFAULT 'CREATED' NOT NULL,

                        total_amount NUMBER(19,2) DEFAULT 0 NOT NULL,

                        created_at TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
                        updated_at TIMESTAMP,

                        CONSTRAINT pk_orders PRIMARY KEY (id),
                        CONSTRAINT uk_orders_order_number UNIQUE (order_number)
);

CREATE TABLE order_item (
                            id NUMBER(19) DEFAULT order_item_id_seq.NEXTVAL NOT NULL,
                            order_id NUMBER(19) NOT NULL,
                            product_id NUMBER(19) NOT NULL,

                            quantity NUMBER(19) NOT NULL,
                            unit_price NUMBER(19,2) NOT NULL,
                            total_price NUMBER(19,2) NOT NULL,

                            CONSTRAINT pk_order_item PRIMARY KEY (id),

                            CONSTRAINT fk_order_item_order
                                FOREIGN KEY (order_id)
                                    REFERENCES orders(id),

                            CONSTRAINT fk_order_item_product
                                FOREIGN KEY (product_id)
                                    REFERENCES products(id)
);

CREATE TABLE cart (
                      id NUMBER(19) DEFAULT cart_id_seq.NEXTVAL NOT NULL,
                      version NUMBER(19) DEFAULT 0 NOT NULL,

                      customer_id NUMBER(19) NOT NULL,

                      total_amount NUMBER(19,2) DEFAULT 0 NOT NULL,

                      created_at TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
                      updated_at TIMESTAMP,

                      CONSTRAINT pk_cart PRIMARY KEY (id),

                      CONSTRAINT uk_cart_customer UNIQUE (customer_id)
);

CREATE TABLE cart_item (
                           id NUMBER(19) DEFAULT cart_item_id_seq.NEXTVAL NOT NULL,
                           version NUMBER(19) DEFAULT 0 NOT NULL,

                           cart_id NUMBER(19) NOT NULL,
                           product_id NUMBER(19) NOT NULL,

                           quantity NUMBER(19) NOT NULL,

                           unit_price NUMBER(19,2) NOT NULL,
                           total_price NUMBER(19,2) NOT NULL,

                           created_at TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,

                           CONSTRAINT pk_cart_item PRIMARY KEY (id),

                           CONSTRAINT fk_cart_item_cart
                               FOREIGN KEY (cart_id)
                                   REFERENCES cart(id),

                           CONSTRAINT fk_cart_item_product
                               FOREIGN KEY (product_id)
                                   REFERENCES products(id),

                           CONSTRAINT uk_cart_item UNIQUE(cart_id, product_id)
);

