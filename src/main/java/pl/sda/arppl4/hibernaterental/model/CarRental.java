package pl.sda.arppl4.hibernaterental.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CarRental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String clientName;
    private String clientSurname;

    private LocalDateTime rentDateTime;
    private LocalDateTime returnDateTime;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Car car;

    public CarRental(String clientName, String clientSurname, LocalDateTime rentDateTime, Car car) {
        this.clientName = clientName;
        this.clientSurname = clientSurname;
        this.rentDateTime = rentDateTime;
        this.car = car;
    }
}
