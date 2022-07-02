package pl.sda.arppl4.hibernaterental.dao;

import pl.sda.arppl4.hibernaterental.model.Car;

import java.util.List;
import java.util.Optional;

public interface ICarDao {
    public void addCar(Car car);
    public void deleteCar(Car car);
    public Optional<Car> returnCar(Long id);
    public List<Car> returnCarsList();
    public void updateCar(Car car);
}
