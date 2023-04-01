package hospital.hospitalReservation.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Hospital {


    @Id
    @GeneratedValue
    @Column(name = "hospital_id")
    private Long id;

    private String hospitalName;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "hospital")
    List<Office> officeList = new ArrayList<>();

    @OneToMany(mappedBy = "hospital")
    List<Reservation> reservationList = new ArrayList<>();
}
