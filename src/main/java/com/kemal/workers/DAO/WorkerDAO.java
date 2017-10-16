package com.kemal.workers.DAO;

import com.kemal.workers.model.Worker;
import javafx.collections.ObservableList;

/**
 * Created by Keno&Kemo on 16.10.2017..
 */
public interface WorkerDAO {
    ObservableList<Worker> getWorkersNameSurname();
    void deleteSelectedWorker (Worker worker);
    Worker getWorkersInfo(String nameSurname);

}
