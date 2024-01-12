package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    //CREATE - Add a new medical record
    @PostMapping("/medicalRecord")
    public MedicalRecord addNewMedicalRecord(@RequestBody MedicalRecord newMedicalRecord) {
        return medicalRecordService.addNewMedicalRecord(newMedicalRecord);
    }

    //UPDATE - update medical record details
    @PutMapping("/medicalRecord")
    public MedicalRecord updateMedicalRecord(@RequestParam String firstName, @RequestParam String lastName, @RequestBody MedicalRecord medicalRecord) {
        return medicalRecordService.updateMedicalRecord(firstName, lastName, medicalRecord);
    }

    //DELETE - delete a medical record from medicalRecordList
    @DeleteMapping("/medicalRecord")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
        medicalRecordService.deleteMedicalRecord(firstName, lastName);
    }
}
