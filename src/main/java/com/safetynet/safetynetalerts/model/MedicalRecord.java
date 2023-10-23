package com.safetynet.safetynetalerts.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MedicalRecord {

    private List<String> allergies = new ArrayList<>();

}
