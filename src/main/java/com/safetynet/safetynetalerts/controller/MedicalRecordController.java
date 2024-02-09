package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    private static final Logger log = LogManager.getLogger();

    /**
     *  Returns the list of all the Medical Record information available
     *
     * @return - List of all the Medical Record information available
     */
    @GetMapping("/medicalrecords/all")
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordService.getAllMedicalRecords();
    }


    /**
     * Creates new Medical Record object
     *
     * @param newMedicalRecord - New Medical Record object to create
     * @return                 - Added Medical Record object
     */
    @PostMapping("/medicalRecord")
    public MedicalRecord addNewMedicalRecord(@RequestBody MedicalRecord newMedicalRecord) {
        return medicalRecordService.addNewMedicalRecord(newMedicalRecord);
    }

    /**
     * Updates a Medical Record object
     *
     * @param firstName      - First name of the person's medical record to update
     * @param lastName       - Last name of the person's medical record to update
     * @param medicalRecord  - Medical Record info to update with
     * @return               - Updated Medical Record object
     */
    @PutMapping("/medicalRecord")
    public MedicalRecord updateMedicalRecord(@RequestParam String firstName, @RequestParam String lastName, @RequestBody MedicalRecord medicalRecord) {
        try
        {
            return medicalRecordService.updateMedicalRecord(firstName, lastName, medicalRecord);
        }
        catch ( ResourceNotFoundException r )
        {
            log.error( r.getMessage() );
            throw r;
        }
    }


    /**
     * Deletes a Medical Record object
     *
     * @param firstName - First name of the person's medical record to delete
     * @param lastName  - Last name of the person's medical record to delete
     */
    @DeleteMapping("/medicalRecord")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
        medicalRecordService.deleteMedicalRecord(firstName, lastName);
    }
}
