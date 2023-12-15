package com.safetynet.safetynetalerts.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class PersonInfoDTO {

    private String firstName;
    private String lastName;
    private String address;
    private int age;
    private String email;
    private List<String> medication;
    private List<String> allergies;

}
