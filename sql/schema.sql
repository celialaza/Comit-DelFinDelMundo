-- sql/setup.sql

CREATE TABLE Cartas (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        titulo VARCHAR(100) NOT NULL,
                        descripcion TEXT,
                        imagen VARCHAR(255),
                        salud INT DEFAULT 0,
                        bienestar INT DEFAULT 0,
                        legado INT DEFAULT 0,
                        recursos INT DEFAULT 0
);

INSERT INTO Cartas
(titulo, descripcion, imagen, salud, bienestar, legado, recursos)
VALUES
    ('La Forja', 'Construyes una nueva herramienta para la aldea.', 'img/la_forja.png', 0, 10, 5, -5),
    ('El Mercado', 'Intercambias bienes con un mercader errante.', 'img/mercado.png', 0, 5, 0, 15);