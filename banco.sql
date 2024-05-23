CREATE TABLE salas (
  id_sala serial primary key,
  bloco char(3) not null,
  numero int not null
);

CREATE TABLE patrimonio (
  id_patrimonio serial primary key,
  nome_do_ativo varchar(70) not null,
  data_compra timestamp,
  descricao varchar(40) not null,
  status varchar(20) not null
);

CREATE TABLE funcionarios (
  id_fun serial primary key,
  email varchar(70) not null,
  setor varchar(30) not null,
  nome varchar(70) not null,
  cargo varchar(40) not null
);

CREATE TABLE movimentacao (
  id_movimentacao serial primary key,
  id_solicitante int not null,
  id_aprovador int not null,
 status varchar(20) not null, 
	data_movimentacao date not null,
descricao varchar(100) not null,
  id_origem int not null,
  id_destino int not null,

foreign key (id_solicitante)

references funcionarios(id_fun),

foreign key (id_aprovador)

references funcionarios(id_fun),

foreign key (id_origem)

references salas(id_sala),

foreign key (id_destino)

references salas(id_sala)
);


ALTER TABLE salas
ADD COLUMN responsavel int not null;

ALTER TABLE salas
ADD CONSTRAINT fk_responsavel FOREIGN KEY (responsavel) REFERENCES funcionarios (id_fun);

ALTER TABLE patrimonio
ADD COLUMN pertence int not null;

ALTER TABLE patrimonio
ADD CONSTRAINT fk_pertence FOREIGN KEY (pertence) REFERENCES salas(id_sala);

ALTER TABLE movimentacao
ADD COLUMN patrimonio_movimentacao int not null;

ALTER TABLE movimentacao
ADD CONSTRAINT fk_patrimonio_movimentacao FOREIGN KEY (patrimonio_movimentacao) REFERENCES patrimonio (id_patrimonio);

SELECT * FROM salas

SELECT * FROM funcionarios

SELECT * FROM patrimonio

SELECT * FROM movimentacao
