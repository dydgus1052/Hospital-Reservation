package hospital.hospitalReservation.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Doctor {

    @Id
    @GeneratedValue
    @Column(name = "doctor_id")
    private Long id;

    private String doctorName;

    private Long experienceYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id")
    private Office office;

    private String hospitalName;

    @OneToMany(mappedBy = "doctor")
    private List<Reservation> reservationList = new ArrayList<>();

    // 연관관계 편의 메소드
    public void setOffice(Office office) {
        this.office = office;
        office.getDoctorList().add(this);
    }

    public void setHospitalName(Office office) {
        this.hospitalName = office.getHospital().getHospitalName();
    }
}
