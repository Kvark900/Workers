package com.kemal.workers.service;

import com.kemal.workers.model.ContactInformation;
import com.kemal.workers.model.EmploymentInformation;
import com.kemal.workers.model.Worker;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.function.Predicate;


/**
 * Created by Keno&Kemo on 06.08.2017..
 */
public class StageAddEditWorkerService {

    public static void changeDateFormat(DatePicker datePicker) {
        datePicker.setConverter(new StringConverter<LocalDate>() {
            private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            private final DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null) return "";
                return dateTimeFormatter1.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, dateTimeFormatter1);
            }
        });
    }

    public static void disableEndDateIfContractTypeIsPermanent(DatePicker endDate, ChoiceBox<String> contractType) {
        contractType.getSelectionModel().
                selectedItemProperty().addListener
                ((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    if (Objects.equals(newValue, "Permanent")) {
                        endDate.setDisable(true);

                    } else endDate.setDisable(false);
                });
    }

    public static <T extends Node> void isEmptyFieldByPredicate(T node, PseudoClass errorClass, Predicate<T> predicate) {
        node.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) {
                node.pseudoClassStateChanged(errorClass, predicate.test(node));
            }
        });
    }

    public static <T extends Node> boolean validateField(T node, Predicate<T> predicate) {
        return predicate.test(node);
    }

    public static void changeBorderColorIfInputIsValid(Node node, boolean isValid, PseudoClass errorClass) {
        node.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                if (!isValid) {
                    node.pseudoClassStateChanged(errorClass, true);
                } else
                    node.pseudoClassStateChanged(errorClass, false);
            }
        });
    }


    public static Worker createWorker(String name, String surname, LocalDate age, String address,
                                      String city, String telephoneNumber, String email, String department,
                                      Long idNumber, LocalDate startDate, String contractType, LocalDate endDate,
                                      String payFreq, Long accountNumber, double taxCoeficient, double netSalary) {

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
        employmentInformation.setTaxCoeficient(taxCoeficient);
        employmentInformation.setNetSalary(netSalary);

        worker.setEmploymentInformation(employmentInformation);
        return worker;
    }


}
