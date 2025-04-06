CREATE TABLE usuario (
                         id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
                         login VARCHAR(255) NOT NULL UNIQUE,
                         senha VARCHAR(255) NOT NULL,
                         nome VARCHAR(255) NOT NULL,
                         email VARCHAR(255) NOT NULL UNIQUE,
                         perfil ENUM('ADMIN', 'USER') NOT NULL
);

CREATE TABLE produto (
                         id_produto VARCHAR(255) PRIMARY KEY,
                         nome VARCHAR(255) NOT NULL,
                         descricao TEXT,
                         categoria VARCHAR(255),
                         preco DECIMAL(38, 2) NOT NULL,
                         quantidade_estoque BIGINT NOT NULL,
                         data_criacao DATETIME(6),
                         data_atualizacao DATETIME(6)
);

CREATE TABLE pedido (
                        id_pedido BIGINT AUTO_INCREMENT PRIMARY KEY,
                        preco DECIMAL(38, 2) NOT NULL,
                        status ENUM('CANCELADO', 'PAGO', 'PENDENTE') NOT NULL,
                        id_usuario BIGINT,
                        data_criacao DATETIME(6),
                        data_atualizacao DATETIME(6),
                        FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

CREATE TABLE pedido_item (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             preco DECIMAL(38, 2) NOT NULL,
                             quantidade INT NOT NULL,
                             id_pedido BIGINT,
                             id_produto VARCHAR(255),
                             FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido),
                             FOREIGN KEY (id_produto) REFERENCES produto(id_produto)
);

CREATE INDEX idx_pedido_usuario ON pedido(id_usuario);
CREATE INDEX idx_pedidoitem_pedido ON pedido_item(id_pedido);
CREATE INDEX idx_pedidoitem_produto ON pedido_item(id_produto);