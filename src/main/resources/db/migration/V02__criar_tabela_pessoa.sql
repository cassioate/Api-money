CREATE TABLE Pessoa (
	codigo SERIAL,
	nome VARCHAR(50) NOT NULL,
	ativo boolean,
	logradouro Varchar(200),
	numero INTEGER,
	complemento varchar(50),
	bairro varchar(50),
	cep varchar(20),
	cidade varchar(50),
	estado varchar(20),
	constraint pk_pessoa primary key (codigo)
);

INSERT INTO Pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) 
values ('Cassio', true, 'Rua Tenente Adelino Barbosa de Melo', 344, 'apt 101', 'Catolé', 
'58410-505', 'Campina Grande', 'Paraiba');

INSERT INTO Pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) 
values ('Alice', true, 'Rua Tenente Adelino Barbosa de Melo', 344, 'apt 101', 'Catolé', 
'58410-505', 'Campina Grande', 'Paraiba');

INSERT INTO Pessoa (nome, ativo, cidade, estado) 
values ('Carlinhos', true, 'Campina Grande', 'Paraiba');

INSERT INTO Pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) 
values ('Samella', false, 'Rua Tenente Adelino Barbosa de Melo', 344, 'apt 402', 'Catolé', 
'58410-505', 'Campina Grande', 'Paraiba');

INSERT INTO Pessoa (nome, ativo, bairro, cep, cidade, estado) 
values ('Carla', false, 'Catolé', '58410-505', 'Campina Grande', 'Paraiba');

INSERT INTO pessoa (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('João Silva', 'Rua do Abacaxi', '10', null, 'Brasil', '38.400-12', 'Uberlândia', 'MG', true);
INSERT INTO pessoa (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Maria Rita', 'Rua do Sabiá', '110', 'Apto 101', 'Colina', '11.400-12', 'Ribeirão Preto', 'SP', true);
INSERT INTO pessoa (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Pedro Santos', 'Rua da Bateria', '23', null, 'Morumbi', '54.212-12', 'Goiânia', 'GO', true);
INSERT INTO pessoa (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Ricardo Pereira', 'Rua do Motorista', '123', 'Apto 302', 'Aparecida', '38.400-12', 'Salvador', 'BA', true);
INSERT INTO pessoa (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Josué Mariano', 'Av Rio Branco', '321', null, 'Jardins', '56.400-12', 'Natal', 'RN', true);
INSERT INTO pessoa (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Pedro Barbosa', 'Av Brasil', '100', null, 'Tubalina', '77.400-12', 'Porto Alegre', 'RS', true);
INSERT INTO pessoa (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Henrique Medeiros', 'Rua do Sapo', '1120', 'Apto 201', 'Centro', '12.400-12', 'Rio de Janeiro', 'RJ', true);
INSERT INTO pessoa (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Carlos Santana', 'Rua da Manga', '433', null, 'Centro', '31.400-12', 'Belo Horizonte', 'MG', true);
INSERT INTO pessoa (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Leonardo Oliveira', 'Rua do Músico', '566', null, 'Segismundo Pereira', '38.400-00', 'Uberlândia', 'MG', true);
INSERT INTO pessoa (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Isabela Martins', 'Rua da Terra', '1233', 'Apto 10', 'Vigilato', '99.400-12', 'Manaus', 'AM', true);
