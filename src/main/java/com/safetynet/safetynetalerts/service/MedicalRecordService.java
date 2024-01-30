package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.utils.JsonReaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {

    private JsonReaderUtil jsonReaderUtil;
    private List<MedicalRecord> medicalRecordList;

    @Autowired
    public MedicalRecordService(JsonReaderUtil jsonReaderUtil) {
        this.jsonReaderUtil = jsonReaderUtil;
        medicalRecordList = jsonReaderUtil.getMedicalRecordsList();
    }

    //TEST - list all MedicalRecord content
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordList;
    }

    //Add a new medical record
    public List<MedicalRecord> addNewMedicalRecord(MedicalRecord newMedicalRecord) {

        medicalRecordList.add(newMedicalRecord);
        return medicalRecordList;
    }

    //Update a medical record
    public MedicalRecord updateMedicalRecord(String firstName, String lastName, MedicalRecord medicalRecord) {

        MedicalRecord medicalRecordToUpdate = jsonReaderUtil.getMedicalRecordByName(firstName, lastName);

        if(medicalRecordToUpdate.getFirstName() != null && medicalRecordToUpdate.getLastName() != null) {

            LocalDate birthdate = medicalRecord.getBirthdate();
            if(birthdate != null) {
                medicalRecordToUpdate.setBirthdate(birthdate);
            }

            List<String> medications = medicalRecord.getMedications();
            if(medications != null) {
                medicalRecordToUpdate.setMedications(medications);
            }

            List<String> allergies = medicalRecord.getAllergies();
            if(allergies != null) {
                medicalRecordToUpdate.setAllergies(allergies);
            }

            return medicalRecordToUpdate;

        } else {
            return null;
        }
    }

    //Delete a person
    public void deleteMedicalRecord(String firstName, String lastName) {

        MedicalRecord medicalRecordToDelete = jsonReaderUtil.getMedicalRecordByName(firstName, lastName);
        medicalRecordList.remove(medicalRecordToDelete);
    }
}
