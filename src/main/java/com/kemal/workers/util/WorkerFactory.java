package com.kemal.workers.util;

import com.kemal.workers.dao.WorkerDao;
import com.kemal.workers.dao.WorkerDaoFactory;
import com.kemal.workers.model.ContactInformation;
import com.kemal.workers.model.EmploymentInformation;
import com.kemal.workers.model.Worker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.time.LocalDate;


/**
 * Created by Keno&Kemo on 06.08.2017..
 */
public class WorkerFactory {

    public static Worker createWorker(String name, String surname, LocalDate age, String address,
                                      String city, String telephoneNumber, String email, String department,
                                      Long idNumber, LocalDate startDate, String contractType, LocalDate endDate,
                                      String payFreq, Long accountNumber, double taxCoefficient, double netSalary) {

        Worker worker = new Worker();

        worker.setName(name);
        worker.setSurname(surname);
        worker.setAge(age);

        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setAddress(address);
        contactInformation.setCity(city);
        contactInformation.setTelephoneNum(telephoneNumber);
        contactInformation.setEmail(email);
        worker.setContactInformation(contactInformation);

        EmploymentInformation employmentInformation = new EmploymentInformation();
        employmentInformation.setDepartment(department);
        employmentInformation.setIdNumber(idNumber);
        employmentInformation.setStartDate(startDate);
        employmentInformation.setContractType(contractType);
        employmentInformation.setEndDate(endDate);
        employmentInformation.setPayFreq(payFreq);
        employmentInformation.setAccountNum(accountNumber);
        employmentInformation.setTaxCoefficient(taxCoefficient);
        employmentInformation.setNetSalary(netSalary);

        worker.setEmploymentInformation(employmentInformation);
        return worker;
    }

    public static void showWorkersInformation(TableView<Worker> workersTable, Label name, Label surname,
                                              Label age, Label city, Label address, Label telephoneNumber,
                                              Label email, Label idNumber, Label startDate, Label contractType,
                                              Label endDate, Label payFrequency, Label accountNumber,
                                              Label taxCoefficient, Label netSalary){
        WorkerDao workerDao = WorkerDaoFactory.getWorkerDao();
        workersTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Worker worker = workerDao.getWorker(newValue.getId());
                name.setText(worker.getName());
                surname.setText(worker.getSurname());
                age.setText(String.valueOf(worker.getAge()));
                city.setText(worker.getContactInformation().getCity());
                address.setText(worker.getContactInformation().getAddress());
                telephoneNumber.setText(worker.getContactInformation().getTelephoneNum());
                email.setText(worker.getContactInformation().getEmail());
                idNumber.setText(worker.getEmploymentInformation().getIdNumber().toString());
                startDate.setText(worker.getEmploymentInformation().getStartDate().toString());
                contractType.setText(worker.getEmploymentInformation().getContractType());
                if (worker.getEmploymentInformation().getEndDate() == null) endDate.setText("");
                else endDate.setText(worker.getEmploymentInformation().getEndDate().toString());
                payFrequency.setText(worker.getEmploymentInformation().getPayFreq());
                accountNumber.setText(worker.getEmploymentInformation().getAccountNum().toString());
                taxCoefficient.setText(String.valueOf(worker.getEmploymentInformation().getTaxCoefficient()));
                netSalary.setText(String.valueOf(worker.getEmploymentInformation().getNetSalary()));
            }
        });
    }


}
