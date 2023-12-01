package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.Person;
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
}
