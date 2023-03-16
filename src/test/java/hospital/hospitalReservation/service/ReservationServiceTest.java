package hospital.hospitalReservation.service;

import hospital.hospitalReservation.domain.*;
import hospital.hospitalReservation.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    @Rollback(false)
    public void 예약() throws Exception {

        Patient patientA = new Patient();
        patientA.setPatientName("환자1");
        patientA.setAge(24L);
        patientA.setGender(Gender.FEMALE);
        patientService.join(patientA);

        Patient patientB = new Patient();
        patientB.setPatientName("환자2");
        patientB.setAge(30L);
        patientB.setGender(Gender.MALE);
        patientService.join(patientB);

        Hospital hospital = new Hospital();
        hospital.setHospitalName("서울대학병원");
        hospitalService.join(hospital);

        Office office = new Office();
        office.setHospital(hospital);
        office.setOfficeName("이비인후과");
        officeService.join(office);

        Doctor doctor = new Doctor();
        doctor.setOffice(office);
        doctor.setHospitalName(office);
        doctor.setExperienceYear(10L);
        doctorService.join(doctor);

        Long reserveId = reservationService.reserve(patientA.getId(), doctor.getId());

        Reservation find = reservationRepository.findOne(reserveId);

        reservationService.cancel(find.getId());
    }
}