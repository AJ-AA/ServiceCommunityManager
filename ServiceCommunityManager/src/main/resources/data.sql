CREATE TABLE IF NOT EXISTS usuario (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       nombre VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS servicio (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        nombre VARCHAR(255) UNIQUE,
    capacidad INT
    );

CREATE TABLE IF NOT EXISTS reserva (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       usuario_id BIGINT,
                                       servicio_id BIGINT,
                                       fecha_hora TIMESTAMP,
                                       FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (servicio_id) REFERENCES servicio(id)
    );


INSERT INTO usuario (username, email, password) VALUES ('AntonioArenal', 'juan@example.com', '$2y$10$9rewViCjK4s08cFAAGLE/eJLeL02gzYtyUcnnovgV57nXl5mzS8QW');
INSERT INTO usuario (username, email, password) VALUES ('MariaLopez', 'maria@example.com', 'contraseña123');

INSERT INTO servicio (nombre, capacidad) VALUES ('Piscina', 10);
INSERT INTO servicio (nombre, capacidad) VALUES ('Padel', 20);
INSERT INTO servicio (nombre, capacidad) VALUES ('Gimnasio', 30);

-- NP 141350 Antonio Jose Arenal Armesto Feedback Final Programacion Concurrente

