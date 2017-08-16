package main.java.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import main.java.Main;

import java.io.IOException;

public class SceneBtnController {
    @FXML Button mehDugme;
    @FXML Button elDugme;
    @FXML ImageView mehImg;
    @FXML ImageView elImg;

    //Show workers from Mechanical Department
    @FXML
    private void mehDugmeKlik () throws IOException {
        Main.showMechanical();}

    //Show workers from Electrical Department
    @FXML
    private  void elDugmeKlik () throws IOException {
        Main.showElectrical();}

}
