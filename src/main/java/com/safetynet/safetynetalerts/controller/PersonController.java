package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.Dtos.ChildrenPerHouseholdDto;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.Dtos.PersonInfoDto;
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
public class PersonController {

    private final PersonService personService;

    private static final Logger log = LogManager.getLogger();

    /**
     * Returns the list of all the person information available
     *
     * @return - List of all the person information available
     */
    @GetMapping("/allpersons")
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }


    /**
     * Creates new Person object
     *
     * @param newPerson - New Person object to create
     * @return          - New Person object
     */
    @PostMapping("/person")
    public Person addNewPerson(@RequestBody Person newPerson) {
        return personService.addNewPerson(newPerson);
    }


    /**
     * Updates a Person object
     *
     * @param firstName - First name of the person to update
     * @param lastName  - Last name of the person to update
     * @param person    - New Person object to update with
     * @return          - Updated Person object
     */
    @PutMapping("/person")
    public Person updatePersonInfo(@RequestParam String firstName, @RequestParam String lastName, @RequestBody Person person) {
        try
        {
            return personService.updatePersonInfo(firstName, lastName, person);
        }
        catch ( ResourceNotFoundException r )
        {
            log.error( r.getMessage() );
            throw r;
        }
    }


    /**
     * Deletes a Person object
     *
     * @param firstName - First name of person to delete
     * @param lastName  - Last name of person to delete
     */
    @DeleteMapping("/person")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        personService.deletePerson(firstName, lastName);
    }


    /**
     * Gets a list of children living at a specific address
     *
     * @param address - Address to research
     * @return        - List of children living at a specific address
     */
    @GetMapping("/childAlert")
    public List<ChildrenPerHouseholdDto> getChildrenPerHousehold(@RequestParam String address) {
        return personService.getChildrenPerHousehold(address);
    }


    /**
     * Gets a list of phone numbers per firestation
     *
     * @param station - Number of the firestation to research
     * @return        - List of phone numbers
     */
    @GetMapping("/phoneAlert")
    public List<String> getPhoneNumbersPerFirestation(@RequestParam String station) {
        return personService.getPhoneNumbersPerFirestation(station);
    }


    /**
     * Gets a list of personal info for an inhabitant
     *
     * @param firstName - Inhabitant's first name
     * @param lastName  - Inhabitant's last name
     * @return          - List of personal info for an inhabitant
     */
    @GetMapping("/personInfo")
    public List<PersonInfoDto> getPersonInfo(@RequestParam String firstName, @RequestParam String lastName) {
        return personService.getPersonInfo(firstName, lastName);
    }


    /**
     * Gets a list of all the city inhabitants' email
     *
     * @param city - Name of the city to research
     * @return     - List of all the city inhabitants' email
     */
    @GetMapping("/communityEmail")
    public List<String> getEmails(@RequestParam String city) {
        return personService.getEmails(city);
    }
}
