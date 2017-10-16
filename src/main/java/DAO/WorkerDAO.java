package main.java.DAO;

import javafx.collections.ObservableList;
import main.java.model.Worker;

/**
 * Created by Keno&Kemo on 16.10.2017..
 */
public interface WorkerDAO {
    ObservableList<Worker> getWorkersNameSurname();
    void deleteSelectedWorker (Worker worker);
    Worker getWorkersInfo(String nameSurname);

}
