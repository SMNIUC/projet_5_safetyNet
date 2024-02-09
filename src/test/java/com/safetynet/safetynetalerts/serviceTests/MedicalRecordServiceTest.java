package com.safetynet.safetynetalerts.serviceTests;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MedicalRecordServiceTest {


    @Autowired
    private MedicalRecordService service;

    private static MedicalRecord medicalRecordTest;

    @BeforeAll
    static void setUp() {
        medicalRecordTest = new MedicalRecord();
        MedicalRecord medicalRecordTest = new MedicalRecord();
        medicalRecordTest.setFirstName("Reginold");
        medicalRecordTest.setLastName("Walker");
        List<String> medications = new ArrayList<>();
        medications.add("omeprazol");
        medicalRecordTest.setMedications(medications);
        List<String> allergies = new ArrayList<>();
        allergies.add("peanuts");
        medicalRecordTest.setAllergies(allergies);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthday = LocalDate.parse("05/06/2010", formatter);
        medicalRecordTest.setBirthdate(birthday);
    }

    @Test
    void addNewMedicalRecordTest() {

        MedicalRecord medicalRecord = service.addNewMedicalRecord(medicalRecordTest);
        assertThat(medicalRecord).isEqualTo(medicalRecordTest);
    }

    @Test
    void updateMedicalRecordTest() {

        MedicalRecord medicalRecordToUpdate = new MedicalRecord();
        medicalRecordToUpdate.setFirstName("Reginold");
        medicalRecordToUpdate.setLastName("Walker");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        medicalRecordToUpdate.setBirthdate(LocalDate.parse("05/06/2010", formatter));
        List<String> medications = new ArrayList<>();
        medications.add("omeprazol");
        medicalRecordToUpdate.setMedications(medications);
        List<String> allergies = new ArrayList<>();
        allergies.add("peanuts");
        medicalRecordToUpdate.setAllergies(allergies);

        MedicalRecord medicalRecordTest = service.updateMedicalRecord("Reginold", "Walker", medicalRecordToUpdate);

        assertThat(medicalRecordTest).isEqualTo(medicalRecordToUpdate);
    }

    @Test
    void deleteMedicalRecordTest() {

        service.deleteMedicalRecord("Reginold", "Walker");
        assertThat(service.getAllMedicalRecords()).isNotEmpty().doesNotContain(medicalRecordTest);
    }
}
