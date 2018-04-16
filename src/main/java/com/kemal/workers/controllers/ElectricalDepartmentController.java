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

    @FXML private Label name;
    @FXML private Label surname;
    @FXML private Label age;
    @FXML private Label city;
    @FXML private Label address;
    @FXML private Label telephoneNum;
    @FXML private Label email;
    @FXML private Label idNumber;
    @FXML private Label startDate;
    @FXML private Label contractType;
    @FXML private Label endDate;
    @FXML private Label payFrequency;
    @FXML private Label accountNumber;
    @FXML private Label taxCoefficient;
    @FXML private Label netSalary;
    @FXML private Button refreshButton;
    @FXML private Button deleteButton;
    @FXML private Button editButton;
    @FXML private TableView<Worker> workersTable;
    @FXML private TableColumn<Worker, String> workersTableColumn;

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
        for(Worker worker : workerDao.getWorkersNameSurname("Electrical")) workersList.addAll(worker);
        workersTable.setItems(workersList);
    }

    //Show worker's information when row is selected
    @SuppressWarnings("Duplicates")
    @FXML
    private void rowSelected() {

        workersTable.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {

                    if (newValue != null) {
                        name.setText(workerDao.getWorkersInfoByNameSurname(newValue.getNameSurname()).getName());
                        surname.setText(workerDao.getWorkersInfoByNameSurname(newValue.getNameSurname()).getSurname());
                        age.setText(String.valueOf(workerDao.getWorkersInfoByNameSurname(newValue.getNameSurname()).getAge()));
                        city.setText(workerDao.getWorkersInfoByNameSurname(newValue.getNameSurname()).getContactInformation().getCity());
                        address.setText(workerDao.getWorkersInfoByNameSurname(newValue.getNameSurname()).getContactInformation().getAddress());
                        telephoneNum.setText(workerDao.getWorkersInfoByNameSurname(newValue.getNameSurname()).getContactInformation().getTelephoneNum());
                        email.setText(workerDao.getWorkersInfoByNameSurname(newValue.getNameSurname()).getContactInformation().getEmail());
                        idNumber.setText(workerDao.getWorkersInfoByNameSurname(newValue.getNameSurname()).getEmploymentInformation().getIdNumber().toString());
                        startDate.setText(workerDao.getWorkersInfoByNameSurname(newValue.getNameSurname()).getEmploymentInformation().getStartDate().toString());
                        contractType.setText(workerDao.getWorkersInfoByNameSurname(newValue.getNameSurname()).getEmploymentInformation().getContractType());

                        if(workerDao.getWorkersInfoByNameSurname(newValue.getNameSurname()).getEmploymentInformation().getEndDate()== null)endDate.setText("");
                        else endDate.setText(workerDao.getWorkersInfoByNameSurname(newValue.getNameSurname()).getEmploymentInformation().getEndDate().toString());

                        payFrequency.setText(workerDao.getWorkersInfoByNameSurname(newValue.getNameSurname()).getEmploymentInformation().getPayFreq());
                        accountNumber.setText(workerDao.getWorkersInfoByNameSurname(newValue.getNameSurname()).getEmploymentInformation().getAccountNum().toString());
                        taxCoefficient.setText(String.valueOf(workerDao.getWorkersInfoByNameSurname(newValue.getNameSurname()).getEmploymentInformation().getTaxCoeficient()));
                        netSalary.setText(String.valueOf(workerDao.getWorkersInfoByNameSurname(newValue.getNameSurname()).getEmploymentInformation().getNetSalary()));
                    }
                });
    }
    @FXML
    public void editButtonClicked(){
        try {
            DepartmentsService.editButtonClicked(workersTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        refreshButtonClicked();

    }


}





