package pl.sda.arppl4.hibernaterental.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    @Enumerated
    private Body body;
    private int amountOfPassengers;
    @Enumerated
    private Gearbox gearbox;
    private Double engineCapacity;

}
