package com.kemal.workers.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import com.kemal.workers.Main;

import java.io.IOException;

public class SceneWithButtonsController {
    @FXML private Button mechanicalDepartmentButton;
    @FXML private Button electricalDepartmentButton;
    @FXML private ImageView mechanical;
    @FXML private ImageView electrical;

    @FXML
    private void mechanicalDepartmentButtonClicked() throws IOException {
        Main.showMechanicalDepartment();
    }

    @FXML
    private  void electricalDepartmentButtonClicked() throws IOException {
        Main.showElectricalDepartment();
    }

}
