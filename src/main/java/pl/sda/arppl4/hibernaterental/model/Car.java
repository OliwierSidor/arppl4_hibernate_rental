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

}
