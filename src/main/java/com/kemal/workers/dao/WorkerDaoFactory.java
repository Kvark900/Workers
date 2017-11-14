package com.kemal.workers.dao;

/**
 * Created by Keno&Kemo on 16.10.2017..
 */
public class WorkerDaoFactory {
    public static WorkerDao getWorkerDao(){
        return new WorkerDaoImpl();
    }

}
