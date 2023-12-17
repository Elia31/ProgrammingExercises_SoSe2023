module com.example.carsellingplatform {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javax.mail.api;


    opens com.example.carsellingplatform to javafx.fxml;
    exports com.example.carsellingplatform;
}