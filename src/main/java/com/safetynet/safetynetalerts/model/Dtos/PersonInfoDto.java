package com.safetynet.safetynetalerts.model.Dtos;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class PersonInfoDto {

    private String firstName;
    private String lastName;
    private String address;
    private int age;
    private String email;
    private List<String> medication;
    private List<String> allergies;

}
