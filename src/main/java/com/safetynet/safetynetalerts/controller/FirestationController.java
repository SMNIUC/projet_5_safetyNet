package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.service.FirestationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FirestationController {

    private final FirestationService firestationService;

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
}
