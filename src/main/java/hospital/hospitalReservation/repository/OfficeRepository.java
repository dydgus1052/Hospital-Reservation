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

    public List<Office> findAllByHospitalId(Long hospitalId) {
        return em.createQuery("select o from Office o where o.hospital.id = :id", Office.class)
                .setParameter("id", hospitalId)
                .getResultList();
    }

    public Office findByTwoId(Long hospitalId, Long officeId) {
        return em.createQuery("select o from Office o where o.id = :officeId AND o.hospital.id =:hospitalId", Office.class)
                .setParameter("officeId", officeId)
                .setParameter("hospitalId", hospitalId)
                .getSingleResult();
    }
}
