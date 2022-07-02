package pl.sda.arppl4.hibernaterental.parser;

import pl.sda.arppl4.hibernaterental.dao.GenericDao;
import pl.sda.arppl4.hibernaterental.model.Body;
import pl.sda.arppl4.hibernaterental.model.Car;
import pl.sda.arppl4.hibernaterental.model.CarRent;
import pl.sda.arppl4.hibernaterental.model.Gearbox;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CarService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final Scanner scanner;
    private GenericDao <Car> dao;
    private GenericDao <CarRent> daoRent;

    public CarService(Scanner scanner, GenericDao dao, GenericDao daoRent) {
        this.scanner = scanner;
        this.dao = dao;
        this.daoRent = daoRent;
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

    private void addRent() {
        System.out.println("What car do you want to rent? (You have to get ID of product)");
        Long idCar = scanner.nextLong();
        Optional <Car> carOptional = dao.findId(idCar, Car.class);
        if (carOptional.isPresent()){
            Car car = carOptional.get();

            System.out.println("Type your name: ");
            String rentName = scanner.next();
            System.out.println("Type your surname: ");
            String rentSurname = scanner.next();
            System.out.println("When do you want rent a car?");
            LocalDateTime rentCarDate = loadDateWhenUserRent();
            System.out.println("When do you want return a car?");
            LocalDateTime returnCarDate = loadDateWhenUserReturn();

            CarRent carRent = new CarRent(rentCarDate, rentName, rentSurname, car);

            daoRent.rent(carRent);
        }
    }

    private void handleAddCommand() {
        System.out.println("Type name: ");
        String name = scanner.next();
        System.out.println("Type brand: ");
        String brand = scanner.next();
        LocalDate productionDate = loadProductionDate();
        Body body = loadBody();
        System.out.println("Type amount of passengers: ");
        int amountOfPassenger = scanner.nextInt();
        Gearbox gearbox = loadGearbox();
        System.out.println("Type amount of engine capacity");
        Double engineCapcity = scanner.nextDouble();
        Car car = new Car(name, brand, productionDate, body, amountOfPassenger, gearbox, engineCapcity);
        dao.add(car);
    }

    private void handleListCommand() {
        List<Car> carList = dao.list(Car.class);
        for (Car car : carList) {
            System.out.println(car);
        }
        System.out.println();
    }

    private void handleShowCommand() {
        System.out.println("What car do you want to see? (You have to get ID of car)");
        Long idCar = scanner.nextLong();
        List<Car> carList = dao.list(Car.class);
        for (Car car : carList) {
            if (car.getId() == idCar) {
                System.out.println(car);
            }
        }
    }

    private void handleEditCommand() {
        Car car;
        System.out.println("What car do you need? (id)");
        Long idCar = scanner.nextLong();
        Optional<Car> optionalCar = dao.showCar(idCar, Car.class);
        if (optionalCar.isPresent()) {
            System.out.println("What you want to update? name/body/date(production/brand)/amount(passengers)/gearbox/engine(capacity)");
            String text = scanner.next();
            car = optionalCar.get();
            if (text.equals("name")) {
                System.out.println("Type name: ");
                String name = scanner.next();
                car.setName(name);
            } else if (text.equals("body")) {
                System.out.println("Type price: ");
                Body body = loadBody();
                car.setBody(body);
            } else if (text.equals("date")) {
                System.out.println("Type production date: ");
                LocalDate productionDate = loadProductionDate();
                car.setProductionDate(productionDate);
            } else if (text.equals("brand")) {
                System.out.println("Type new brand: ");
                String brand = scanner.next();
                car.setBrand(brand);
            } else if (text.equals("amount")) {
                System.out.println("Type amount of passengers: ");
                int amount = scanner.nextInt();
                car.setAmountOfPassengers(amount);
            } else if (text.equals("gearbox")) {
                System.out.println("Type gearbox: ");
                Gearbox gearbox = loadGearbox();
                car.setGearbox(gearbox);
            } else if (text.equals("engine")) {
                System.out.println("Type engine capacity");
                Double engineCapacity = scanner.nextDouble();
                car.setEngineCapacity(engineCapacity);
            }
            dao.update(car);
            System.out.println(car + "updated");
        } else {
            System.out.println("Input is incorrect");
        }
    }

    private void handleDeleteCommand() {
        System.out.println("Enter the ID of the car what you want to remove");
        Long id = scanner.nextLong();
        Optional<Car> optionalCar = dao.showCar(id, Car.class);
        if (optionalCar.isPresent()) {
            dao.remove(optionalCar.get());
            System.out.println("Car removed");
        } else {
            System.out.println("Car not found");
        }
    }


    private Gearbox loadGearbox() {
        Gearbox gearbox = null;
        do {
            try {
                System.out.println("Type gearbox: ");
                String unitString = scanner.next();
                gearbox = Gearbox.valueOf(unitString.toUpperCase());
            } catch (IllegalArgumentException iae) {
                System.err.println("Wrong unit, please type unit (manual/auto)");
            }
        } while (gearbox == null);
        return gearbox;
    }

    private Body loadBody() {
        Body body = null;
        do {
            try {
                System.out.println("Type body: ");
                String unitString = scanner.next();
                body = Body.valueOf(unitString.toUpperCase());
            } catch (IllegalArgumentException iae) {
                System.err.println("Wrong unit, please type unit (sedan/cabrio/suv)");
            }
        } while (body == null);
        return body;
    }

    private LocalDate loadProductionDate() {
        LocalDate productionDate = null;
        do {
            try {
                System.out.println("Type production date: ");
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

    private LocalDateTime loadDateWhenUserRent() {
        LocalDateTime firstDate = null;
        do {
            try {
                System.out.println("Type expiry date: ");
                String firstDay = scanner.next();

                firstDate = LocalDateTime.parse(firstDay, FORMATTER);

                LocalDateTime today = LocalDateTime.now();
                if (firstDate.isBefore(today)) {
                    throw new IllegalArgumentException("Date is before today");
                }
            } catch (IllegalArgumentException | DateTimeException iae) {
                firstDate = null;
                System.err.println("Wrong date, please type date in format: yyyy-MM-dd");
            }
        } while (firstDate == null);
        return firstDate;
    }
    private LocalDateTime loadDateWhenUserReturn() {
        LocalDateTime lastDate = null;
        do {
            try {
                System.out.println("Type expiry date: ");
                String firstDay = scanner.next();

                lastDate = LocalDateTime.parse(firstDay, FORMATTER);

                LocalDateTime today = LocalDateTime.now();
                if (lastDate.isBefore(today)) {
                    throw new IllegalArgumentException("Date is before today");
                }
            } catch (IllegalArgumentException | DateTimeException iae) {
                lastDate = null;
                System.err.println("Wrong date, please type date in format: yyyy-MM-dd");
            }
        } while (lastDate == null);
        return lastDate;
    }
}
