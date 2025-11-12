module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires java.naming;


    opens org.example.demo to javafx.fxml;
    exports org.example.demo;
    exports org.example.demo.UI;
    opens org.example.demo.UI to javafx.fxml;
}