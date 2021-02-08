CREATE TABLE Perfis (
	user_id SERIAL,
	PERFIS VARCHAR(50) NOT NULL,
	CONSTRAINT fk_perfil FOREIGN KEY (user_id) REFERENCES Users(id)
);

INSERT INTO perfis (user_id, PERFIS) values (1, 1);