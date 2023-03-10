module com.example.praktikam {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens com.example.praktikam to javafx.fxml;
    exports com.example.praktikam;
}