package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.service.FirestationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FirestationController {

    private final FirestationService firestationService;

    @GetMapping("/firestations/all")
    public List<Firestation> getAllFirestations() {
        return firestationService.getAllFirestations();
    }

}
