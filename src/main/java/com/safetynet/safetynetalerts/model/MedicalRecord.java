package com.safetynet.safetynetalerts.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class MedicalRecord {

    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private List<String> allergies = new ArrayList<>();
    private List<String> medications = new ArrayList<>();

}
