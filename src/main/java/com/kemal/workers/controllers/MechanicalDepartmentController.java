package com.kemal.workers.controllers;


import com.kemal.workers.dao.WorkerDao;
import com.kemal.workers.dao.WorkerDaoFactory;
import com.kemal.workers.model.Worker;
import com.kemal.workers.util.WorkerFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MechanicalDepartmentController extends DepartmentsBaseController {

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
    private Label telephoneNumber;
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
    private TableView<Worker> workersTable;
    @FXML
    private TableColumn<Worker, String> workersTableColumn;

    private WorkerDao workerDao = WorkerDaoFactory.getWorkerDao();
    private final ObservableList<Worker> workersList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        workersTableColumn.setCellValueFactory(new PropertyValueFactory<>("nameSurname"));
        rowSelected();
    }

    @FXML
    public void refreshButtonClicked() {
        workersList.clear();
        populateTable();
    }

    @FXML
    public void populateTable() {
        workersList.addAll(workerDao.getWorkersByDepartment("Mechanical"));
        workersTable.setItems(workersList);
    }

    @FXML
    private void deleteButtonClicked() {
        Worker worker = workersTable.getSelectionModel().getSelectedItem();
        workersTable.getItems().remove(worker);
        workerDao.deleteWorker(worker.getId());
    }

    @FXML
    private void rowSelected() {
        WorkerFactory.showWorkersInformation(workersTable, name, surname, age, city, address,
                telephoneNumber, email, idNumber, startDate, contractType, endDate, payFrequency, accountNumber,
                taxCoefficient, netSalary);
    }

    @FXML
    public void editButtonClicked() {
        try {
            editButtonClicked(workersTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        refreshButtonClicked();
    }


}
