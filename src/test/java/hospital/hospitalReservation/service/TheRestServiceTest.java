package hospital.hospitalReservation.service;

import hospital.hospitalReservation.domain.Doctor;
import hospital.hospitalReservation.domain.Hospital;
import hospital.hospitalReservation.domain.Office;
import hospital.hospitalReservation.repository.DoctorReposotiry;
import hospital.hospitalReservation.repository.HospitalRepository;
import hospital.hospitalReservation.repository.OfficeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class TheRestServiceTest {

    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private OfficeService officeService;
    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private DoctorReposotiry doctorReposotiry;


    @Test
    @Rollback(false)
    public void 병원진료과의사등록() throws Exception {

        //given
        Hospital hospital = new Hospital();
        hospitalService.join(hospital);


        Office officeA = new Office();
        officeA.setHospital(hospital);
        officeService.join(officeA);

        Office officeB = new Office();
        officeB.setHospital(hospital);
        officeService.join(officeB);

        Doctor doctorA = new Doctor();
        doctorA.setOffice(officeA);
        doctorA.setHospitalName(officeA);
        doctorService.join(doctorA);

        Doctor doctorB = new Doctor();
        doctorB.setOffice(officeA);
        doctorB.setHospitalName(officeA);
        doctorService.join(doctorB);

        Doctor doctorC = new Doctor();
        doctorC.setOffice(officeB);
        doctorC.setHospitalName(officeB);
        doctorService.join(doctorC);

        Doctor doctorD = new Doctor();
        doctorD.setOffice(officeB);
        doctorD.setHospitalName(officeB);
        doctorService.join(doctorD);

        // 병원 조회
        Hospital findHospital = hospitalRepository.findOne(hospital.getId());
        Assertions.assertThat(hospital.getId()).isEqualTo(findHospital.getId());
        Assertions.assertThat(findHospital.getOfficeList().size()).isEqualTo(2);

        List<Hospital> allHospital = hospitalRepository.findAll();
        Assertions.assertThat(allHospital.size()).isEqualTo(1);


        // 진료과 조회
        Office findOfficeA = officeRepository.findOne(officeA.getId());
        Assertions.assertThat(findOfficeA.getId()).isEqualTo(officeA.getId());
        Assertions.assertThat(findOfficeA.getHospital().getId()).isEqualTo(findHospital.getId());
        Assertions.assertThat(findOfficeA.getDoctorList().size()).isEqualTo(2);


        List<Office> allOffice = officeRepository.findAll();
        Assertions.assertThat(allOffice.size()).isEqualTo(2);

        // 의사 조회
        Doctor findDoctorA = doctorReposotiry.findOne(doctorA.getId());
        Assertions.assertThat(findDoctorA.getId()).isEqualTo(doctorA.getId());
        Assertions.assertThat(findDoctorA.getOffice().getId()).isEqualTo(findOfficeA.getId());
        Assertions.assertThat(findDoctorA.getHospitalName()).isEqualTo(findHospital.getHospitalName());

        List<Doctor> allDoctor=doctorReposotiry.findAll();
        Assertions.assertThat(allDoctor.size()).isEqualTo(4);
    }
}
