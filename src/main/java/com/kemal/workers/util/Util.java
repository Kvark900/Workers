package com.kemal.workers.util;

import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

public class Util {

    private Util(){}

    public static void changeDateFormat(DatePicker datePicker, DateTimeFormatter dateTimeFormatter) {
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null) return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, dateTimeFormatter);
            }
        });
    }

    public static <T extends Node> void addListener(T node, Predicate<T> isValid, PseudoClass errorClass) {
        node.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue)
                node.pseudoClassStateChanged(errorClass, !isValid.test(node));
        });
    }
}
