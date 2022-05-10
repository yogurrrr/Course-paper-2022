package com.assignmentproblem;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

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
        matrix.getItems().clear();
        matrix.getColumns().clear();
        ObservableList<Agent> dataForMatrix = FXCollections.observableArrayList();
        for (int i = 0; i < agentsNumber; ++i) {
            dataForMatrix.add(new Agent(tasksNumber));
        }
        matrix.setEditable(true);
        for (int i = 0; i < tasksNumber; ++i) {
            TableColumn<Agent, Integer> newTaskColumn = new TableColumn<>(i + 1 + " task");
            makeColumnEditable(newTaskColumn);
            //TODO
            // придумать, как вместо 900.0 использовать ширину scene/stage
            newTaskColumn.setPrefWidth(900.0 / tasksNumber);
            newTaskColumn.setStyle("-fx-alignment: CENTER;");
            int finalI = i;
            newTaskColumn.setCellValueFactory(agentIntegerCellDataFeatures ->
                    new ReadOnlyObjectWrapper(agentIntegerCellDataFeatures.getValue().getTaskCost().get(finalI)));
            matrix.getColumns().add(newTaskColumn);
        }
        matrix.getItems().addAll(dataForMatrix);
        Algorithm.solveProblem(matrix.getItems());
    }

    private void makeColumnEditable(TableColumn<Agent, Integer> column) {
        column.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        column.setOnEditCommit(value -> value.getTableView().getItems().get(value.getTablePosition().getRow()).setTaskCostByIndex(value.getTablePosition().getColumn(), value.getNewValue()));
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