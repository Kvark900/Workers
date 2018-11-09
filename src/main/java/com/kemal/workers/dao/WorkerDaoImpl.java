package com.kemal.workers.dao;


import com.kemal.workers.model.Worker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;

import java.util.List;

public class WorkerDaoImpl implements WorkerDao {

    public ObservableList<Worker> getWorkersWithNameSurname(String department) {
        ObservableList<Worker> workersWithNameSurname = FXCollections.observableArrayList();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            org.hibernate.query.Query query = session.createQuery("from Worker where employmentInformation.department = ?");
            query.setParameter(0, department);
            List<Worker> list = query.list();

            for (Worker worker : list) {
                worker.setNameSurname();
                workersWithNameSurname.addAll((new Worker(worker.getNameSurname())));
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return workersWithNameSurname;
    }

    public void deleteSelectedWorker(Worker worker, String department) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            org.hibernate.query.Query query= session.createQuery("delete from Worker where " +
                                                                    "employmentInformation.department = ? and name =? " +
                                                                    "and surname = ?");
            query.setParameter(0, department);
            query.setParameter(1, worker.getNameSurname().split(" ")[0]);
            query.setParameter(2, worker.getNameSurname().split(" ")[1]);
            query.executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Worker getWorkersAllInfoByNameSurname(String nameSurname) {
        Worker worker = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            org.hibernate.query.Query query = session.createQuery("from Worker where name =? and surname = ?");
            query.setParameter(0, nameSurname.split(" ")[0]);
            query.setParameter(1, nameSurname.split(" ")[1]);
            worker = (Worker) query.uniqueResult();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return worker;
    }

    @Override
    public void saveWorker(Worker worker) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(worker);
            System.out.println("Worker has been saved!!");

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateWorker(Worker worker) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            session.update(worker);
            System.out.println("Worker has been updated!!");

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
