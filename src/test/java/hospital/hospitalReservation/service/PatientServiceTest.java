package hospital.hospitalReservation.service;

import hospital.hospitalReservation.domain.Gender;
import hospital.hospitalReservation.domain.Patient;
import hospital.hospitalReservation.repository.PatientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class PatientServiceTest {

    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientRepository patientRepository;

    @Test
    //@Rollback(false)
    public void 환자등록() throws Exception {

        //given
        Patient patientA = new Patient();
        patientA.setPatientName("patientA");

        //when
        patientService.join(patientA);
        Patient findPatient = patientRepository.findOne(patientA.getId());

        //then
        Assertions.assertThat(patientA).isEqualTo(findPatient);
    }

    @Test
    @Rollback(false)
    public void 환자조회() throws Exception {

        //given
        Patient patientA = new Patient();
        patientA.setPatientName("patientA");

        Patient patientB = new Patient();
        patientB.setPatientName("patientB");

        Patient patientC = new Patient();
        patientC.setPatientName("하리무상");
        patientC.setAge(24L);
        patientC.setGender(Gender.FEMALE);

        patientService.join(patientA);
        patientService.join(patientB);
        patientService.join(patientC);

        //when
        int length = patientService.findAll().size();

        //then
        Assertions.assertThat(length).isEqualTo(3);
    }

}