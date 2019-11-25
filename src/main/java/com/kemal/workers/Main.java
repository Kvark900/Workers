package com.kemal.workers;

import com.kemal.workers.controllers.ElectricalDepartmentController;
import com.kemal.workers.controllers.MechanicalDepartmentController;
import com.kemal.workers.dao.HibernateUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    private static Stage mainStage;
    private static BorderPane layoutWithHomeAndAdd;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        mainStage.setTitle("Radnici kompanije \"XY\"");
        mainStage.resizableProperty().setValue(Boolean.FALSE);
        showLayoutWithHomeAndAdd();
        showSceneWithButtons();
        mainStage.setOnCloseRequest(event -> HibernateUtil.shutdown());
    }

    private static void showLayoutWithHomeAndAdd() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/views/Layout.fxml"));
        layoutWithHomeAndAdd = loader.load();
        Scene scena1 = new Scene(layoutWithHomeAndAdd);
        mainStage.setScene(scena1);
        mainStage.show();
    }

    public static void showSceneWithButtons() throws IOException {
        FXMLLoader loader = new FXMLLoader(getResource("/views/SceneWithButtons.fxml"));
        BorderPane rasporedSaDugmadima = loader.load();
        layoutWithHomeAndAdd.setCenter(rasporedSaDugmadima);
    }

    public static void showElectricalDepartment() throws IOException {
        FXMLLoader loader = new FXMLLoader(getResource("/views/ElectricalDepartment.fxml"));
        BorderPane elOdjel = loader.load();
        ElectricalDepartmentController controller = loader.getController();
        controller.populateTable();
        layoutWithHomeAndAdd.setCenter(elOdjel);
    }

    public static void showMechanicalDepartment() throws IOException {
        FXMLLoader loader = new FXMLLoader(getResource("/views/MechanicalDepartment.fxml"));
        BorderPane mehOdjel = loader.load();
        MechanicalDepartmentController controller = loader.getController();
        controller.populateTable();
        layoutWithHomeAndAdd.setCenter(mehOdjel);
    }

    public static void showStageAdd() throws IOException {
        BorderPane addWorkerPane = FXMLLoader.load(getResource("/views/StageAddEditWorker.fxml"));
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

    private static URL getResource(String name) {
        return Main.class.getResource(name);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
