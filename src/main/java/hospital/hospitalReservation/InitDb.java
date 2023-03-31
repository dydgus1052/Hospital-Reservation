package hospital.hospitalReservation;

import hospital.hospitalReservation.domain.*;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {
            Patient patient1 = createPatient("patient1", 24L, Gender.FEMALE);
            Patient patient2 = createPatient("patient2", 29L, Gender.FEMALE);
            Patient patient3 = createPatient("patient3", 19L, Gender.MALE);
            Patient patient4 = createPatient("patient4", 15L, Gender.MALE);
            em.persist(patient1);
            em.persist(patient2);
            em.persist(patient3);
            em.persist(patient4);

            Hospital hospital1 = createHospital("hospital1", "안양시", "시민대로1", "1111");
            em.persist(hospital1);

            Office office1 = createOffice("이비인후과", "031-1111-1111", hospital1);
            Office office2 = createOffice("내과", "031-2222-2222", hospital1);
            em.persist(office1);
            em.persist(office2);

            Doctor doctor1 = createDoctor("doctor1", office1, 11L);
            Doctor doctor2 = createDoctor("doctor2", office2, 6L);
            Doctor doctor3 = createDoctor("doctor3", office1, 21L);
            Doctor doctor4 = createDoctor("doctor4", office2, 15L);
            em.persist(doctor1);
            em.persist(doctor2);
            em.persist(doctor3);
            em.persist(doctor4);

            Reservation reservation1 = Reservation.createReservation(patient1, doctor4);
            Reservation reservation2 = Reservation.createReservation(patient2, doctor3);
            Reservation reservation3 = Reservation.createReservation(patient3, doctor2);
            Reservation reservation4 = Reservation.createReservation(patient4, doctor1);
            Reservation reservation5 = Reservation.createReservation(patient1, doctor3);
            Reservation reservation6 = Reservation.createReservation(patient1, doctor2);
            Reservation reservation7 = Reservation.createReservation(patient1, doctor1);
            em.persist(reservation1);
            em.persist(reservation2);
            em.persist(reservation3);
            em.persist(reservation4);
            em.persist(reservation5);
            em.persist(reservation6);
            em.persist(reservation7);
        }

        private Patient createPatient(String name, Long age, Gender gender) {
            Patient patient = new Patient();
            patient.setPatientName(name);
            patient.setAge(age);
            patient.setGender(gender);

            return patient;
        }

        private Hospital createHospital(String name, String city, String street, String zipcode) {
            Hospital hospital = new Hospital();
            hospital.setHospitalName(name);
            hospital.setAddress(new Address(city, street, zipcode));

            return hospital;
        }

        private Office createOffice(String name, String tel, Hospital hospital) {
            Office office = new Office();
            office.setOfficeName(name);
            office.setTel(tel);
            office.setHospital(hospital);

            return office;
        }

        private Doctor createDoctor(String name, Office office, Long year) {
            Doctor doctor = new Doctor();
            doctor.setDoctorName(name);
            doctor.setOffice(office);
            doctor.setHospitalName(office);
            doctor.setExperienceYear(year);

            return doctor;
        }
    }
}
