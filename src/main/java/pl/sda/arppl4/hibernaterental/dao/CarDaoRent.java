package pl.sda.arppl4.hibernaterental.dao;

import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pl.sda.arppl4.hibernaterental.model.Car;
import pl.sda.arppl4.hibernaterental.model.CarRent;
import pl.sda.arppl4.hibernaterental.util.HibernateUtil;

import java.util.*;

public class CarDaoRent implements ICarDaoRent {

    @Override
    public void rentCar(CarRent carRent) {
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

    @Override
    public Set<CarRent> returnCarSet() {
        Set<CarRent> carSet = new HashSet<>();
        SessionFactory factory = HibernateUtil.getSessionFactory();

        try (Session session = factory.openSession()) {
            TypedQuery<CarRent> query = session.createQuery("from CarRent ", CarRent.class);

            carSet.addAll(query.getResultList());

        } catch (SessionException se) {
            System.err.println("Wrong data");
        }
        return carSet;
    }

    @Override
    public Optional <CarRent> showCar(Long id) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            CarRent objectCarRent = session.get(CarRent.class, id);
            return Optional.ofNullable(objectCarRent);
        }
    }
}




