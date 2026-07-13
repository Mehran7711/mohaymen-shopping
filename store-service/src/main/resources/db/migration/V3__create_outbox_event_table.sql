CREATE TABLE outbox_events
(
    id            NUMBER(19) NOT NULL,
    aggregate_id  NUMBER(19) NOT NULL,
    aggregate_type VARCHAR2(100) NOT NULL,
    event_type    VARCHAR2(100) NOT NULL,
    topic         VARCHAR2(100) NOT NULL,
    payload       CLOB NOT NULL,
    status        VARCHAR2(20) DEFAULT 'NEW' NOT NULL,
    retry_count   NUMBER(10) DEFAULT 0 NOT NULL,
    created_at    TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
    processed_at  TIMESTAMP,

    CONSTRAINT pk_outbox_events PRIMARY KEY (id)
);

CREATE SEQUENCE outbox_event_seq START WITH 1 INCREMENT BY 1;