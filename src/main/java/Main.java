package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.controllers.ElectricalDepartmentController;
import main.java.controllers.MechanicalDepartmentController;

import java.io.IOException;

public class Main extends Application {
    private  static Stage stage1;
    private  static BorderPane layout1;
    private  static Stage stageAdd;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage1 = primaryStage;
        stage1.setTitle("Radnici kompanije \"XY\"");
        stage1.resizableProperty().setValue(Boolean.FALSE);
        showLayout1();
        showSceneBtn();
    }

    private static void showLayout1() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/resources/view/Layout1.fxml"));
        layout1 = loader.load();
        Scene scena1 = new Scene(layout1);
        stage1.setScene(scena1);
        stage1.show();
    }

    public static void showSceneBtn() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/resources/view/SceneBtn.fxml"));
        BorderPane rasporedSaDugmadima = loader.load();
        layout1.setCenter(rasporedSaDugmadima);
    }

    public static void  showElectrical () throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/resources/view/ElectricalDepartment.fxml"));
        BorderPane elOdjel;
        elOdjel = loader.load();
        ElectricalDepartmentController electricalDepartmentController = loader.getController();
        electricalDepartmentController.populateTable();
        layout1.setCenter(elOdjel);
    }

    public static void  showMechanical () throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/resources/view/MechanicalDepartment.fxml"));
        BorderPane mehOdjel;
        mehOdjel = loader.load();
        MechanicalDepartmentController mechanicalDepartmentController = loader.getController();
        mechanicalDepartmentController.populateTable();
        layout1.setCenter(mehOdjel);
    }

    public static void showStageAdd() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/resources/view/StageAdd.fxml"));
        BorderPane dodajRadnika;
        dodajRadnika = loader.load();
        stageAdd = new Stage();
        stageAdd.setTitle("Dodaj Novog Radnika");
        stageAdd.resizableProperty().setValue(Boolean.FALSE);
        Scene scene = new Scene(dodajRadnika);
        scene.getStylesheets().addAll(Main.class.getResource("/resources/css/text-field-red-border.css").toExternalForm(),
                Main.class.getResource("/resources/css/date-picker-red-border.css").toExternalForm());
        stageAdd.setScene(scene);
        stageAdd.initModality(Modality.WINDOW_MODAL);
        stageAdd.initOwner(stage1);
        stageAdd.showAndWait();
    }

    public void closeStageAdd () {
        stageAdd.close();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
