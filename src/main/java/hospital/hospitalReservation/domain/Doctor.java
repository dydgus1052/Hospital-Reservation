package hospital.hospitalReservation.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    // 연관관계 편의 메소드
    public void setOffice(Office office) {
        this.office = office;
        office.getDoctorList().add(this);
    }
}
