package hospital.hospitalReservation.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Reservation {

    @Id
    @GeneratedValue
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    private String hospitalName;

    private String officeName;

    private LocalDateTime dateTime;

    // 예약 메소드
    public static Reservation createReservation(Patient patient, Doctor doctor) {
        Reservation reservation = new Reservation();
        reservation.setPatient(patient);
        reservation.setDoctor(doctor);
        reservation.setReservationStatus(ReservationStatus.RESERVE);
        reservation.setDateTime(LocalDateTime.now());

        return reservation;
    }

    // 취소 메소드
    public void cancel() {
        this.setReservationStatus(ReservationStatus.CANCEL);
    }

    // 연관관계 편의 메소드
    public void setPatient(Patient patient) {
        this.patient = patient;
        patient.getReservationList().add(this);
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
        doctor.getReservationList().add(this);

        Office findOffice = doctor.getOffice();
        officeName = findOffice.getOfficeName();
        hospitalName = findOffice.getHospital().getHospitalName();
    }
}
