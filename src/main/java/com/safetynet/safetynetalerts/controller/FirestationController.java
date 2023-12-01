package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.FirestationService;
import com.safetynet.safetynetalerts.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FirestationController {

    private final FirestationService firestationService;
    private final PersonService personService;

    //TEST - List all Firestation content
    @GetMapping("/firestations/all")
    public List<Firestation> getAllFirestations() {
        return firestationService.getAllFirestations();
    }

    //CREATE - Add a new firestation
    @PostMapping("/firestation")
    public Firestation addNewFirestation(@RequestBody Firestation newFirestation) {
        return firestationService.addNewFirestation(newFirestation);
    }

    //UPDATE - update firestation details
    @PutMapping("/firestation")
    public Firestation updateFirestationInfo(@RequestParam String address, @RequestBody Firestation firestation) {
        return firestationService.updateFirestationInfo(address, firestation);
    }

    //DELETE - delete a firestation from firestationList
    @DeleteMapping("/firestation")
    public void deleteFirestation(@RequestParam String address, @RequestParam String station) {
        firestationService.deleteFirestation(address, station);
    }

    //GET - returns list of persons covered by a certain firestation
    @GetMapping("/firestation")
    public List<Person> getPersonPerFirestation(@RequestParam String station) {
        return personService.getPersonPerFirestation(station);
    }
}
