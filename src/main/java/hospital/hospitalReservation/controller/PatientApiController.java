package hospital.hospitalReservation.controller;

import hospital.hospitalReservation.domain.*;
import hospital.hospitalReservation.service.PatientService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PatientApiController {

    private final PatientService patientService;

    @PostMapping("/api/patients")
    public CreatePatientResponse savePatient(@RequestBody @Valid CreatePatientRequest request) {
        Patient patient = new Patient();
        patient.setPatientName(request.getName());
        patient.setAge(request.getAge());

        Long id = patientService.join(patient);
        return new CreatePatientResponse(id, patient.getPatientName());
    }

    @PutMapping("/api/patients/{id}")
    public UpdatePatientResponse updatePatient(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdatePatientRequest request) {

        patientService.update(id, request.getAge());
        Patient findPatient = patientService.findOne(id);

        return new UpdatePatientResponse(findPatient.getPatientName(), findPatient.getAge());
    }

    @GetMapping("/api/patients")
    public List<PatientWithReservationDto> patients() {
        List<Patient> all = patientService.findAll();

        List<PatientWithReservationDto> result = all.stream()
                .map(patient -> new PatientWithReservationDto(patient))
                .collect(Collectors.toList());

        return result;
    }


    @Data
    @AllArgsConstructor
    static class CreatePatientResponse {
        private Long id;
        private String name;
    }

    @Data
    static class CreatePatientRequest {

        @NotEmpty
        private String name;
        @NotNull
        private Long age;
    }

    @Data
    @AllArgsConstructor
    static class UpdatePatientResponse {
        private String name;
        private Long age;
    }

    @Data
    static class UpdatePatientRequest {
        private Long age;
    }

    @Data
    @AllArgsConstructor
    static class PatientWithReservationDto {
        private String name;
        private Long age;
        private Gender gender;

        private List<ReservationDto> reservationList;

        public PatientWithReservationDto(Patient patient) {
            name = patient.getPatientName();
            age = patient.getAge();
            gender = patient.getGender();

            reservationList = patient.getReservationList().stream()
                    .map(reservation -> new ReservationDto(reservation))
                    .collect(Collectors.toList());
        }
    }

    @Data
    @AllArgsConstructor
    static class ReservationDto {

        private String doctorName;

        private ReservationStatus reservationStatus;

        private String hospitalName;

        private String officeName;

        private LocalDateTime dateTime;

        public ReservationDto(Reservation reservation) {
            doctorName = reservation.getDoctor().getDoctorName();
            hospitalName = reservation.getHospitalName();
            officeName = reservation.getOfficeName();

            reservationStatus = reservation.getReservationStatus();
            dateTime = reservation.getDateTime();
        }
    }
}
