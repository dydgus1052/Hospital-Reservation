package hospital.hospitalReservation.service;

import hospital.hospitalReservation.domain.Doctor;
import hospital.hospitalReservation.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public Long join(Doctor doctor) {
        doctorRepository.save(doctor);
        return doctor.getId();
    }

    public Doctor findOne(Long id) {
        return doctorRepository.findOne(id);
    }

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }
}