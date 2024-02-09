package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.utils.JsonReaderUtil;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalRecordService
{

    private final JsonReaderUtil jsonReaderUtil;
    private static final Logger log = LogManager.getLogger();


    /**
     * Adds a new Medical Record object to the Medical Record list
     *
     * @param newMedicalRecord - New Medical Record object to add
     * @return                 - Added Medical Record object
     */
    public MedicalRecord addNewMedicalRecord( MedicalRecord newMedicalRecord )
    {
        log.debug( "addNewMedicalRecord()" );
        jsonReaderUtil.getMedicalRecordsList( ).add( newMedicalRecord );
        log.info("New medical record {} added to the list.", newMedicalRecord );

        return newMedicalRecord;
    }


    /**
     * Updates a person's medical record information
     *
     * @param firstName      - First name of the person's medical record to update
     * @param lastName       - Last name of the person's medical record to update
     * @param medicalRecord  - Medical record information with which to update
     * @return               - Updated Medical Record object
     *
     * @throws ResourceNotFoundException
     */
    public MedicalRecord updateMedicalRecord( String firstName, String lastName, MedicalRecord medicalRecord ) throws ResourceNotFoundException
    {
        log.debug( "updateMedicalRecord()" );
        MedicalRecord medicalRecordToUpdate = jsonReaderUtil.getMedicalRecordByName( firstName, lastName );

        if ( medicalRecordToUpdate.getFirstName( ) != null && medicalRecordToUpdate.getLastName( ) != null )
        {

            LocalDate birthdate = medicalRecord.getBirthdate( );
            if ( birthdate != null )
            {
                medicalRecordToUpdate.setBirthdate( birthdate );
            }

            List<String> medications = medicalRecord.getMedications( );
            if ( medications != null )
            {
                medicalRecordToUpdate.setMedications( medications );
            }

            List<String> allergies = medicalRecord.getAllergies( );
            if ( allergies != null )
            {
                medicalRecordToUpdate.setAllergies( allergies );
            }
            log.info( "{} {}'s medical record has been updated with the following information: {}.", firstName, lastName, medicalRecord );

            return medicalRecordToUpdate;
        }
        else
        {
            throw new ResourceNotFoundException( "Medical Record to update not found" );
        }
    }


    /**
     * Deletes the medical record for a specific person
     *
     * @param firstName - First name of the person's medical record to delete
     * @param lastName  - Last name of the person's medical record to delete
     */
    public void deleteMedicalRecord( String firstName, String lastName )
    {
        log.debug( "deleteMedicalRecord()" );
        MedicalRecord medicalRecordToDelete = jsonReaderUtil.getMedicalRecordByName( firstName, lastName );
        jsonReaderUtil.getMedicalRecordsList( ).remove( medicalRecordToDelete );
        log.info( "The medical record for {} {} has been deleted.", firstName, lastName );
    }


    /**
     * Returns a list of all the medical record data available
     *
     * @return - List of all the medical record data available
     */
    public List<MedicalRecord> getAllMedicalRecords( )
    {
        log.debug( "getAllMedicalRecords()" );
        log.info( "All available medical records returned." );

        return jsonReaderUtil.getMedicalRecordsList( );
    }
}
