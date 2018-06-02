package com.kemal.workers.service;

import com.kemal.workers.controllers.StageAddEditWorkerController;
import com.kemal.workers.dao.WorkerDao;
import com.kemal.workers.dao.WorkerDaoFactory;
import com.kemal.workers.model.Worker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Keno&Kemo on 15.11.2017..
 */
public class DepartmentsService {

    public static void editButtonClicked(TableView<Worker> workerTableView) throws IOException{
        Worker workerSelectedForEdit = workerTableView.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader(DepartmentsService.class.getResource("/views/StageAddEditWorker.fxml"));
        Parent parent = loader.load();

        StageAddEditWorkerController controller = loader.getController();
        controller.showEditWorkerOldInformation(workerSelectedForEdit);

        Stage stage = new Stage();
        stage.setTitle("Edit Worker");
        stage.setScene(new Scene(parent));
        parent.getStylesheets().add("/css/redBorder.css");

        stage.showAndWait();
    }

    public static void showWorkersInformationWhenRowIsSelected(TableView<Worker> workersTable, Label name, Label surname,
                                                               Label age, Label city, Label address, Label telephoneNumber,
                                                               Label email, Label idNumber, Label startDate, Label contractType,
                                                               Label endDate, Label payFrequency, Label accountNumber,
                                                               Label taxCoefficient, Label netSalary){
        WorkerDao workerDao = WorkerDaoFactory.getWorkerDao();
        workersTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null) {
                name.setText(workerDao.getWorkersInfoByNameSurname(newValue.getNameSurname()).getName());
                surname.setText(workerDao.getWorkersInfoByNameSurname(newValue.getNameSurname()).getSurname());
                age.setText(String.valueOf(workerDao.getWorkersInfoByNameSurname(newValue.getNameSurname()).getAge()));
                city.setText(workerDao.getWorkersInfoByNameSurname(newValue.getNameSurname()).getContactInformation().getCity());
                address.setText(workerDao.getWorkersInfoByNameSurname(newValue.getNameSurname()).getContactInformation().getAddress());
                telephoneNumber.setText(workerDao.getWorkersInfoByNameSurname(newValue.getNameSurname()).getContactInformation().getTelephoneNum());
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


}
