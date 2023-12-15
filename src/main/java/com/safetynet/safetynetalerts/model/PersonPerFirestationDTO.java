package com.safetynet.safetynetalerts.model;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.utils.JsonReaderUtil;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Data
@Component
public class PersonPerFirestationDTO {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private int numberOfAdults;
    private int numberOfChildren;
}
