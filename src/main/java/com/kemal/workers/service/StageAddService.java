package com.kemal.workers.service;

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
import java.util.function.Predicate;


/**
 * Created by Keno&Kemo on 06.08.2017..
 */
public class StageAddService {

    private boolean isParsable = false;

    //Method to change date format in JavaFX DatePicker
    public void changeDateFormat(DatePicker datePicker){
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
    public void disableEndDate (DatePicker endDate, ChoiceBox<String> contractType){
        contractType.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) ->
                {if (Objects.equals(newValue, "Permanent")) {
                    endDate.setDisable(true);
                }
                else endDate.setDisable(false);
                });
    }

    //Listen and validate for empty field and change border color
    public <T extends Node> void validateNodeForEmptyByPredicate(T node, PseudoClass errorClass, Predicate<T> predicate) {
        node.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) {
                node.pseudoClassStateChanged(errorClass, predicate.test(node));
            }
        });
    }

    //Validate fields
    public <T extends Node> boolean validateField(T node, Predicate<T> predicate){
        boolean b;
        b = (predicate.test(node));
        return b;
    }

    //Listen and validate for Double
    public void validateTextFieldDouble (TextField textField, PseudoClass errorClass) {
        textField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            try {
                Double.parseDouble(textField.getText().trim());
                isParsable = true;
            } catch (NumberFormatException e) {
                System.out.println(e);
                isParsable = false;
            }

            if (!newValue) { //when focus lost
                if (textField.getText().trim().isEmpty() || !isParsable) {
                    textField.pseudoClassStateChanged(errorClass, true);
                } else textField.pseudoClassStateChanged(errorClass, false);
            }
        });
    }

    //Listen and validate for Long
    public void validateTextFieldLong (TextField textField, PseudoClass errorClass) {
        textField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            try {
                Long.parseLong(textField.getText().trim());
                isParsable = true;
            } catch (NumberFormatException e) {
                System.out.println(e);
                isParsable = false;
            }

            if (!newValue) { //when focus lost
                if (textField.getText().trim().isEmpty() || !isParsable) {
                    textField.pseudoClassStateChanged(errorClass, true);
                } else textField.pseudoClassStateChanged(errorClass, false);
            }
        });
    }



}
