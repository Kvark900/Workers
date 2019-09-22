package com.kemal.workers.dao;

import com.kemal.workers.model.Worker;

import java.util.List;

/**
 * Created by Keno&Kemo on 16.10.2017..
 */
public interface WorkerDao {
    List<Worker> getWorkersByDepartment(String department);
    void deleteWorker(Long id);
    Worker getWorker(Long id);
    void saveWorker(Worker worker);
    void updateWorker(Worker worker);
}
