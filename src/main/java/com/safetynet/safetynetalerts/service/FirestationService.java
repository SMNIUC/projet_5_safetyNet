package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.utils.JsonReaderUtil;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FirestationService
{

    private static final Logger log = LogManager.getLogger();
    private final JsonReaderUtil jsonReaderUtil;


    /**
     * Adds a new firestation to the firestation list
     *
     * @param newFirestation - New firestation object
     * @return               - New added Firestation object
     */
    public Firestation addNewFirestation( Firestation newFirestation )
    {
        log.debug( "addNewFirestation()" );
        jsonReaderUtil.getFirestationList().add( newFirestation );
        log.info("New firestation {} added to the list.", newFirestation );

        return newFirestation;
    }


    /**
     * Updates a firestation information based on that firestation's address and number
     *
     * @param address      - Address of the firestation to update
     * @param firestation  - Number of the firestation to update
     * @return             - Returns updated Firestation object
     *
     * @throws ResourceNotFoundException
     */
    public Firestation updateFirestationInfo( String address, Firestation firestation ) throws ResourceNotFoundException
    {
        log.debug( "updateFirestationInfo()" );
        Firestation firestationToUpdate = jsonReaderUtil.getFirestationByAddress( address );

        if ( firestationToUpdate.getAddress( ) != null )
        {

            String station = firestation.getStation( );
            if ( station != null )
            {
                firestationToUpdate.setStation( station );
            }
            log.info( "Firestation at {} was updated with the following information: {}.", address, firestation );

            return firestationToUpdate;
        }
        else
        {
            throw new ResourceNotFoundException( "Firestation to update not found" );
        }
    }


    /**
     * Deletes all the information for a specific firestation
     *
     * @param address - Address of the firestation to delete
     * @param station - Station number of the firestation to delete
     */
    public void deleteFirestation( String address, String station )
    {
        log.debug( "deleteFirestation()" );
        if ( station != null )
        {
            Firestation firestationToDelete = jsonReaderUtil.getFirestationByStation( station );
            jsonReaderUtil.getFirestationList().remove( firestationToDelete );
        }

        if ( address != null )
        {
            Firestation firestationToDelete = jsonReaderUtil.getFirestationByAddress( address );
            jsonReaderUtil.getFirestationList().remove( firestationToDelete );
        }
    }


    /**
     * Returns a list of all the firestation data available
     *
     * @return - List of all the firestation data available
     */
    public List<Firestation> getAllFirestations( )
    {
        log.debug( "getAllFirestations()" );

        return jsonReaderUtil.getFirestationList();
    }

 }
