package com.kemal.workers.controllers;


import com.kemal.workers.dao.WorkerDao;
import com.kemal.workers.dao.WorkerDaoFactory;
import com.kemal.workers.model.Worker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MechanicalDepartmentController {

    @FXML
    private Label name;
    @FXML private Label surname;
    @FXML private Label age;
    @FXML private Label city;
    @FXML private Label address;
    @FXML private Label telephoneNumber;
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
    @FXML private TableView<Worker> workersTable;
    @FXML private TableColumn<Worker, String> workersTableColumn;

    private WorkerDao workerDao = WorkerDaoFactory.getWorkerDAO();
    private final ObservableList<Worker> workersList = FXCollections.observableArrayList();

    //initialize method
    @FXML
    public void initialize(){
        workersTableColumn.setCellValueFactory(new PropertyValueFactory<>("nameSurname"));
        rowSelected();
    }

    //Refresh the table when button is clicked
    @FXML
    public void osvježiBtnKlik(){
        workersList.removeAll(workersList);
        populateTable();
    }

    //Populate the table
    @FXML
    public void populateTable(){
        for(Worker worker : workerDao.getWorkersNameSurname("Mechanical")) workersList.addAll(worker);
        workersTable.setItems(workersList);
    }

    //Delete a worker
    @FXML
    private void deleteBtnClicked(){
        Worker selectedItems = workersTable.getSelectionModel().getSelectedItem();
        workersTable.getItems().remove(selectedItems);
        workerDao.deleteSelectedWorker(selectedItems, "Mechanical");
    }

    //Show worker's information when row is selected
    @FXML
    private void rowSelected() {
        workersTable.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        name.setText(workerDao.getWorkersInfo(newValue.getNameSurname()).getName());
                        surname.setText(workerDao.getWorkersInfo(newValue.getNameSurname()).getSurname());
                        age.setText(String.valueOf(workerDao.getWorkersInfo(newValue.getNameSurname()).getAge()));
                        city.setText(workerDao.getWorkersInfo(newValue.getNameSurname()).getContactInformation().getCity());
                        address.setText(workerDao.getWorkersInfo(newValue.getNameSurname()).getContactInformation().getAddress());
                        telephoneNumber.setText(workerDao.getWorkersInfo(newValue.getNameSurname()).getContactInformation().getTelephoneNum());
                        email.setText(workerDao.getWorkersInfo(newValue.getNameSurname()).getContactInformation().getEmail());
                        idNumber.setText(workerDao.getWorkersInfo(newValue.getNameSurname()).getEmploymentInformation().getIdNumber().toString());
                        startDate.setText(workerDao.getWorkersInfo(newValue.getNameSurname()).getEmploymentInformation().getStartDate().toString());
                        contractType.setText(workerDao.getWorkersInfo(newValue.getNameSurname()).getEmploymentInformation().getContractType());

                        if(workerDao.getWorkersInfo(newValue.getNameSurname()).getEmploymentInformation().getEndDate()== null)endDate.setText("");
                        else endDate.setText(workerDao.getWorkersInfo(newValue.getNameSurname()).getEmploymentInformation().getEndDate().toString());

                        payFrequency.setText(workerDao.getWorkersInfo(newValue.getNameSurname()).getEmploymentInformation().getPayFreq());
                        accountNumber.setText(workerDao.getWorkersInfo(newValue.getNameSurname()).getEmploymentInformation().getAccountNum().toString());
                        taxCoefficient.setText(String.valueOf(workerDao.getWorkersInfo(newValue.getNameSurname()).getEmploymentInformation().getTaxCoeficient()));
                        netSalary.setText(String.valueOf(workerDao.getWorkersInfo(newValue.getNameSurname()).getEmploymentInformation().getNetSalary()));
                    }
                });
    }


}
