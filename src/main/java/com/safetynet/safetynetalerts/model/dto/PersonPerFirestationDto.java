package com.safetynet.safetynetalerts.model.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PersonPerFirestationDto {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private int numberOfAdults;
    private int numberOfChildren;
}
