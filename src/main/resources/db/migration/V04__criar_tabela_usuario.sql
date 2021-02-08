CREATE TABLE Users (
	id SERIAL,
	email VARCHAR(50) NOT NULL,
	password VARCHAR(500),
	constraint pk_usuario primary key (id)
);

INSERT INTO Users (email, password) values ('admin', '$2b$10$Y1bFfPVcqMqTZeI/RAXS4Onhnd6D1Gj6t2e6EkdCOKF3ZMScPwvHi');