package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.ChildrenPerHousehold;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.PersonInfoDTO;
import com.safetynet.safetynetalerts.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    //TEST - List all Person content
    @GetMapping("/persons/all")
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    //CREATE - Add a new person
    @PostMapping("/person")
    public Person addNewPerson(@RequestBody Person newPerson) {
        return personService.addNewPerson(newPerson);
    }

    //UPDATE - update person's details
    @PutMapping("/person")
    public Person updatePersonInfo(@RequestParam String firstName, @RequestParam String lastName, @RequestBody Person person) {
        return personService.updatePersonInfo(firstName, lastName, person);
    }

    //DELETE - delete a person from personList
    @DeleteMapping("/person")
    public void deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        personService.deletePerson(firstName, lastName);
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

    //GET - returns a list of personal info for each inhabitant
    @GetMapping("/personInfo")
    public List<PersonInfoDTO> getPersonInfo(@RequestParam String firstName, @RequestParam String lastName) {
        return personService.getPersonInfo(firstName, lastName);
    }

    //GET - returns a list of all the city inhabitants' email
    @GetMapping("/communityEmail")
    public List<String> getEmails(@RequestParam String city) {
        return personService.getEmails(city);
    }
}
