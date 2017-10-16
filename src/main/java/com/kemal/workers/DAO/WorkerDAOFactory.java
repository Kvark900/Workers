package com.kemal.workers.DAO;

/**
 * Created by Keno&Kemo on 16.10.2017..
 */
public class WorkerDAOFactory {
    public static WorkerDAO getWorkerDAOByDepartment(String type){
        if (type.equalsIgnoreCase("Electrical")){
            return new WorkerDAOElecImpl();
        }else if(type.equalsIgnoreCase("Mechanical")){
            return new WorkerDAOMechImpl();
        }
        else return null;
    }
    public static AddWorkerDAO getAddWorkerDAO(){
        return new AddWorkerDAOImpl();
    }

}
