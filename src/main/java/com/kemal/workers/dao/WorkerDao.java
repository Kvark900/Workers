package com.kemal.workers.dao;

import com.kemal.workers.model.Worker;
import javafx.collections.ObservableList;

/**
 * Created by Keno&Kemo on 16.10.2017..
 */
public interface WorkerDao {
    ObservableList<Worker> getWorkersNameSurname(String department);
    void deleteSelectedWorker (Worker worker, String department);
    Worker getWorkersInfoByNameSurname(String nameSurname);
    void saveWorker(Worker worker);
    void updateWorker(Worker worker);
}
