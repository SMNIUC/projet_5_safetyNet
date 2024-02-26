package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.*;
import com.safetynet.safetynetalerts.model.dto.PersonForFireAlertDto;
import com.safetynet.safetynetalerts.model.dto.PersonPerFirestationDto;
import com.safetynet.safetynetalerts.service.FirestationService;
import com.safetynet.safetynetalerts.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FirestationController {

    private final FirestationService firestationService;
    private final PersonService personService;

    private static final Logger log = LogManager.getLogger();

    /**
     * Returns the list of all the firestation information available
     *
     * @return - List of all the firestation information available
     */
    @GetMapping("/firestations/all")
    public List<Firestation> getAllFirestations() {
        return firestationService.getAllFirestations();
    }


    /**
     * Creates new Firestation object
     *
     * @param newFirestation - New Firestation object to create
     * @return               - New Firestation object
     */
    @PostMapping("/firestation")
    public Firestation addNewFirestation(@RequestBody Firestation newFirestation) {
        return firestationService.addNewFirestation(newFirestation);
    }


    /**
     * Updates a Firestation object
     *
     * @param address     - Address of the firestation to update
     * @param firestation - Firestation info to update with
     * @return            - Updated Firestation object
     */
    @PutMapping("/firestation")
    public Firestation updateFirestationInfo(@RequestParam String address, @RequestBody Firestation firestation) {
        try
        {
            return firestationService.updateFirestationInfo(address, firestation);
        }
        catch ( ResourceNotFoundException r )
        {
            log.error( r.getMessage() );
            throw r;
        }
    }


    /**
     * Deletes Firestation object
     *
     * @param address - Address of the firestation to delete
     * @param station - Station number of the firestation to delete
     */
    @DeleteMapping("/firestation")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteFirestation(@RequestParam String address, @RequestParam String station) {
        firestationService.deleteFirestation(address, station);
    }


    /**
     * Gets a list of persons covered by a certain firestation
     *
     * @param station - Number of the station to research
     * @return        - List of persons covered by a certain firestation
     */
    @GetMapping("/firestation")
    public List<PersonPerFirestationDto> getPersonPerFirestation(@RequestParam String station) {
        return personService.getPersonPerFirestation(station);
    }


    /**
     * Gets a list of inhabitants at a specific address and the associated firestation
     *
     * @param address - Address to research
     * @return        - List of inhabitants at a specific address and the associated firestation
     */
    @GetMapping("/fire")
    public List<PersonForFireAlertDto> getFireAlert(@RequestParam String address) {
        return personService.getFireAlert(address);
    }


    /**
     * Gets a list of all households for a specific firestation in case of a flood
     *
     * @param stationsList - List of station numbers to research
     * @return             - List of all households for a specific firestation
     */
    @GetMapping("/flood/stations")
    public List<Household> getFloodAlert(@RequestParam List<String> stationsList) {
        return personService.getFloodAlert(stationsList);
    }
}