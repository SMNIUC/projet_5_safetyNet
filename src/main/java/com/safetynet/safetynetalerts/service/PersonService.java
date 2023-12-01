package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.utils.JsonReaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final JsonReaderUtil jsonReaderUtil;

    //Test - list all Person content
    public List<Person> getAllPersons() {
        return jsonReaderUtil.getPersonList();
    }

    //Add a new person
    public Person addNewPerson(Person newPerson) {

        List<Person> personList = jsonReaderUtil.getPersonList();

        Person person = new Person();

        person.setFirstName(newPerson.getFirstName());
        person.setLastName(newPerson.getLastName());
        person.setAddress(newPerson.getAddress());
        person.setCity(newPerson.getCity());
        person.setZip(newPerson.getZip());
        person.setPhone(newPerson.getPhone());
        person.setEmail(newPerson.getEmail());

        personList.add(person);
        return person;
    }

    public void deletePerson(String firstName, String lastName) {

        List<Person> personList = jsonReaderUtil.getPersonList();

        Person personToDelete = jsonReaderUtil.getPersonByName(firstName, lastName);

        personList.remove(personToDelete);
    }

    //Update a person's details
//    public void updatePersonDetails(Person person) {
//
//        List<Person> personList = jsonReaderUtil.getPersonList();
//
//        if()
//
//    }

    //Delete a person
//    public String deletePerson(String firstName, String lastName) {
//        jsonReaderUtil.getPersonList()
//    }

}