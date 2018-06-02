package com.kemal.workers.controllers;

import com.kemal.workers.dao.WorkerDao;
import com.kemal.workers.dao.WorkerDaoFactory;
import com.kemal.workers.model.Worker;
import com.kemal.workers.service.DepartmentsService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ElectricalDepartmentController {

    @FXML
    private Label name;
    @FXML
    private Label surname;
    @FXML
    private Label age;
    @FXML
    private Label city;
    @FXML
    private Label address;
    @FXML
    private Label telephoneNum;
    @FXML
    private Label email;
    @FXML
    private Label idNumber;
    @FXML
    private Label startDate;
    @FXML
    private Label contractType;
    @FXML
    private Label endDate;
    @FXML
    private Label payFrequency;
    @FXML
    private Label accountNumber;
    @FXML
    private Label taxCoefficient;
    @FXML
    private Label netSalary;
    @FXML
    private Button refreshButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private TableView<Worker> workersTable;
    @FXML
    private TableColumn<Worker, String> workersTableColumn;

    private WorkerDao workerDao = WorkerDaoFactory.getWorkerDao();
    private final ObservableList<Worker> workersList = FXCollections.observableArrayList();

    //initialize method
    public void initialize() {
        workersTableColumn.setCellValueFactory(new PropertyValueFactory<>("nameSurname"));
        rowSelected();
    }

    //Refresh the table when the button is clicked
    @FXML
    public void refreshButtonClicked() {
        workersList.removeAll(workersList);
        populateTable();
    }

    //Delete worker
    @FXML
    private void deleteButtonClicked() {
        Worker selectedWorker = workersTable.getSelectionModel().getSelectedItem();
        workersTable.getItems().remove(selectedWorker);
        workerDao.deleteSelectedWorker(selectedWorker, "Electrical");
    }

    //Populate the table
    @FXML
    public void populateTable() {
        for (Worker worker : workerDao.getWorkersNameSurname("Electrical")) workersList.addAll(worker);
        workersTable.setItems(workersList);
    }

    //Show worker's information when row is selected
    @FXML
    private void rowSelected() {
        DepartmentsService.showWorkersInformationWhenRowIsSelected(workersTable, name, surname, age, city, address,
                telephoneNum, email, idNumber, startDate, contractType, endDate, payFrequency, accountNumber,
                taxCoefficient, netSalary);
    }

    @FXML
    public void editButtonClicked() {
        try {
            DepartmentsService.editButtonClicked(workersTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        refreshButtonClicked();

    }


}





