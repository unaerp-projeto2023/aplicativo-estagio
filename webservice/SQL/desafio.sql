#----------------------------------------------------------------------------------
# Banco de dados para o projeto Desafio Final
#----------------------------------------------------------------------------------
#
#----------------------------------------------------------------------------------
# Apaga as tabelas, se existirem
#----------------------------------------------------------------------------------
  DROP TABLE IF EXISTS tb_announcement;
  DROP TABLE IF EXISTS tb_users;
#----------------------------------------------------------------------------------


#----------------------------------------------------------------------------------
# cria as tabelas
#----------------------------------------------------------------------------------
  CREATE TABLE IF NOT EXISTS tb_users (
    id       int(11)       NOT NULL auto_increment ,
    name     varchar(200)           default ''     ,
    email    varchar(200)  NOT NULL default ''     COMMENT 'Email como Login de acesso',
    password varchar(200)  NOT NULL default ''     COMMENT 'Senha de acesso'           ,
    type     enum('U','A')          default 'U'    COMMENT '(U)suario - (A)nunciante'  ,
    PRIMARY KEY (id),
    INDEX idx_login_acesso (email),
    INDEX idx_senha_acesso (password)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1 COMMENT='Tabela de Usuários';
#----------------------------------------------------------------------------------

#----------------------------------------------------------------------------------
# Cadastro de Usuarios padrao
#----------------------------------------------------------------------------------
 INSERT INTO tb_users (name, email, password) VALUES ('Guilherme Galvão', 'guilherme@outlook.com', 'YWRtaW4%3D');
 #--------------------------------------
 # base64 da senha (admin): YWRtaW4%3D
 #--------------------------------------
#----------------------------------------------------------------------------------
  CREATE TABLE IF NOT EXISTS tb_announcement (
    id               int(11)       NOT NULL auto_increment,
    description      varchar(200)           default ''    ,
    contact_name     varchar(200)           default ''    ,
    contact_email    varchar(200)           default ''    ,
    contact_phone    varchar(200)           default ''    ,
    company          varchar(200)           default ''    ,
    company_show     enum('S','N')          default 'S'   ,
    area_description varchar(200)           default ''    ,
    locality         varchar(200)           default ''    ,
    start_date       datetime               default NULL  ,
    final_date       datetime               default NULL  ,
    remuneration     decimal(12,2)          default '0.00',
    PRIMARY KEY (id),
    INDEX idx_company  (company),
    INDEX idx_are      (area_description),
    INDEX idx_locality (locality)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1 COMMENT='Tabela de Anúncio';
#----------------------------------------------------------------------------------
  
#----------------------------------------------------------------------------------
  CREATE TABLE IF NOT EXISTS tb_relation (
    id               int(11)       NOT NULL auto_increment,
    id_user          int(11)       NOT NULL default '0'   COMMENT 'Relacionamento com a Tabela de Usuario',
    id_announcement  int(11)       NOT NULL default '0'   COMMENT 'Relacionamento com a Tabela de Anuncio',
    PRIMARY KEY (id),
    INDEX idx_relation_user         (id_user),
    INDEX idx_relation_announcement (id_user),
    CONSTRAINT fk_relation_user         FOREIGN KEY (id_user)         REFERENCES tb_users        (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_relation_announcement FOREIGN KEY (id_announcement) REFERENCES tb_announcement (id) ON DELETE CASCADE ON UPDATE CASCADE
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1 COMMENT='Tabela de Usuários';
#----------------------------------------------------------------------------------
  
  
  
  
  

