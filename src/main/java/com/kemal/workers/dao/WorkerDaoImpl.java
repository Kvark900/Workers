package com.kemal.workers.dao;


import com.kemal.workers.model.Worker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;

import java.util.List;

public class WorkerDaoImpl implements WorkerDao {

    //Get Worker (nameSurname)
    public ObservableList<Worker> getWorkersNameSurname(String department) {
        ObservableList<Worker> nameSurname = FXCollections.observableArrayList();
       try (Session session = HibernateUtil.getSessionFactory().openSession()) {
           session.beginTransaction();
           org.hibernate.query.Query query= session.createQuery("from Worker where employmentInformation.department =?");
           query.setParameter(0, department);
           List<Worker> list = query.list();

           for (Worker worker:list) {
               worker.setNameSurname();
               nameSurname.addAll(( new Worker(worker.getNameSurname())));
           }

           session.getTransaction().commit();
       }catch (Exception e) {e.printStackTrace();}

        return nameSurname;
    }

    //Delete selected worker
    public void deleteSelectedWorker (Worker worker, String department){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            org.hibernate.query.Query query= session.createQuery("delete from Worker where employmentInformation.department = ? and name =? and surname = ?");
            query.setParameter(0, department);
            query.setParameter(1, worker.getNameSurname().split(" ")[0]);
            query.setParameter(2, worker.getNameSurname().split(" ")[1]);
            query.executeUpdate();

            session.getTransaction().commit();
        }catch (Exception e) {e.printStackTrace();}
    }

    //Get Worker (allInfo)
    public Worker getWorkersInfo(String nameSurname) {
        Worker worker = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            org.hibernate.query.Query query= session.createQuery("from Worker where name =? and surname = ?");
            query.setParameter(0, nameSurname.split(" ")[0]);
            query.setParameter(1, nameSurname.split(" ")[1]);
            worker = (Worker) query.uniqueResult();

            session.getTransaction().commit();
        }catch (Exception e) {e.printStackTrace();}

        return worker;
    }

    @Override
    public void saveWorker(Worker worker) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            session.save(worker);
            System.out.println("Worker has been saved!!");

            session.getTransaction().commit();
        }catch (Exception e) {e.printStackTrace();}

    }
}
