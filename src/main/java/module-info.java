module com.example.xo2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.xo2 to javafx.fxml;
    exports com.example.xo2;
    exports com.example.xo2.dataBase;
    opens com.example.xo2.dataBase to javafx.fxml;
    exports com.example.xo2.dataBase.lists;
    opens com.example.xo2.dataBase.lists to javafx.fxml;
}