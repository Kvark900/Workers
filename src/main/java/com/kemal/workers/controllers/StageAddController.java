package com.kemal.workers.controllers;

import com.kemal.workers.Main;
import com.kemal.workers.dao.WorkerDao;
import com.kemal.workers.dao.WorkerDaoFactory;
import com.kemal.workers.model.ContactInformation;
import com.kemal.workers.model.EmploymentInformation;
import com.kemal.workers.model.Worker;
import com.kemal.workers.service.StageAddService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StageAddController {

    private final Main main = new Main();
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
    private final StageAddService stageAddService = new StageAddService();


    public void initialize() throws IOException {
        departmentBox.setItems(departmentList);
        contractType.setItems(contractTypeList);
        payFrequency.setItems(payFreqList);

        allControls= new Control[] {
                name, surname, age, address, city,
                telephoneNumber, email, departmentBox, idNumber, startDate,
                contractType, endDate, payFrequency, accountNumber, taxCoeficient,
                netSalary };

        //change date format
        stageAddService.changeDateFormat(age);
        stageAddService.changeDateFormat(startDate);
        stageAddService.changeDateFormat(endDate);

        //Disable endDate when contract type is permanent
        stageAddService.disableEndDate(endDate, contractType);

        //enterKeyPressed -> focus on the next item
        for (int i = 0 ; i < allControls.length-1; i++) {
            Control nextControl = allControls[i+1];
            allControls[i].addEventHandler(ActionEvent.ACTION, e -> nextControl.requestFocus());
        }

        //ENTER key pressed on okButton -> Save info
        okButton.setOnKeyPressed((KeyEvent event) ->{ switch (event.getCode()){
            case ENTER:try {
                okButtonClicked() /*method for saving data into database*/;}
            catch (IOException e) {e.printStackTrace();}}});

        //Validate fields for Long and Double variables
        stageAddService.validateTextFieldLong(idNumber, errorClass);
        stageAddService.validateTextFieldLong(accountNumber, errorClass);
        stageAddService.validateTextFieldDouble(taxCoeficient, errorClass);
        stageAddService.validateTextFieldDouble(netSalary, errorClass);

        //Listen and check for an empty input
        for(Control c : allControls){
            if(!c.equals(email)&& !c.equals(idNumber) && !c.equals(accountNumber)
                    && !c.equals(taxCoeficient) && !c.equals(netSalary)){
                emptyFields.add(c);
            }
        }

        for (Control c : emptyFields){
            if (c instanceof TextField) {
                stageAddService.validateNodeForEmptyByPredicate((TextField)c,
                        errorClass, field ->  field.getText().trim().isEmpty());}
            else if (c instanceof DatePicker)
                stageAddService.validateNodeForEmptyByPredicate((DatePicker)c,
                        errorClass1, picker -> picker.getValue() == null);
            else
                stageAddService.validateNodeForEmptyByPredicate((ChoiceBox) c,
                        errorClass2, box -> box.getValue() == null);
        }
    }// end of initialize

    //Close Stage
    @FXML private void closeButtonClicked() throws IOException {main.closeStageAdd();}

    //Submit entered information - OK button pressed
    @FXML private void okButtonClicked() throws IOException{
        WorkerDao workerDao = WorkerDaoFactory.getWorkerDAO();
        List<Control> controlList = new ArrayList<>();
        int count=0;

        // Getting required fields
        for(Control c : allControls){
            if(!c.equals(email) && !c.equals(endDate)){
                controlList.add(c);
            }
        }

        //Check if all required fields are valid
        for (Control c : controlList) {
            if (c instanceof TextField) {
                if (!stageAddService.validateField((TextField)c, field-> field.getText().trim().isEmpty()))
                    count++;
                else c.pseudoClassStateChanged(errorClass, true);
            }
            else if (c instanceof DatePicker){
                if (!stageAddService.validateField((DatePicker)c, field-> field.getValue()==null))
                    count++;
                else c.pseudoClassStateChanged(errorClass1, true);
            }
            else {
                if (!stageAddService.validateField((ChoiceBox)c, field-> field.getValue()==null))
                    count++;
                else c.pseudoClassStateChanged(errorClass2, true);
            }
        }

        //If required fields are valid, save data
        if (count == controlList.size()) {
            Worker worker = new Worker();
            worker.setName(name.getText().trim());
            worker.setSurname(surname.getText().trim());
            worker.setAge(age.getValue());


            ContactInformation contactInformation = new ContactInformation();
            contactInformation.setAddress(address.getText().trim());
            contactInformation.setCity(city.getText().trim());
            contactInformation.setTelephoneNum(telephoneNumber.getText().trim());
            contactInformation.setEmail(email.getText().trim());
            worker.setContactInformation(contactInformation);

            EmploymentInformation employmentInformation = new EmploymentInformation();
            employmentInformation.setDepartment(departmentBox.getValue());
            employmentInformation.setIdNumber(Long.parseLong(idNumber.getText().trim()));
            employmentInformation.setStartDate(startDate.getValue());
            employmentInformation.setContractType(contractType.getValue());
            employmentInformation.setEndDate(endDate.getValue());
            employmentInformation.setPayFreq(payFrequency.getValue());
            employmentInformation.setAccountNum(Long.parseLong(accountNumber.getText().trim()));
            employmentInformation.setTaxCoeficient(Double.parseDouble(taxCoeficient.getText().trim()));
            employmentInformation.setNetSalary(Double.parseDouble(netSalary.getText().trim()));
            worker.setEmploymentInformation(employmentInformation);

            workerDao.saveWorker(worker);


            main.closeStageAdd();
        }
        else {error1.setText("Fill in all red fields");
            error2.setText("Fill in all red fields");

        }

    }

}