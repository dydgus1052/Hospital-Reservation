package hospital.hospitalReservation.service;

import hospital.hospitalReservation.domain.Patient;
import hospital.hospitalReservation.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PatientService {

    private final PatientRepository patientRepository;

    // 환자 등록
    @Transactional
    public Long join(Patient patient) {
        patientRepository.save(patient);
        return patient.getId();
    }

    // 환자 한명 검색
    public Patient findOne(Long id) {
        return patientRepository.findOne(id);
    }

    // 환자 전체 검색
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }
}
