package com.kemal.workers.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import com.kemal.workers.Main;

import java.io.IOException;

public class SceneWithButtonsController {
    @FXML Button mechanicalDepartmentButton;
    @FXML Button electricalDepartmentButton;
    @FXML ImageView mechanical;
    @FXML ImageView electrical;

    //Show workers from Mechanical Department
    @FXML
    private void mehDugmeKlik () throws IOException {
        Main.showMechanicalDepartment();}

    //Show workers from Electrical Department
    @FXML
    private  void elDugmeKlik () throws IOException {
        Main.showElectricalDepartment();}

}
