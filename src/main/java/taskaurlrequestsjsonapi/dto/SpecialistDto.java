package taskaurlrequestsjsonapi.dto;

import lombok.Data;

@Data
public class SpecialistDto {
    private int id;
    private String name;
    private String username;
    private String email;
    private AddressDto address;
    private String phone;
    private String website;
    private CompanyDto company;
}

