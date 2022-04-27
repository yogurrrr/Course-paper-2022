package com.assignmentproblem;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.util.*;

public class Controller implements Initializable {
    @FXML
    private Button confirmButton;

    @FXML
    private TableView<Agent> matrix;

    @FXML
    private void getAgentsAndTasksNumbers() {
        int agentsNumber = agentsSpinner.getValue();
        int tasksNumber = tasksSpinner.getValue();
        fillTableView(agentsNumber, tasksNumber);
    }

    private void fillTableView(int agentsNumber, int tasksNumber) {
        ObservableList<Agent> dataForMatrix = FXCollections.observableArrayList();
        for (int i = 0; i < agentsNumber; ++i) {
            dataForMatrix.add(new Agent(tasksNumber));
        }
        for (int i = 0; i < tasksNumber; ++i) {
            TableColumn<Agent, SimpleIntegerProperty> newTaskColumn = new TableColumn<>(i + 1 + " task");
            //TODO
            // придумать, как вместо 900.0 использовать ширину scene/stage
            // и стирать старые столбцы при нажатии на кнопку
            newTaskColumn.setPrefWidth(900.0 / tasksNumber);
            newTaskColumn.setStyle("-fx-alignment: CENTER;");
            int finalI = i;
            newTaskColumn.setCellValueFactory(agentIntegerCellDataFeatures ->
                    new ReadOnlyObjectWrapper(agentIntegerCellDataFeatures.getValue().getTaskCost().get(finalI).get()));
            matrix.getColumns().add(newTaskColumn);
        }
        matrix.getItems().addAll(dataForMatrix);
    }


    @FXML
    private Spinner<Integer> agentsSpinner;

    @FXML
    private Spinner<Integer> tasksSpinner;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SpinnerValueFactory<Integer> agentsSpinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 4);
        this.agentsSpinner.setValueFactory(agentsSpinnerFactory);
        SpinnerValueFactory<Integer> tasksSpinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 4);
        this.tasksSpinner.setValueFactory(tasksSpinnerFactory);
    }

}