package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.ChildrenPerHousehold;
import com.safetynet.safetynetalerts.model.FireAlert;
import com.safetynet.safetynetalerts.model.PersonInfo;
import com.safetynet.safetynetalerts.model.PersonPerFirestation;
import com.safetynet.safetynetalerts.service.FirestationService;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import com.safetynet.safetynetalerts.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SpecialEventsController {

    private final FirestationService firestationService;
    private final PersonService personService;
    private final MedicalRecordService medicalRecordService;

    //GET - returns list of persons covered by a certain firestation
    @GetMapping("/firestation")
    public List<PersonPerFirestation> getPersonPerFirestation(@RequestParam String station) {
        return personService.getPersonPerFirestation(station);
    }

    //GET - returns a list of children living at a specific address
    @GetMapping("/childAlert")
    public List<ChildrenPerHousehold> getChildrenPerHousehold(@RequestParam String address) {
        return personService.getChildrenPerHousehold(address);
    }

    //GET - returns a list of phone numbers per firestation
    @GetMapping("/phoneAlert")
    public List<String> getPhoneNumbersPerFirestation(@RequestParam String station) {
        return personService.getPhoneNumbersPerFirestation(station);
    }

    //GET - returns a list of inhabitants at a specific address and the associated firestation
    @GetMapping("/fire")
    public List<FireAlert> getFireAlert(@RequestParam String address) {
        return personService.getFireAlert(address);
    }

//    //GET - returns a list of inhabitants at a specific address and the associated firestation
//    @GetMapping("/fire")
//    public List<FireAlert> getFireAlert(@RequestParam String address) {
//        return personService.getFireAlert(address);
//    }

    //GET - returns a list of personal info for each inhabitant
    @GetMapping("/personInfo")
    public List<PersonInfo> getPersonInfo(@RequestParam String firstName, @RequestParam String lastName) {
        return personService.getPersonInfo(firstName, lastName);
    }

    //GET - returns a list of all the city inhabitants' email
    @GetMapping("/communityEmail")
    public List<String> getEmails(@RequestParam String city) {
        return personService.getEmails(city);
    }
}
