
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

INSERT INTO Cartas (
    titulo,
    descripcion,
    imagen,
    salud,
    bienestar,
    legado,
    recursos
)
VALUES
    -- 1. Estrella de TikTok
    ('Estrella de TikTok', 'Una estrella de TikTok con millones de seguidores que ha convertido su creatividad y carisma en un imperio.', 'img/01_tiktok_star.png', -10, 35, 15, 0),

    -- 2. Médico Joven (1)
    ('Médico Joven (1)', 'Un médico joven y guapo que acaba de terminar su residencia. Es el médico que todas las mujeres (y hombres) sueñan con tener. Su sonrisa derrite corazones, pero es su calidez y compasión lo que te hace querer quedarte a su lado para siempre.', 'img/02_medico_joven.png', 30, 10, 15, 5),

    -- 3. Médicos Jóvenes (2)
    ('Médicos Jóvenes (2)', 'Dos médicos jóvenes y guapos con mucha experiencia en el sector. No solo son médicos brillantes, sino también unas personas compasivas que realmente se preocupan por sus pacientes. Su juventud y atractivo son solo un extra.', 'img/03_medicos_dos.png', 40, 5, 10, -5),

    -- 4. Cien Semillas
    ('Cien Semillas (100kg)', 'Cien tipos de semillas aleatorias (1 kilogramo por cada una). ¿Cuáles? No lo sabemos.', 'img/04_cien_semillas.png', 5, -10, 0, 45),

    -- 5. Psicóloga
    ('Psicóloga', 'Una psicóloga guapa e inteligente. Su belleza es solo el reflejo de su alma compasiva y su profesionalismo es la prueba de su dedicación a ayudarte a superar cualquier obstáculo que se interponga en tu camino.', 'img/05_psicologa.png', 25, 30, 5, 0),

    -- 6. Disco Duro (Historia)
    ('Disco Duro (Historia)', 'Un disco duro con la historia de toda la humanidad. Desde nuestros orígenes hasta el día de hoy.', 'img/06_disco_historia.png', 0, 5, 40, 0),

    -- 7. Cincuenta Niños
    ('Cincuenta Niños', 'Cincuenta niñ@s aleatorios. Hemos sorteado estos puestos entre todos los niñ@s de 5 a 10 años del mundo y estos han sido los elegidos.', 'img/07_cincuenta_ninos.png', 0, 10, 45, -25),

    -- 8. Padres de los Niños
    ('Padres de los Niños', 'Los padres de estos niñ@s. No sabemos nada de ellos.', 'img/08_padres_ninos.png', 10, 10, 0, 15),

    -- 9. Cristiano y Messi
    ('Cristiano y Messi', 'Cristiano Ronaldo y Lionel Messi. La humanidad les votó como representantes de nuestra especie. Se les conoce como símbolos de unión entre las diferencias que siempre nos han enfrentado.', 'img/09_cr_messi.png', -15, 40, 20, -20),

    -- 10. Armas y Exmilitar
    ('Armas y Exmilitar', 'Un cargamento de armas y, además, un exmilitar con traumas de guerra y malhumorado.', 'img/10_armas_exmilitar.png', -10, -10, 0, 40),

    -- 11. Químico Políglota
    ('Químico Políglota', 'Un químico, junto a sus utensilios, con pocas habilidades sociales y que huele un pelín mal. Hay que mencionar que además es políglota, habla doce idiomas.', 'img/11_quimico.png', 0, -5, 15, 35),

    -- 12. Explorador Líder
    ('Explorador Líder', 'Un explorador, especialista en sobrevivir en las condiciones más límites. Está un poco flipado pero está preparado para lo peor, sería buen líder.', 'img/12_explorador.png', 10, -10, 0, 30),

    -- 13. La Biblia
    ('La Biblia (Idiomas)', 'La biblia. Las sagradas escrituras. En todos los idiomas.', 'img/13_biblia.png', 0, 25, 20, 0),

    -- 14. Lote de Higiene
    ('Lote de Higiene', 'Un lote de productos de higiene básico para cada uno de los integrantes de la nave. Gel, toallas, cepillo de dientes, desodorante, y demás.', 'img/14_higiene.png', 30, 15, 0, 5),

    -- 15. Profesores (Arte y Mates)
    ('Profesores', 'Un profesor de arte y una profesora de matemáticas, cada uno con sus libros. Creemos que mantienen una relación en secreto, o eso se rumorea en el colegio del que vienen.', 'img/15_profesores.png', 5, 15, 30, 5),

    -- 16. Drogas Ilimitadas
    ('Drogas Ilimitadas', 'Drogas, de todo tipo. Cantidad ilimitada.', 'img/16_drogas.png', -30, 65, -10, -15),

    -- 17. 2º Mejor Físico NASA
    ('2º Mejor Físico NASA', 'El segundo mejor físico de la NASA. Su enfoque innovador y técnicas personalizadas ayudarán a tu tripulación a mantener la calma y la concentración en el espacio profundo.', 'img/17_fisico_mejor.png', 0, 5, 30, 35),

    -- 18. Peor Físico NASA
    ('Peor Físico NASA', 'El peor físico de la NASA, de hecho, estuvo a punto de ser despedido por su bajo rendimiento. Se rumorea que es hijo del segundo mejor físico de la NASA. Es simpático pero está en muy mala forma.', 'img/18_fisico_peor.png', 0, 15, 0, -5),

    -- 19. Internet
    ('Internet', 'Internet. No ocupa ningún hueco en la nave, no te preocupes, puedes escogerlo libremente.', 'img/19_internet.png', -5, -30, 30, 20),

    -- 20. Máquina Pis-Agua
    ('Máquina Pis-Agua', 'Una máquina que transforma el pis en agua. De hecho, transforma el pis en mucha más agua por lo que siempre habrá más agua, el invento es raro pero muy práctico.', 'img/20_maquina_agua.png', 15, 0, 10, 45),

    -- 21. 10 Prostitutas/Gigolós
    ('10 Prostitutas/Gigolós', 'Cinco prostitutas y cinco gigolós. Expert@s en el arte de la excitación y el placer.', 'img/21_prostitutas.png', -15, 40, 0, -15),

    -- 22. Adolescente "Elegido"
    ('Adolescente "Elegido"', 'Un adolescente, considerado el elegido por muchos, profetizó el cataclismo devastador. Afirmó que sus sueños le habían mostrado el camino hacia el nuevo mundo, y que por lo tanto debía ser él quien guiara la expedición.', 'img/22_elegido.png', -5, 10, 10, -5),

    -- 23. El Psicópata (Efecto Especial)
    ('El Psicópata', 'Un espacio libre... ¿Una bendición o una trampa? Un psicópata acecha entre ellos, ofreciendo más espacio en la nave sin necesidad de ocupar uno propio. Su identidad permanece oculta, y cada decisión podría ser la última. Actualmente tienen 5 huecos, pero si ceden ante la oferta del psicópata, tendrán un lugar adicional.', 'img/23_psicopata.png', -40, -40, -20, 5),

    -- 24. Tesoro Culinario
    ('Tesoro Culinario (Med.)', 'El tesoro culinario del Mediterráneo: una colección completa de recetas e ingredientes que te transportarán a las soleadas costas de Grecia, Italia y España. Cada plato es una explosión de sabor y tradición.', 'img/24_culinario.png', 10, 25, 5, 20),

    -- 25. Superordenador (IA)
    ('Superordenador (IA)', 'El superordenador definitivo, equipado con la IA más avanzada. Su capacidad de procesamiento y análisis no tienen límites. Desde cálculos intrincados hasta simulaciones detalladas, es la clave para desbloquear el futuro.', 'img/25_superordenador.png', 0, 0, 30, 45),

    -- 26. Máquina electricidad + Ciclista
    ('Máquina electricidad + Ciclista', 'Una máquina generadora de electricidad impulsada por pedales, combinada con la fuerza y resistencia de un ciclista profesional. Esta innovadora tecnología te permitirá generar energía limpia de forma eficiente.', 'img/26_ciclista.png', 5, 5, 0, 30),

    -- 27. Obras de Arte
    ('Obras de Arte', 'Todas las obras de arte. Desde las esculturas de la antigua Grecia hasta las instalaciones de arte moderno, cada obra te contará una historia y te revelará los secretos de la creatividad humana.', 'img/27_arte.png', 0, 25, 35, 0),

    -- 28. Botiquín Definitivo
    ('Botiquín Definitivo', 'El botiquín definitivo, una colección completa de medicina y utensilios médicos que te permitirán atender cualquier dolencia o lesión.', 'img/28_botiquin.png', 45, 0, 0, 5),

    -- 29. "Seres Queridos"
    ('Seres Queridos', 'Los seres queridos de ass. Lo siento, pero vosotros no vais incluidos en esta opción.', 'img/29_seres_queridos.png', -10, 40, 0, -10),

    -- 30. Integrantes del Comité
    ('Integrantes del Comité', 'Los integrantes del comité.', 'img/30_comite.png', 0, 0, 5, 5)