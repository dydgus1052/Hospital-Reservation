package hospital.hospitalReservation.service;

import hospital.hospitalReservation.domain.Doctor;
import hospital.hospitalReservation.repository.DoctorReposotiry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DoctorService {

    private final DoctorReposotiry doctorReposotiry;

    public Long join(Doctor doctor) {
        doctorReposotiry.save(doctor);
        return doctor.getId();
    }

    public Doctor findOne(Long id) {
        return doctorReposotiry.findOne(id);
    }

    public List<Doctor> findAll() {
        return doctorReposotiry.findAll();
    }
}
