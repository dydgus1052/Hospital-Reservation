package hospital.hospitalReservation.repository;

import hospital.hospitalReservation.domain.Office;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OfficeRepository {

    private final EntityManager em;

    public void save(Office office) {
        em.persist(office);
    }

    public Office findOne(Long id) {
        return em.find(Office.class, id);
    }

    public List<Office> findAll() {
        return em.createQuery("select o from Office o", Office.class).
                getResultList();
    }
}
