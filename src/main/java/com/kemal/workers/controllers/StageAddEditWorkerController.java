package com.kemal.workers.controllers;

import com.kemal.workers.dao.WorkerDao;
import com.kemal.workers.dao.WorkerDaoFactory;
import com.kemal.workers.model.Worker;
import com.kemal.workers.service.StageAddEditWorkerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class StageAddEditWorkerController {
    //region UI Fields
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
    private ChoiceBox <String> departmentBox;
    @FXML
    private TextField idNumber;
    @FXML
    private DatePicker startDate;
    @FXML
    private ChoiceBox <String> contractType;
    @FXML
    private DatePicker endDate;
    @FXML
    private ChoiceBox <String> payFrequency;
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

    private final ObservableList<String> departmentList = FXCollections.observableArrayList ("Mechanical", "Electrical");
    private final ObservableList<String> contractTypeList = FXCollections.observableArrayList ("Permanent", "Fixed-term");
    private final ObservableList<String> payFreqList = FXCollections.observableArrayList ("Weekly", "Monthly");
    private final PseudoClass textFieldRedBorder = PseudoClass.getPseudoClass("textFieldRedBorder");
    private final PseudoClass datePickerRedBorder = PseudoClass.getPseudoClass("datePickerRedBorder");
    private final PseudoClass choiceBoxRedBorder = PseudoClass.getPseudoClass("choiceBoxRedBorder");

    // Fields for which input is necessary
    private final List<Control> requiredFields = new ArrayList<>();

    private Boolean isInEditMode = false;

    private static Long id;

    @FXML
    private BorderPane rootPane;


    public void initialize() {
        departmentBox.setItems(departmentList);
        contractType.setItems(contractTypeList);
        payFrequency.setItems(payFreqList);

        //Getting all controls
        Control[] allControls = new Control[]{name, surname, age, address, city, telephoneNumber,
                                              email, departmentBox, idNumber, startDate, contractType,
                                              endDate, payFrequency, accountNumber, taxCoeficient, netSalary};

        //Getting only required fields
        for(Control control : allControls) if(!control.equals(email)) requiredFields.add(control);

        //region Changing date format
        StageAddEditWorkerService.changeDateFormat(age);
        StageAddEditWorkerService.changeDateFormat(startDate);
        StageAddEditWorkerService.changeDateFormat(endDate);
        //endregion

        //Disabling endDate when contract type is permanent
        StageAddEditWorkerService.disableEndDate(endDate, contractType);

        //enterKeyPressed -> focus on the next item
        for (int i = 0; i < allControls.length-1; i++) {
            Control nextControl = allControls[i+1];
            allControls[i].addEventHandler(ActionEvent.ACTION, e -> nextControl.requestFocus());
        }

        //ENTER key pressed on okButton -> Saving worker information
        okButton.setOnKeyPressed((KeyEvent event) ->{ switch (event.getCode()){
            case ENTER:
                try { okButtonClicked() /*method for saving data into database*/;}
                catch (Exception e) {e.printStackTrace(); }
        } });


        //region Listening and checking for an empty input on required fields
        //==============================================================================================================
        for (Control control : requiredFields){
            if (control instanceof TextField)
                StageAddEditWorkerService.isEmptyFieldByPredicate((TextField) control, textFieldRedBorder, field ->  field.getText().trim().isEmpty());
            else if (control instanceof DatePicker)
                StageAddEditWorkerService.isEmptyFieldByPredicate((DatePicker)control, datePickerRedBorder, picker -> picker.getValue() == null);
            else StageAddEditWorkerService.isEmptyFieldByPredicate((ChoiceBox)control, choiceBoxRedBorder, box -> box.getValue() == null);
        }
        //==============================================================================================================
        //endregion


        //region Validating fields for Long and Double variables
        //==============================================================================================================
        StageAddEditWorkerService.isParsableIntoLong(idNumber, textFieldRedBorder);
        StageAddEditWorkerService.isParsableIntoLong(accountNumber, textFieldRedBorder);
        StageAddEditWorkerService.isParsableIntoDouble(taxCoeficient, textFieldRedBorder);
        StageAddEditWorkerService.isParsableIntoDouble(netSalary, textFieldRedBorder);
        //==============================================================================================================
        //endregion

    }// End of initialize

    //Submit entered information - OK button pressed
    @FXML private void okButtonClicked() {
        WorkerDao workerDao = WorkerDaoFactory.getWorkerDao();
        int numberOfFilledFields=0;
        boolean isParsableIntoDouble = false;
        boolean isParsableIntoLong = false;

        //If endDate is disabled remove it from required fields
        if(endDate.isDisabled()) requiredFields.remove(endDate);

        //Check if all required fields are valid
        for (Control control : requiredFields) {
            if(control.equals(idNumber) || control.equals(accountNumber))
                isParsableIntoLong = StageAddEditWorkerService.isParsableIntoLong((TextField) control, textFieldRedBorder);
            else if(control.equals(taxCoeficient) || control.equals(netSalary))
                isParsableIntoDouble = StageAddEditWorkerService.isParsableIntoDouble((TextField) control, textFieldRedBorder);

            //region Counting number of filled fields
            if (control instanceof TextField) {
                if (!StageAddEditWorkerService.validateField((TextField)control, field-> field.getText().trim().isEmpty())) numberOfFilledFields++;
                else control.pseudoClassStateChanged(textFieldRedBorder, true);
            }
            else if (control instanceof DatePicker){
                if (!StageAddEditWorkerService.validateField((DatePicker)control, field-> field.getValue()==null)) numberOfFilledFields++;
                else control.pseudoClassStateChanged(datePickerRedBorder, true);
            }
            else {
                if (!StageAddEditWorkerService.validateField((ChoiceBox)control, field-> field.getValue()==null)) numberOfFilledFields++;
                else control.pseudoClassStateChanged(choiceBoxRedBorder, true);
            }
            //endregion
        }

        //If required fields are valid, save data
        if (numberOfFilledFields == requiredFields.size() && isParsableIntoDouble && isParsableIntoLong) {

            Worker worker = StageAddEditWorkerService.createWorker(name.getText().trim(),surname.getText().trim(),
                                                                    age.getValue(), address.getText().trim(),
                                                                    city.getText().trim(), telephoneNumber.getText().trim(),
                                                                    email.getText().trim(), departmentBox.getValue(),
                                                                    Long.parseLong(idNumber.getText().trim()),
                                                                    startDate.getValue(), contractType.getValue(),
                                                                    endDate.getValue(), payFrequency.getValue(),
                                                                    Long.parseLong(accountNumber.getText().trim()),
                                                                    Double.parseDouble(taxCoeficient.getText().trim()),
                                                                    Double.parseDouble(netSalary.getText().trim()));

            if(isInEditMode){
                worker.setId(id);
                workerDao.updateWorker(worker);
            }
            else workerDao.saveWorker(worker);

            closeButtonClicked();

        }
        else {
            error1.setText("Fill in all red fields");
            error2.setText("Fill in all red fields");
        }
    }

    //Close Stage
    @FXML private void closeButtonClicked(){
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    public void showEditWorkerOldInformation(Worker worker) {
        WorkerDao workerDao = WorkerDaoFactory.getWorkerDao();
        Worker oldWorker = workerDao.getWorkersInfoByNameSurname(worker.getNameSurname());

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
        taxCoeficient.setText(String.valueOf(oldWorker.getEmploymentInformation().getTaxCoeficient()));
        netSalary.setText(String.valueOf(oldWorker.getEmploymentInformation().getNetSalary()));

        isInEditMode = true;
    }

}