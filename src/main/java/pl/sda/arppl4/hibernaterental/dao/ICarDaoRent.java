package pl.sda.arppl4.hibernaterental.dao;

import pl.sda.arppl4.hibernaterental.model.CarRent;

import java.util.Optional;
import java.util.Set;

public interface ICarDaoRent {
    public void rentCar(CarRent carRent);
    public Set<CarRent> returnCarSet();
    public Optional<CarRent> showCar(Long id);

}
