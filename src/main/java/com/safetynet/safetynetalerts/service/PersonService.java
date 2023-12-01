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

        personList.add(newPerson);

        return newPerson;
    }

//    //Update a person's details
//    public Person updatePersonInfo(String firstName, String lastName, Person person) {
//
//        List<Person> personList = jsonReaderUtil.getPersonList();
//
//        Person personToUpdate = jsonReaderUtil.getPersonByName(firstName, lastName);
//
//
//
//    }

    //Delete a person
    public void deletePerson(String firstName, String lastName) {

        List<Person> personList = jsonReaderUtil.getPersonList();

        Person personToDelete = jsonReaderUtil.getPersonByName(firstName, lastName);

        personList.remove(personToDelete);
    }


}