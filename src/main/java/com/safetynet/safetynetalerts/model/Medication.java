package com.safetynet.safetynetalerts.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Medication {

    private String medicineName;
    private String dosage;

}
