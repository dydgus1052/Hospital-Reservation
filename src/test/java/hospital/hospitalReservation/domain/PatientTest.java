package hospital.hospitalReservation.domain;

import hospital.hospitalReservation.repository.DoctorReposotiry;
import hospital.hospitalReservation.repository.HospitalRepository;
import hospital.hospitalReservation.repository.OfficeRepository;
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
    @Autowired
    private DoctorReposotiry doctorReposotiry;

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

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

        patientRepository.save(patientA);
        patientRepository.save(patientB);

        //==================병원등록====================
        Hospital hospitalA = new Hospital();
        hospitalA.setHospitalName("hospitalA");
        hospitalA.setAddress(new Address("서울", "강가", "123-123"));

        hospitalRepository.save(hospitalA);

        //==================진료과등록====================
        Office officeA = new Office();
        officeA.setOfficeName("officeA");
        officeA.setHospital(hospitalA);
        Office officeB = new Office();
        officeB.setOfficeName("officeB");
        officeB.setHospital(hospitalA);

        officeRepository.save(officeA);
        officeRepository.save(officeB);

        //==================의사등록====================
        Doctor doctorA = new Doctor();
        doctorA.setDoctorName("doctorA");
        doctorA.setOffice(officeA);
        doctorA.setHospitalName(officeA);

        Doctor doctorB = new Doctor();
        doctorB.setDoctorName("doctorB");
        doctorB.setOffice(officeA);
        doctorB.setHospitalName(officeA);

        doctorReposotiry.save(doctorA);
        doctorReposotiry.save(doctorB);

        //==================진료예약====================
        Reservation reservation = Reservation.createReservation(patientA, doctorB);
        em.persist(reservation);

        em.flush();
        em.clear();

        Reservation find = em.find(Reservation.class, reservation.getId());
        find.cancel();
        System.out.println("reservation1.getReservationStatus() = " + reservation.getReservationStatus());
    }

}