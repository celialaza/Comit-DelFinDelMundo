module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires java.naming;
    requires jdk.jfr;
    requires static lombok;
    requires java.desktop;


    opens org.example.demo to javafx.fxml;
    exports org.example.demo;
    exports org.example.demo.UI;
    opens org.example.demo.UI to javafx.fxml;
    exports org.example.demo.DB;
    opens org.example.demo.DB to javafx.fxml;
}