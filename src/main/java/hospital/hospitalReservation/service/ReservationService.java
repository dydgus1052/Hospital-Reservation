package hospital.hospitalReservation.service;

import hospital.hospitalReservation.domain.Doctor;
import hospital.hospitalReservation.domain.Patient;
import hospital.hospitalReservation.domain.Reservation;
import hospital.hospitalReservation.repository.DoctorRepository;
import hospital.hospitalReservation.repository.PatientRepository;
import hospital.hospitalReservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Transactional
    public Long reserve(Long patientId, Long doctorId) {

        Patient patient = patientRepository.findOne(patientId);
        Doctor doctor = doctorRepository.findOne(doctorId);

        Reservation reservation = Reservation.createReservation(patient, doctor);
        reservationRepository.save(reservation);

        return reservation.getId();
    }

    public void cancel(Long cancelId) {
        Reservation cancelReservation = reservationRepository.findOne(cancelId);
        cancelReservation.cancel();
    }
}
