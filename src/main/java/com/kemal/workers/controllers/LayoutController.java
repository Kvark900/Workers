package com.kemal.workers.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import com.kemal.workers.Main;

import java.io.IOException;

public class LayoutController {
    @FXML private Button homeButton;
    @FXML private Button addButton;

    @FXML
    private void homeButtonClicked() throws IOException {
        Main.showSceneWithButtons();
    }

    @FXML
    private void addButtonClicked() throws IOException{
        Main.showStageAdd();
    }
}
