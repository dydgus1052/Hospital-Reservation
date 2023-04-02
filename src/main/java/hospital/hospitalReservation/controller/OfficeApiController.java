package hospital.hospitalReservation.controller;

import hospital.hospitalReservation.domain.Doctor;
import hospital.hospitalReservation.domain.Hospital;
import hospital.hospitalReservation.domain.Office;
import hospital.hospitalReservation.service.HospitalService;
import hospital.hospitalReservation.service.OfficeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OfficeApiController {
    private final OfficeService officeService;
    private final HospitalService hospitalService;

    @PostMapping("/api/offices/{id}")
    public CreateOfficeResponse saveOffice(
            @PathVariable("id") Long hospitalId,
            @RequestBody @Valid CreateOfficeRequest request
    ) {
        Hospital findHospital = hospitalService.findOne(hospitalId);

        Office office = new Office();
        office.setHospital(findHospital);
        office.setOfficeName(request.getName());
        office.setTel(request.getTel());

        Long id = officeService.join(office);
        return new CreateOfficeResponse(id);
    }

    @PutMapping("/api/offices/{id}/{officeId}")
    public UpdateOfficeResponse updateOffice(
            @PathVariable("id") Long hospitalId,
            @PathVariable("officeId") Long officeId,
            @RequestBody @Valid UpdateOfficeRequest request
    ) {

        officeService.update(hospitalId, officeId, request.getTel());
        Office findOffice = officeService.findOne(officeId);

        return new UpdateOfficeResponse(findOffice.getHospital().getHospitalName(),
                findOffice.getOfficeName(),
                findOffice.getTel());
    }

    @GetMapping("/api/offices/{id}")
    public List<OfficeWithDoctorDto> offices(
            @PathVariable("id") Long hospitalId
    ) {
        List<Office> offices = officeService.findAllByHospitalId(hospitalId);
        List<OfficeWithDoctorDto> result = offices.stream()
                .map(office -> new OfficeWithDoctorDto(office))
                .collect(Collectors.toList());

        return result;
    }


    @Data
    @AllArgsConstructor
    static class CreateOfficeResponse {
        private Long id;
    }

    @Data
    static class CreateOfficeRequest {
        private String name;
        private String tel;
    }

    @Data
    @AllArgsConstructor
    static class UpdateOfficeResponse {
        private String hospitalName;
        private String officeName;
        private String tel;
    }

    @Data
    static class UpdateOfficeRequest {
        private String tel;
    }

    @Data
    static class OfficeWithDoctorDto {
        private String hospitalName;
        private String officeName;
        private String tel;
        private List<DoctorListDto> doctorList;


        public OfficeWithDoctorDto(Office office) {
            hospitalName = office.getHospital().getHospitalName();
            officeName = office.getOfficeName();
            tel = office.getTel();
            doctorList = office.getDoctorList().stream()
                    .map(doctor -> new DoctorListDto(doctor))
                    .collect(Collectors.toList());
        }
    }

    @Data
    static class DoctorListDto {
        private String name;

        public DoctorListDto(Doctor doctor) {
            name = doctor.getDoctorName();
        }
    }
}
