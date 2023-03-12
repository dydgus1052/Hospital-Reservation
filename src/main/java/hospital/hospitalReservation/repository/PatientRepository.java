package hospital.hospitalReservation.repository;

import hospital.hospitalReservation.domain.Patient;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PatientRepository {

    private final EntityManager em;

    public void savePatient(Patient patient) {
        em.persist(patient);
    }

    public Patient findPatient(Long id) {
        return em.find(Patient.class, id);
    }
}
