-- from spring-session-jdbc-3.0.0.jar!/org/springframework/session/jdbc/schema-postgresql.sql
CREATE TABLE if not exists store_app_session (
	PRIMARY_ID CHAR(36) NOT NULL,
	SESSION_ID CHAR(36) NOT NULL,
	CREATION_TIME BIGINT NOT NULL,
	LAST_ACCESS_TIME BIGINT NOT NULL,
	MAX_INACTIVE_INTERVAL INT NOT NULL,
	EXPIRY_TIME BIGINT NOT NULL,
	PRINCIPAL_NAME VARCHAR(100),
	CONSTRAINT store_app_session_pk PRIMARY KEY (PRIMARY_ID)
);

CREATE UNIQUE INDEX if not exists store_app_session_ix1 ON store_app_session (SESSION_ID) ;
CREATE INDEX if not exists store_app_session_ix2 ON store_app_session (EXPIRY_TIME);
CREATE INDEX if not exists store_app_session_ix3 ON store_app_session (PRINCIPAL_NAME);

CREATE TABLE if not exists store_app_session_attributes (
	SESSION_PRIMARY_ID CHAR(36) NOT NULL,
	ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
	ATTRIBUTE_BYTES BYTEA NOT NULL,
	CONSTRAINT store_app_session_attributes_pk PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
	CONSTRAINT store_app_session_attributes_fk FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES store_app_session(PRIMARY_ID) ON DELETE CASCADE
);
