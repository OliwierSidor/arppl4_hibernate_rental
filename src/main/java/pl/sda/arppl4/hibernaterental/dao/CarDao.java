package pl.sda.arppl4.hibernaterental.dao;

import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pl.sda.arppl4.hibernaterental.model.Car;
import pl.sda.arppl4.hibernaterental.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarDao implements ICarDao {

    @Override
    public void addCar(Car car) {
        SessionFactory factory = HibernateUtil.getSessionFactory();

        Transaction transaction = null;
        try (Session session = factory.openSession()) {

            transaction = session.beginTransaction();
            session.merge(car);

            transaction.commit();
        } catch (SessionException se) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void deleteCar(Car car) {
        SessionFactory factory = HibernateUtil.getSessionFactory();

        Transaction transaction = null;
        try (Session session = factory.openSession()) {

            transaction = session.beginTransaction();
            session.remove(car);

            transaction.commit();
        } catch (SessionException se) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public Optional<Car> returnCar(Long id) {
        SessionFactory factory = HibernateUtil.getSessionFactory();

        try (Session session = factory.openSession()) {
            Car objectCar = session.get(Car.class, id);
            return Optional.ofNullable(objectCar);
        }
    }

    @Override
    public List<Car> returnCarsList() {
        List<Car> carList = new ArrayList<>();

        SessionFactory factory = HibernateUtil.getSessionFactory();

        try (Session session = factory.openSession()) {
            TypedQuery<Car> question = session.createQuery("from Car ", Car.class);
            List<Car> resultOfQuestion = question.getResultList();

            carList.addAll(resultOfQuestion);
        } catch (SessionException se) {
            System.err.println("Błąd wczytywania danych");
        }
        return carList;
    }

    @Override
    public void updateCar(Car car) {
        SessionFactory factory = HibernateUtil.getSessionFactory();

        Transaction transaction = null;
        try (Session session = factory.openSession()) {

            transaction = session.beginTransaction();
            session.merge(car);

            transaction.commit();
        } catch (SessionException se) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
