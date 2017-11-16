package com.kemal.workers;

import com.kemal.workers.controllers.ElectricalDepartmentController;
import com.kemal.workers.controllers.MechanicalDepartmentController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private  static Stage mainStage;
    private  static BorderPane layoutWithHomeAndAdd;


    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage;
        mainStage.setTitle("Radnici kompanije \"XY\"");
        mainStage.resizableProperty().setValue(Boolean.FALSE);
        showLayoutWithHomeAndAdd();
        showSceneWithButtons();
    }

    private static void showLayoutWithHomeAndAdd() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/Layout1.fxml"));
        layoutWithHomeAndAdd = loader.load();
        Scene scena1 = new Scene(layoutWithHomeAndAdd);
        mainStage.setScene(scena1);
        mainStage.show();
    }

    public static void showSceneWithButtons() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/SceneBtn.fxml"));
        BorderPane rasporedSaDugmadima = loader.load();
        layoutWithHomeAndAdd.setCenter(rasporedSaDugmadima);
    }

    public static void showElectricalDepartment() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/ElectricalDepartment.fxml"));
        BorderPane elOdjel = loader.load();
        ElectricalDepartmentController electricalDepartmentController = loader.getController();
        electricalDepartmentController.populateTable();
        layoutWithHomeAndAdd.setCenter(elOdjel);
    }

    public static void showMechanicalDepartment() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/MechanicalDepartment.fxml"));
        BorderPane mehOdjel = loader.load();
        MechanicalDepartmentController mechanicalDepartmentController = loader.getController();
        mechanicalDepartmentController.populateTable();
        layoutWithHomeAndAdd.setCenter(mehOdjel);
    }

    public static void showStageAdd() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/StageAdd.fxml"));
        BorderPane addWorkerPane = loader.load();
        Stage stageAdd = new Stage();
        stageAdd.setTitle("Add New Worker");
        stageAdd.resizableProperty().setValue(Boolean.FALSE);
        Scene scene = new Scene(addWorkerPane);
        scene.getStylesheets().add("/css/redBorder.css");
        stageAdd.setScene(scene);
        stageAdd.initModality(Modality.WINDOW_MODAL);
        stageAdd.initOwner(mainStage);
        stageAdd.showAndWait();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
