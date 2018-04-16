package com.kemal.workers.service;

import com.kemal.workers.model.ContactInformation;
import com.kemal.workers.model.EmploymentInformation;
import com.kemal.workers.model.Worker;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;


/**
 * Created by Keno&Kemo on 06.08.2017..
 */
public class StageAddService {

    //Method to change date format in JavaFX DatePicker
    public static void changeDateFormat(DatePicker datePicker){
        datePicker.setConverter(new StringConverter<LocalDate>() {
            private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            private final DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

            @Override
            public String toString(LocalDate localDate) {
                if(localDate == null) return "";
                return dateTimeFormatter1.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if(dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString,dateTimeFormatter1);}
        });
    }

    //Disable endDate when contract type is permanent
    public static void disableEndDate (DatePicker endDate, ChoiceBox<String> contractType){
        contractType.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) ->
                {if (Objects.equals(newValue, "Permanent")) {
                    endDate.setDisable(true);
                }
                else endDate.setDisable(false);
                });
    }

    //Listen and validate for empty field and change border color
    public static  <T extends Node> void validateNodeForEmptyByPredicate(T node, PseudoClass errorClass, Predicate<T> predicate) {
        node.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) {
                node.pseudoClassStateChanged(errorClass, predicate.test(node));
            }
        });
    }

    //Validate fields
    public static  <T extends Node> boolean validateField(T node, Predicate<T> predicate){
        boolean b;
        b = (predicate.test(node));
        return b;
    }

    //Listen and validate for Double
    public static void validateTextFieldDouble (TextField textField, PseudoClass errorClass) {
        AtomicBoolean isParsable = new AtomicBoolean(false);
        textField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            try {
                Double.parseDouble(textField.getText().trim());
                isParsable.set(true);
            } catch (NumberFormatException e) {
                System.out.println(e);
                isParsable.set(false);
            }

            if (!newValue) { //when focus lost
                if (textField.getText().trim().isEmpty() || !isParsable.get()) {
                    textField.pseudoClassStateChanged(errorClass, true);
                } else textField.pseudoClassStateChanged(errorClass, false);
            }
        });
    }

    //Listen and validate for Long
    public static void validateTextFieldLong (TextField textField, PseudoClass errorClass) {
        AtomicBoolean isParsable = new AtomicBoolean(false);
        textField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            try {
                Long.parseLong(textField.getText().trim());
                isParsable.set(true);
            } catch (NumberFormatException e) {
                System.out.println(e);
                isParsable.set(false);
            }

            if (!newValue) { //when focus lost
                if (textField.getText().trim().isEmpty() || !isParsable.get()) {
                    textField.pseudoClassStateChanged(errorClass, true);
                } else textField.pseudoClassStateChanged(errorClass, false);
            }
        });
    }

    public static Worker createWorker(String name, String surname, LocalDate age, String address,
                                    String city, String telephoneNumber, String email, String department, Long idNumber,
                                    LocalDate startDate, String contractType, LocalDate endDate, String payFreq,
                                    Long accountNumber, double taxCoeficient, double netSalary){

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
