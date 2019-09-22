package com.kemal.workers.controllers;

import com.kemal.workers.model.Worker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Keno&Kemo on 15.11.2017..
 */
abstract class DepartmentsBaseController {

    static void editButtonClicked(TableView<Worker> workerTableView) throws IOException{
        Worker worker = workerTableView.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(DepartmentsBaseController.class.getResource("/views/StageAddEditWorker.fxml"));
        Parent parent = loader.load();
        StageAddEditWorkerController controller = loader.getController();
        controller.showWorkersOldInformation(worker);
        Stage stage = new Stage();
        stage.setTitle("Edit Worker");
        stage.setScene(new Scene(parent));
        parent.getStylesheets().add("/css/redBorder.css");
        stage.showAndWait();
    }

}
