package com.assignmentproblem;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.TextAlignment;
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
    private Button exitButton;

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
            DataMatrix.makeNewMatrix();
            DataMatrix.setVoidMatrix(agentsNumber, tasksNumber);
            fillTableView(tasksNumber);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning about input data");
            alert.setHeaderText(null);
            alert.setContentText("Number of agents must be less or equal to number of tasks.");
            alert.showAndWait();
        }
    }

    //@SuppressWarnings("unchecked")
    private void fillTableView(int tasksNumber) {
        inputTable.getItems().clear();
        inputTable.getColumns().clear();
        inputTable.setEditable(true);
        TableColumn<Agent, Character> agentIdColumn = new TableColumn<>("Agent");
        agentIdColumn.setPrefWidth(inputTable.getPrefWidth() / (tasksNumber + 1));
        agentIdColumn.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold");
        agentIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        inputTable.getColumns().add(agentIdColumn);
        for (int i = 0; i < tasksNumber; ++i) {
            TableColumn<Agent, Integer> newTaskColumn = new TableColumn<>("Task " + (i + 1));
            makeColumnEditable(newTaskColumn);
            newTaskColumn.setPrefWidth(inputTable.getPrefWidth() / (tasksNumber + 1));
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
        column.setOnEditCommit(value -> value.getTableView().getItems().get(value.getTablePosition().getRow())
                .setTaskCostByIndex(value.getTablePosition().getColumn(), value.getNewValue()));
    }

    @FXML
    private void startAlgorithm() {
        agentsSpinner.setDisable(false);
        tasksSpinner.setDisable(false);
        generateButton.setDisable(true);
        startAlgorithmButton.setDisable(true);
        inputTable.setEditable(false);
        outputLabel.setVisible(true);
        ArrayList<Integer> algorithmOutput = Algorithm.solveProblem(DataMatrix.getMatrix());
        outputLabel.setTextAlignment(TextAlignment.CENTER);
        outputLabel.setText("Minimum cost of assignment: "
                + algorithmOutput.get(algorithmOutput.size() - 1) + "\n\n");
        algorithmOutput.remove(algorithmOutput.size() - 1);
        for (int matchedColumn = 0; matchedColumn < algorithmOutput.size() - 1; ++matchedColumn) {
            int matchedRow = algorithmOutput.get(matchedColumn);
            if (matchedRow != -1) {
                outputLabel.setText(outputLabel.getText() + "Agent " + (char)((int)'A' + matchedRow) + " → "
                        + "Task " + (matchedColumn + 1) + '\n');
            }
        }
    }

    @FXML
    private void generateData() {
        int agentsNumber = agentsSpinner.getValue();
        int tasksNumber = tasksSpinner.getValue();
        DataMatrix.clear();
        DataMatrix.setVoidMatrix(agentsNumber, tasksNumber);
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
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 3);
        this.agentsSpinner.setValueFactory(agentsSpinnerFactory);
        SpinnerValueFactory<Integer> tasksSpinnerFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 3);
        this.tasksSpinner.setValueFactory(tasksSpinnerFactory);
    }

    @FXML
    private void exit() {
        Platform.exit();
    }

}