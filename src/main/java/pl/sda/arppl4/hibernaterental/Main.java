package pl.sda.arppl4.hibernaterental;

import pl.sda.arppl4.hibernaterental.dao.GenericDao;
import pl.sda.arppl4.hibernaterental.model.Car;
import pl.sda.arppl4.hibernaterental.model.CarRent;
import pl.sda.arppl4.hibernaterental.parser.CarService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GenericDao <Car> genericDao = new GenericDao<>();
        GenericDao <CarRent> genericDaoRent = new GenericDao<>();
        CarService carService = new CarService(scanner, genericDao, genericDaoRent);

        carService.handleCommand();
    }
}
