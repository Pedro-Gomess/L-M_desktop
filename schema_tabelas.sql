USE `projetolm_desktop`;

#Criando Tabelas!!!

CREATE TABLE `pessoa`(
	`id_pessoa` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nome` VARCHAR(55),
    `email` VARCHAR(65),
    `cpf` VARCHAR(15),
    `senha` VARCHAR(20)
); 

CREATE TABLE `funcionario`(
	`id_funcionario` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`matricula` VARCHAR(10) UNIQUE NOT NULL,
    `id_pessoa` INT NOT NULL,
     FOREIGN KEY(`id_pessoa`) REFERENCES `pessoa`(`id_pessoa`)
);

CREATE TABLE `administrador`(
	`id_administrador` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `matricula` VARCHAR(10) UNIQUE NOT NULL,
    `id_pessoa` INT NOT NULL,
    FOREIGN KEY(`id_pessoa`) REFERENCES `pessoa`(`id_pessoa`)   
);

CREATE TABLE `assinante`(
	`id_assinante` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `id_pessoa` INT NOT NULL,
    FOREIGN KEY(`id_pessoa`) REFERENCES `pessoa`(`id_pessoa`)   
);

CREATE TABLE `livros`(
	`id_livro` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `titulo` VARCHAR(40),
    `categoria` VARCHAR(30),
    `qtd_livros` VARCHAR(5),
    `nome_arquivo` VARCHAR(45)
);