INSERT INTO conta (conta_id, nome, email, senha, perfil) 
VALUES (NULL, 'Administrador', 'admin@magalu.com.br', '$2a$06$xIvBeNRfS65L1N17I7JzgefzxEuLAL0Xk0wFAgIkoNqu9WD6rmp4m', 'ROLE_ADMIN');

INSERT INTO conta (conta_id, nome, email, senha, perfil) 
VALUES (NULL, 'Cliente', 'cliente@magalu.com.br', '$2a$06$xIvBeNRfS65L1N17I7JzgefzxEuLAL0Xk0wFAgIkoNqu9WD6rmp4m', 'ROLE_CLIENTE');

INSERT INTO loja (loja_id, codigo, descricao, cep) 
VALUES (NULL, '123456', 'Magazine Luz', '01102000');

INSERT INTO loja (loja_id, codigo, descricao, cep) 
VALUES (NULL, '234567', 'Magazine Santana', '02405006');
