package com.kemal.workers.dao;


import com.kemal.workers.model.Worker;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class WorkerDaoImpl implements WorkerDao {

    public List<Worker> getWorkersByDepartment(String department) {
        List<Worker> workers = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            org.hibernate.query.Query query = session.createQuery("from Worker where employmentInformation.department = ?");
            query.setParameter(0, department);
            workers = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workers;
    }

    public void deleteWorker(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("delete from Worker where " + "id = ?");
            query.setParameter(0, id);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Worker getWorker(Long id) {
        Worker worker = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Worker where id = ?");
            query.setParameter(0, id);
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
