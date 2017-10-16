package main.java.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.DAO.WorkerDAO;
import main.java.DAO.WorkerDAOFactory;
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

    private WorkerDAO workerDAO = WorkerDAOFactory.getWorkerDAOByDepartment("mechanical");
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
        for(Worker worker : workerDAO.getWorkersNameSurname()) workersList.addAll(worker);
        workersTable.setItems(workersList);
    }

    //Delete a worker
    @FXML
    private void deleteBtnClicked(){
        selectedItems = workersTable.getSelectionModel().getSelectedItem();
        workersTable.getItems().remove(selectedItems);
        workerDAO.deleteSelectedWorker(selectedItems);
    }

    //Show worker's information when row is selected
    @FXML
    private void rowSelected() {
        workersTable.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        name.setText(workerDAO.getWorkersInfo(newValue.getNameSurname()).getName());
                        surname.setText(workerDAO.getWorkersInfo(newValue.getNameSurname()).getSurname());
                        age.setText(workerDAO.getWorkersInfo(newValue.getNameSurname()).getAge());
                        city.setText(workerDAO.getWorkersInfo(newValue.getNameSurname()).getCity());
                        address.setText(workerDAO.getWorkersInfo(newValue.getNameSurname()).getAddress());
                        telephoneNum.setText(workerDAO.getWorkersInfo(newValue.getNameSurname()).getTelephoneNum());
                        email.setText(workerDAO.getWorkersInfo(newValue.getNameSurname()).getEmail());
                        idNum.setText(workerDAO.getWorkersInfo(newValue.getNameSurname()).getIdNumber().toString());
                        startDate.setText(workerDAO.getWorkersInfo(newValue.getNameSurname()).getStartDate().toString());
                        contractType.setText(workerDAO.getWorkersInfo(newValue.getNameSurname()).getContractType());

                        if(workerDAO.getWorkersInfo(newValue.getNameSurname()).getEndDate()== null)endDate.setText("");
                        else endDate.setText(workerDAO.getWorkersInfo(newValue.getNameSurname()).getEndDate().toString());

                        payFreq.setText(workerDAO.getWorkersInfo(newValue.getNameSurname()).getPayFreq());
                        accountNum.setText(workerDAO.getWorkersInfo(newValue.getNameSurname()).getAccountNum().toString());
                        taxCoefficient.setText(String.valueOf(workerDAO.getWorkersInfo(newValue.getNameSurname()).getTaxCoeficient()));
                        netSalary.setText(String.valueOf(workerDAO.getWorkersInfo(newValue.getNameSurname()).getNetSalary()));
                    }
                });
    }


}
