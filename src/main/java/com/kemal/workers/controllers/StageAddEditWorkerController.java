package com.kemal.workers.controllers;

import com.kemal.workers.dao.WorkerDao;
import com.kemal.workers.dao.WorkerDaoFactory;
import com.kemal.workers.model.Worker;
import com.kemal.workers.util.WorkerFactory;
import com.kemal.workers.util.InputValidator;
import com.kemal.workers.util.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StageAddEditWorkerController {
    //region UI Fields
    @FXML
    private BorderPane rootPane;
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private DatePicker age;
    @FXML
    private TextField address;
    @FXML
    private TextField city;
    @FXML
    private TextField telephoneNumber;
    @FXML
    private TextField email;
    @FXML
    private ChoiceBox<String> departmentBox;
    @FXML
    private TextField idNumber;
    @FXML
    private DatePicker startDate;
    @FXML
    private ChoiceBox<String> contractType;
    @FXML
    private DatePicker endDate;
    @FXML
    private ChoiceBox<String> payFrequency;
    @FXML
    private TextField accountNumber;
    @FXML
    private TextField taxCoeficient;
    @FXML
    private TextField netSalary;
    @FXML
    private Label error1;
    @FXML
    private Label error2;
    @FXML
    private Button closeButton;
    @FXML
    private Button okButton;
    //endregion

    private final ObservableList<String> departmentList = FXCollections.observableArrayList("Mechanical", "Electrical");
    private final ObservableList<String> contractTypeList = FXCollections.observableArrayList("Permanent", "Fixed-term");
    private final ObservableList<String> payFreqList = FXCollections.observableArrayList("Weekly", "Monthly");
    private final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
    private final List<Control> allControls = new ArrayList<>();
    private final List<Control> requiredFields = new ArrayList<>();
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
    private boolean isInEditMode;
    private static Long id;

    public void initialize() {
        initFields();
        initChoiceBoxItems();
        changeDateFormat();
        initListenerForContractType();
        initValidationListeners();
        initEventHandlerForFocusChangeOnEnter();
        initEventHandlerForOkButtonEnterPressed();
    }

    @FXML
    private void okButtonClicked() {
        WorkerDao workerDao = WorkerDaoFactory.getWorkerDao();
        if (endDate.isDisabled())
            requiredFields.remove(endDate);
        if (isValidOnSubmit()) {
            Worker worker = WorkerFactory.createWorker(name.getText().trim(), surname.getText().trim(),
                                                        age.getValue(), address.getText().trim(),
                                                        city.getText().trim(), telephoneNumber.getText().trim(),
                                                        email.getText().trim(), departmentBox.getValue(),
                                                        Long.parseLong(idNumber.getText().trim()),
                                                        startDate.getValue(), contractType.getValue(),
                                                        endDate.getValue(), payFrequency.getValue(),
                                                        Long.parseLong(accountNumber.getText().trim()),
                                                        Double.parseDouble(taxCoeficient.getText().trim()),
                                                        Double.parseDouble(netSalary.getText().trim()));
            if (isInEditMode) {
                worker.setId(id);
                workerDao.updateWorker(worker);
            } else workerDao.saveWorker(worker);
            closeButtonClicked();
        } else {
            error1.setText("Fill in all red fields");
            error2.setText("Fill in all red fields");
        }
    }

    private boolean isValidOnSubmit() {
        boolean valid = true;
        for (Control control : requiredFields) {
            boolean validField = isValidField(control);
            control.pseudoClassStateChanged(errorClass, !validField);
            if (valid && !validField) valid = false;
        }
        return valid;
    }

    @FXML
    private void closeButtonClicked() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    public void showWorkersOldInformation(Worker worker) {
        WorkerDao workerDao = WorkerDaoFactory.getWorkerDao();
        Worker oldWorker = workerDao.getWorker(worker.getId());
        id = oldWorker.getId();
        name.setText(oldWorker.getName());
        surname.setText(oldWorker.getSurname());
        age.setValue(oldWorker.getAge());
        address.setText(oldWorker.getContactInformation().getAddress());
        city.setText(oldWorker.getContactInformation().getCity());
        telephoneNumber.setText(oldWorker.getContactInformation().getTelephoneNum());
        email.setText(oldWorker.getContactInformation().getEmail());
        departmentBox.setValue(oldWorker.getEmploymentInformation().getDepartment());
        idNumber.setText(oldWorker.getEmploymentInformation().getIdNumber().toString());
        startDate.setValue(oldWorker.getEmploymentInformation().getStartDate());
        contractType.setValue(oldWorker.getEmploymentInformation().getContractType());
        endDate.setValue(oldWorker.getEmploymentInformation().getEndDate());
        payFrequency.setValue(oldWorker.getEmploymentInformation().getPayFreq());
        accountNumber.setText(oldWorker.getEmploymentInformation().getAccountNum().toString());
        taxCoeficient.setText(String.valueOf(oldWorker.getEmploymentInformation().getTaxCoefficient()));
        netSalary.setText(String.valueOf(oldWorker.getEmploymentInformation().getNetSalary()));
        isInEditMode = true;
    }

    private void initChoiceBoxItems() {
        departmentBox.setItems(departmentList);
        contractType.setItems(contractTypeList);
        payFrequency.setItems(payFreqList);
    }

    private void initFields() {
        try {
            Field[] declaredFields = this.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                Object o = field.get(this);
                if (!isInputField(o)) continue;
                Control control = (Control) o;
                allControls.add(control);
                if (!control.getId().equals(email.getId()))
                    requiredFields.add(control);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isInputField(Object control) {
        return control instanceof TextField || control instanceof DatePicker || control instanceof ChoiceBox;
    }

    private void changeDateFormat() {
        Util.changeDateFormat(age, DATE_TIME_FORMATTER);
        Util.changeDateFormat(startDate, DATE_TIME_FORMATTER);
        Util.changeDateFormat(endDate, DATE_TIME_FORMATTER);
    }

    private void initEventHandlerForFocusChangeOnEnter() {
        for (int i = 0; i < allControls.size() - 1; i++) {
            Control nextControl = allControls.get(i + 1);
            allControls.get(i).addEventHandler(ActionEvent.ACTION, e -> nextControl.requestFocus());
        }
    }

    private void initEventHandlerForOkButtonEnterPressed() {
        okButton.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                try { okButtonClicked(); }
                catch (Exception e) { e.printStackTrace(); }
            }
        });
    }

    private void initValidationListeners() {
        requiredFields.forEach(c -> Util.addListener(c, this::isValidField, errorClass));
    }

    private boolean isValidField(Control control) {
        if (control instanceof TextField) {
            if (((TextField) control).getText().trim().isEmpty())
                return false;
            else if (control == idNumber || control == accountNumber)
                return InputValidator.isParsableIntoLong(((TextField) control).getText());
            else if (control == taxCoeficient || control == netSalary)
                return InputValidator.isParsableIntoDouble(((TextField) control).getText());
        } else if (control instanceof DatePicker)
            return ((DatePicker) control).getValue() != null;
        else if (control instanceof ChoiceBox)
            return ((ChoiceBox) control).getValue() != null;
        return true;
    }

    private void initListenerForContractType() {
        contractType.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {endDate.setDisable(Objects.equals(newValue, "Permanent"));});
    }

}