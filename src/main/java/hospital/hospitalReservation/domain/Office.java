package hospital.hospitalReservation.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Office {

    @Id
    @GeneratedValue
    @Column(name = "office_id")
    private Long id;

    private String officeName;

    private String tel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="hospital_id")
    private Hospital hospital;

    @OneToMany(mappedBy = "office")
    private List<Doctor> doctorList = new ArrayList<>();

    // 연관관계 편의 메소드
    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
        hospital.getOfficeList().add(this);
    }
}
