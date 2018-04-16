package com.kemal.workers.controllers;

import com.kemal.workers.dao.WorkerDao;
import com.kemal.workers.dao.WorkerDaoFactory;
import com.kemal.workers.model.Worker;
import com.kemal.workers.service.StageAddService;
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

public class StageAddController {
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

    private final ObservableList<String> departmentList = FXCollections.observableArrayList ("Mechanical", "Electrical");
    private final ObservableList<String> contractTypeList = FXCollections.observableArrayList ("Permanent", "Fixed-term");
    private final ObservableList<String> payFreqList = FXCollections.observableArrayList ("Weekly", "Monthly");
    private final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
    private final PseudoClass errorClass1 = PseudoClass.getPseudoClass("error1");
    private final PseudoClass errorClass2 = PseudoClass.getPseudoClass("error2");

    //All fields
    private Control[] allControls;

    // Fields for which input is necessary
    private final List<Control> emptyFields = new ArrayList<>();

    private Boolean isInEditMode = false;
    private static Long id;

    @FXML
    private BorderPane rootPane;



    public void initialize() {
        departmentBox.setItems(departmentList);
        contractType.setItems(contractTypeList);
        payFrequency.setItems(payFreqList);
        allControls = new Control[] {name, surname, age, address, city, telephoneNumber,
                                     email, departmentBox, idNumber, startDate, contractType,
                                     endDate, payFrequency, accountNumber, taxCoeficient, netSalary };

        //Change date format
        StageAddService.changeDateFormat(age);
        StageAddService.changeDateFormat(startDate);
        StageAddService.changeDateFormat(endDate);

        //Disable endDate when contract type is permanent
        StageAddService.disableEndDate(endDate, contractType);

        //enterKeyPressed -> focus on the next item
        for (int i = 0 ; i < allControls.length-1; i++) {
            Control nextControl = allControls[i+1];
            allControls[i].addEventHandler(ActionEvent.ACTION, e -> nextControl.requestFocus());
        }

        //ENTER key pressed on okButton -> Save info
        okButton.setOnKeyPressed((KeyEvent event) ->{ switch (event.getCode()){
            case ENTER:
                try { okButtonClicked() /*method for saving data into database*/;}
                catch (Exception e) {e.printStackTrace();}}});

        //Validate fields for Long and Double variables
        StageAddService.validateTextFieldLong(idNumber, errorClass);
        StageAddService.validateTextFieldLong(accountNumber, errorClass);
        StageAddService.validateTextFieldDouble(taxCoeficient, errorClass);
        StageAddService.validateTextFieldDouble(netSalary, errorClass);

        //Listen and check for an empty input
        for(Control control : allControls){
            if(!control.equals(email) && !control.equals(idNumber) && !control.equals(accountNumber) && !control.equals(taxCoeficient) && !control.equals(netSalary)){
                emptyFields.add(control);
            }
        }

        for (Control control : emptyFields){
            if (control instanceof TextField)
                StageAddService.validateNodeForEmptyByPredicate((TextField) control, errorClass, field ->  field.getText().trim().isEmpty());
            else if (control instanceof DatePicker)
                StageAddService.validateNodeForEmptyByPredicate((DatePicker)control, errorClass1, picker -> picker.getValue() == null);
            else StageAddService.validateNodeForEmptyByPredicate((ChoiceBox)control, errorClass2, box -> box.getValue() == null);
        }
    }// End of initialize

    //Close Stage
    @FXML private void closeButtonClicked(){
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    //Submit entered information - OK button pressed
    @FXML private void okButtonClicked() {
        WorkerDao workerDao = WorkerDaoFactory.getWorkerDao();
        List<Control> requiredFieleds = new ArrayList<>();
        int count=0;

        // Getting required fields
        for(Control c : allControls){
            if(!c.equals(email) && !c.equals(endDate)){
                requiredFieleds.add(c);
            }
        }

        //Check if all required fields are valid
        for (Control control : requiredFieleds) {
            if (control instanceof TextField) {
                if (!StageAddService.validateField((TextField)control, field-> field.getText().trim().isEmpty())) count++;
                else control.pseudoClassStateChanged(errorClass, true);
            }
            else if (control instanceof DatePicker){
                if (!StageAddService.validateField((DatePicker)control, field-> field.getValue()==null)) count++;
                else control.pseudoClassStateChanged(errorClass1, true);
            }
            else {
                if (!StageAddService.validateField((ChoiceBox)control, field-> field.getValue()==null)) count++;
                else control.pseudoClassStateChanged(errorClass2, true);
            }
        }

        //If required fields are valid, save data
        if (count == requiredFieleds.size()) {

            Worker worker = StageAddService.createWorker(name.getText().trim(),surname.getText().trim(),age.getValue(),
                                                        address.getText().trim(), city.getText().trim(),
                                                        telephoneNumber.getText().trim(), email.getText().trim(),
                                                        departmentBox.getValue(),Long.parseLong(idNumber.getText().trim()),
                                                        startDate.getValue(), contractType.getValue(), endDate.getValue(),
                                                        payFrequency.getValue(), Long.parseLong(accountNumber.getText().trim()),
                                                        Double.parseDouble(taxCoeficient.getText().trim()),
                                                        Double.parseDouble(netSalary.getText().trim()));

            if(isInEditMode){
                worker.setId(id);
                workerDao.updateWorker(worker);
            }
            else workerDao.saveWorker(worker);

            closeButtonClicked();

        }
        else {error1.setText("Fill in all red fields");
            error2.setText("Fill in all red fields");
        }

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