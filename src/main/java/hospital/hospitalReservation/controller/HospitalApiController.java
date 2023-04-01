package hospital.hospitalReservation.controller;

import hospital.hospitalReservation.domain.*;
import hospital.hospitalReservation.service.HospitalService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class HospitalApiController {
    private final HospitalService hospitalService;

    @PostMapping("/api/hospitals")
    public CreateHospitalResponse saveHospital(@RequestBody @Valid CreateHospitalRequest request) {
        Hospital hospital = new Hospital();
        hospital.setHospitalName(request.getName());
        hospital.setAddress(new Address(request.getCity(), request.getStreet(), request.getZipcode()));

        Long id = hospitalService.join(hospital);

        return new CreateHospitalResponse(id);
    }

    @PutMapping("/api/hospitals/{id}")
    public UpdateHospitalResponse updateHospital(
            @PathVariable("id") Long id
            , @RequestBody @Valid UpdateHospitalRequest request) {

        Address address = new Address(request.getCity(), request.getStreet(), request.getZipcode());
        hospitalService.update(id, address);

        Hospital findHospial = hospitalService.findOne(id);
        return new UpdateHospitalResponse(findHospial.getHospitalName(), findHospial.getAddress());
    }


    @GetMapping("/api/hospitals")
    public List<HospitalWithOfficeAndReservationDto> hospitals() {
        List<Hospital> hospitals = hospitalService.findAll();
        List<HospitalWithOfficeAndReservationDto> result = hospitals.stream()
                .map(hospital -> new HospitalWithOfficeAndReservationDto(hospital))
                .collect(Collectors.toList());

        return result;
    }

    @Data
    static class CreateHospitalRequest {
        @NotEmpty
        private String name;
        private String city;
        private String street;
        private String zipcode;
    }

    @Data
    @AllArgsConstructor
    static class CreateHospitalResponse {
        private Long id;
    }

    @Data
    static class UpdateHospitalRequest {
        private String city;
        private String street;
        private String zipcode;
    }

    @Data
    @AllArgsConstructor
    static class UpdateHospitalResponse {
        private String name;
        private Address address;
    }

    @Data
    @AllArgsConstructor
    static class HospitalWithOfficeAndReservationDto {
        List<OfficeNameDto> officeList;
        List<ReservationDto> reservationList;
        private String name;

        public HospitalWithOfficeAndReservationDto(Hospital hospital) {
            this.name = hospital.getHospitalName();

            officeList = hospital.getOfficeList().stream()
                    .map(office -> new OfficeNameDto(office))
                    .collect(Collectors.toList());

            reservationList = hospital.getReservationList().stream()
                    .map(reservation -> new ReservationDto(reservation))
                    .collect(Collectors.toList());
        }
    }

    @Data
    @AllArgsConstructor
    static class OfficeNameDto {
        private String name;

        public OfficeNameDto(Office office) {
            this.name = office.getOfficeName();
        }
    }

    @Data
    @AllArgsConstructor
    static class ReservationDto {
        private String officeName;
        private String doctorName;
        private String patientName;

        private LocalDateTime localDateTime;
        private ReservationStatus reservationStatus;

        public ReservationDto(Reservation reservation) {
            officeName = reservation.getOfficeName();
            doctorName = reservation.getDoctor().getDoctorName();
            patientName = reservation.getPatient().getPatientName();

            localDateTime = reservation.getDateTime();
            reservationStatus = reservation.getReservationStatus();
        }
    }

}
