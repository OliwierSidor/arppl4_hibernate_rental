package pl.sda.arppl4.hibernaterental.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class CarRent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime rentCarDate;
    private String rentName;
    private String rentSurname;
    private LocalDateTime returnCarDate;

    public CarRent(LocalDateTime rentCarDate, String rentName, String rentSurname, Car car) {
        this.rentCarDate = rentCarDate;
        this.rentName = rentName;
        this.rentSurname = rentSurname;
        this.car = car;
    }

    @ManyToOne
    private Car car;
}
