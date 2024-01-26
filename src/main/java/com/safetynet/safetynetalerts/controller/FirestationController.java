package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.*;
import com.safetynet.safetynetalerts.model.Dtos.PersonForFireAlertDto;
import com.safetynet.safetynetalerts.model.Dtos.PersonPerFirestationDto;
import com.safetynet.safetynetalerts.service.FirestationService;
import com.safetynet.safetynetalerts.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FirestationController {

    private final FirestationService firestationService;
    private final PersonService personService;

    //TESTING
    @GetMapping("/firestations/all")
    public List<Firestation> getAllFirestations() {
        return firestationService.getAllFirestations();
    }


    //CREATE - Add a new firestation
    @PostMapping("/firestation")
    public List<Firestation> addNewFirestation(@RequestBody Firestation newFirestation) {
        return firestationService.addNewFirestation(newFirestation);
    }

    //UPDATE - update firestation details
    @PutMapping("/firestation")
    public Firestation updateFirestationInfo(@RequestParam String address, @RequestBody Firestation firestation) {
        return firestationService.updateFirestationInfo(address, firestation);
    }

    //DELETE - delete a firestation from firestationList
    @DeleteMapping("/firestation")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteFirestation(@RequestParam String address, @RequestParam String station) {
        firestationService.deleteFirestation(address, station);
    }

    //GET - returns list of persons covered by a certain firestation
    @GetMapping("/firestation")
    public List<PersonPerFirestationDto> getPersonPerFirestation(@RequestParam String station) {
        return personService.getPersonPerFirestation(station);
    }

    //GET - returns a list of inhabitants at a specific address and the associated firestation
    @GetMapping("/fire")
    public List<PersonForFireAlertDto> getFireAlert(@RequestParam String address) {
        return personService.getFireAlert(address);
    }

    //GET - returns a list of all households for a specific firestation in case of a flood
    @GetMapping("/flood/stations")
    public List<Household> getFloodAlert(@RequestParam List<String> stationsList) {
        return personService.getFloodAlert(stationsList);
    }
}