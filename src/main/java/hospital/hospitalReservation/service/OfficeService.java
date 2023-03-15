package hospital.hospitalReservation.service;

import hospital.hospitalReservation.domain.Office;
import hospital.hospitalReservation.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OfficeService {

    private final OfficeRepository officeRepository;

    @Transactional
    public Long join(Office office) {
        officeRepository.save(office);
        return office.getId();
    }

    public Office findOne(Long id) {
        return officeRepository.findOne(id);
    }

    public List<Office> findAll() {
        return officeRepository.findAll();
    }
}