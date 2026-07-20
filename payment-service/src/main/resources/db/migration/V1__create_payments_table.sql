CREATE SEQUENCE payment_id_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE payments (
                          id NUMBER(19) DEFAULT payment_id_seq.NEXTVAL NOT NULL,
                          version NUMBER(19) DEFAULT 0 NOT NULL,

                          order_id NUMBER(19) NOT NULL,
                          order_number VARCHAR2(50) NOT NULL,

                          customer_id VARCHAR2(200) NOT NULL,

                          amount NUMBER(19,2) NOT NULL,

                          transaction_id VARCHAR2(255),

                          status VARCHAR2(30) NOT NULL,

                          failure_reason VARCHAR2(1000),

                          created_at TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
                          updated_at TIMESTAMP,

                          CONSTRAINT pk_payments PRIMARY KEY (id),

                          CONSTRAINT uk_payment_order UNIQUE(order_id)
);