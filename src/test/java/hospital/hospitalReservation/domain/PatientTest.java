package hospital.hospitalReservation.domain;

import hospital.hospitalReservation.repository.PatientRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class PatientTest {

    @Autowired
    EntityManager em;
    @Autowired
    private PatientRepository patientRepository;

    @Test
    @Rollback(false)
    public void 환자와병원등록및예약() throws Exception {

        //==================환자등록====================
        Patient patientA = new Patient();
        patientA.setPatientName("patientA");
        patientA.setGender(Gender.MALE);

        Patient patientB = new Patient();
        patientB.setPatientName("patientB");
        patientB.setGender(Gender.MALE);

        patientRepository.savePatient(patientA);
        patientRepository.savePatient(patientB);

        //==================병원등록====================
        Hospital hospitalA = new Hospital();
        hospitalA.setHospitalName("hospitalA");
        em.persist(hospitalA);

        //==================진료과등록====================
        Office officeA = new Office();
        officeA.setOfficeName("officeA");
        officeA.setHospital(hospitalA);
        Office officeB = new Office();
        officeB.setOfficeName("officeB");
        officeB.setHospital(hospitalA);
        em.persist(officeA);
        em.persist(officeB);

        //==================의사등록====================
        Doctor doctorA = new Doctor();
        doctorA.setDoctorName("doctorA");
        doctorA.setOffice(officeA);
        doctorA.setHospitalName(officeA);

        Doctor doctorB = new Doctor();
        doctorB.setDoctorName("doctorB");
        doctorB.setOffice(officeA);
        doctorB.setHospitalName(officeA);

        em.persist(doctorA);
        em.persist(doctorB);

        //==================진료예약====================
        Reservation reservation = new Reservation();
        Reservation reservation1 = reservation.createReservation(patientA, doctorB);
        em.persist(reservation);
        em.persist(reservation1);

        em.flush();
        em.clear();

        em.find(Reservation.class, reservation1.getId());
        reservation1.cancel();
        System.out.println("reservation1.getReservationStatus() = " + reservation1.getReservationStatus());
    }

}