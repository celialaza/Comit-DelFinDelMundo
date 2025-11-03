# 🚀 Proyecto de Acceso a Datos: El comité del fin del mundo

Una breve descripción de lo que hace el proyecto. 
Este proyecto conecta una aplicación Java
con una base de datos MySQL para gestionar un juego de cartas.

---

## 🛠️ Requisitos

* [Java 17](https://www.oracle.com/java/technologies/downloads/) (o la versión que uses)
* [IntelliJ IDEA](https://www.jetbrains.com/idea/)
* [MySQL Community Server](https://dev.mysql.com/downloads/installer/)

---

## ⚙️ Configuración del Entorno Local

Para poder ejecutar el proyecto en tu máquina local, sigue estos pasos:

1.  **Clona el repositorio:**
    ```bash
    git clone [https://github.com/TuUsuario/proyecto-acceso-datos.git](https://github.com/TuUsuario/proyecto-acceso-datos.git)
    ```

2.  **Configura la Base de Datos:**
    * Asegúrate de que tienes MySQL Server ejecutándose en `localhost:3306`.
    * Crea una base de datos. Puedes llamarla `juego_db`.
    * Ejecuta el script SQL que encontrarás en `/sql/setup.sql` para crear la tabla `Cartas` y añadir los datos iniciales.

3.  **Configura las Credenciales:**
    * En la carpeta `src/main/resources/`, busca el fichero `db.properties.example`.
    * **Haz una copia** de ese fichero y renómbrala a **`db.properties`**.
    * Abre `db.properties` y añade tu contraseña local de MySQL donde corresponda.

4.  **Abre el proyecto** en IntelliJ IDEA y ¡listo para programar!