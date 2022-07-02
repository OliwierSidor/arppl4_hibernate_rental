package pl.sda.arppl4.hibernaterental;

import pl.sda.arppl4.hibernaterental.dao.CarDao;
import pl.sda.arppl4.hibernaterental.dao.CarDaoRent;
import pl.sda.arppl4.hibernaterental.parser.CarService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CarDao dao = new CarDao();
        CarDaoRent daoRent = new CarDaoRent();
        CarService carService = new CarService(scanner, dao, daoRent);

        carService.handleCommand();
    }
}
