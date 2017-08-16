package main.java.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.DAO.DaoMechDept;
import main.java.model.Worker;

public class MechanicalDepartmentController {

    @FXML private Label name;
    @FXML private Label surname;
    @FXML private Label age;
    @FXML private Label city;
    @FXML private Label address;
    @FXML private Label telephoneNum;
    @FXML private Label email;
    @FXML Label idNum;
    @FXML Label startDate;
    @FXML Label contractType;
    @FXML Label endDate;
    @FXML Label payFreq;
    @FXML Label accountNum;
    @FXML Label taxCoefficient;
    @FXML Label netSalary;

    @FXML Button refreshBtn;
    @FXML Button deletBtn;
    @FXML private TableView<Worker> workersTable;
    @FXML private TableColumn<Worker, String> workersTableColumn;

    private DaoMechDept daoMechDept;
    private final ObservableList<Worker> workersList = FXCollections.observableArrayList();
    private Worker selectedItems;

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
        daoMechDept = new DaoMechDept();
        for(Worker worker : daoMechDept.getWorkersNameSurnameMechDep()) workersList.addAll(worker);
        workersTable.setItems(workersList);
    }

    //Delete a worker
    @FXML
    private void deleteBtnClicked(){
        selectedItems = workersTable.getSelectionModel().getSelectedItem();
        workersTable.getItems().remove(selectedItems);
        daoMechDept = new DaoMechDept();
        daoMechDept.deleteSelectedWorker(selectedItems);
    }

    //Show worker's information when row is selected
    @FXML
    private void rowSelected() {
        daoMechDept = new DaoMechDept();
        workersTable.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        name.setText(daoMechDept.getWorkerInfo(newValue.getNameSurname()).getName());
                        surname.setText(daoMechDept.getWorkerInfo(newValue.getNameSurname()).getSurname());
                        age.setText(daoMechDept.getWorkerInfo(newValue.getNameSurname()).getAge());
                        city.setText(daoMechDept.getWorkerInfo(newValue.getNameSurname()).getCity());
                        address.setText(daoMechDept.getWorkerInfo(newValue.getNameSurname()).getAddress());
                        telephoneNum.setText(daoMechDept.getWorkerInfo(newValue.getNameSurname()).getTelephoneNum());
                        email.setText(daoMechDept.getWorkerInfo(newValue.getNameSurname()).getEmail());
                        idNum.setText(daoMechDept.getWorkerInfo(newValue.getNameSurname()).getIdNumber().toString());
                        startDate.setText(daoMechDept.getWorkerInfo(newValue.getNameSurname()).getStartDate().toString());
                        contractType.setText(daoMechDept.getWorkerInfo(newValue.getNameSurname()).getContractType());

                        if(daoMechDept.getWorkerInfo(newValue.getNameSurname()).getEndDate()== null)endDate.setText("");
                        else endDate.setText(daoMechDept.getWorkerInfo(newValue.getNameSurname()).getEndDate().toString());

                        payFreq.setText(daoMechDept.getWorkerInfo(newValue.getNameSurname()).getPayFreq());
                        accountNum.setText(daoMechDept.getWorkerInfo(newValue.getNameSurname()).getAccountNum().toString());
                        taxCoefficient.setText(String.valueOf(daoMechDept.getWorkerInfo(newValue.getNameSurname()).getTaxCoeficient()));
                        netSalary.setText(String.valueOf(daoMechDept.getWorkerInfo(newValue.getNameSurname()).getNetSalary()));
                    }
                });
    }


}
