module com.assignmentproblem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.assignmentproblem to javafx.fxml;
    exports com.assignmentproblem;
}