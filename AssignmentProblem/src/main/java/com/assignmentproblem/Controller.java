package com.assignmentproblem;

import javafx.beans.property.ReadOnlyObjectWrapper;
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
    private Button generateButton;

    @FXML
    private Button startAlgorithmButton;

    @FXML
    private Label answerLabel;

    @FXML
    private TableView<Agent> table;

    @FXML
    private void getAgentsAndTasksNumbers() {
        //TODO
        // сделать проверку на то, что agentsNumber <= tasksNumber
        int agentsNumber = agentsSpinner.getValue();
        int tasksNumber = tasksSpinner.getValue();
        DataMatrix.makeNewMatrix();
        DataMatrix.setVoidMatrix(agentsNumber, tasksNumber);
        fillTableView(agentsNumber, tasksNumber);
    }

    //@SuppressWarnings("unchecked")
    private void fillTableView(int agentsNumber, int tasksNumber) {
        table.getItems().clear();
        table.getColumns().clear();
        table.setEditable(true);
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
            table.getColumns().add(newTaskColumn);
        }
        table.getItems().addAll(DataMatrix.getMatrix());
    }

    private void makeColumnEditable(TableColumn<Agent, Integer> column) {
        column.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        column.setOnEditCommit(value -> value.getTableView().getItems().get(value.getTablePosition().getRow()).setTaskCostByIndex(value.getTablePosition().getColumn(), value.getNewValue()));
    }

    @FXML
    private void startAlgorithm() {
        answerLabel.setText(Integer.toString(Algorithm.solveProblem(DataMatrix.getMatrix())));
    }

    @FXML
    private void generateData() {
        int agentsNumber = agentsSpinner.getValue();
        int tasksNumber = tasksSpinner.getValue();
        DataMatrix.clear();
        DataMatrix.setVoidMatrix(agentsNumber, tasksNumber);
        // если данные из спиннеров не совпадают с реальными размерами таблицы, создаём корректную таблицу
        if (agentsNumber != table.getItems().size() || tasksNumber != table.getItems().get(0).getTaskCost().size()) {
            fillTableView(agentsNumber, tasksNumber);
        }
        InputDataGenerator.generate(agentsNumber, tasksNumber);
        table.getItems().clear();
        table.getItems().addAll(DataMatrix.getMatrix());
    }


    @FXML
    private Spinner<Integer> agentsSpinner;

    @FXML
    private Spinner<Integer> tasksSpinner;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SpinnerValueFactory<Integer> agentsSpinnerFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 2);
        this.agentsSpinner.setValueFactory(agentsSpinnerFactory);
        SpinnerValueFactory<Integer> tasksSpinnerFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 2);
        this.tasksSpinner.setValueFactory(tasksSpinnerFactory);
    }

}