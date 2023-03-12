package hospital.hospitalReservation.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

    private String city;
    private String zipcode;

    protected Address() {

    }

    public Address(String city, String zipcode) {
        this.city = city;
        this.zipcode = zipcode;
    }
}
