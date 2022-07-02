package pl.sda.arppl4.hibernaterental.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String brand;
    private LocalDate productionDate;
    @Enumerated(EnumType.STRING)
    private Body body;
    private int amountOfPassengers;
    @Enumerated(EnumType.STRING)
    private Gearbox gearbox;
    private Double engineCapacity;

    @OneToMany
    private Set<CarRent> rents;

    public Car(String name, String brand, LocalDate productionDate, Body body, int amountOfPassengers, Gearbox gearbox, Double engineCapacity) {
        this.name = name;
        this.brand = brand;
        this.productionDate = productionDate;
        this.body = body;
        this.amountOfPassengers = amountOfPassengers;
        this.gearbox = gearbox;
        this.engineCapacity = engineCapacity;
    }
}
