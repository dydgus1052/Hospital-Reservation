package hospital.hospitalReservation.domain;

import hospital.hospitalReservation.repository.PatientRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PatientTest {

    @Autowired
    EntityManager em;
    @Autowired
    private PatientRepository patientRepository;

    @Test
    @Rollback(false)
    public void 환자정보등록() throws Exception {
        Patient patient = new Patient();
        patient.setPatientName("userA");
        patient.setGender(Gender.MALE);

        patientRepository.savePatient(patient);

        Patient findPatient = patientRepository.findPatient(patient.getId());


    }

    @Test
    @Rollback(false)
    public void 병원등록() {
        Hospital hospitalA = new Hospital();
        hospitalA.setHospitalName("hospitalA");

        Hospital hospitalB = new Hospital();
        hospitalB.setHospitalName("hospitalB");

        Office office = new Office();
        office.setOfficeName("officeA");

        office.setHospital(hospitalB);

        Doctor doctor = new Doctor();
        doctor.setDoctorName("doctorA");
        doctor.setOffice(office);

        em.persist(hospitalA);
        em.persist(hospitalB);
        em.persist(office);
        em.persist(doctor);
    }
}