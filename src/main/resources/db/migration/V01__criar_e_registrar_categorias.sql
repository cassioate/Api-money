CREATE TABLE categoria (
	codigo SERIAL,
	nome VARCHAR(50) NOT NULL,
	constraint pk_categoria primary key (codigo)
);

INSERT INTO categoria (nome) values ('Lazer');

INSERT INTO categoria (nome) values ('Alimentação');

INSERT INTO categoria (nome) values ('Supermercado');

INSERT INTO categoria (nome) values ('Farmacia');

INSERT INTO categoria (nome) values ('Outros');
