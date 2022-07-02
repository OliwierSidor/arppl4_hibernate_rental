package pl.sda.arppl4.hibernaterental.parser;

import pl.sda.arppl4.hibernaterental.dao.CarDao;
import pl.sda.arppl4.hibernaterental.model.Body;
import pl.sda.arppl4.hibernaterental.model.Car;
import pl.sda.arppl4.hibernaterental.model.Gearbox;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CarService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final Scanner scanner;
    private final CarDao dao;

    public CarService(Scanner scanner, CarDao dao) {
        this.scanner = scanner;
        this.dao = dao;
    }

    public void handleCommand() {
        String command;
        do {
            System.out.println("What do you want? add/list/show/edit/delete");
            command = scanner.next();
            if (command.equals("add")) {
                handleAddCommand();
            } else if (command.equals("list")) {
                handleListCommand();
            } else if (command.equals("show")) {
                handleShowCommand();
            } else if (command.equals("edit")) {
                handleEditCommand();
            } else if (command.equals("delete")) {
                handleDeleteCommand();
            } else {
                System.out.println("Input is incorrect");
            }

        } while (!command.equals("quit"));
    }

    private void handleShowCommand() {
        System.out.println("What product do you want to see? (You have to get ID of product)");
        Long idCar = scanner.nextLong();
        List<Car> carList = dao.returnCarsList();
        for (Car car : carList) {
            if (car.getId() == idCar) {
                System.out.println(car);
            }
        }
    }

    private void handleDeleteCommand() {
        System.out.println("Enter the ID of the car what you want to remove");
        Long id = scanner.nextLong();
        Optional<Car> optionalCar = dao.returnCar(id);
        if (optionalCar.isPresent()) {
            dao.deleteCar(optionalCar.get());
            System.out.println("Car removed");
        }  else {
            System.out.println("Car not found");
        }
    }

    private void handleEditCommand() {
    }

    private void handleListCommand() {
        List<Car> carList = dao.returnCarsList();
        for (Car car : carList) {
            System.out.println(car);
        }
        System.out.println();
    }

    private void handleAddCommand() {
        System.out.println("Type name: ");
        String name = scanner.next();
        System.out.println("Type brand: ");
        String brand = scanner.next();
        System.out.println("Type production date");
        LocalDate productionDate = loadProductionDate();
        Body body = loadBody();
        System.out.println("Type amount of passengers: ");
        int amountofPassenger = scanner.nextInt();
        Gearbox gearbox = loadGearbox();
        System.out.println("Type amount of engine capacity");
        Double engineCapcity = scanner.nextDouble();
        Car car = new Car(null, name, brand, productionDate, body, amountofPassenger, gearbox, engineCapcity);
        dao.addCar(car);
    }

    private Gearbox loadGearbox() {
        Gearbox gearbox = null;
        do {
            try {
                System.out.println("Type unit: ");
                String unitString = scanner.next();
                gearbox = Gearbox.valueOf(unitString.toUpperCase());
            } catch (IllegalArgumentException iae) {
                System.err.println("Wrong unit, please type unit (unit/gram/milliliter)");
            }
        } while (gearbox == null);
        return gearbox;
    }

    private Body loadBody() {
        Body body = null;
        do {
            try {
                System.out.println("Type unit: ");
                String unitString = scanner.next();
                body = Body.valueOf(unitString.toUpperCase());
            } catch (IllegalArgumentException iae) {
                System.err.println("Wrong unit, please type unit (unit/gram/milliliter)");
            }
        } while (body == null);
        return body;
    }

    private LocalDate loadProductionDate() {
        LocalDate productionDate = null;
        do {
            try {
                System.out.println("Type expiry date: ");
                String expiryDateString = scanner.next();

                productionDate = LocalDate.parse(expiryDateString, FORMATTER);

                LocalDate today = LocalDate.now();
                if (productionDate.isAfter(today)) {
                    throw new IllegalArgumentException("Date is after today");
                }
            } catch (IllegalArgumentException | DateTimeException iae) {
                productionDate = null;
                System.err.println("Wrong date, please type date in format: yyyy-MM-dd");
            }
        } while (productionDate == null);
        return productionDate;
    }
}
