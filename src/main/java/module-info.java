module com.example.sudokugame {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.sudokugame to javafx.fxml;
    exports com.example.sudokugame;
    exports com.example.sudokugame.Controllers;
    opens com.example.sudokugame.Controllers to javafx.fxml;
}