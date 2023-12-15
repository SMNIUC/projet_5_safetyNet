package com.safetynet.safetynetalerts.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Data
@Component
public class Person {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;
    private MedicalRecord medicalRecord;

    public boolean isChild() {
        LocalDate today = LocalDate.now();
        int age = Period.between(medicalRecord.getBirthdate(), today).getYears();
        return age < 18;
    }
}

