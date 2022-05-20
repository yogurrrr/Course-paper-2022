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
    private Label bestMatchingLabel;

    @FXML
    private Button generateButton;

    @FXML
    private Button startAlgorithmButton;

    @FXML
    private TableView<Agent> inputTable;

    @FXML
    private Label outputLabel;

    @FXML
    private void getAgentsAndTasksNumbers() {
        int agentsNumber = agentsSpinner.getValue();
        int tasksNumber = tasksSpinner.getValue();
        if (agentsNumber <= tasksNumber) {
            generateButton.setDisable(false);
            startAlgorithmButton.setDisable(false);
            agentsSpinner.setDisable(true);
            tasksSpinner.setDisable(true);
            inputTable.setDisable(false);
            outputLabel.setVisible(false);
//            answerLabel.setText("");
//            answerLabel.setVisible(false);
//            outputTable.getColumns().clear();
//            outputTable.getItems().clear();
//            bestMatchingLabel.setVisible(false);
//            outputTable.setVisible(false);
            DataMatrix.makeNewMatrix();
            DataMatrix.setVoidMatrix(agentsNumber, tasksNumber);
            fillTableView(agentsNumber, tasksNumber);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning about input data");
            alert.setHeaderText(null);
            alert.setContentText("Number of agents must be less or equal to number of tasks.");
            alert.showAndWait();
        }
    }

    //@SuppressWarnings("unchecked")
    private void fillTableView(int agentsNumber, int tasksNumber) {
        inputTable.getItems().clear();
        inputTable.getColumns().clear();
        inputTable.setEditable(true);
        for (int i = 0; i < tasksNumber; ++i) {
            TableColumn<Agent, Integer> newTaskColumn = new TableColumn<>(i + 1 + " task");
            makeColumnEditable(newTaskColumn);
            newTaskColumn.setPrefWidth(inputTable.getPrefWidth() / tasksNumber);
            newTaskColumn.setStyle("-fx-alignment: CENTER;");
            int finalI = i;
            newTaskColumn.setCellValueFactory(agentIntegerCellDataFeatures ->
                    new ReadOnlyObjectWrapper(agentIntegerCellDataFeatures.getValue().getTaskCost().get(finalI)));
            inputTable.getColumns().add(newTaskColumn);
        }
        inputTable.getItems().addAll(DataMatrix.getMatrix());
    }

    private void makeColumnEditable(TableColumn<Agent, Integer> column) {
        column.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        column.setOnEditCommit(value -> value.getTableView().getItems().get(value.getTablePosition().getRow()).setTaskCostByIndex(value.getTablePosition().getColumn(), value.getNewValue()));
    }

    @FXML
    private void startAlgorithm() {
        agentsSpinner.setDisable(false);
        tasksSpinner.setDisable(false);
        generateButton.setDisable(true);
        startAlgorithmButton.setDisable(true);
        inputTable.setEditable(false);
        outputLabel.setVisible(true);
//        outputTable.setItems(inputTable.getItems());
//        outputTable.getColumns().addAll(inputTable.getColumns());
//        answerLabel.setVisible(true);
//        bestMatchingLabel.setVisible(true);
//        outputTable.setVisible(true);
//        answerLabel.setText(Integer.toString(Algorithm.solveProblem(DataMatrix.getMatrix())));
        ArrayList<Integer> algorithmOutput = Algorithm.solveProblem(DataMatrix.getMatrix());
        outputLabel.setText("Best matching: " + algorithmOutput.get(algorithmOutput.size() - 1) + "\n\n");
//        answerLabel.setText(Integer.toString(algorithmOutput.get(algorithmOutput.size() - 1)));
        algorithmOutput.remove(algorithmOutput.size() - 1);
        for (int matchedColumn = 0; matchedColumn < algorithmOutput.size() - 1; ++matchedColumn) {
            int matchedRow = algorithmOutput.get(matchedColumn);
            if (matchedRow != -1) {
                outputLabel.setText(outputLabel.getText() + (matchedRow + 1) + " agent --> " + (matchedColumn + 1) + " task\n");
            }
        }
    }

    @FXML
    private void generateData() {
        int agentsNumber = agentsSpinner.getValue();
        int tasksNumber = tasksSpinner.getValue();
        DataMatrix.clear();
        DataMatrix.setVoidMatrix(agentsNumber, tasksNumber);
        // если данные из спиннеров не совпадают с реальными размерами таблицы, создаём корректную таблицу
        if (agentsNumber != inputTable.getItems().size() || tasksNumber != inputTable.getItems().get(0).getTaskCost().size()) {
            fillTableView(agentsNumber, tasksNumber);
        }
        InputDataGenerator.generate(agentsNumber, tasksNumber);
        inputTable.getItems().clear();
        inputTable.getItems().addAll(DataMatrix.getMatrix());
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