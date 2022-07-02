package pl.sda.arppl4.hibernaterental.dao;

import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import pl.sda.arppl4.hibernaterental.util.HibernateUtil;

import java.util.*;


public class GenericDao<T> {
    public void add(T addObject) {
        SessionFactory factory = HibernateUtil.getSessionFactory();

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            session.merge(addObject);

            transaction.commit();
        } catch (SessionException sessionException) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void remove(T removeObject) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

            session.remove(removeObject);

            transaction.commit();
        }
    }

    public Optional<T> findId(Long id, Class<T> classType) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            T foundObject = session.get(classType, id);

            return Optional.ofNullable(foundObject);
        }
    }

    public List<T> list(Class<T> classType) {
        List<T> list = new ArrayList<>();

        SessionFactory fabrykaPolaczen = HibernateUtil.getSessionFactory();
        try (Session session = fabrykaPolaczen.openSession()) {
            TypedQuery<T> zapytanie = session.createQuery("from " + classType.getName(), classType);
            List<T> wynikZapytania = zapytanie.getResultList();

            list.addAll(wynikZapytania);
        } catch (SessionException sessionException) {
            System.err.println("Błąd wczytywania danych.");
        }

        return list;
    }

    public void update(T updateObject) {
        SessionFactory fabrykaPolaczen = HibernateUtil.getSessionFactory();

        Transaction transaction = null;
        try (Session session = fabrykaPolaczen.openSession()) {
            transaction = session.beginTransaction();

            session.merge(updateObject);

            transaction.commit();
        } catch (SessionException sessionException) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
    public void rent(T carRent) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.getTransaction();
            session.persist(carRent);
            transaction.commit();
        } catch (SessionException se) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public Optional <T> showCar(Long id, Class<T> classType) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            T objectCarRent = session.get(classType, id);
            return Optional.ofNullable(objectCarRent);
        }
    }
}

