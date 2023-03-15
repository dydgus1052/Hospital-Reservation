package hospital.hospitalReservation.repository;

import hospital.hospitalReservation.domain.Patient;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<Patient> findAll() {
        return em.createQuery("select p from Patient p", Patient.class)
                .getResultList();
    }
}
