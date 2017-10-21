package com.kemal.workers.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import com.kemal.workers.Main;

import java.io.IOException;

public class LayoutController {
    @FXML private Button homeButton;
    @FXML private Button addButton;

    //Go home when button is clikced
    @FXML
    private void počDugmeKlik () throws IOException {
        Main.showSceneWithButtons();
    }

    //Show Stage to add a worker
    @FXML
    private void dodajDugmeKlik () throws IOException{
        Main.showStageAdd();
    }
}
