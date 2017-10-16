package com.kemal.workers.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import com.kemal.workers.Main;

import java.io.IOException;

public class LayoutController {
    @FXML Button počDugme;
    @FXML Button dodajDugme;

    //Go home when button is clikced
    @FXML
    private void počDugmeKlik () throws IOException {
        Main.showSceneBtn();
    }

    //Show Stage to add a worker
    @FXML
    private void dodajDugmeKlik () throws IOException{
        Main.showStageAdd();
    }
}
