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

import java.util.List;

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
        validateDuplicateDoctor(patient, doctor);

        Reservation reservation = Reservation.createReservation(patient, doctor);
        reservationRepository.save(reservation);

        return reservation.getId();
    }

    private void validateDuplicateDoctor(Patient patient, Doctor doctor) {
        Patient findPatient = patientRepository.findOne(patient.getId());
        Doctor findDoctor = doctorRepository.findOne(doctor.getId());

        List<Reservation> findReservationList = findPatient.getReservationList();
        for (Reservation findReservation : findReservationList) {
            if (findReservation.getDoctor().getId() == findDoctor.getId())
                throw new IllegalStateException("환자는 특정 의사에 대해 하나의 예약만 가질 수 있습니다.");
        }
    }

    public void cancel(Long cancelId) {
        Reservation cancelReservation = reservationRepository.findOne(cancelId);
        cancelReservation.cancel();
    }
}
