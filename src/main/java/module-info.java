module com.example.scenebuilderexample {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.TSP to javafx.fxml;
    exports com.TSP;
}