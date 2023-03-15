package hospital.hospitalReservation.service;

import hospital.hospitalReservation.domain.Hospital;
import hospital.hospitalReservation.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    @Transactional
    public Long join(Hospital hospital) {
        hospitalRepository.save(hospital);
        return hospital.getId();
    }

    public Hospital findOne(Long id) {
        return hospitalRepository.findOne(id);
    }

    public List<Hospital> findAll() {
        return hospitalRepository.findAll();
    }
}
