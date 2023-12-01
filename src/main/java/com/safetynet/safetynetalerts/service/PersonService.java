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

    //TEST - list all Person content
    public List<Person> getAllPersons() {
        return jsonReaderUtil.getPersonList();
    }

    //Add a new person
    public Person addNewPerson(Person newPerson) {

        List<Person> personList = jsonReaderUtil.getPersonList();
        personList.add(newPerson);

        return newPerson;
    }

    //Update a person's details
    public Person updatePersonInfo(String firstName, String lastName, Person person) {

        Person personToUpdate = jsonReaderUtil.getPersonByName(firstName, lastName);

        if(personToUpdate.getFirstName() != null && personToUpdate.getLastName() != null) {

            String address = person.getAddress();
            if(address != null) {
                personToUpdate.setAddress(address);
            }

            String city = person.getCity();
            if(city != null) {
                personToUpdate.setCity(city);
            }

            String zip = person.getZip();
            if(zip != null) {
                personToUpdate.setZip(zip);
            }

            String phone = person.getPhone();
            if(phone != null) {
                personToUpdate.setPhone(phone);
            }

            String email = person.getEmail();
            if(email != null) {
                personToUpdate.setEmail(email);
            }

            return personToUpdate;

        } else {
            return null;
        }
    }

    //Delete a person
    public void deletePerson(String firstName, String lastName) {

        List<Person> personList = jsonReaderUtil.getPersonList();
        Person personToDelete = jsonReaderUtil.getPersonByName(firstName, lastName);

        personList.remove(personToDelete);
    }
}