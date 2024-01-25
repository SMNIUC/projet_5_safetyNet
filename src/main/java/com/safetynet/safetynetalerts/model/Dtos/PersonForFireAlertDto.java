package com.safetynet.safetynetalerts.model.Dtos;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class PersonForFireAlertDto {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int age;
    private List<String> medicines;
    private List<String> allergies;
    private String firestation;

}
