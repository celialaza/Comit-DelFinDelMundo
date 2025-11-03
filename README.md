# 🚀 Proyecto de Acceso a Datos: El comité del fin del mundo

Imagina que el futuro de la humanidad depende de las decisiones que tomes, ¿Te
atreves?
Es un thriller narrativo de toma de decisiones 2D ambientado en un futuro
apocalíptico donde la Tierra está perdida. Un meteorito es inevitable. Tu trabajo no es
salvar el mundo, sino decidir qué trozos de él merecen ser salvados. Los jugadores
dirigen el comité que llena la última nave colonial a un nuevo planeta aparentemente
habitable. Cada decisión es brutal y permanente. ¿Crearás una utopía de científicos,
una dictadura militar o una sociedad equilibrada? El futuro de la especie se escribe
en tu lista de embarque.

---

## 🛠️ Requisitos

* [Java 17](https://www.oracle.com/java/technologies/downloads/) (o la versión que uses)
* [IntelliJ IDEA](https://www.jetbrains.com/idea/)
* [MySQL Community Server](https://dev.mysql.com/downloads/installer/)
* [Scene Builder](https://gluonhq.com/products/scene-builder/) 

---

## ⚙️ Configuración del Entorno Local

Para poder ejecutar el proyecto en tu máquina local, sigue estos pasos:

1.  **Clona el repositorio:**

    ```bash
    git clone https://github.com/celialaza/Comit-DelFinDelMundo.git
    ```

2.  **Configura la Base de Datos:**
    * Asegúrate de que tienes MySQL Server/Workbench ejecutándose en `localhost:3306`.
    * En IntelliJ, abre el fichero /sql/setup.sql. 
    * Copia todo el contenido de ese archivo. 
    * Vuelve a Workbench, abre una pestaña de "Query" y pega el contenido (Ctrl+V). 
    * Ejecuta el script completo haciendo clic en el icono del rayo ⚡. 
    * Refresca el panel "SCHEMAS" (clic derecho > Refresh All) y verás tu base de datos juego_db creada.
    
3.  **Configura las Credenciales:**
    * En la carpeta `src/main/resources/`, busca el fichero `db.properties.example`.
    * **Haz una copia** de ese fichero y renómbrala a **`db.properties`**.
    * Abre `db.properties` y añade tu contraseña local de MySQL donde corresponda.

4.  **Abre el proyecto** en IntelliJ IDEA y ¡listo para programar!