-- sql/setup.sql
CREATE DATABASE IF NOT EXISTS juego_db;
USE juego_db;
CREATE TABLE IF NOT EXISTS Cartas (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        titulo VARCHAR(100) NOT NULL,
                        descripcion VARCHAR(500),
                        imagen VARCHAR(255),
                        salud INT DEFAULT 0,
                        bienestar INT DEFAULT 0,
                        legado INT DEFAULT 0,
                        recursos INT DEFAULT 0
);

INSERT INTO Cartas
(titulo, descripcion, imagen, salud, bienestar, legado, recursos)
VALUES
    ('Granja Hidropónica', 'Instalas un sistema para cultivar alimentos sin tierra, asegurando un suministro constante.', 'img/granja_hidroponica.png', 10, 5, 5, -15),
    ('Refuerzos Médicos', 'Dos médicos errantes encuentran tu refugio y piden unirse. Su conocimiento es invaluable.', 'img/medicos_refugio.png', 20, 10, 5, -5),

    -- Cartas de Suministros (positivas)
    ('Búnker Olvidado', 'Encontráis la escotilla de un búnker militar. Dentro hay suministros médicos y comida enlatada.', 'img/bunker_olvidado.png', 15, 10, 0, 30),
    ('Expedición Exitosa', 'El grupo de exploración regresa con mochilas llenas de herramientas y chatarra útil.', 'img/expedicion_exitosa.png', 0, 5, 0, 25),

    -- Cartas de Amenaza (negativas)
    ('Ataque Nocturno', 'Un grupo de saqueadores ataca al amparo de la oscuridad. Tuvisteis que defenderos.', 'img/ataque_nocturno.png', -15, -20, 0, -10),
    ('La Plaga', 'Una enfermedad desconocida se extiende por el campamento. Necesitáis medicinas urgentemente.', 'img/la_plaga.png', -30, -15, 0, -5),

    -- Cartas de Decisión Moral/Legado (balance)
    ('Noche de Historias', 'A pesar de todo, encendéis una hoguera y compartís historias del "mundo de antes".', 'img/hoguera_historias.png', 0, 20, 10, -2),
    ('El Dilema del Extraño', 'Un extraño herido pide asilo. Cuidarlo gastará medicinas, pero rechazarlo... ¿quiénes seríamos?', 'img/dilema_extraño.png', 0, -10, 5, -10);