package main.java.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.DAO.DaoElecDept;
import main.java.model.Worker;

import java.io.IOException;

public class ElectricalDepartmentController {

    @FXML private Label name;
    @FXML private Label surname;
    @FXML private Label age;
    @FXML private Label city;
    @FXML private Label address;
    @FXML private Label telephoneNum;
    @FXML private Label email;
    @FXML
    private Label idNum;
    @FXML
    private Label startDate;
    @FXML
    private Label contractType;
    @FXML
    private Label endDate;
    @FXML
    private Label payFreq;
    @FXML
    private Label accountNum;
    @FXML
    private Label taxCoefficient;
    @FXML
    private Label netSalary;
    @FXML Button refreshBtn;
    @FXML Button deletBtn;
    @FXML private TableView<Worker> workersTable;
    @FXML private TableColumn<Worker, String> workersTableColumn;

    private DaoElecDept daoElecDept;
    private final ObservableList<Worker> workersList = FXCollections.observableArrayList();

    //initialize method
    public void initialize() throws IOException {
        workersTableColumn.setCellValueFactory(new PropertyValueFactory<>("nameSurname"));
        rowSelected();
    }

    //Refresh table when button is clicked
    @FXML
    public void osvježiBtnKlik() {
        workersList.removeAll(workersList);
        populateTable();
    }

    //Delete worker
    @FXML
    private void obrišiBtnKlik() {
        Worker selectedItems = workersTable.getSelectionModel().getSelectedItem();
        workersTable.getItems().remove(selectedItems);
        daoElecDept = new DaoElecDept();
        daoElecDept.deleteSelectedWorker(selectedItems);
    }

    //Populate the table
    @FXML
    public void populateTable() {
        daoElecDept = new DaoElecDept();
        for(Worker worker : daoElecDept.getWorkersNameSurnameMechDep()) workersList.addAll(worker);
        workersTable.setItems(workersList);
    }

    //Show worker's information when row is selected
    @FXML
    private void rowSelected() {

        workersTable.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        name.setText(daoElecDept.getWorkerInfo(newValue.getNameSurname()).getName());
                        surname.setText(daoElecDept.getWorkerInfo(newValue.getNameSurname()).getSurname());
                        age.setText(daoElecDept.getWorkerInfo(newValue.getNameSurname()).getAge());
                        city.setText(daoElecDept.getWorkerInfo(newValue.getNameSurname()).getCity());
                        address.setText(daoElecDept.getWorkerInfo(newValue.getNameSurname()).getAddress());
                        telephoneNum.setText(daoElecDept.getWorkerInfo(newValue.getNameSurname()).getTelephoneNum());
                        email.setText(daoElecDept.getWorkerInfo(newValue.getNameSurname()).getEmail());
                        idNum.setText(daoElecDept.getWorkerInfo(newValue.getNameSurname()).getIdNumber().toString());
                        startDate.setText(daoElecDept.getWorkerInfo(newValue.getNameSurname()).getStartDate().toString());
                        contractType.setText(daoElecDept.getWorkerInfo(newValue.getNameSurname()).getContractType());

                        if(daoElecDept.getWorkerInfo(newValue.getNameSurname()).getEndDate()== null)endDate.setText("");
                        else endDate.setText(daoElecDept.getWorkerInfo(newValue.getNameSurname()).getEndDate().toString());

                        payFreq.setText(daoElecDept.getWorkerInfo(newValue.getNameSurname()).getPayFreq());
                        accountNum.setText(daoElecDept.getWorkerInfo(newValue.getNameSurname()).getAccountNum().toString());
                        taxCoefficient.setText(String.valueOf(daoElecDept.getWorkerInfo(newValue.getNameSurname()).getTaxCoeficient()));
                        netSalary.setText(String.valueOf(daoElecDept.getWorkerInfo(newValue.getNameSurname()).getNetSalary()));
                    }
                });
    }

}





